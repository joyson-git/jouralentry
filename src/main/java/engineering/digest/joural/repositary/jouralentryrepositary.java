package engineering.digest.joural.repositary;

import org.springframework.data.mongodb.repository.MongoRepository;

import engineering.digest.joural.entity.JouralEntry;


public  interface jouralentryrepositary extends MongoRepository<JouralEntry, String>{
	
}