package in.com.finzly.loan.web;

import in.com.finzly.loan.core.LoanService;
import in.com.finzly.loan.core.exception.CoreException;
import in.com.finzly.loan.core.exception.ApiError;
import in.com.finzly.loan.repo.domain.Loan;
import in.com.finzly.loan.repo.domain.Payment;
import in.com.finzly.loan.web.dto.LoanApprovalPayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * This class implements HTTP CRUD operations for loanservice and
 * the records are inserted into
 * {@link in.com.finzly.loan.repo.domain.Loan}
 * {@link in.com.finzly.loan.repo.domain.Payment}
 * repository respectively.
 */

@RestController
@RequestMapping("/loan")
@Slf4j
public class LoanController {
    @Autowired
    LoanService loanService;

    /**
     * API to save an loan in DB.
     * @param loanDetails -  sent as an input
     * @return loan like customer id
     * @throws CoreException -throws exception for various errors condition
     */
    @Operation
            (summary = "The loan details are stored into the db ")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Loan details saved Successfully",
                    content = @Content(schema = @Schema(
                            implementation = ApiError.class))),
            @ApiResponse(responseCode = "400",
                    description = "Bad request like json parse error",
                    content = @Content(schema = @Schema(
                            implementation = ApiError.class))),
            @ApiResponse(
                    responseCode = "406",
                    description = "Customer Name should not be null",
                    content = @Content(schema = @Schema(
                            implementation = ApiError.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(schema = @Schema(
                            implementation = ApiError.class)))})
    @PostMapping
    public Loan saveLoanDetails(@Valid @RequestBody Loan loanDetails)
            throws CoreException

    {
        log.info("INSIDE SAVE LOAN SERVICE ");
        return loanService.saveLoanDetails(loanDetails);
    }

    /**
     * API to Update loan details in DB.
     * @param customerID - customer ID is sent as an input
     * @param loanApprovalPayload - start date and maturity date as an input
     * @return loan - loan details
     * @throws CoreException -throws exception for various errors condition
     */
    @Operation(summary = "Loan details are approved to provide customerID " +
            "into the db")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Loan details updated successfully",
                    content = @Content(schema = @Schema(
                            implementation = ApiError.class))),
            @ApiResponse(responseCode = "400",
                    description = "Bad request like json parse error",
                    content = @Content(schema = @Schema(
                            implementation = ApiError.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Invalid customerID",
                    content = @Content(schema = @Schema(
                            implementation = ApiError.class))),
            })
    @PutMapping("/approval/{customerID}")
    public Loan loanApprovalByCustomerID(@PathVariable String customerID,
                   @Valid @RequestBody LoanApprovalPayload loanApprovalPayload)
            throws CoreException
    {
        log.info("INSIDE LOAN APPROVAL SERVICE ");
        return loanService
                .loanApprovalByCustomerID(customerID,loanApprovalPayload);
    }

    /**
     * API to Update loan details in DB.
     * @param customerID - customer ID is sent as an input
     * @return loan - loan details
     * @throws CoreException -throws exception for various errors condition
     */
    @Operation(summary = "Loan details are rejected to provide customerID " +
            "into the db")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Loan details updated successfully",
                    content = @Content(schema = @Schema(
                            implementation = ApiError.class))),
            @ApiResponse(responseCode = "400",
                    description = "Bad request like json parse error",
                    content = @Content(schema = @Schema(
                            implementation = ApiError.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Invalid customerID",
                    content = @Content(schema = @Schema(
                            implementation = ApiError.class))),
    })
    @PutMapping("/rejected/{customerID}")
    public Loan loanRejectedByCustomerID(@PathVariable String customerID)
            throws CoreException
    {
        log.info("INSIDE LOAN REJECTED SERVICE ");
        return loanService
                .loanRejectedByCustomerID(customerID);
    }



    /**
     * API to get all the loan user from the DB.
     * @return list of loan user present in the DB.
     * @throws CoreException -throws exception for various errors condition
     */
    @Operation
            (summary = "Used to retrieve the all loan user details from the db")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Loan details retrieved successfully",
                    content = @Content(schema = @Schema(
                            implementation = ApiError.class)))})
    @GetMapping
    public ResponseEntity<List<Loan>> getALLLoanCustomer()
            throws CoreException
    {
        log.info("INSIDE GET LOAN SERVICE ");
        return ResponseEntity.ok(loanService.findAll());
    }

    /**
     * API to get the payment from the DB.
     * @return list of payment present in the DB.
     * @throws CoreException -throws exception for various errors condition
     */
    @Operation
            (summary = "Used to retrieve the payment details from the db")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Payment details retrieved successfully",
                    content = @Content(schema = @Schema(
                            implementation = ApiError.class)))})
    @GetMapping("/customer/payment/{customerID}")
    public ResponseEntity<List<Payment>> getPaymentForCustomer(
            @PathVariable String customerID) throws CoreException
    {
        log.info("INSIDE GET PAYMENT CUSTOMER SERVICE ");
        return ResponseEntity.ok(loanService.getPaymentForCustomer(customerID));
    }

    /**
     * API to get the payment from the DB.
     * @return list of payment present in the DB.
     * @throws CoreException -throws exception for various errors condition
     */
    @Operation
            (summary = "Used to retrieve the payment details from the db")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Payment details retrieved successfully",
                    content = @Content(schema = @Schema(
                            implementation = ApiError.class)))})
    @GetMapping("/payment/{paymentID}")
    public ResponseEntity<Payment> getPayment(
            @PathVariable String paymentID) throws CoreException
    {
        log.info("INSIDE GET PAYMENT SERVICE ");
        return ResponseEntity.ok(loanService.getPayment(paymentID));
    }

    /**
     * API to update status the payment into the DB.
     * @return payment present in the DB.
     */
    @Operation
            (summary = "Payment details update into the db")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Payment details update successfully",
                    content = @Content(schema = @Schema(
                            implementation = ApiError.class)))})
    @PutMapping("/payment/paid/{paymentID}")
    public ResponseEntity<Payment> updateStatus(
            @PathVariable String paymentID) throws CoreException
    {
        log.info("INSIDE PAYMENT UPDATE STATUS SERVICE ");
        return ResponseEntity.ok(loanService.updateStatus(paymentID));
    }

}
