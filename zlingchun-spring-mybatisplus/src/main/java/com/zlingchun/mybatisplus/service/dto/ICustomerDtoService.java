package com.zlingchun.mybatisplus.service.dto;

import com.zlingchun.mybatisplus.doman.dto.CustomerDto;

import java.util.List;

/**
 * @author achun
 * @create 2022/7/19
 * @description descrip
 */
public interface ICustomerDtoService {

    boolean save(List<CustomerDto> customerDtos);

    boolean saveBatch(List<CustomerDto> customerDtos);

    boolean remove(List<CustomerDto> customerDtos);
}
