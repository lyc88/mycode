package com.whyuan.service;


import com.whyuan.pojo.User;

public interface IUserService {
    public User getUserById(int userId) throws Exception;
}
