package net.engineeringdigest.journalApp.scheduler;

import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.entity.JournalEntryV2;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.enums.Sentiment;
import net.engineeringdigest.journalApp.model.SentimentData;
import net.engineeringdigest.journalApp.repository.UserRepositoryImpl;
import net.engineeringdigest.journalApp.service.EmailService;
import net.engineeringdigest.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;

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


    public void fetchUserAndSendEmailV2(){
        List<User> users = repository.getUserForSA();
        for (User user: users){
            List<Sentiment> sentiments = user.getJournalEntries().stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCount = new HashMap<>();
            for(Sentiment sentiment: sentiments){
                if(sentiment != null)
                    sentimentCount.put(sentiment, sentimentCount.getOrDefault(sentiment,0)+1);
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for(Map.Entry<Sentiment, Integer> entry : sentimentCount.entrySet()){
                if(entry.getValue() > maxCount){
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if(mostFrequentSentiment !=null){
//                emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days", mostFrequentSentiment.toString());

                //using kakfa
                SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("sentiment for last 7 days").build();
                kafkaTemplate.send("weekly-sentiments", sentimentData.getEmail(), sentimentData);
            }
        }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache(){
        appCache.init();
    }
}
