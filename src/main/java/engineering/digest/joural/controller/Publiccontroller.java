package engineering.digest.joural.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import engineering.digest.joural.entity.User;
import engineering.digest.joural.service.Userservice;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/public")
public class Publiccontroller {

    @Autowired
    private Userservice userservice;

    @Autowired
    private PasswordEncoder passwordEncoder; // To securely store passwords

    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@Validated @RequestBody User user) {
        try {
            // Encode password before saving the user
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userservice.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        } catch (IllegalArgumentException e) {
            // Handle validation exceptions
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user: " + e.getMessage());
        }
    }
}
