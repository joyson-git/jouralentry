package engineering.digest.joural.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import engineering.digest.joural.entity.User;
import engineering.digest.joural.repositary.Userrespositary;
import engineering.digest.joural.service.Userservice;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private Userrespositary userrespositary;
	
    @Autowired
    private Userservice userservice;

    @Autowired
    private PasswordEncoder passwordEncoder; // Password encoder for secure password storage

    @GetMapping
    public List<User> getAll() {
        return userservice.getAll();
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword())); // Secure password encoding
            userservice.save(user);
            return ResponseEntity.ok("User created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user: " + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        // Getting the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        // Find user by username in the database
        User userInDb = userservice.findByUserName(userName);

        if (userInDb == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        // Update user details, securely updating the password if changed
        userInDb.setUserName(user.getUserName());
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            userInDb.setPassword(passwordEncoder.encode(user.getPassword())); // Encode new password
        }

        userservice.save(userInDb);

        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
    }
    
    @DeleteMapping
    public ResponseEntity<?> deleteUserById() {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       
        userrespositary.deleteByUserName(authentication.getName());
        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
    }
    
    
    
    
    
    
}
