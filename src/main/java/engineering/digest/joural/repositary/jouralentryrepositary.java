package engineering.digest.joural.repositary;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import engineering.digest.joural.entity.JournalEntry;

@Repository
public  interface jouralentryrepositary extends  JpaRepository<JournalEntry, Long> {
	
}