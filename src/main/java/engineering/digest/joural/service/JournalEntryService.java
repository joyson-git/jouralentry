package engineering.digest.joural.service;



import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import engineering.digest.joural.entity.JournalEntry;
import engineering.digest.joural.entity.User;
import engineering.digest.joural.repositary.jouralentryrepositary;

@Service
public class JournalEntryService {

    @Autowired
    private jouralentryrepositary journalEntryRepository;

    @Autowired
    private Userservice userService;

    @Transactional
    public JournalEntry save(JournalEntry journalEntry, String userName) {
        User user = userService.findByUserName(userName);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        journalEntry.setDate(OffsetDateTime.now());
        JournalEntry savedEntry = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(savedEntry);
        userService.save(user);  // Ensure both JournalEntry and User are saved in the same transaction
        return savedEntry;  // Return the saved entry
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(Long id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteById(Long id) {
        journalEntryRepository.deleteById(id);
    }

    @Transactional
    public Optional<JournalEntry> updateEntry(Long id, JournalEntry updatedEntry) {
        return journalEntryRepository.findById(id)
            .map(existingEntry -> {
                existingEntry.setTitle(updatedEntry.getTitle());
                existingEntry.setContent(updatedEntry.getContent());
                return journalEntryRepository.save(existingEntry);
            });
    }
}
