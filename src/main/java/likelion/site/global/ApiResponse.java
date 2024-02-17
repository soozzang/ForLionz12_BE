package likelion.site.global;

import likelion.site.global.exception.SuccessDefault;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {

    private static final String FAIL_STATUS = "fail";
    private static final String ERROR_STATUS = "error";

    private HttpStatus status;
    private T data;
    private String message;

    public static <T> ApiResponse<T> createSuccess(SuccessDefault success, T data) {
        return new ApiResponse<>(success.getHttpStatus() , data, success.getMessage());
    }

    public static ApiResponse<?> createSuccessWithNoContent(SuccessDefault success) {
        return new ApiResponse<>(success.getHttpStatus(), null, success.getMessage());
    }

    public static ApiResponse<?> createError(HttpStatus status, String message) {
        return new ApiResponse<>(status, null, message);
    }

    public static ApiResponse<?> noContent(String success) {
        return new ApiResponse<>(HttpStatus.NO_CONTENT,null, success);
    }
}
