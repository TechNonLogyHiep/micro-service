package vti.dtn.auth_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vti.dtn.auth_service.dto.req.RegisterRequest;
import vti.dtn.auth_service.dto.res.RegisterRespone;
import vti.dtn.auth_service.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterRespone> register(@RequestBody @Valid RegisterRequest request){
        RegisterRespone response = userService.registerUser(request);
        return ResponseEntity.status(response.getStatus())
                .body(response);
    }
}

