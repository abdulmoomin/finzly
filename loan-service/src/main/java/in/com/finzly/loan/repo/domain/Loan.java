package in.com.finzly.loan.repo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import in.com.finzly.loan.repo.validator.ValidAmount;
import in.com.finzly.loan.repo.validator.ValidFrequency;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "LOAN")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "ID")
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "CUSTOMER_ID")
    private String customerID;

    @Size(max = 50,
            message = "The customer name "
                    + "must be less than 50 character")
    @Pattern(regexp = "^[a-zA-Z]*", message = "Customer Name must not " +
            "contain any special characters or numbers")
    @NotNull(message = "Customer name should not be null")
    @Column(name = "CUSTOMER_NAME")
    private String customerName;

    @JsonIgnore
    @Column(name = "TRADE_DATE")
    private LocalDate tradeDate;

    @ValidAmount(message = "Loan amount should be greater than zero")
    @NotNull(message = "Loan amount should not be null")
    @Column(name = "LOAN_AMOUNT")
    private Double loanAmount;

    @JsonIgnore
    @Column(name = "LOAN_START_DATE")
    private LocalDate loanStartDate;

    @JsonIgnore
    @Column(name = "MATURITY_DATE")
    private LocalDate maturityDate;

    @ValidFrequency(message = "Payment frequency should be Monthly or Quarterly or HalfYearly or Yearly")
    @NotNull(message = "Payment frequency should not be null")
    @Column(name = "PAYMENT_FREQUENCY")
    private String paymentfrequency;

    @Min(value = 1, message = "PaymentTerm should be number 1:Interest Only or 2:Even Principal")
    @Max(value = 2, message = "PaymentTerm should be number 1:Interest Only or 2:Even Principal")
    @NotNull(message = "PaymentTerm should not be null")
    @Column(name = "PAYMENT_TERM")
    private long paymentTerm;

    @ValidAmount(message = "InterestRate should be greater than zero")
    @NotNull(message = "InterestRate should not be null")
    @Column(name = "INTEREST_RATE")
    private Double interestRate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "LOAN_STATUS")
    private String loanStatus;


}
