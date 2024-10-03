package engineering.digest.joural.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import engineering.digest.joural.entity.User;  // Assuming this is your entity class
import engineering.digest.joural.repositary.Userrespositary;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private Userrespositary userrespositary;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch the user entity from the repository
        User user = userrespositary.findByUserName(username);

        if (user != null) {
            // Build UserDetails object using the retrieved user data
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))  // Assuming role is a collection
                    .build();
        }

        // Throw an exception if user is not found
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
