package com.zlingchun.jdbc.controller;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author achun
 * @create 2022/5/29
 * @description descrip
 */
@RestController
//@RequestMapping(value = "/druid") 这里不能使用/druid开头，原因是因为StatViewServlet会处理/druid/*的请求
public class DruidStatController {
    @GetMapping("/stat")
    public Object druidStat(){
        // 获取数据源的监控数据
        return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
    }
}
