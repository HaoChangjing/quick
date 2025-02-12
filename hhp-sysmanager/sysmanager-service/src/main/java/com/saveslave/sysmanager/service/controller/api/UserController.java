package com.saveslave.sysmanager.service.controller.api;


import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.saveslave.commons.util.Result;
import com.saveslave.sysmanager.service.persistent.entity.User;
import com.saveslave.sysmanager.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2025-02-12
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService usersService;
    /**
     * 新增用户
     */
    @PostMapping("/savaUser")
    public Result<String> savaUser(@RequestBody User user) {
        // 新增用户需要/insert权限
        StpUtil.checkPermission("/insert");
        return usersService.savaUser(user);
    }
    /**
     * 查询全部用户,下面这个方法也是mybatis-plus自带的查询方法,很方便
     */
    @GetMapping("/selectAllUser")
    public List<User> queryAllUser() {
        // 查询全部用户需要/select权限
        StpUtil.checkPermission("/select");
        return usersService.list();
    }
}

