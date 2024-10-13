package net.engineeringdigest.journalApp.controller;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.engineeringdigest.journalApp.api.response.WheatherResponse;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        List<User> allUser = userService.getAllUser();
        return new ResponseEntity<>(allUser, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user){
        ///Jab koi user authenticate hota hai toh uski detail save hoti hai
        ///agar user authenticate hoga tbhi details aayegi
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User userInDb = userService.findByUserName(userName);
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveUser(userInDb);
        return new ResponseEntity<>(userInDb, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        userRepository.deleteByUserName(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/temperature")
    public ResponseEntity<?> getTemperature(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WheatherResponse wheatherResponse = weatherService.getWheather("Mumbai");

        String greeting = "";
        if(wheatherResponse !=null){
            greeting = ", Weather feels like " + wheatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hello "+ authentication.getName() + greeting , HttpStatus.OK);
    }
}
