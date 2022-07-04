package com.zlingchun.mybatis.converter.mapper;

import cn.hutool.core.lang.Snowflake;
import com.zlingchun.mybatis.constant.MapStructConstant;
import com.zlingchun.mybatis.entity.dto.DepDto;
import com.zlingchun.mybatis.entity.dto.EmpDto;
import com.zlingchun.mybatis.entity.pojo.Dep;
import com.zlingchun.mybatis.entity.pojo.Emp;
import com.zlingchun.mybatis.utils.MapStructHelper;
import com.zlingchun.mybatis.utils.test.RandomInfo;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author achun
 * @create 2022/7/3
 * @description descrip
 */
@Mapper(uses = MapStructHelper.class)
public interface EmpMapper {

    EmpMapper INSTANCE = Mappers.getMapper(EmpMapper.class);

    BigDecimal[] salary = {BigDecimal.valueOf(8000.01), BigDecimal.valueOf(10000.5), BigDecimal.valueOf(3000)};
    String[] sexs = {"男", "女"};
    String[] depNames = {"总经办", "研发", "测试", "产品"};
    default EmpDto baseData() {
        Snowflake snowflake = new Snowflake(1, 1);
        return EmpDto.builder().empName(RandomInfo.getRandomName(null))
                .empNum(snowflake.nextIdStr().substring(8))
                .telNumber(RandomInfo.createMobile(RandomInfo.randomInt(3)))
                .email(RandomInfo.getRandomQQEmail())
                .birthday(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(RandomInfo.getRandomBirthday()))
                .salary(salary[RandomInfo.randomInt(salary.length)].multiply(BigDecimal.valueOf(RandomInfo.random.nextFloat())).setScale(2, RoundingMode.HALF_UP))
                .sex(sexs[RandomInfo.randomInt(sexs.length)])
                .address(RandomInfo.getRandomAddress())
                .depName(depNames[RandomInfo.randomInt(depNames.length)])
                .did(RandomInfo.randomInt(100)).build();
    }

    @Mappings(value = {
            @Mapping(target = "birthday", dateFormat = "yyyy-MM-dd"),
            @Mapping(target = "salary", numberFormat = "#.00"),
            @Mapping(target = "address", source = "empAddress"),
            @Mapping(target = "sex", expression = "java(com.zlingchun.mybatis.utils.MapStructHelper.sexChar2Str(emp.getSex()))"),
            @Mapping(target = "email", ignore = true),
            @Mapping(target = "depDto", source = "dep"),
            @Mapping(target = "telNumber", qualifiedByName = MapStructConstant.SENSITIVE_TELNUMBER)
    })
    EmpDto emp2EmpDto(Emp emp);

    List<EmpDto> emp2EmpDto(List<Emp> emps);

    @Mapping(target = "name", source = "depName")
    DepDto dep2DepDto(Dep dep);

    @Mapping(target = "sex", qualifiedByName = MapStructConstant.SEX_STR_2_CHAR)
    @Mapping(target = "telNumber")
    @InheritInverseConfiguration(name = "emp2EmpDto")
    Emp empDto2Emp(EmpDto empDto);

    List<Emp> empDto2Emp(List<EmpDto> empDtos);

    @InheritConfiguration(name = "emp2EmpDto")
    void updateEmpDto(Emp emp, @MappingTarget EmpDto empDto);

    @BeforeMapping
    default void emp2EmpDtoBefore(Emp emp){
        emp.setDep(Dep.builder().did(20).depName("经理").build());
    }

    @AfterMapping
    default void emp2EmpDtoAfter(Emp emp, @MappingTarget EmpDto.EmpDtoBuilder empDto){
        empDto.email(emp.getEmail());
    }
}
