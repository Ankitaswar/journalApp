package net.engineeringdigest.journalApp.scheduler;

import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.entity.JournalEntryV2;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepositoryImpl;
import net.engineeringdigest.journalApp.service.EmailService;
import net.engineeringdigest.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private UserRepositoryImpl repository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SentimentAnalysisService service;

    @Autowired
    private AppCache appCache;

    //    @Scheduled(cron = "0 * * ? * *")
    @Scheduled(cron = "0 0 9 * * SUN")   //cron expression get from website
    public void fetchUserAndSendEmail(){
        List<User> users = repository.getUserForSA();
        for (User user: users){
            List<String> filterEntries = user.getJournalEntries().stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getContent()).collect(Collectors.toList());
            String entry = String.join(" ", filterEntries);
            String sentiment = service.getSentiment(entry);
           emailService.sendEmail(user.getEmail(),"Sentiments for lst 7 days", sentiment);
        }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache(){
        appCache.init();
    }
}
