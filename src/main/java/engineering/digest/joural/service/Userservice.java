package engineering.digest.joural.service;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import engineering.digest.joural.entity.User;
import engineering.digest.joural.repositary.Userrespositary;

@Service
public class Userservice {

    @Autowired
    private Userrespositary userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));  // Ensure roles are set correctly
        userRepository.save(user);
    }

    public void saveNewUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Transactional
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }
}
