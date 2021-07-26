package in.com.finzly.loan.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    private List<ErrorDetail> detail = new ArrayList<>();
    private Date timestamp;
    private HttpStatus status;

}
