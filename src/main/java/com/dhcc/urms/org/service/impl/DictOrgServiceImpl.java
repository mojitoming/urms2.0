package com.dhcc.urms.org.service.impl;

import com.dhcc.urms.org.dto.OrgDTO;
import com.dhcc.urms.org.entity.DictOrg;
import com.dhcc.urms.org.entity.OrgVO;
import com.dhcc.urms.org.mapper.DictOrgMapper;
import com.dhcc.urms.org.service.IDictOrgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 机构表 服务实现类
 * </p>
 *
 * @author Adam Ming
 * @since 2020-07-06
 */
@Service("dictOrgService")
public class DictOrgServiceImpl extends ServiceImpl<DictOrgMapper, DictOrg> implements IDictOrgService {
    @Resource
    private DictOrgMapper orgMapper;

    @Override
    public List<OrgVO> findOrgInfo(OrgDTO dto) {
        return orgMapper.findOrgInfo(dto);
    }
}
