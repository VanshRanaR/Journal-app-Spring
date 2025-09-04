package com.example.journal.app.controller;

import com.example.journal.app.entity.JournalEntry;
import com.example.journal.app.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2{
  @Autowired
  private JournalEntryService journalEntryService;

    @GetMapping()
    public List<JournalEntry> getAll()
    {
         return  journalEntryService.getAll();
    }
    @PostMapping()
    public JournalEntry createEntry(@RequestBody JournalEntry myEntry)
    {
      myEntry.setDate(LocalDateTime.now());
      journalEntryService.saveEntry(myEntry);

        return myEntry;
    }
    @GetMapping("id/{myid}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId myid)
    {
       return journalEntryService.findById(myid).orElse(null);

    }
    @DeleteMapping("id/{myid}")
    public Boolean deleteJournalEntryById(@PathVariable ObjectId myid)
    {
      journalEntryService.deleteById(myid);
      return true;
    }


  @PutMapping("/id/{id}")
  public JournalEntry updateJournalById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
    JournalEntry old = journalEntryService.findById(id).orElse(null);

    if (old != null) {
      old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
      old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
    }

    journalEntryService.saveEntry(old);
    return old;
  }



}

