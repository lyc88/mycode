package com.whyuan.service.impl;

import com.whyuan.dao.UserMapper;
import com.whyuan.pojo.User;
import com.whyuan.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService{
    //idea有时候无法正确识别@Autowired
    // 解决办法：File->Settings->Editor->Inspections->Spring->Spring Core->Code->Autowiring for Bean Class-> 从Error 修改为Warning 就好了
    @Autowired
    UserMapper userMapper;
    public User getUserById(int userId) throws Exception {
        User u=this.userMapper.selectByPrimaryKey(userId);
        return u;
    }
}
