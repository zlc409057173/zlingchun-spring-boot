package com.zlingchun.mybatisplus.service.dto;

import com.zlingchun.mybatisplus.doman.dto.UserDto;

import java.util.Map;

/**
 * @author achun
 * @create 2022/7/26
 * @description descrip
 */
public interface IUserDtoService extends BaseService<UserDto> {

    UserDto getUser(String userCode);

    Map<String, Object> login(String usercode, String loginPass);

    UserDto verify(String uuid, String userCode, Long userId);
}
