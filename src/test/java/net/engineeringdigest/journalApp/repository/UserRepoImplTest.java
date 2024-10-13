package net.engineeringdigest.journalApp.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepoImplTest {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Test
    public void getSAUser(){
        userRepository.getUserForSA();
    }
}
