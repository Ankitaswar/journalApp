package net.engineeringdigest.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedis(){
//        redisTemplate.opsForValue().set("email","ankitaswar3@gmail.com");
        Object email = redisTemplate.opsForValue().get("email");
        Object salary = redisTemplate.opsForValue().get("salary");
        int a =1;
    }
}

/// jab hum yaah se set krre hai toh redis cli m nhi show hora and vice versa
/// but jo jaha set krre wha accessible hai
/// jab hum data set kre tb serialization hota hai and jab lete hai de-serialization hota.
/// private RedisTemplate redisTemplate; redis and spring boot k serializer or de-serializer alag alag hai islye hume wo key nahi mil rhi hai
/// RedisConfig m ek method banao jo return krega redisTemplate
/// ab redis m code k thorught set ho jayegi and redis k through set hui value code k through get kr payenge.