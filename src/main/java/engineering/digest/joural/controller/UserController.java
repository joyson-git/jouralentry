package engineering.digest.joural.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import engineering.digest.joural.entity.User;
import engineering.digest.joural.service.Userservice;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private Userservice userservice;

    @GetMapping
    public List<User> getAll() {
        return userservice.getAll();
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        userservice.save(user);
        return ResponseEntity.ok("User created successfully");
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user,@PathVariable String  userName) {
        User userInDb = userservice.findByUserName(userName);

        if (userInDb != null) {
            userInDb.setUserName(user.getUserName());
            userInDb.setPasswords(user.getPasswords());
            userservice.save(userInDb);
            return ResponseEntity.ok("User updated successfully");
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }
}
