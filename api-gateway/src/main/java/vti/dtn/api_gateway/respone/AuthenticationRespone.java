package vti.dtn.api_gateway.respone;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationRespone {
    private int status;
    private String message;
}
