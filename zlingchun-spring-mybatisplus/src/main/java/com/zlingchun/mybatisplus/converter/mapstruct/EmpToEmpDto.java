package com.zlingchun.mybatisplus.converter.mapstruct;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zlingchun.mybatisplus.doman.dto.CustomerDto;
import com.zlingchun.mybatisplus.doman.dto.DepDto;
import com.zlingchun.mybatisplus.doman.dto.EmpDto;
import com.zlingchun.mybatisplus.doman.pojo.Customer;
import com.zlingchun.mybatisplus.doman.pojo.Dep;
import com.zlingchun.mybatisplus.doman.pojo.Emp;
import com.zlingchun.mybatisplus.util.mapstruct.MapStructHelper;
import com.zlingchun.mybatisplus.util.mapstruct.constant.MapStructConstant;
import org.mapstruct.*;

import java.util.List;

/**
 * @author achun
 * @create 2022/7/15
 * @description descrip
 */
@Mapper(componentModel = "spring", uses = MapStructHelper.class)
public interface EmpToEmpDto {

    @Mappings(value = {
            @Mapping(target = "phone", source = "empPhone", qualifiedByName = MapStructConstant.SENSITIVE_PHONE),
            @Mapping(target = "salary", numberFormat = "$#.00"),
            @Mapping(target = "sex", qualifiedByName = MapStructConstant.SEX_NUMTOCHINESE),
            @Mapping(target = "birth", dateFormat = "yyyy-MM-dd"),
            @Mapping(target = "address", qualifiedByName = MapStructConstant.SENSITIVE_ADDRESS),
            @Mapping(target = "depDto", source = "dep"),
            @Mapping(target = "customerDtos", source = "customers"),
            @Mapping(target = "createTime", dateFormat = "yyyy-MM-dd HH:mm:ss"),
            @Mapping(target = "updateTime", dateFormat = "yyyy-MM-dd HH:mm:ss"),
            @Mapping(target = "pageNum", ignore = true),
            @Mapping(target = "pageSize", ignore = true)
    })
    EmpDto emp2EmpDto(Emp emp);

    List<EmpDto> emp2EmpDto(List<Emp> emps);

    Page<EmpDto> emp2EmpDto(Page<Emp> emps);

    @InheritConfiguration(name = "emp2EmpDto")
    void updateEmpDto(Emp emp, @MappingTarget EmpDto empDto);

    @Mapping(target = "createTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "updateTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "pageNum", ignore = true)
    @Mapping(target = "pageSize", ignore = true)
    DepDto dep2DepDto(Dep dep);

    List<DepDto> dep2DepDto(List<Dep> deps);

    Page<DepDto> dep2DepDto(Page<Dep> depPage);

    @Mapping(target = "createTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "updateTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "pageNum", ignore = true)
    @Mapping(target = "pageSize", ignore = true)
    CustomerDto customer2CustomerDto(Customer customer);

    List<CustomerDto> customer2CustomerDto(List<Customer> customer);

    @Mapping(target = "empPhone", source = "phone")
    @Mapping(target = "sex", qualifiedByName = MapStructConstant.SEX_CHINESETONUM)
    @Mapping(target = "address")
    @Mapping(target = "depId", ignore = true)
    @Mapping(target = "version", ignore = true)
    @InheritInverseConfiguration(name = "emp2EmpDto")
    Emp empDto2Emp(EmpDto empDto);

    List<Emp> empDto2Emp(List<EmpDto> empDtos);

    Page<Emp> empDto2Emp(Page<EmpDto> empDtos);

    @InheritConfiguration(name = "empDto2Emp")
    void updateEmp(EmpDto empDto, @MappingTarget Emp emp);

    @InheritInverseConfiguration(name = "dep2DepDto")
    Dep depDto2Dep(DepDto depDto);

    @InheritInverseConfiguration(name = "customer2CustomerDto")
    Customer customerDto2Customer(CustomerDto customerDto);

    List<Customer> customerDto2Customer(List<CustomerDto> customerDto);

    @BeforeMapping
    default void emp2EmpDtoBefore(Emp emp){
    }

    @AfterMapping
    default void emp2EmpDtoAfter(Emp emp, @MappingTarget EmpDto.EmpDtoBuilder empDto){
    }
}
