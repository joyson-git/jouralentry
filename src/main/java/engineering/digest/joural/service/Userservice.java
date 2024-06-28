package engineering.digest.joural.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import engineering.digest.joural.entity.User;
import engineering.digest.joural.repositary.Userrespositary;

@Service
public class Userservice {

    @Autowired
    private Userrespositary userrespositary;

    public void save(User userEntry) {
        userrespositary.save(userEntry);
    }

    public List<User> getAll() {
        return userrespositary.findAll();
    }

    public Optional<User> findById(String id) {
        return userrespositary.findById(id);
    }

    public void deleteById(String id) {
        userrespositary.deleteById(id);
    }

    public User findByUserName(String userName) {
        return userrespositary.findByUserName(userName);
    }
}
