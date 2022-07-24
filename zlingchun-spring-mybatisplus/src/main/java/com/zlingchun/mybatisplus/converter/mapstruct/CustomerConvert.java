package com.zlingchun.mybatisplus.converter.mapstruct;

import com.zlingchun.mybatisplus.doman.dto.CustomerDto;
import com.zlingchun.mybatisplus.doman.pojo.Customer;
import com.zlingchun.mybatisplus.util.mapstruct.MapStructHelper;
import com.zlingchun.mybatisplus.util.mapstruct.constant.MapStructConstant;
import org.mapstruct.*;

import java.util.List;

/**
 * @author achun
 * @create 2022/7/24
 * @description descrip
 */
@Mapper(componentModel = "spring", uses = MapStructHelper.class)
public interface CustomerConvert {

    @Mapping(target = "sex", qualifiedByName = MapStructConstant.SEX_NUMTOCHINESE)
    @Mapping(target = "createTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "updateTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "pageNum", ignore = true)
    @Mapping(target = "pageSize", ignore = true)
    CustomerDto customer2CustomerDto(Customer customer);

    List<CustomerDto> customer2CustomerDto(List<Customer> customer);

    @Mapping(target = "sex", qualifiedByName = MapStructConstant.SEX_CHINESETONUM)
    @Mapping(target = "version", ignore = true)
    @InheritInverseConfiguration(name = "customer2CustomerDto")
    Customer customerDto2Customer(CustomerDto customerDto);

    List<Customer> customerDto2Customer(List<CustomerDto> customerDto);

    @BeforeMapping
    default void toCustDtoBefore(Customer customer){
    }

    @AfterMapping
    default void toCustDtoAfter(Customer customer, @MappingTarget CustomerDto.CustomerDtoBuilder customerDto){
    }
}
