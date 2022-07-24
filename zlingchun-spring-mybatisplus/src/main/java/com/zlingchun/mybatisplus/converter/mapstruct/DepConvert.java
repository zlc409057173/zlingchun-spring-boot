package com.zlingchun.mybatisplus.converter.mapstruct;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zlingchun.mybatisplus.doman.dto.DepDto;
import com.zlingchun.mybatisplus.doman.pojo.Dep;
import com.zlingchun.mybatisplus.util.mapstruct.MapStructHelper;
import org.mapstruct.*;

import java.util.List;

/**
 * @author achun
 * @create 2022/7/24
 * @description descrip
 */
@Mapper(componentModel = "spring", uses = MapStructHelper.class)
public interface DepConvert {

    @Mapping(target = "createTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "updateTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "pageNum", ignore = true)
    @Mapping(target = "pageSize", ignore = true)
    DepDto dep2DepDto(Dep dep);

    List<DepDto> dep2DepDto(List<Dep> deps);

    @Mapping(target = "optimizeJoinOfCountSql", ignore = true)
    Page<DepDto> dep2DepDto(Page<Dep> depPage);

    @Mapping(target = "version", ignore = true)
    @InheritInverseConfiguration(name = "dep2DepDto")
    Dep depDto2Dep(DepDto depDto);

    @BeforeMapping
    default void toDepDtoBefore(Dep dep){
    }

    @AfterMapping
    default void toDepDtoAfter(Dep dep, @MappingTarget DepDto.DepDtoBuilder depDto){
    }
}
