package in.com.finzly.loan.repo.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateValidator implements
        ConstraintValidator<ValidDate, LocalDate>
{
    @Override
    public void initialize(ValidDate annotation) {
        //Not much to do - this is invoked before "isValid", and can be used to pass annotation parameters
    }

    @Override
    public boolean isValid(LocalDate ValidDate, ConstraintValidatorContext context) {
        //Insert validation logic for "value" here. Return true/false

        LocalDate today = LocalDate.now();
        int diff = today.compareTo(ValidDate);

        if(diff > 0 ){
            return false;
        }
        return true;
    }
}
