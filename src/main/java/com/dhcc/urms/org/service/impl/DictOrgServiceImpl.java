package com.dhcc.urms.org.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dhcc.urms.org.dto.OrgDTO;
import com.dhcc.urms.org.entity.DictOrg;
import com.dhcc.urms.org.entity.DictOrgTypeSub;
import com.dhcc.urms.org.entity.OrgVO;
import com.dhcc.urms.org.mapper.DictOrgMapper;
import com.dhcc.urms.org.mapper.DictOrgTypeSubMapper;
import com.dhcc.urms.org.service.IDictOrgService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Resource
    private DictOrgTypeSubMapper orgTypeSubMapper;

    @Override
    public List<OrgVO> findOrgInfo(OrgDTO dto) {
        return orgMapper.findOrgInfo(dto);
    }

    @Transactional
    @Override
    public void addOrg(OrgDTO dto) {
        DictOrg org = dto.getOrg();
        List<DictOrgTypeSub> orgTypeSubList = dto.getOrgTypeSubList();

        orgMapper.insert(org);

        for (DictOrgTypeSub orgTypeSub : orgTypeSubList) {
            orgTypeSubMapper.insert(orgTypeSub);
        }
    }

    @Transactional
    @Override
    public void updateOrg(OrgDTO dto) {
        DictOrg org = dto.getOrg();
        DictOrgTypeSub orgTypeSub = dto.getOrgTypeSub();

        orgMapper.updateById(org);

        UpdateWrapper<DictOrgTypeSub> uw = new UpdateWrapper<>();
        uw.eq("ORG_CODE", org.getOrgCode());
        uw.ne("ORG_TYPE_CODE", "DHCC");
        orgTypeSubMapper.update(orgTypeSub, uw);
    }

    @Override
    public void deleteOrg(OrgDTO dto) {
        DictOrg org = dto.getOrg();
        DictOrgTypeSub orgTypeSub = dto.getOrgTypeSub();

        orgMapper.deleteById(org.getOrgCode()); // 删除 org

        UpdateWrapper<DictOrgTypeSub> uw = new UpdateWrapper<>();
        uw.eq("ORG_CODE", org.getOrgCode());
        orgTypeSubMapper.delete(uw); // 删除 orgTypeSub
    }
}
