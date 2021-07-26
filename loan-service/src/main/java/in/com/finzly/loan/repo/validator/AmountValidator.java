package in.com.finzly.loan.repo.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class AmountValidator implements
        ConstraintValidator<ValidAmount, Double>
{
    @Override
    public void initialize(ValidAmount annotation) {
        //Not much to do - this is invoked before "isValid", and can be used to pass annotation parameters
    }

    @Override
    public boolean isValid(Double amount, ConstraintValidatorContext context) {
        //Insert validation logic for "value" here. Return true/false

        if(amount <= 0 ){
            return false;
        }
        return true;
    }
}

