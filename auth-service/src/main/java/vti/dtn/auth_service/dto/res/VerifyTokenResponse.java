package vti.dtn.auth_service.dto.res;

import lombok.Builder;
import lombok.*;

@Getter
@Setter
@Builder
public class VerifyTokenResponse {
    private Integer status;
    private String message;
    private String xUserToken;
}
