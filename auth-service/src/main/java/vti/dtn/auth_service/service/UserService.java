package vti.dtn.auth_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vti.dtn.auth_service.dto.req.RegisterRequest;
import vti.dtn.auth_service.dto.res.RegisterRespone;
import vti.dtn.auth_service.entity.UserEntity;
import vti.dtn.auth_service.enums.Role;
import vti.dtn.auth_service.repo.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterRespone registerUser(RegisterRequest registerRequest) {
        String email = registerRequest.getEmail();
        String username = registerRequest.getUsername();
        String password = registerRequest.getPassword();
        String role = registerRequest.getRole();
        String firstName = registerRequest.getFirstName();
        String lastName = registerRequest.getLastName();

        Optional<UserEntity> user = userRepository.findByEmail(email);
        Optional<UserEntity> existingUser = userRepository.findByUsername(username);
        if (user.isPresent() || existingUser.isPresent()) {
            return RegisterRespone.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .messege("Email or username already exists")
                    .build();
        }
        UserEntity newUser = UserEntity.builder()
                .email(email)
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(Role.toEnum(role))
                .firstName(firstName)
                .lastName(lastName)
                .build();
        userRepository.save(newUser);

        return RegisterRespone.builder()
                .status(HttpStatus.OK.value()).
                messege("User created successfully")
                .build();
    }
}
