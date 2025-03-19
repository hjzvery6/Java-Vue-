package com.example.controller;

import com.example.common.config.JwtTokenUtils;
import com.example.common.Result;
import com.example.common.ResultCode;
import com.example.entity.*;
import com.example.service.*;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.*;

// 系统用户操作相关接口
@RestController // 标记为RESTful控制器，返回的数据通常是JSON格式
public class AccountController {
	// 使用注解注入服务层对象，以便在控制器中使用
	@Resource
	private AdminService adminService;
	@Resource
	private UserService userService;

    // 用户登录接口 -> 登录页面的登录按钮
    @PostMapping("/login") // 接收/login请求
    public Result login(@RequestBody Account account) { // @RequestBody注解表示将请求体中的JSON数据映射为Account对象
        Integer role = account.getRole(); // 获取登录用户的角色
        Account loginUser = new Account();
        Map<String, Object> map = new HashMap<>();
		// 根据角色调用各自的服务层方法进行登录验证
		if (1 == role) {
			loginUser = adminService.login(account.getUsername(), account.getPassword());
			map.put("user", loginUser);
		}
		if (2 == role) {
			loginUser = userService.login(account.getUsername(), account.getPassword());
			map.put("user", loginUser);
		}

        // 创建token，并返回给前端
        String token = JwtTokenUtils.genToken(loginUser.getId() + "-" + loginUser.getRole(), loginUser.getPassword());
        map.put("token", token);
        return Result.success(map);
    }

    // 用户注册接口 -> 注册页面的注册按钮
    @PostMapping("/register") // 接收/register请求
    public Result register(@RequestBody Account account) { // @RequestBody注解表示将请求体中的JSON数据映射为Account对象
        Integer role = account.getRole(); // 获取注册用户的角色
        Account login = new Account();
		// 根据角色调用服务层方法进行注册（新增）
		if (1 == role) {
			Admin newAdmin = new Admin();
			// 直接将account对象中的属性值复制到info对象中，从而简化了对象之间的属性赋值操作
			BeanUtils.copyProperties(account, newAdmin);
			login = adminService.add(newAdmin);
		}
		if (2 == role) {
			User newUser = new User();
			BeanUtils.copyProperties(account, newUser);
			login = userService.add(newUser);
		}
        return Result.success(login);
    }

    // 描述当前登录用户信息 -> 用户和管理员后台的个人信息页面，将当前登录用户的信息展示出来
    @GetMapping("/getUser")
    public Result getAccountInfo() {
		// 获取登录的这个管理员/用户
        Account account = JwtTokenUtils.getCurrentUser();
        if (account == null) {
			return Result.error(ResultCode.USER_NOT_EXIST_ERROR.code, ResultCode.USER_NOT_EXIST_ERROR.msg);
        }
		// 根据当前登录的这个用户的角色，分别在数据库中去查询管理员/用户信息，并返回给前端
        Integer role = account.getRole();
		if (1 == role) {
			return Result.success(adminService.findById(account.getId()));
		}
		if (2 == role) {
			return Result.success(userService.findById(account.getId()));
		}
		return Result.success();
    }

    // 修改密码接口 -> 用户和管理员后台的修改密码页面的保存按钮
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody Account loginAccount) {
		// 获取当前登录的这个用户
        Account account = JwtTokenUtils.getCurrentUser();
		// 对于登录用户不存在和密码错误的情况返回错误响应
        if (account == null) {
            return Result.error(ResultCode.USER_NOT_EXIST_ERROR.code, ResultCode.USER_NOT_EXIST_ERROR.msg);
        }
        String oldPassword = loginAccount.getPassword();
        if (!oldPassword.equals(account.getPassword())) {
            return Result.error(ResultCode.PARAM_PASSWORD_ERROR.code, ResultCode.PARAM_PASSWORD_ERROR.msg);
        }

		// 根据当前登录的这个用户的角色，分别在数据库中更新管理员/用户信息
        Integer role = account.getRole();
		if (1 == role) {
			Admin admin = new Admin();
			BeanUtils.copyProperties(account, admin);
			admin.setPassword(loginAccount.getNpw()); // 设置新密码
			adminService.update(admin);
		}
		if (2 == role) {
			User user = new User();
			BeanUtils.copyProperties(account, user);
			user.setPassword(loginAccount.getNpw()); // 设置新密码
			userService.update(user);
		}
		// 对于修改密码功能，只需要返回一个成功的信号给前端
        return Result.success();
    }

//    // 重置密码接口 -> 这个接口好像没用上
//    @PostMapping("/resetPassword")
//    public Result resetPassword(@RequestBody Account account) {
//        Integer role = account.getRole();
//		if (1 == role) {
//			Admin info = adminService.findByUsername(account.getUsername());
//			if (info == null) {
//				return Result.error(ResultCode.USER_NOT_EXIST_ERROR);
//			}
//			info.setPassword("123456");
//			adminService.update(info);
//		}
//		if (2 == role) {
//			User info = userService.findByUserName(account.getUsername());
//			if (info == null) {
//				return Result.error(ResultCode.USER_NOT_EXIST_ERROR);
//			}
//			info.setPassword("123456");
//			userService.update(info);
//		}
//        return Result.success();
//    }
}
