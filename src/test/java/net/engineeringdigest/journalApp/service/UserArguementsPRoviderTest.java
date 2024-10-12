package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

@SpringBootTest
public class UserArguementsPRoviderTest implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(User.builder().userName("shyam").password("Shaym123@").build()),
                Arguments.of(User.builder().userName("suraj").password("").build())
        );
    }
}
