package com.dhcc.urms.org.blh;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dhcc.urms.common.entity.DTreeNodeVO;
import com.dhcc.urms.common.entity.DTreeVO;
import com.dhcc.urms.common.entity.DictEnum;
import com.dhcc.urms.org.dto.OrgDTO;
import com.dhcc.urms.org.entity.DictOrgType;
import com.dhcc.urms.org.entity.OrgVO;
import com.dhcc.urms.org.service.IDictOrgService;
import com.dhcc.urms.org.service.IDictOrgTypeService;
import com.dhcc.urms.roleprivilege.entity.RolePrivilege;
import com.dhcc.urms.roleprivilege.service.IRolePrivilegeService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Annotation:
 * ORG 逻辑层
 *
 * @Author: Adam Ming
 * @Date: Jul 6, 2020 at 3:07:32 PM
 */
@Component
public class DictOrgBLH implements Serializable {
    private static final long serialVersionUID = 1264059757184684002L;

    @Resource
    private IDictOrgService orgService;

    @Resource
    private IDictOrgTypeService orgTypeService;

    @Resource
    private IRolePrivilegeService rolePrivilegeService;

    /*
     * Annotation:
     * 机构树
     *
     * @Author: Adam Ming
     * @Date: Jul 6, 2020 at 3:07:55 PM
     */
    public void OrgTree(OrgDTO dto) {
        // 机构类型 List，用来构建根结点
        QueryWrapper<DictOrgType> qw = new QueryWrapper<>();
        qw.eq("STATUS", DictEnum.STATUS_ACTIVE.getCode());
        qw.ne("ORG_TYPE_CODE", "DHCC");
        qw.orderByAsc("ODN");
        List<DictOrgType> orgTypeList = orgTypeService.list(qw);

        // 机构 - 机构类型 对应 List
        List<OrgVO> orgVOList = orgService.findOrgInfo(dto);
        // 转为 Map
        Map<String, List<OrgVO>> orgVOMap = orgVOList.stream().collect(Collectors.groupingBy(OrgVO::getOrgTypeCode));

        // 根据 roleId 获取 role-privilege 对应关系，转换为 map
        QueryWrapper<RolePrivilege> qwRP = new QueryWrapper<>();
        qwRP.eq("ROLE_ID", dto.getRoleId());
        qwRP.eq("PRIVI_TYPE_CODE", DictEnum.PRIVI_TYPE_DATA.getCode());
        List<RolePrivilege> rolePrivilegeList = rolePrivilegeService.list(qwRP);
        Map<String, String> privilegeMap = rolePrivilegeList.stream().collect(Collectors.toMap(RolePrivilege::getPriviId, e -> "1"));

        // 添加一个 root 节点
        String roleName = dto.getRoleName();
        String rootTitle = "机构树";
        rootTitle = StringUtils.isEmpty(roleName) ? rootTitle : rootTitle + "-" + roleName;

        DTreeVO dTreeVO = new DTreeVO();
        DTreeNodeVO dTreeNodeVO = new DTreeNodeVO();
        dTreeNodeVO.setId("0");
        dTreeNodeVO.setTitle(rootTitle);
        dTreeVO.getData().add(dTreeNodeVO);

        String isCheck;
        for (DictOrgType orgType : orgTypeList) {
            String orgTypeCode = orgType.getOrgTypeCode();
            if (!orgVOMap.containsKey(orgTypeCode)) { // 如果没有对应关系，不显示这颗树
                continue;
            }

            dTreeNodeVO = new DTreeNodeVO();
            dTreeNodeVO.setId(orgTypeCode);
            dTreeNodeVO.setTitle(orgType.getOrgTypeName());
            dTreeNodeVO.setParentId("0");

            dTreeVO.getData().add(dTreeNodeVO);

            orgVOList = orgVOMap.get(orgTypeCode);
            for (OrgVO orgVO : orgVOList) {
                dTreeNodeVO = new DTreeNodeVO();
                dTreeNodeVO.setId(orgVO.getOrgCode());
                dTreeNodeVO.setTitle(orgVO.getOrgName());
                dTreeNodeVO.setParentId(orgTypeCode);

                isCheck = StringUtils.isEmpty(privilegeMap.get(orgVO.getOrgCode())) ? "0" : "1";
                dTreeNodeVO.setCheckArr(isCheck);

                dTreeVO.getData().add(dTreeNodeVO);
            }
        }

        dto.setDTreeVO(dTreeVO);
    }
}
