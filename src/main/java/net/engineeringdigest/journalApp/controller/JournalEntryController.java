package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/_journal")
public class JournalEntryController {

    private Map<String, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAll(){
        return new ArrayList<>(journalEntries.values());
    }

    @GetMapping("/id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable Long myId){
        return journalEntries.get(myId);
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry myEntry){
            journalEntries.put(myEntry.getId(), myEntry);
            return true;
    }

    @DeleteMapping("/id/{id}")
    public JournalEntry deleteEntryById(@PathVariable Long id){
        return journalEntries.remove(id);
    }

    @PutMapping("/id/{id}")
    public JournalEntry updateEntryById(@PathVariable Long Id, @RequestBody JournalEntry myEntry){
        return journalEntries.put(myEntry.getId(), myEntry);
    }
}
