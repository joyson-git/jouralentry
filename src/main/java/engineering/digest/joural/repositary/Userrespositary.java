package engineering.digest.joural.repositary;




import org.springframework.data.jpa.repository.JpaRepository;

import engineering.digest.joural.entity.User;

public  interface Userrespositary extends   JpaRepository<User, String>{
	User findByUserName(String username);
	void deleteByUserName(String username);
}