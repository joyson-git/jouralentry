package engineering.digest.joural.repositary;

import org.springframework.data.mongodb.repository.MongoRepository;


import engineering.digest.joural.entity.User;

public  interface Userrespositary extends MongoRepository<User, String>{
	User findByUserName(String username);
}