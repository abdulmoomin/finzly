package in.com.finzly.loan.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoreException extends Exception {
    private static final long serialVersionUID = 9059733513436544390L;
    private String details;
    private HttpStatus httpStatusCode;
    private String message;
    private String parameter;
    private String pointer;

}
