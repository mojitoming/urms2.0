package com.dhcc.urms.condfilter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dhcc.urms.condfilter.entity.DictFilterKeyword;
import com.dhcc.urms.condfilter.mapper.CondFilterMapper;
import com.dhcc.urms.condfilter.service.ICondFilterService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 过滤器关键字 服务实现类
 * </p>
 *
 * @author Adam Ming
 * @since 2020-04-17
 */
@Service("condFilterService")
public class CondFilterServiceImpl extends ServiceImpl<CondFilterMapper, DictFilterKeyword> implements ICondFilterService {

}
