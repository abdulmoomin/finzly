package in.com.finzly.loan.core.exception;

import lombok.extern.slf4j.Slf4j;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@ControllerAdvice
public class CustomizedErrorResponseHandler
{
    /**
     * This exception handling method is used
     * when any exception is invoked.
     * @param ex - exception values
     * @param request - parameters
     * @return custom error message and error code
     */
    @ExceptionHandler(CoreException.class)
    public final ResponseEntity<Object>
    userNotFoundException(CoreException ex,
                          WebRequest request)
    {

        List<ErrorDetail> errorListDetail = new ArrayList<>();

        ErrorDetail errorValue = new ErrorDetail();
        errorValue.setMessage(ex.getMessage());
        errorValue.setDetail(ex.getDetails());
        errorValue.setParameter(ex.getParameter());
        errorValue.setPointer(ex.getPointer());
        errorListDetail.add(errorValue);

        ApiError errorDetails =
                new ApiError(errorListDetail, new Date(),
                        ex.getHttpStatusCode());

        return new ResponseEntity<>(errorDetails,
                ex.getHttpStatusCode());
    }

    /**
     * This exception handling method is used
     * when any MethodArgumentNotValidException exception is invoked.
     * @param ex - exception values
     * @param request - parameters
     * @return custom error message and error code
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Object>
    handleUserNotFoundException(MethodArgumentNotValidException ex,
                                WebRequest request)
    {

        final HttpStatus statusCode = HttpStatus.NOT_ACCEPTABLE;
        final List<FieldError> fieldErrors =
                ex.getBindingResult().getFieldErrors();
        final List<ApiError> customFieldErrors = new ArrayList<>();

        List<ErrorDetail> errorDetailValue = new ArrayList<>();

        for (FieldError fieldError : fieldErrors)
        {
            ErrorDetail errorValue = new ErrorDetail();
            errorValue.setMessage(fieldError.getDefaultMessage());
            errorValue.setDetail(fieldError.getDefaultMessage());
            errorValue.setParameter(fieldError.getField());
            errorValue.setPointer(fieldError.getObjectName());
            errorDetailValue.add(errorValue);
        }

        ApiError errorDetails = new
                ApiError(errorDetailValue, new Date(), statusCode);

        customFieldErrors.add(errorDetails);

        /*
         * return ResponseEntity.badRequest() .body(customFieldErrors);
         */
        return new ResponseEntity<>(customFieldErrors,
                statusCode);

    }
}
