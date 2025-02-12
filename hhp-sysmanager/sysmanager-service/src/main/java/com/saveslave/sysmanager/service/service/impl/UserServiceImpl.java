package com.saveslave.sysmanager.service.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saveslave.commons.util.Result;
import com.saveslave.sysmanager.service.model.query.LoginBody;
import com.saveslave.sysmanager.service.persistent.entity.User;
import com.saveslave.sysmanager.service.persistent.mapper.UserMapper;
import com.saveslave.sysmanager.service.service.UserService;
import com.saveslave.sysmanager.service.util.PasswordUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2025-02-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Result<String> savaUser(User user) {
        //查询数据库中是否有相同的用户
        if (baseMapper.selectById(user.getUserId()) != null){
            return Result.failed("新增用户失败,用户已存在");
        }
        User users = new User();
        users.setUserId(user.getUserId());
        users.setUserName(user.getUserName());
        //密码加密存储到数据库中
        String hashedPassword = PasswordUtil.encryptPassword(user.getPassword());
        users.setPassword(hashedPassword);
        users.setUserType(user.getUserType());
        int insert = baseMapper.insert(users);
        if (insert <= 0){
            return Result.failed("新增用户失败");
        }
        return Result.succeed("新增用户成功");
    }

    /**
     * 登录
     */
    @Override
    public Result login(LoginBody loginBody) {
        // 第一步:首先判断一下入参是否为空
        if (loginBody == null || loginBody.getUserName().isEmpty() || loginBody.getPassword().isEmpty()){
            return Result.failed("用户名或密码为空");
        }
        // 第二步:不为空的话,检查密码是否正确,根据用户名去数据库查找对应的用户信息,得到存储的暗文密码
        QueryWrapper<User> usersQueryWrapper = new QueryWrapper<>();
        usersQueryWrapper.eq("user_name",loginBody.getUserName());
        User user = baseMapper.selectOne(usersQueryWrapper);
        String password = user.getPassword();
        if (!PasswordUtil.verifyPassword(loginBody.getPassword(),password)){
            return Result.failed("密码错误");
        }
        // 第三步:使用sa-token登录认证
        StpUtil.login(loginBody.getUserName());

        //第四步:根据用户角色不的不同,赋予不同的权限
        List<String> permissionListResult = setUsernamePermission(user.getUserType());
        // 把该用户的权限存储到 Sa-Token的session中
        StpUtil.getSession().set("permissionList", permissionListResult);

        // 第五步:获取登录认证的token,返回前端
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        SaResult data = SaResult.data(tokenInfo);
        return Result.succeed(data);
    }

    /**
     * 根据用户类型的不同授予不同的权限
     * 1:管理员 2:访客
     */
    public List<String> setUsernamePermission(Integer userType) {
        ArrayList<String> permissionList = new ArrayList<>();
        switch (userType) {
            case 1:
                // 1:代表管理员权限,拥有所有权限
                permissionList.add("*");
                return permissionList;
            case 2:
                // 2:代表访客权限,可以查看用户信息,不能增删改用户信息
                Collections.addAll(permissionList, "/select");
                return permissionList;
            default:
                return null;
        }
    }
}
