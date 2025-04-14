package org.example.userservice.service;

import org.example.userservice.model.User;
import org.example.userservice.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired private UsersRepository repository;

    public void registerUser(User user){
        repository.save(user);
    }

    public List<User> listAll(){
        return repository.findAll();
    }

    public User getUserById(String userid){
        return repository.findByUserid(userid);
    }

    public User getUserByIdAndPwd(String userid, String pwd){
        return repository.findByUseridAndPwd(userid, pwd);
    }
}
