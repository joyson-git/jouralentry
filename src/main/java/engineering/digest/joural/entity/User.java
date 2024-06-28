package engineering.digest.joural.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;



import lombok.Data;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;
    
    @Indexed(unique = true)
    @NonNull
    private String userName;
    
    @NonNull
    private String passwords;
 
    @DBRef
    private List<JouralEntry> jouralEntries = new ArrayList<>();


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPasswords() {
		return passwords;
	}

	public void setPasswords(String passwords) {
		this.passwords = passwords;
	}

	public List<JouralEntry> getJouralEntries() {
		return jouralEntries;
	}

	public void setJouralEntries(List<JouralEntry> jouralEntries) {
		this.jouralEntries = jouralEntries;
	}
    
    
}
