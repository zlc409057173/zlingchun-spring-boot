package com.zlingchun.mybatisplus.service.dto;

import com.zlingchun.mybatisplus.doman.dto.CustomerDto;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author achun
 * @create 2022/7/19
 * @description descrip
 */
public interface ICustomerDtoService {

    boolean save(List<CustomerDto> customerDtos);

    @Transactional(propagation = Propagation.REQUIRED)
    boolean removeByEmpId(List<Long> empIds);
}
