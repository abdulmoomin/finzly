package in.com.finzly.loan.repo;

import in.com.finzly.loan.repo.domain.Loan;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LoanRepository extends CrudRepository<Loan,Long> {

    /**
     * Used for finding loan details using customerID.
     * @param customerID - customerID
     * @return - loan details if record is found using UUID.
     */
    Optional<Loan> findByCustomerID(String customerID);



}
