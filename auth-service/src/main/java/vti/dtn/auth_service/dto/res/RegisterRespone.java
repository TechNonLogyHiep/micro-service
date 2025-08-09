package vti.dtn.auth_service.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RegisterRespone {
    int status;
    String messege;
}
