package vti.dtn.api_gateway.respone;

import lombok.*;

@Setter
@Getter
@Builder
public class VerifyTokenRespone {
    private Integer status;
    private String message;
    private String xUserToken;
}
