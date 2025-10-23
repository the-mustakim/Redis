package com.api.redis.dao;

import com.api.redis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserDao {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String KEY="USER";

    //Save user
    public User save(User user){
        HashOperations<String, Object, Object> hashUser = redisTemplate.opsForHash();
        hashUser.put(KEY,user.getUserId(),user);
        return user;
    }

    public User get(String userId){
        return (User) redisTemplate.opsForHash().get(KEY, userId);
    }

    //Find all the users
    public Map<Object,Object> findAll(){
        return redisTemplate.opsForHash().entries(KEY);
    }

    //Delete users
    public void delete(String userId){
        Long delete = redisTemplate.opsForHash().delete(KEY, userId);
        return;
    }

    //Update user
    public void updateUsed(String userId, String newName){
        User user = (User) redisTemplate.opsForHash().get(KEY, userId);
        if(user!=null){
            user.setName(newName);
            redisTemplate.opsForHash().put(KEY, userId, user);
        }
    }

}
