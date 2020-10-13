package com.lc.service.serviceImpl;

import com.lc.mapper.UserDao;
import com.lc.pojo.User;
import com.lc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired(required = true)
    private UserDao userDao;

    @Override
    public User queryByName(String name) {
        return userDao.queryByName(name);
    }

    @Override
    public User queryByPassword(String password) {
        return userDao.queryByPassword(password);
    }
}
