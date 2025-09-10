package com.example.journal.app.controller;

import com.example.journal.app.entity.JournalEntry;
import com.example.journal.app.entity.User;
import com.example.journal.app.service.JournalEntryService;
import com.example.journal.app.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {
  @Autowired
  private JournalEntryService journalEntryService;
  @Autowired
  private UserService userService;

  @GetMapping("{userName}")
  public ResponseEntity<?> getAllJournalEntryOfUser(@PathVariable String userName) {
    User user = userService.findByUserName(userName);
    List<JournalEntry> all = user.getJournalEntries();
    if (all != null && !all.isEmpty()) {
      return new ResponseEntity<>(all, HttpStatus.OK);

    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PostMapping("{userName}")
  public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName) {
    try {

//      myEntry.setDate(LocalDateTime.now());
      journalEntryService.saveEntry(myEntry, userName);
      return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("id/{myid}")
  public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myid) {
    Optional<JournalEntry> journalEntry = journalEntryService.findById(myid);
    if (journalEntry.isPresent()) {
      return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("id/{userName}/{myid}")
  public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myid  ,@PathVariable String userName) {
    journalEntryService.deleteById(myid,userName);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }



  @PutMapping("/id/{userName}/{id}")
  public ResponseEntity<JournalEntry> updateJournalById(@PathVariable ObjectId id,
                                                        @RequestBody JournalEntry newEntry,@PathVariable String userName) {
  JournalEntry old = journalEntryService.findById(id).orElse(null);

    if (old != null) {
      old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("")
              ? newEntry.getTitle()
              : old.getTitle());

      old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("")
              ? newEntry.getContent()
              : old.getContent());

      journalEntryService.saveEntry(old);
      return new ResponseEntity<>(old, HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }


}

