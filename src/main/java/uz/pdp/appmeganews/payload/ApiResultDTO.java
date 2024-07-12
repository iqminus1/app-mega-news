package uz.pdp.appmeganews.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.FieldError;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResultDTO<T> {
    private boolean success;
    private String errorMessage;
    private T data;
    private List<FieldErrorDTO> fieldErrors;

    public static <T> ApiResultDTO<T> success(T data) {
        ApiResultDTO<T> apiResultDTO = new ApiResultDTO<>();
        apiResultDTO.setSuccess(true);
        apiResultDTO.setData(data);
        return apiResultDTO;
    }

    public static ApiResultDTO<?> error(String errorMessage) {
        ApiResultDTO<?> apiResultDTO = new ApiResultDTO<>();
        apiResultDTO.setErrorMessage(errorMessage);
        return apiResultDTO;
    }

    public static ApiResultDTO<?> error(List<FieldError> fieldErrors) {
        ApiResultDTO<?> apiResultDTO = new ApiResultDTO<>();
        List<FieldErrorDTO> errors = fieldErrors.stream()
                .map(err -> new FieldErrorDTO(err.getField(), err.getDefaultMessage()))
                .toList();
        apiResultDTO.setFieldErrors(errors);
        return apiResultDTO;
    }

    public static ApiResultDTO<?> error(DataIntegrityViolationException exception) {
        ConstraintViolationException cause = (ConstraintViolationException) exception.getCause();
        PSQLException psqlException = (PSQLException) cause.getSQLException();
        ServerErrorMessage serverErrorMessage = psqlException.getServerErrorMessage();
        String detail = serverErrorMessage.getDetail();
        String table = serverErrorMessage.getTable();
        String errorMessage = "Table -> %s <-----> exception -> %s".formatted(table, detail);
        return error(errorMessage);
    }
}
