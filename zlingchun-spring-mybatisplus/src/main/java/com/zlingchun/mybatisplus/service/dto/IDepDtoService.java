package com.zlingchun.mybatisplus.service.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zlingchun.mybatisplus.doman.dto.DepDto;

import java.util.List;

/**
 * @author achun
 * @create 2022/7/19
 * @description descrip
 */
public interface IDepDtoService {

    boolean save(DepDto depDto);

    DepDto findDepOne(DepDto depDto);

    boolean remove(DepDto depDto);

    boolean remove(Long id);

    boolean update(Long id, DepDto depDto);

    List<DepDto> list(DepDto depDto);

    Page<DepDto> page(DepDto depDto);
}
