package wods.crossfit.global.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse<T> {

    private HttpStatus status;
    private String message;
    private T data;

    public ErrorResponse(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }

    public static <T> ErrorResponse<T> res(final HttpStatus status, final String message) {
        return res(status, message, null);
    }

    public static <T> ErrorResponse<T> res(final HttpStatus status, final String message,
            final T t) {
        return ErrorResponse.<T>builder()
                .status(status)
                .message(message)
                .data(t)
                .build();
    }
}
