package in.com.finzly.loan.repo.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class PaymentFrequencyValidator implements
        ConstraintValidator<ValidFrequency, String>
{
    @Override
    public void initialize(ValidFrequency annotation) {
        //Not much to do - this is invoked before "isValid", and can be used to pass annotation parameters
    }

    @Override
    public boolean isValid(String validFrequency, ConstraintValidatorContext context) {
        //Insert validation logic for "value" here. Return true/false

        if(!validFrequency.equals("Monthly") && !validFrequency.equals("Quarterly")
                && !validFrequency.equals("HalfYearly") && !validFrequency.equals("Yearly")){
            return false;
        }
        return true;
    }
}

