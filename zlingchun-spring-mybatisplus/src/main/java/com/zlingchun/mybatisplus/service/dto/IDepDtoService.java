package com.zlingchun.mybatisplus.service.dto;

import com.zlingchun.mybatisplus.doman.dto.DepDto;

/**
 * @author achun
 * @create 2022/7/19
 * @description descrip
 */
public interface IDepDtoService {

    boolean save(DepDto depDto);

    DepDto findDepOne(DepDto depDto);
}
