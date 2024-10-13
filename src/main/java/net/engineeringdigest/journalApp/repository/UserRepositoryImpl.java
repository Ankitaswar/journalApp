package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.schema.JsonSchemaObject;

import java.util.List;

public class UserRepositoryImpl {

     @Autowired
     private MongoTemplate mongoTemplate;

    public List<User> getUserForSA(){
        Query query = new Query();

        //EX:1
//        query.addCriteria(Criteria.where("userName").is("Ankit"));

        //EX:2  : And condition apply hogi ese case m
//        query.addCriteria(Criteria.where("userName").is("Ankit"));
//        query.addCriteria(Criteria.where("age").gte(22));

        //EX: 3
//        query.addCriteria(Criteria.where("email").exists(true));
//        query.addCriteria(Criteria.where("email").ne(null).ne(""));  // not equal
//        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));

        //EX: 3 Better way use regex
        query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9_.±]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$"));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));

        //EX:4 Or lagana ho toh manually krna padega
//        Criteria criteria = new Criteria();
//        query.addCriteria(criteria.orOperator(Criteria.where("email")
//                .exists(true),Criteria.where("sentimentAnalysis").is(true)));

//        EX: 5 When working with aarays
//        query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9_.±]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$"));
//        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
//        query.addCriteria(Criteria.where("roles").in("ADMIN", "USER")); //nin not in
//        query.addCriteria(Criteria.where("sentimentAnalysis").type(JsonSchemaObject.Type.BsonType.BOOLEAN));

        List<User> users = mongoTemplate.find(query, User.class);
        return users;
    }

}
