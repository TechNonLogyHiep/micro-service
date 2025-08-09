package vti.dtn.auth_service.dto.res;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginRespone {
    private int status;
    private String message;

    private Long userId;
    private String accessToken;
    private String refreshToken;
}
