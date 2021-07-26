package in.com.finzly.loan.core.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception is thrown when the user's input
 * doesn't match with the functionality.
 */
public class CustomValidationException extends CoreException
{
    private static HttpStatus statusCode = HttpStatus.NOT_ACCEPTABLE;
    /**
     * Exception is thrown when uuid is invalid.
     *
     * @param message - message to be returned to the user
     * @param details - details from the exception
     * @param parameter - parameter from the exception
     * @param pointer - pointer from the exception
     */
    public CustomValidationException(String message,
                                     String details,
                                     String parameter,
                                     String pointer)
    {
        super(details, statusCode, message, parameter, pointer);
    }
}
