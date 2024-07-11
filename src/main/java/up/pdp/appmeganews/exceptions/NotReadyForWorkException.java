package up.pdp.appmeganews.exceptions;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
@AllArgsConstructor
public class NotReadyForWorkException extends RuntimeException {
    private String message;
}
