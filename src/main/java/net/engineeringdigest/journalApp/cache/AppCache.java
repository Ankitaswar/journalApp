package net.engineeringdigest.journalApp.cache;

import net.engineeringdigest.journalApp.entity.ConfigJorunalApp;
import net.engineeringdigest.journalApp.repository.ConfigJournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component  // ye bean el baar load hogi
public class AppCache {

    public enum keys{
        WEATHER_API
    }

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

//    public Map<String, String> appCache = new HashMap<>();   // ye bhi ek baar banega ye means In memory cache ki tarah kaam krra


    public Map<String, String> appCache;

    @PostConstruct
    public void init(){
        appCache = new HashMap<>();
        List<ConfigJorunalApp> all = configJournalAppRepository.findAll();
        for (ConfigJorunalApp configJorunalApp: all){
            appCache.put(configJorunalApp.getKey(), configJorunalApp.getValue());
        }
    }
}

//aapCahe jo hai wo intiliaze hoga ek baar jab application run hoga but what if hume dynamically value chahiye or agar
// hum db m kuch change krte hai toh humne dobara se fetch krna padega but ye toh ek hi baar initilaize hoga toh hum kya kr skte
// iss init method toh api se call krwa denge , but map m do value chali jayenge toh uske liye map ko bahar intilalize na krke andar krdo method k
