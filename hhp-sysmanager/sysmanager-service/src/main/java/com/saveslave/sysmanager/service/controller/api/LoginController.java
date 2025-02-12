package com.saveslave.sysmanager.service.controller.api;


import com.baomidou.mybatisplus.extension.api.R;
import com.saveslave.commons.util.Result;
import com.saveslave.sysmanager.service.model.query.LoginBody;
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
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserService usersService;
    /**
     * 登录
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginBody loginBody){
        return usersService.login(loginBody);
    }
}

