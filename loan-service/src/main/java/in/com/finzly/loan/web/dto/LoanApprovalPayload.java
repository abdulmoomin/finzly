package in.com.finzly.loan.web.dto;

import in.com.finzly.loan.repo.validator.ValidDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class LoanApprovalPayload {

    @ValidDate(message ="LoanStartDate should be current date or future date")
    private LocalDate loanStartDate;
    @ValidDate(message ="MaturityDate should be current date or future date")
    private LocalDate maturityDate;

}
