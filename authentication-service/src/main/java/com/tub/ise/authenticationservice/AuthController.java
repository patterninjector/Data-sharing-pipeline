package com.tub.ise.authenticationservice;


import com.tub.ise.authenticationservice.service.UserService;
import com.tub.ise.commondtos.AuthRequest;
import com.tub.ise.commondtos.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/validate")
    public ResponseEntity<AuthResponse> validate(@RequestBody AuthRequest request) {
        boolean isValid = userService.validateUser(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(new AuthResponse(isValid));
    }
}

