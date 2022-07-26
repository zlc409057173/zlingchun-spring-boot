package com.zlingchun.mybatisplus.converter.mapstruct;

import com.zlingchun.mybatisplus.doman.dto.UserDto;
import com.zlingchun.mybatisplus.doman.pojo.User;
import com.zlingchun.mybatisplus.util.mapstruct.MapStructHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author achun
 * @create 2022/7/26
 * @description descrip
 */
@Mapper(componentModel = "spring", uses = MapStructHelper.class)
public interface UserConvert {

    @Mapping(target = "version", ignore = true)
    User userDto2User(UserDto userDto);

    UserDto user2UserDto(User user);
}
