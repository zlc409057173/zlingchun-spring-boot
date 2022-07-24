package com.zlingchun.mybatisplus.converter.mapstruct;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zlingchun.mybatisplus.doman.dto.DepDto;
import com.zlingchun.mybatisplus.doman.dto.DepQueryDto;
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

    /**
     * Dep Convert to DepDto
     * @param dep
     * @return
     */
    @Mapping(target = "createTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "updateTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    DepDto dep2DepDto(Dep dep);

    /**
     * List<Dep> Convert to List<DepDto>
     * @param deps
     * @return
     */
    List<DepDto> dep2DepDto(List<Dep> deps);

    /**
     * Page<Dep> Convert to Page<DepDto>
     * @param depPage
     * @return
     */
    @Mapping(target = "optimizeJoinOfCountSql", ignore = true)
    Page<DepDto> dep2DepDto(Page<Dep> depPage);

    /**
     * DepDto Convert to Dep
     * @param depDto
     * @return
     */
    @Mapping(target = "version", ignore = true)
    @InheritInverseConfiguration(name = "dep2DepDto")
    Dep depDto2Dep(DepDto depDto);

    /**
     * DepDto Convert to DepQueryDto
     * @param depDto
     * @return
     */
    @Mapping(target = "pageNum", ignore = true)
    @Mapping(target = "pageSize", ignore = true)
    DepQueryDto depDto2DepQueryDto(DepDto depDto);

    /**
     * DepQueryDto Convert to Dep
     * @param depQueryDto
     * @return
     */
    @Mapping(target = "version", ignore = true)
    @InheritInverseConfiguration(name = "dep2DepDto")
    Dep depQueryDto2Dep(DepQueryDto depQueryDto);

    @BeforeMapping
    default void toDepDtoBefore(Dep dep){
    }

    @AfterMapping
    default void toDepDtoAfter(Dep dep, @MappingTarget DepDto.DepDtoBuilder depDto){
    }
}
