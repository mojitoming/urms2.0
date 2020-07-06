package com.dhcc.urms.org.service;

import com.dhcc.urms.org.dto.OrgDTO;
import com.dhcc.urms.org.entity.DictOrg;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dhcc.urms.org.entity.OrgVO;

import java.util.List;

/**
 * <p>
 * 机构表 服务类
 * </p>
 *
 * @author Adam Ming
 * @since 2020-07-06
 */
public interface IDictOrgService extends IService<DictOrg> {

    /*
     * Annotation:
     * 获取机构信息
     * 机构、机构类型 对应
     *
     * @Author: Adam Ming
     * @Date: Jul 6, 2020 at 3:32:31 PM
     */
    List<OrgVO> findOrgInfo(OrgDTO dto);
}
