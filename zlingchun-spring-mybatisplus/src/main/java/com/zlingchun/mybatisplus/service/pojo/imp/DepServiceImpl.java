package com.zlingchun.mybatisplus.service.pojo.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zlingchun.mybatisplus.doman.pojo.Dep;
import com.zlingchun.mybatisplus.mapper.DepMapper;
import com.zlingchun.mybatisplus.service.pojo.IDepService;
import org.springframework.stereotype.Service;

/**
 * @author achun
 * @create 2022/7/18
 * @description descrip
 */
@Service
public class DepServiceImpl extends ServiceImpl<DepMapper, Dep> implements IDepService {
}