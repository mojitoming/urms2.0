package com.dhcc.urms.org.blh;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dhcc.urms.common.entity.DTreeNodeVO;
import com.dhcc.urms.common.entity.DTreeVO;
import com.dhcc.urms.common.entity.DictEnum;
import com.dhcc.urms.org.dto.OrgDTO;
import com.dhcc.urms.org.entity.DictOrg;
import com.dhcc.urms.org.entity.DictOrgType;
import com.dhcc.urms.org.entity.DictOrgTypeSub;
import com.dhcc.urms.org.entity.OrgVO;
import com.dhcc.urms.org.service.IDictOrgService;
import com.dhcc.urms.org.service.IDictOrgTypeService;
import com.dhcc.urms.roleprivilege.entity.RolePrivilege;
import com.dhcc.urms.roleprivilege.service.IRolePrivilegeService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
        if (!StringUtils.isEmpty(dto.getStatus())) {
            qw.eq("STATUS", dto.getStatus());
        }
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
            if (!orgVOMap.containsKey(orgTypeCode) && !dto.isAllTree()) { // 如果没有对应关系，不显示这颗树
                continue;
            }

            dTreeNodeVO = new DTreeNodeVO();
            dTreeNodeVO.setId(orgTypeCode);
            dTreeNodeVO.setTitle(orgType.getOrgTypeName());
            dTreeNodeVO.setParentId("0");

            dTreeVO.getData().add(dTreeNodeVO);

            orgVOList = orgVOMap.get(orgTypeCode);
            if (orgVOList == null) continue;
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

    public void orgAllTree(OrgDTO dto) {
        dto.setAllTree(true);
        OrgTree(dto);
    }

    /*
     * Annotation:
     * 获取 ORG
     *
     * @Author: Adam Ming
     * @Date: Jul 7, 2020 at 10:43:21 AM
     */
    public void findOrg(OrgDTO dto) {
        QueryWrapper<DictOrg> qw = new QueryWrapper<>();
        if (!StringUtils.isEmpty(dto.getOrgCode())) {
            qw.eq("ORG_CODE", dto.getOrgCode());
        }
        qw.orderByAsc("ODN");
        List<DictOrg> orgList = orgService.list(qw);

        List<OrgVO> orgVOList = orgList.stream().map(e -> {
            OrgVO vo = new OrgVO();
            BeanUtils.copyProperties(e, vo);
            vo.setStatusName(Objects.requireNonNull(DictEnum.getDictEnumByCode(e.getStatus())).getName());

            return vo;
        }).collect(Collectors.toList());

        dto.setOrgVOList(orgVOList);
    }

    /*
     * Annotation:
     * 根据 orgType 获取机构信息
     *
     * @Author: Adam Ming
     * @Date: Jul 7, 2020 at 8:10:21 PM
     */
    public void findOrgByType(OrgDTO dto) {
        List<OrgVO> orgVOList = orgService.findOrgInfo(dto);

        dto.setOrgVOList(orgVOList);
    }

    /*
     * Annotation:
     * 新增机构
     *
     * @Author: Adam Ming
     * @Date: Jul 7, 2020 at 11:02:21 AM
     */
    public void addOrg(OrgDTO dto) {

        divideOrgVO(dto);

        List<DictOrgTypeSub> orgTypeSubList = new ArrayList<>();
        DictOrgTypeSub orgTypeSub = new DictOrgTypeSub();
        orgTypeSub.setOrgTypeCode("DHCC");
        orgTypeSub.setOrgCode(dto.getOrgVO().getOrgCode());

        orgTypeSubList.add(orgTypeSub);
        orgTypeSubList.add(dto.getOrgTypeSub());

        dto.setOrgTypeSubList(orgTypeSubList);

        orgService.addOrg(dto);
    }

    /*
     * Annotation:
     * 修改机构
     *
     * @Author: Adam Ming
     * @Date: Jul 7, 2020 at 11:21:44 AM
     */
    public void updateOrg(OrgDTO dto) {
        divideOrgVO(dto);

        orgService.updateOrg(dto);
    }

    /*
     * Annotation:
     * 删除机构
     *
     * @Author: Adam Ming
     * @Date: Jul 7, 2020 at 11:22:46 AM
     */
    public void deleteOrg(OrgDTO dto) {
        divideOrgVO(dto);

        orgService.deleteOrg(dto);
    }

    /*
     * Annotation:
     * 机构顺序调整
     *
     * @Author: Adam Ming
     * @Date: Jul 8, 2020 at 10:54:27 AM
     */
    public void updateOrgOdn(OrgDTO dto) {
        List<String> orgCodeList = dto.getOrgCodeList();

        List<DictOrg> orgList = new ArrayList<>();
        DictOrg org;
        for (int i = 0; i < orgCodeList.size(); i++) {
            org = new DictOrg();

            org.setOrgCode(orgCodeList.get(i));
            org.setOdn((long) i);

            orgList.add(org);
        }

        orgService.updateBatchById(orgList);
    }

    /*
     * Annotation:
     * 获取 OrgType
     *
     * @Author: Adam Ming
     * @Date: Jul 7, 2020 at 5:56:53 PM
     */
    public void findOrgType(OrgDTO dto) {
        QueryWrapper<DictOrgType> qw = new QueryWrapper<>();
        if (!StringUtils.isEmpty(dto.getOrgTypeCode())) {
            qw.eq("ORG_TYPE_CODE", dto.getOrgTypeCode());
        }
        qw.orderByAsc("ODN");
        List<DictOrgType> orgTypeList = orgTypeService.list(qw);

        List<OrgVO> orgVOList = orgTypeList.stream().map(e -> {
            OrgVO vo = new OrgVO();
            BeanUtils.copyProperties(e, vo);
            vo.setStatusName(Objects.requireNonNull(DictEnum.getDictEnumByCode(e.getStatus())).getName());

            return vo;
        }).collect(Collectors.toList());

        dto.setOrgVOList(orgVOList);
    }

    /*
     * Annotation:
     * 新建机构类型
     *
     * @Author: Adam Ming
     * @Date: Jul 7, 2020 at 11:26:27 AM
     */
    public void addOrgType(OrgDTO dto) {
        DictOrgType orgType = dto.getOrgType();
        orgType.setStatus(DictEnum.convertStatus(orgType.getStatus()));

        orgTypeService.save(orgType);
    }

    /*
     * Annotation:
     * 修改机构类型
     *
     * @Author: Adam Ming
     * @Date: Jul 7, 2020 at 11:28:55 AM
     */
    public void updateOrgType(OrgDTO dto) {
        DictOrgType orgType = dto.getOrgType();
        orgType.setStatus(DictEnum.convertStatus(orgType.getStatus()));

        orgTypeService.updateById(orgType);
    }

    /*
     * Annotation:
     * 删除机构类型
     *
     * @Author: Adam Ming
     * @Date: Jul 7, 2020 at 11:30:05 AM
     */
    public void deleteOrgType(OrgDTO dto) {
        orgTypeService.removeById(dto.getOrgType().getOrgTypeCode());
    }

    /*
     * Annotation:
     * 机构类型顺序调整
     *
     * @Author: Adam Ming
     * @Date: Jul 8, 2020 at 11:03:20 AM
     */
    public void updateOrgTypeOdn(OrgDTO dto) {
        List<String> orgTypeCodeList = dto.getOrgTypeCodeList();

        List<DictOrgType> orgTypeList = new ArrayList<>();
        DictOrgType orgType;
        for (int i = 0; i < orgTypeCodeList.size(); i++) {
            orgType = new DictOrgType();

            orgType.setOrgTypeCode(orgTypeCodeList.get(i));
            orgType.setOdn((long) i);

            orgTypeList.add(orgType);
        }

        orgTypeService.updateBatchById(orgTypeList);
    }

    // ==================== private method ====================
    /*
     * Annotation:
     * 对象分割
     * OrgVO ---> Org
     *        |
     *        --> OrgTypeSub
     *
     * @Author: Adam Ming
     * @Date: Jul 7, 2020 at 4:22:43 PM
     */
    private void divideOrgVO(OrgDTO dto) {
        OrgVO orgVO = dto.getOrgVO();

        DictOrg org = new DictOrg(); // 分解对象 DictOrg
        org.setOrgCode(orgVO.getOrgCode());
        org.setOrgName(orgVO.getOrgName());
        org.setStatus(DictEnum.convertStatus(orgVO.getStatus()));

        DictOrgTypeSub orgTypeSub = new DictOrgTypeSub(); // 分解对象 DictOrgTypeSub
        orgTypeSub.setOrgTypeCode(orgVO.getOrgTypeCode());
        orgTypeSub.setOrgCode(orgVO.getOrgCode());

        dto.setOrg(org);
        dto.setOrgTypeSub(orgTypeSub);
    }
}
