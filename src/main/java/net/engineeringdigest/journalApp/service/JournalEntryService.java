package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntryV2;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntryV2 journalEntry, String userName){
        try{
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntryV2 saved = journalEntryRepository.save(journalEntry);

            user.getJournalEntries().add(saved);
            userService.saveNewUser(user);
        }catch(Exception e){
            log.info("Error occurred", e);
            throw new RuntimeException("An error occurred while saving the entry" + e);
        }
    }

    public List<JournalEntryV2> getAllEntry(){
        return  journalEntryRepository.findAll();
    }

    public Optional<JournalEntryV2> getEntryById(ObjectId id){
      return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName){
        boolean removed = false;
        try {
            User user = userService.findByUserName(userName);
             removed = user.getJournalEntries().removeIf(x -> x.getId() == id);
            if (removed) {
                userService.saveNewUser(user);
                journalEntryRepository.deleteById(id);
            }
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occurred while deleting the entry.", e);
        }
        return removed;
    }

    public void updateEntry(JournalEntryV2 journalEntry){
        journalEntryRepository.save(journalEntry);
    }

}
