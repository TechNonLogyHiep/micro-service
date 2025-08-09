package vti.dtn.account_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AccountDTO {
    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
}
