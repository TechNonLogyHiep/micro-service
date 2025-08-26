package vti.dtn.account_service.service;

import org.springframework.stereotype.Service;
import vti.dtn.account_service.dto.AccountDTO;

import java.util.List;

@Service
public interface AccountService {
    List<AccountDTO> getListAccounts();
}
