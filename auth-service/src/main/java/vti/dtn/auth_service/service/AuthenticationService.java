package vti.dtn.auth_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import vti.dtn.auth_service.dto.req.LoginRequest;
import vti.dtn.auth_service.dto.res.LoginRespone;
import vti.dtn.auth_service.dto.res.VerifyTokenResponse;
import vti.dtn.auth_service.entity.UserEntity;
import vti.dtn.auth_service.repo.UserRepository;

import java.util.Base64;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private static final int TOKEN_INDEX = 7;
    public LoginRespone login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        Optional<UserEntity> userEntityByUsername = userRepository.findByUsername(username);
        if(userEntityByUsername.isEmpty()) {
            return LoginRespone.builder().status(404).message("User not found").
                    build();
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserEntity userEntity = userEntityByUsername.get();
        String accessToken = jwtService.generateAccessToken(userEntity);
        String refreshToken = jwtService.generateRefreshToken(userEntity);
        userEntity.setAccessToken(accessToken);
        userEntity.setRefreshToken(refreshToken);
        userRepository.save(userEntity);

        return LoginRespone.builder().status(HttpStatus.OK.value()).
                message("Login successful").
                accessToken(accessToken).
                refreshToken(refreshToken).
                userId(userEntity.getId()).build();
    }
    public LoginRespone refreshToken(String authHanlder){
        if(!StringUtils.hasText(authHanlder) || !authHanlder.startsWith("Bearer ")){
            return LoginRespone.builder().status(HttpStatus.UNAUTHORIZED.value()).
                    message("Invalid token").build();
        }
        String refreshToken = authHanlder.substring(TOKEN_INDEX);
        if(!jwtService.validateToken(refreshToken)){
            return LoginRespone.builder()
                    .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .message("Invalid refresh token").build();
        }
        String username = jwtService.extractUsername(refreshToken);
        Optional<UserEntity> userFoundByUsername = userRepository.findByUsername(username);
        if (userFoundByUsername.isEmpty()) {
            return LoginRespone.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message("Token revoked")
                    .build();
        }

        UserEntity userEntity = userFoundByUsername.get();
        String accessToken = jwtService.generateAccessToken(userEntity);
        String newRefreshToken = jwtService.generateRefreshToken(userEntity);

        userEntity.setAccessToken(accessToken);
        userEntity.setRefreshToken(newRefreshToken);
        userRepository.save(userEntity);

        return LoginRespone.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(userEntity.getId())
                .message("Refresh token successfully")
                .status(HttpStatus.OK.value())
                .build();
    }
    public VerifyTokenResponse verifyToken(String authHeader){
        log.info("verifyToken :{}",authHeader);
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            log.error("verifyToken|Authorization header is missing or invalid");
            return VerifyTokenResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message("Invalid token")
                    .build();
        }
        String token = authHeader.substring(TOKEN_INDEX);
        if (!jwtService.validateToken(token)) {
            log.error("verifyToken|Invalid token");
            return VerifyTokenResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message("Invalid token")
                    .build();
        }
        String username = jwtService.extractUsername(token);
        Optional<UserEntity> userFoundByUsername = userRepository.findByUsername(username);
        if (userFoundByUsername.isEmpty()) {
            log.error("verifyToken|User not found for username: {}", username);
            return VerifyTokenResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message("Token revoked")
                    .build();
        }
        String role = userFoundByUsername.get().getRole().name();
        String userInfoStr = username + ":" + role;
        String xUserToken = Base64.getEncoder().encodeToString(userInfoStr.getBytes());

        log.info("verifyToken|X-User-Token: {}", xUserToken);

        return VerifyTokenResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .xUserToken(xUserToken)
                .build();
    }
}
