package com.dhcc.urms.org.controller;

import com.alibaba.fastjson.JSONObject;
import com.dhcc.urms.common.entity.DTreeVO;
import com.dhcc.urms.org.blh.DictOrgBLH;
import com.dhcc.urms.org.dto.OrgDTO;
import com.dhcc.urms.org.entity.DictOrgType;
import com.dhcc.urms.org.entity.OrgVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Annotation:
 * ORG REST
 *
 * @Author: Adam Ming
 * @Date: Jul 6, 2020 at 4:13:14 PM
 */
@RestController
@RequestMapping("/org-api")
public class DictOrgRest {

    @Resource
    private DictOrgBLH orgBLH;

    @Resource
    private OrgDTO orgDTO;

    /*
     * Annotation:
     * 获取机构树
     *
     * @Author: Adam Ming
     * @Date: Jul 6, 2020 at 4:16:11 PM
     */
    @GetMapping("/org-tree")
    public DTreeVO orgTree(OrgDTO dto) {
        orgBLH.OrgTree(dto);

        return dto.getDTreeVO();
    }

    /*
     * Annotation:
     * 获取全部机构树
     *
     * @Author: Adam Ming
     * @Date: Jul 7, 2020 at 10:10:02 AM
     */
    @GetMapping("/org-all-tree")
    public DTreeVO orgAllTree(OrgDTO dto) {
        orgBLH.orgAllTree(dto);

        return dto.getDTreeVO();
    }

    /*
     * Annotation:
     * 获取机构信息
     *
     * @Author: Adam Ming
     * @Date: Jul 7, 2020 at 10:42:27 AM
     */
    @GetMapping("/org")
    public OrgDTO findOrg(OrgDTO dto) {
        orgBLH.findOrg(dto);

        return dto;
    }

    /*
     * Annotation:
     * 根据机构类型获取机构信息
     *
     * @Author: Adam Ming
     * @Date: Jul 7, 2020 at 8:21:24 PM
     */
    @GetMapping("/org-by-type")
    public OrgDTO findOrgByType(OrgDTO dto) {
        orgBLH.findOrgByType(dto);

        return dto;
    }

    /*
     * Annotation:
     * 新建机构
     *
     * @Author: Adam Ming
     * @Date: Jul 7, 2020 at 10:58:57 AM
     */
    @PostMapping("/org/modify")
    public OrgDTO addOrg(@RequestBody JSONObject jsonObject) {
        OrgVO orgVO = jsonObject.toJavaObject(OrgVO.class);

        orgDTO.setOrgVO(orgVO);

        orgBLH.addOrg(orgDTO);

        return orgDTO;
    }

    /*
     * Annotation:
     * 修改机构
     *
     * @Author: Adam Ming
     * @Date: Jul 7, 2020 at 4:28:08 PM
     */
    @PutMapping("/org/modify")
    public OrgDTO updateOrg(@RequestBody JSONObject jsonObject) {
        OrgVO orgVO = jsonObject.toJavaObject(OrgVO.class);

        orgDTO.setOrgVO(orgVO);

        orgBLH.updateOrg(orgDTO);

        return orgDTO;
    }

    /*
     * Annotation:
     * 删除机构
     *
     * @Author: Adam Ming
     * @Date: Jul 7, 2020 at 4:29:41 PM
     */
    @DeleteMapping("/org/modify")
    public OrgDTO deleteOrg(@RequestBody JSONObject jsonObject) {
        OrgVO orgVO = jsonObject.toJavaObject(OrgVO.class);

        orgDTO.setOrgVO(orgVO);

        orgBLH.deleteOrg(orgDTO);

        return orgDTO;
    }

    /*
     * Annotation:
     * 机构顺序调整
     *
     * @Author: Adam Ming
     * @Date: Jul 8, 2020 at 10:52:45 AM
     */
    @PutMapping("/org-odn")
    public OrgDTO updateOrgOdn(OrgDTO dto) {
        orgBLH.updateOrgOdn(dto);

        return dto;
    }

    /*
     * Annotation:
     * 获取机构类型
     *
     * @Author: Adam Ming
     * @Date: Jul 7, 2020 at 5:54:29 PM
     */
    @GetMapping("/org-type")
    public OrgDTO findOrgType(OrgDTO dto) {
        orgBLH.findOrgType(dto);

        return dto;
    }

    /*
     * Annotation:
     * 新增机构类型
     *
     * @Author: Adam Ming
     * @Date: Jul 7, 2020 at 4:31:22 PM
     */
    @PostMapping("/org-type/modify")
    public OrgDTO addOrgType(@RequestBody JSONObject jsonObject) {
        DictOrgType orgType = jsonObject.toJavaObject(DictOrgType.class);

        orgDTO.setOrgType(orgType);

        orgBLH.addOrgType(orgDTO);

        return orgDTO;
    }

    /*
     * Annotation:
     * 修改机构类型
     *
     * @Author: Adam Ming
     * @Date: Jul 7, 2020 at 4:31:22 PM
     */
    @PutMapping("/org-type/modify")
    public OrgDTO updateOrgType(@RequestBody JSONObject jsonObject) {
        DictOrgType orgType = jsonObject.toJavaObject(DictOrgType.class);

        orgDTO.setOrgType(orgType);

        orgBLH.updateOrgType(orgDTO);

        return orgDTO;
    }

    /*
     * Annotation:
     * 删除机构类型
     *
     * @Author: Adam Ming
     * @Date: Jul 7, 2020 at 4:31:22 PM
     */
    @DeleteMapping("/org-type/modify")
    public OrgDTO deleteOrgType(@RequestBody JSONObject jsonObject) {
        DictOrgType orgType = jsonObject.toJavaObject(DictOrgType.class);

        orgDTO.setOrgType(orgType);

        orgBLH.deleteOrgType(orgDTO);

        return orgDTO;
    }

    /*
     * Annotation:
     * 机构类型顺序调整
     *
     * @Author: Adam Ming
     * @Date: Jul 8, 2020 at 11:06:58 AM
     */
    @PutMapping("/org-type-odn")
    public OrgDTO updateOrgTypeOdn(OrgDTO dto) {
        orgBLH.updateOrgTypeOdn(dto);

        return dto;
    }
}
