package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.api.response.WheatherResponse;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.constants.AppConstant;
import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

//    private static final String apiKey = "c89f5fcabdfe2d2de98f07408f1622be";
    @Value("${weather.api.key}")
    private String apiKey;

    //yaa toh yaml m daal do ya fir hum database m bhi daal skte but ek call bad jayegi isse database
    // ki islye hum use karenege application cache(ye ek tarika isse frequently used and frequently changing configuration
    // ko database m daad dete ho and usko load krwa kete ho inside you spring boot application)

    private static final String API = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private AppCache appCache;

    @Autowired
    private RestTemplate restTemplate;

    public WheatherResponse getWheather(String city){
//        String finalAPI = API.replace("API_KEY",apiKey).replace("CITY", city);
        String finalAPI =appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(AppConstant.API_KEY,apiKey).replace(AppConstant.CITY, city);
        ResponseEntity<WheatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WheatherResponse.class);
//        response.getStatusCode();
        return response.getBody();
    }

    //POST : just an example
    public WheatherResponse addWheather(String city){
        String finalAPI = API.replace("API_KEY",apiKey).replace("CITY", city);

        //want to send String
        String requestbody = "{\n" +
                "    \"userName\": \"RamSingh\",\n" +
                "    \"password\": \"RamSingh123@\"\n" +
                "}";
        HttpEntity<String> entity = new HttpEntity<>(requestbody);

        //if want to send object
        User user = User.builder().userName("Ankit").password("Ankit").build();
        HttpEntity<User> entity1 = new HttpEntity<>(user);

        //headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("key", "Value");
        HttpEntity<User> entity2 = new HttpEntity<>(user, headers);

        ResponseEntity<WheatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.POST, entity, WheatherResponse.class);
//        response.getStatusCode();
        return response.getBody();
    }




}
