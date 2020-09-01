package com.zhang.devkit.demo.controller;


import com.zhang.devkit.demo.entity.User;
import com.zhang.devkit.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhang.devhit
 * @since 2020-08-31
 */
@RestController
@RequestMapping("/demo")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/user")
    public List<User> getUsers() {

        return userService.getUsers();
    }


}
