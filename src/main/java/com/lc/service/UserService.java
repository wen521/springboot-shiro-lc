package com.lc.service;

import com.lc.pojo.User;



public interface UserService {
    User queryByName(String name);
    User queryByPassword(String password);
}
