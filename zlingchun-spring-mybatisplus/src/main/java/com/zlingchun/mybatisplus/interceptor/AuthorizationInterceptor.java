package com.zlingchun.mybatisplus.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zlingchun.mybatisplus.config.UserContext;
import com.zlingchun.mybatisplus.constant.JWTConstant;
import com.zlingchun.mybatisplus.doman.dto.UserDto;
import com.zlingchun.mybatisplus.handler.response.enums.ReturnCode;
import com.zlingchun.mybatisplus.handler.response.result.ResultData;
import com.zlingchun.mybatisplus.service.dto.IUserDtoService;
import com.zlingchun.mybatisplus.util.security.JJWTUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author achun
 * @create 2022/7/26
 * @description descrip
 */
@Slf4j
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    @Resource
    ObjectMapper objectMapper;

    @Resource
    IUserDtoService userDtoService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        String requestURI = request.getRequestURI();
        String token = request.getHeader(JWTConstant.AUTHORIZATION_TOKEN);
        if (token == null) {
            // 获取当前请求类型
            String httpMethod = request.getMethod();
            // 获取当前请求URI
            log.info("当前请求 {} Authorization属性(Token)为空 请求类型 {}", requestURI, httpMethod);
            // mustLoginFlag = true 开启任何请求必须登录才可访问
            this.response(request, response, ResultData.fail(ReturnCode.RC403.getCode(), ReturnCode.RC403.getMessage()));
            return false;
        }
        // 验证token的有效性
        Integer checkToken = JJWTUtils.checkToken(token);
        switch (checkToken){
            case JWTConstant.TOKEN_ACTIVE:
                break;
            case JWTConstant.TOKEN_EXPIRED:
                this.response(request, response, ResultData.fail(ReturnCode.TOKEN_EXPIRED.getCode(), ReturnCode.TOKEN_EXPIRED.getMessage()));
                return false;
            case JWTConstant.TOKEN_UNSUPPORTED:
                this.response(request, response, ResultData.fail(ReturnCode.TOKEN_UNSUPPORTED.getCode(), ReturnCode.TOKEN_UNSUPPORTED.getMessage()));
                return false;
            case JWTConstant.TOKEN_MALFORMED:
                this.response(request, response, ResultData.fail(ReturnCode.TOKEN_MALFORMED.getCode(), ReturnCode.TOKEN_MALFORMED.getMessage()));
                return false;
            case JWTConstant.TOKEN_SIGNATURE:
                this.response(request, response, ResultData.fail(ReturnCode.TOKEN_SIGNATURE.getCode(), ReturnCode.TOKEN_SIGNATURE.getMessage()));
                return false;
            case JWTConstant.TOKEN_EMPTY:
                this.response(request, response, ResultData.fail(ReturnCode.TOKEN_EMPTY.getCode(), ReturnCode.TOKEN_EMPTY.getMessage()));
                return false;
            case JWTConstant.TOKEN_ILLEGALARGUMENT:
            default:
                this.response(request, response, ResultData.fail(ReturnCode.TOKEN_ILLEGALARGUMENT.getCode(), ReturnCode.TOKEN_ILLEGALARGUMENT.getMessage()));
                return false;
        }
        Claims body = JJWTUtils.decode(token).getBody();
        if(Objects.isNull(body)){
            this.response(request, response, ResultData.fail(ReturnCode.CLIENT_AUTHENTICATION_FAILED.getCode(), ReturnCode.CLIENT_AUTHENTICATION_FAILED.getMessage()));
            return false;
        }
        String uuid = body.get("uuid", String.class);
        String userCode = body.get("userCode", String.class);
        Long userId = body.get("userId", Long.class);
        UserDto userDto = userDtoService.verify(uuid, userCode, userId);
        if(Objects.isNull(userDto)){
            this.response(request, response, ResultData.fail(ReturnCode.USER_NOT_EXIST.getCode(), ReturnCode.USER_NOT_EXIST.getMessage()));
            return false;
        }
        UserContext.set(userDto);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) {
        //关闭threadLocal
        UserContext.remove();
    }

    /**
     * @param request
     * @param response
     * @param data
     */
    private void response(ServletRequest request, ServletResponse response, ResultData<String> data) throws IOException {
        String msg = objectMapper.writeValueAsString(data);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(msg);
    }
}
