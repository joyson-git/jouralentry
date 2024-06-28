package engineering.digest.joural.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import engineering.digest.joural.entity.JouralEntry;
import engineering.digest.joural.entity.User;
import engineering.digest.joural.service.Userservice;
import engineering.digest.joural.service.jouralentryservice;


@RestController
@RequestMapping("/journal")
public class journalEntrycontroller {

    @Autowired
    private jouralentryservice journalEntryService;

    @Autowired
    private Userservice userService;

    @GetMapping("/{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        if (user != null) {
            List<JouralEntry> allEntries = user.getJouralEntries();
            if (allEntries != null && !allEntries.isEmpty()) {
                return ResponseEntity.ok(allEntries);
            } else {
                return ResponseEntity.status(404).body("No journal entries found for user " + userName);
            }
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JouralEntry myEntry,@PathVariable String userName) {
        
    	try {
    		journalEntryService.save(myEntry);
            return ResponseEntity.ok("Journal entry created successfully");
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
    }

    @GetMapping("/id/{myid}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable String myid) {
    	JouralEntry entry = journalEntryService.findById(myid).orElse(null);
        if (entry != null) {
            return ResponseEntity.ok(entry);
        } else {
            return ResponseEntity.status(404).body("Journal entry not found");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable String id) {
        journalEntryService.deleteById(id);
        return ResponseEntity.ok("Journal entry deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable String id, @RequestBody JouralEntry updatedEntry) {
    	JouralEntry entry = journalEntryService.updateEntry(id, updatedEntry);
        if (entry != null) {
            return ResponseEntity.ok(entry);
        } else {
            return ResponseEntity.status(404).body("Journal entry not found");
        }
    }
}
