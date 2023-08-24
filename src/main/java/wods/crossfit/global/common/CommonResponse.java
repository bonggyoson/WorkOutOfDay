package wods.crossfit.global.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonResponse<T> {

    @Schema(name = "status", example = "200")
    private HttpStatus status;
    @Schema(name = "message", example = "response message")
    private String message;
    @Schema(name = "data", example = "response data")
    private T data;

    public CommonResponse(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }

    public static <T> CommonResponse<T> res(final HttpStatus status, final String message) {
        return res(status, message, null);
    }

    public static <T> CommonResponse<T> res(final HttpStatus status, final String message,
            final T t) {
        return CommonResponse.<T>builder()
                .status(status)
                .message(message)
                .data(t)
                .build();
    }
}
