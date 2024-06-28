package engineering.digest.joural.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Component;

import engineering.digest.joural.entity.JouralEntry;
import engineering.digest.joural.repositary.jouralentryrepositary;

@Component
public class jouralentryservice{
	
	@Autowired
	private jouralentryrepositary jouralRepository;

	@Autowired
	private Userservice userservice;
	
	  public void save(JouralEntry journalEntry) {
	        User user = userservice.findByUserName(userName);
	        if (user != null) {
	            journalEntry.setUser(userName);
	            jouralRepository.save(journalEntry);
	        } else {
	            throw new RuntimeException("User not found");
	        }
	    }
public List<JouralEntry> getAll(){
	 return jouralRepository.findAll();
}





public  Optional<JouralEntry> findById(String id) {
	 return jouralRepository.findById(id);
}




public void deleteById(String id) {
    jouralRepository.deleteById(id);
}

public JouralEntry updateEntry(String id, JouralEntry updatedEntry) {
    return jouralRepository.findById(id)
            .map(existingEntry -> {
                existingEntry.setTitle(updatedEntry.getTitle());
                existingEntry.setContent(updatedEntry.getContent());
                return jouralRepository.save(existingEntry);
            })
            .orElseThrow(() -> new RuntimeException("Journal entry not found"));
}
}







//controller --> services -- repositary