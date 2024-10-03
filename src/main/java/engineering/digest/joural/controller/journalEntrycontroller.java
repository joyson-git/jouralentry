package engineering.digest.joural.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import engineering.digest.joural.entity.JournalEntry;
import engineering.digest.joural.entity.User;
import engineering.digest.joural.service.JournalEntryService;
import engineering.digest.joural.service.Userservice;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private Userservice userService;

    @GetMapping("/{userName}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        if (user != null) {
            List<JournalEntry> allEntries = user.getJournalEntries();
            if (allEntries != null && !allEntries.isEmpty()) {
                return new ResponseEntity<>(allEntries, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName) {
        try {
            JournalEntry savedEntry = journalEntryService.save(myEntry, userName);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEntry);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable Long id) {
        return journalEntryService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJournalEntryById(@PathVariable Long id) {
        try {
            journalEntryService.deleteById(id);
            return ResponseEntity.ok("Journal entry deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error deleting journal entry: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable Long id, @RequestBody JournalEntry updatedEntry) {
        return journalEntryService.updateEntry(id, updatedEntry)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
