package com.dhcc.urms.org.service.impl;

import com.dhcc.urms.org.entity.DictOrgType;
import com.dhcc.urms.org.mapper.DictOrgTypeMapper;
import com.dhcc.urms.org.service.IDictOrgTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 机构类型表 服务实现类
 * </p>
 *
 * @author Adam Ming
 * @since 2020-07-06
 */
@Service("dictOrgTypeService")
public class DictOrgTypeServiceImpl extends ServiceImpl<DictOrgTypeMapper, DictOrgType> implements IDictOrgTypeService {

}
