package in.com.finzly.loan.repo.domain;

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
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "PAYMENT")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PAYMENT_ID")
    private String paymentID;

    @Column(name = "CUSTOMER_ID")
    private String customerID;

    @Column(name = "PAYMENT_DATE")
    private LocalDate paymentDate;

    @Column(name = "PAYMENT_AMOUNT")
    private Double paymentAmount;

    @Column(name = "PRINCIPAL_AMOUNT")
    private Double principalAmount;

    @Column(name = "PROJECTED_INTEREST")
    private Double projectedinterest;

    @Column(name = "PAYMENT_STATUS")
    private String paymentStatus;


}
