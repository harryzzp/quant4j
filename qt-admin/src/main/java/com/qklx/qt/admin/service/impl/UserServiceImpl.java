package com.qklx.qt.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qklx.qt.admin.dao.UserMapper;
import com.qklx.qt.admin.entity.User;
import com.qklx.qt.admin.service.UserService;
import com.qklx.qt.core.enums.Status;
import com.qklx.qt.core.api.ApiResult;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yang
 * @since 2019-04-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public ApiResult login(User params) {
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        User user = new User();
        wrapper.eq("username", params.getUsername());
        wrapper.eq("password", params.getPassword());
        wrapper.eq("is_delete", 0);
        User u = user.selectOne(wrapper);
        if (u != null) {
            return new ApiResult(Status.SUCCESS, u.getId());
        } else {
            return new ApiResult(Status.USER_NOT_EXIST);
        }

    }

    @Override
    public ApiResult info(String token) {
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        User user = new User();
        wrapper.eq("id", token);
        wrapper.eq("is_delete", 0);
        User u = user.selectOne(wrapper);
        if (u != null) {
            u.setPassword(null);
            return new ApiResult(Status.SUCCESS, u);
        } else {
            return new ApiResult(Status.USER_NOT_EXIST);
        }

    }

    @Override
    public ApiResult logout() {
        return new ApiResult(Status.SUCCESS);
    }

    @Override
    public ApiResult getUserEmail(@NotNull Integer id) {
        User user = new User();
        User selectById = user.selectById(id);
        return new ApiResult(Status.SUCCESS, selectById);
    }

    @Override
    public ApiResult emailEditer(@NotNull User user) {

        boolean byId = user.updateById();
        if (byId) {
            return new ApiResult(Status.SUCCESS);
        }
        return new ApiResult(Status.ERROR);
    }
}
