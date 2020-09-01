package com.zhang.devkit.demo.service;

import com.zhang.devkit.demo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhang.devhit
 * @since 2020-08-31
 */
public interface IUserService extends IService<User> {

    public List<User> getUsers();

}
