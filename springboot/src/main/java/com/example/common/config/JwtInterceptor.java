package com.example.common.config;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import com.example.common.ResultCode;
import com.example.entity.Account;
import com.example.exception.CustomException;
import com.example.service.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 基于Spring的JWT拦截器类，用于请求到达控制器
@Component // 将该类注册为Spring容器中的一个Bean
public class JwtInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    // 通过@Resource注解注入的服务层对象，分别用于管理员和普通用户
	@Resource
	private AdminService adminService;
	@Resource
	private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 1.从http请求的header中获取token
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            // 如果没拿到，再从请求参数里面拿尝试获取  /api/admin?token=xxxxx
            token = request.getParameter("token");
        }
        // 2.开始执行认证
        if (StrUtil.isBlank(token)) {
            // 如果Token为空抛出自定义异常
            throw new CustomException(ResultCode.TOKEN_INVALID_ERROR);
        }

        Account user = null;

        try {
            // 解析Token，提取其中的用户角色信息
            String userRole = JWT.decode(token).getAudience().get(0);
            String userId = userRole.split("-")[0];
            String role = userRole.split("-")[1];

            // 根据角色类型，分别查询管理员和普通用户
            // 根据token中的userid查询数据库
			if ("1".equals(role)) {
				user = adminService.findById(Integer.valueOf(userId));
			}
			if ("2".equals(role)) {
				user = userService.findById(Integer.valueOf(userId));
			}
        } catch (Exception e) {
            throw new CustomException(ResultCode.TOKEN_CHECK_ERROR);
        }

        // 如果用户不存在，抛出自定义异常
        if (user == null) {
            throw new CustomException(ResultCode.USER_NOT_EXIST_ERROR);
        }

        try {
            // 用户密码加签验证 token
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
            jwtVerifier.verify(token); // 验证token
        } catch (JWTVerificationException e) {
            throw new CustomException(ResultCode.TOKEN_CHECK_ERROR);
        }
        return true;
    }
}