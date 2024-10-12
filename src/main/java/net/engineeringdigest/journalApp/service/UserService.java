package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//    private static final Logger logger = LoggerFactory.getLogger(JournalEntryService.class);
    //Instead is this we can use @slf4f , log.info()

    public boolean saveUser(User user){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        }catch (Exception e){
//            logger.info("User cant be save with same username {}",user.getUserName(), e);

            //By default
            log.info("IUser cant be save with same username {} {}",user.getUserName(), e.getMessage());
            log.error("User cant be save with same username {} {}",user.getUserName(),  e.getMessage());
            log.warn("User cant be save with same username {} {}",user.getUserName(),  e.getMessage());

            //Need to be configure
            log.debug("User cant be save with same username {} {} ",user.getUserName(),  e.getMessage());
            log.trace("User cant be save with same username {} {}",user.getUserName(),  e.getMessage());
            return  false;

        }
    }

    public void saveNewUser(User user){
        userRepository.save(user);
    }

    public void saveAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepository.save(user);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

}
