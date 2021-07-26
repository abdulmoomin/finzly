package in.com.finzly.loan.core.exception;

import org.springframework.http.HttpStatus;

public class InvalidPaymentIDException extends CoreException
{
    private static HttpStatus statusCode = HttpStatus.NOT_FOUND;

    /**
     * Exception is thrown when uuid is invalid.
     *
     * @param message - message to be returned to the user
     * @param details - details from the exception
     * @param parameter - parameter from the exception
     * @param pointer - pointer from the exception
     */
    public InvalidPaymentIDException(String message,
                                     String details,
                                     String parameter,
                                     String pointer)
    {
        super(details, statusCode, message, parameter, pointer);
    }
}
