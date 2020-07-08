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

    /*
     * Annotation:
     * 新增机构
     *
     * @Author: Adam Ming
     * @Date: Jul 7, 2020 at 11:36:44 AM
     */
    void addOrg(OrgDTO dto);

    /*
     * Annotation:
     * 修改机构
     *
     * @Author: Adam Ming
     * @Date: Jul 7, 2020 at 11:37:11 AM
     */
    void updateOrg(OrgDTO dto);

    /*
     * Annotation:
     * 删除机构
     *
     * @Author: Adam Ming
     * @Date: Jul 7, 2020 at 11:37:31 AM
     */
    void deleteOrg(OrgDTO dto);
}
