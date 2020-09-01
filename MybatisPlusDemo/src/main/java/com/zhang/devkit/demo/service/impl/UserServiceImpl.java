package com.zhang.devkit.demo.service.impl;

import com.zhang.devkit.demo.entity.User;
import com.zhang.devkit.demo.mapper.UserMapper;
import com.zhang.devkit.demo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhang.devhit
 * @since 2020-08-31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getUsers() {

        return userMapper.selectList(null);
    }
}
