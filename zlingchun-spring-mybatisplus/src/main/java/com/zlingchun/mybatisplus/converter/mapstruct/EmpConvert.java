package com.zlingchun.mybatisplus.converter.mapstruct;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zlingchun.mybatisplus.doman.dto.CustomerDto;
import com.zlingchun.mybatisplus.doman.dto.DepDto;
import com.zlingchun.mybatisplus.doman.dto.EmpDto;
import com.zlingchun.mybatisplus.doman.dto.EmpQueryDto;
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
public interface EmpConvert {

    @Mappings(value = {
            @Mapping(target = "phone", source = "empPhone", qualifiedByName = MapStructConstant.SENSITIVE_PHONE),
            @Mapping(target = "salary", numberFormat = "$#.000"),
            @Mapping(target = "sex", qualifiedByName = MapStructConstant.SEX_NUMTOCHINESE),
            @Mapping(target = "birth", dateFormat = "yyyy-MM-dd"),
            @Mapping(target = "address", qualifiedByName = MapStructConstant.SENSITIVE_ADDRESS),
            @Mapping(target = "depDto", source = "dep"),
            @Mapping(target = "customerDtos", source = "customers"),
            @Mapping(target = "createTime", dateFormat = "yyyy-MM-dd HH:mm:ss"),
            @Mapping(target = "updateTime", dateFormat = "yyyy-MM-dd HH:mm:ss"),
    })
    EmpDto emp2EmpDto(Emp emp);

    List<EmpDto> emp2EmpDto(List<Emp> emps);

    @Mapping(target = "optimizeJoinOfCountSql", ignore = true)
    Page<EmpDto> emp2EmpDto(Page<Emp> emps);

    @InheritConfiguration(name = "emp2EmpDto")
    void updateEmpDto(Emp emp, @MappingTarget EmpDto empDto);

    @Mapping(target = "empPhone", source = "phone")
    @Mapping(target = "sex", qualifiedByName = MapStructConstant.SEX_CHINESETONUM)
    @Mapping(target = "address")
    @Mapping(target = "depId", source = "depDto.id")
    @Mapping(target = "version", ignore = true)
    @InheritInverseConfiguration(name = "emp2EmpDto")
    Emp empDto2Emp(EmpDto empDto);

    List<Emp> empDto2Emp(List<EmpDto> empDtos);

    @Mapping(target = "empPhone", source = "phone")
    @Mapping(target = "sex", qualifiedByName = MapStructConstant.SEX_CHINESETONUM)
    @Mapping(target = "address")
    @Mapping(target = "depId", source = "depId")
    @Mapping(target = "dep.id", source = "depId")
    @Mapping(target = "dep.depName", source = "depName")
    @Mapping(target = "dep.depNo", source = "depNo")
    @Mapping(target = "customers", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    Emp empQueryDto2Emp(EmpQueryDto empQueryDto);

    @Mapping(target = "depId", source = "depDto.id")
    @Mapping(target = "depName", source = "depDto.depName")
    @Mapping(target = "depNo", source = "depDto.depNo")
    @Mapping(target = "pageNum", ignore = true)
    @Mapping(target = "pageSize", ignore = true)
    EmpQueryDto empDto2EmpQueryDto(EmpDto empDto);

    @Mapping(target = "createTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "updateTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    DepDto dep2DepDto(Dep dep);

    List<DepDto> dep2DepDto(List<Dep> deps);

    @Mapping(target = "version", ignore = true)
    @InheritInverseConfiguration(name = "dep2DepDto")
    Dep depDto2Dep(DepDto depDto);

    @Mapping(target = "sex", qualifiedByName = MapStructConstant.SEX_NUMTOCHINESE)
    @Mapping(target = "createTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "updateTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    CustomerDto customer2CustomerDto(Customer customer);

    List<CustomerDto> customer2CustomerDto(List<Customer> customer);

    @Mapping(target = "version", ignore = true)
    @Mapping(target = "sex", qualifiedByName = MapStructConstant.SEX_CHINESETONUM)
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
