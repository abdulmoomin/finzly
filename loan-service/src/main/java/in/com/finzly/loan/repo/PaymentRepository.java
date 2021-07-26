package in.com.finzly.loan.repo;

import in.com.finzly.loan.repo.domain.Payment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends CrudRepository<Payment,Long> {

    /**
     * Used for finding loan details using customerID.
     * @param customerID - customerID
     * @return - payment details if record is found using UUID.
     */
    List<Payment> findByCustomerID(String customerID);

    /**
     * Used for finding loan details using customerID.
     * @param paymentID - paymentID
     * @return - payment details if record is found using UUID.
     */
    Optional<Payment> findByPaymentID(String paymentID);


}
