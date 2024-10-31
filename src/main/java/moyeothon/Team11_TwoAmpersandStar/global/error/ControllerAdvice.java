package moyeothon.Team11_TwoAmpersandStar.global.error;

import moyeothon.Team11_TwoAmpersandStar.global.error.dto.ErrorResponse;
import moyeothon.Team11_TwoAmpersandStar.member.exception.InvalidMemberException;
import moyeothon.Team11_TwoAmpersandStar.member.exception.NotFoundMemberException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class ControllerAdvice {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler({
            InvalidMemberException.class
    })
    public ResponseEntity<ErrorResponse> handleInvalidData(RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        log.error(e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            NotFoundMemberException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFound(RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
        log.error(e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // 유효성 검사 추가하면 필요
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException e) {
        FieldError fieldError = Objects.requireNonNull(e.getFieldError());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                String.format("%s.(%s)", fieldError.getDefaultMessage(), fieldError.getField()));
        log.error("Validation error for field {}: {}", fieldError.getField(), fieldError.getDefaultMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
