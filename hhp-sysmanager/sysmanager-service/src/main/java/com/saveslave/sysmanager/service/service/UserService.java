package com.saveslave.sysmanager.service.service;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.IService;
import com.saveslave.commons.util.Result;
import com.saveslave.sysmanager.service.model.query.LoginBody;
import com.saveslave.sysmanager.service.persistent.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2025-02-12
 */
public interface UserService extends IService<User> {

    Result<String> savaUser(User user);

    Result login(LoginBody loginBody);
}
