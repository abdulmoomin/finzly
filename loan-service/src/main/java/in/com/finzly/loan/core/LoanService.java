package in.com.finzly.loan.core;

import in.com.finzly.loan.core.exception.CoreException;
import in.com.finzly.loan.core.exception.InvalidCustomerIDException;
import in.com.finzly.loan.core.exception.InvalidPaymentIDException;
import in.com.finzly.loan.repo.LoanRepository;
import in.com.finzly.loan.repo.PaymentRepository;
import in.com.finzly.loan.repo.domain.Loan;
import in.com.finzly.loan.repo.domain.Payment;
import in.com.finzly.loan.web.dto.LoanApprovalPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class LoanService {
    @Autowired
    LoanRepository loanRepository;

    @Autowired
    PaymentRepository paymentRepository;


    /**
     * Saves the loan details in the DB.
     * @param loanDetails - loan details
     * @return - loan
     * @throws CoreException -throws exception for various errors condition
     */
    public Loan saveLoanDetails(Loan loanDetails)
            throws CoreException
    {

        UUID random = UUID.randomUUID();
        String valueOfOrgUuid = random.toString();
        loanDetails.setCustomerID(valueOfOrgUuid);
        loanDetails.setTradeDate(LocalDate.now());
        loanDetails.setLoanStartDate(LocalDate.now());
        loanDetails.setMaturityDate(LocalDate.now());
        loanDetails.setLoanStatus("Pending");
        return loanRepository.save(loanDetails);

    }

    /**
     * update the loan details in the DB.
     * @param customerID - loan details
     * @param loanPay - loan details
     * @return - loan
     * @throws CoreException -throws exception for various errors condition
     */
    public Loan loanApprovalByCustomerID(String customerID,
                                      LoanApprovalPayload loanPay)
            throws CoreException
    {

        Loan loanDetails = loanRepository.
                findByCustomerID(customerID).orElseThrow(()->
                new InvalidCustomerIDException(
                        "CustomerID not found",
                        "Please provide a valid CustomerID",
                        "Approval transaction", "LoanService"));


        double interestRate = loanDetails.getInterestRate();
        double loanAmount = loanDetails.getLoanAmount();
        long paymentTerm = loanDetails.getPaymentTerm();
        String paymentFrequency = loanDetails.getPaymentfrequency();
        long payFrequencyDays = getPaymentFrequencyDays(paymentFrequency);
        long noOfDaysBetween = ChronoUnit.DAYS.between(loanPay.getLoanStartDate(),
                loanPay.getMaturityDate());
        log.info("##noOfDaysBetween :"+noOfDaysBetween);
        log.info("##payFrequencyDays :"+payFrequencyDays);
        long numOfPayment = noOfDaysBetween/payFrequencyDays;
        long roundOff = (long) Math.round(numOfPayment * 100) / 100;
        log.info("##roundOff#"+roundOff);

        if(roundOff<1){
            throw new InvalidCustomerIDException(
                    "MaturityDate should be greater than payment " +
                            "frequency "+paymentFrequency,
                    "MaturityDate should be greater than payment " +
                            "frequency "+paymentFrequency,
                    "Approval transaction", "LoanService");
        }

        loanDetails.setLoanStartDate(loanPay.getLoanStartDate());
        loanDetails.setMaturityDate(loanPay.getMaturityDate());
        loanDetails.setTradeDate(loanPay.getLoanStartDate());
        loanDetails.setLoanStatus("Approved");
        Loan loan = loanRepository.save(loanDetails);

        double principalAmount = 0;
        if(paymentTerm == 2) { //interest only: 1 , even principal :2
            principalAmount = (double) Math.round((loanAmount/roundOff)*100)/100;
            log.info("##principalAmount#"+principalAmount);
        }

        for(int i =1;i<=roundOff;i++){
            Payment payment = new Payment();
            UUID random = UUID.randomUUID();
            String valueOfOrgUuid = random.toString();
            payment.setPaymentID(valueOfOrgUuid);
            payment.setCustomerID(customerID);
            LocalDate loanStDate =  loan.getLoanStartDate();
            LocalDate addPaymentDate =  loanStDate.plusDays(i*payFrequencyDays);
            payment.setPaymentDate(addPaymentDate);
            payment.setPaymentAmount(loanAmount);
            payment.setPrincipalAmount(principalAmount);
            double projectIntrest = 0;
            if(paymentTerm == 2) { //even principal
                projectIntrest = (loanAmount - ((i - 1) * principalAmount)) / 100*interestRate;

            } else {
                projectIntrest = loanAmount * interestRate /100;
            }

            projectIntrest = (double) Math.round(projectIntrest*100)/100;
            log.info("##projectIntrest#"+projectIntrest);
            payment.setProjectedinterest(projectIntrest);
            payment.setPaymentStatus("PROJECTED");
            paymentRepository.save(payment);
        }

        return loan;

    }

    public long getPaymentFrequencyDays(String paymentFrequency){
        long paymentFrequence = 0l;
        switch (paymentFrequency) {
            case "Monthly":
                paymentFrequence = 30l;
                break;
            case "Quarterly":
                paymentFrequence = 90l;
                break;
            case "HalfYearly":
                paymentFrequence = 180l;
                break;
            case "Yearly":
                paymentFrequence = 380l;
                break;
            default:
                paymentFrequence = 30l;
        }
        log.info("##days#"+paymentFrequence);
        return paymentFrequence;

    }

    /**
     * update the loan details in the DB.
     * @param customerID - loan details
     * @return - loan
     * @throws CoreException -throws exception for various errors condition
     */
    public Loan loanRejectedByCustomerID(String customerID)
            throws CoreException
    {
        Loan loanDetails = loanRepository.
                findByCustomerID(customerID).orElseThrow(()->
                new InvalidCustomerIDException(
                        "CustomerID not found",
                        "Please provide a valid CustomerID",
                        "Approval transaction", "LoanService"));
        loanDetails.setLoanStartDate(LocalDate.now());
        loanDetails.setMaturityDate(LocalDate.now());
        loanDetails.setTradeDate(LocalDate.now());
        loanDetails.setLoanStatus("Rejected");
        return loanRepository.save(loanDetails);

    }

    /**
     * To find all the records in the DB.
     * @return the list of loan customers in DB
     */
    public List<Loan> findAll()
    {
        return (List<Loan>) loanRepository.findAll();
    }

    /**
     * To find all the records in the DB.
     * @param customerID - loan details
     * @return - payment
     * @throws CoreException -throws exception for various errors condition
     */
    public List<Payment> getPaymentForCustomer(String customerID)
            throws CoreException
    {
        return  paymentRepository.findByCustomerID(customerID);
    }

    /**
     * To find all the records in the DB.
     * @param paymentID - payment details
     * @return - payment
     * @throws CoreException -throws exception for various errors condition
     */
    public Payment getPayment(String paymentID)
            throws CoreException
    {
        return paymentRepository.
                findByPaymentID(paymentID).orElseThrow(()->
                new InvalidPaymentIDException(
                        "PaymentID not found",
                        "Please provide a valid paymentID",
                        "Approval transaction", "LoanService"));
    }

    /**
     * To update payment details in the DB.
     * @param paymentID - payment details
     * @return - payment
     * @throws CoreException -throws exception for various errors condition
     */
    public Payment updateStatus(String paymentID)
            throws CoreException
    {
        Payment paymentDetails = paymentRepository.
                findByPaymentID(paymentID).orElseThrow(()->
                new InvalidPaymentIDException(
                        "PaymentID not found",
                        "Please provide a valid paymentID",
                        "Approval transaction", "LoanService"));

        paymentDetails.setPaymentStatus("PAID");
        return  paymentRepository.save(paymentDetails);
    }

    /**
     * To update paymentstatus in the DB.
     * Currentdate equals paymentdate to update status AWAITINGPAYMENT.
     */
    @Scheduled(cron = "* * 1 * * ?")
    public void scheduleUpdatePaymentStatus() {
        log.info(
                "ScheduleUpdatePaymentStatus - " + System.currentTimeMillis() / 1000);

        List<Payment> paymentList =(List<Payment>) paymentRepository.findAll();
        LocalDate today = LocalDate.now();

            for(Payment payment:paymentList) {
                int diff = today.compareTo(payment.getPaymentDate());
                if (diff > 0) {
                    log.info("###"+diff);
                    payment.setPaymentStatus("AWAITINGPAYMENT");
                }
                paymentRepository.save(payment);
            }
    }


}
