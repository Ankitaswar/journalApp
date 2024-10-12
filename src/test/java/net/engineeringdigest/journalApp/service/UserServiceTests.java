package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

//ye annotation islye lagana pdta kuki hum autowired krre hai and hum test krre hai without running the whole application
// and if appl run nhi hoga toh run time pe kese inject kha se hoga
@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Disabled
    @Test
    public void testAdd(){
//        assertEquals(4, 2+1);
        assertEquals(4,2+2);
    }

    @Disabled
    @ParameterizedTest
    @ValueSource(strings = {
            "RamSingh",
            "Ankit",
            "Vipul"
    })
    public void testFindByUserName(String name){
//        assertNotNull(userRepository.findByUserName(name));
        assertNotNull(userRepository.findByUserName(name),"Failed for: "+ name);
    }

    @Disabled
    @Test
    public void testFindByUser(){
        User user = userRepository.findByUserName("RamSingh");
        assertFalse(user.getJournalEntries().isEmpty());
    }

    @BeforeEach
//    @BeforeAll
//    @AfterEach
//    @AfterAll
    void setUp(){

    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,3,9"
    })
    public void test(int a, int b, int expected){
        assertEquals(expected, a+b);
    }

    @Disabled
    @ParameterizedTest
    @ArgumentsSource(UserArguementsPRoviderTest.class)
    public void testSaveNewUser(User user){
            assertTrue(userService.saveUser(user));
    }
}
