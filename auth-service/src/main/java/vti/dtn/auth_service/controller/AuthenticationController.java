package vti.dtn.auth_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vti.dtn.auth_service.dto.req.LoginRequest;
import vti.dtn.auth_service.dto.res.LoginRespone;
import vti.dtn.auth_service.dto.res.VerifyTokenResponse;
import vti.dtn.auth_service.entity.UserEntity;
import vti.dtn.auth_service.repo.UserRepository;
import vti.dtn.auth_service.service.AuthenticationService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/login")
    public ResponseEntity<LoginRespone> login(@RequestBody @Valid LoginRequest loginRequest) {
        LoginRespone loginRespone = authenticationService.login(loginRequest);

        return ResponseEntity
                .status(loginRespone.getStatus())
                .body(loginRespone);
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<LoginRespone> refreshToken(@RequestHeader("Authorization") String authHeader) {
        LoginRespone response = authenticationService.refreshToken(authHeader);
        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }
    @GetMapping("/verify")
    public ResponseEntity<VerifyTokenResponse> verifyToken(@RequestHeader("Authorization")String authHeader){
        log.info("Verifying token :{}",authHeader);
        VerifyTokenResponse response = authenticationService.verifyToken(authHeader);
        return ResponseEntity.status(response.getStatus())
                .body(response);
    }
}
