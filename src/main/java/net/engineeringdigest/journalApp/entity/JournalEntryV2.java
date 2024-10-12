package net.engineeringdigest.journalApp.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;


@Document(collection = "journal_entries")
@Data
@NoArgsConstructor
public class JournalEntryV2 {

    @Id
    private ObjectId id;

    @NonNull
    private String title;

    @NonNull
    private String content;

    private LocalDateTime date;

}
