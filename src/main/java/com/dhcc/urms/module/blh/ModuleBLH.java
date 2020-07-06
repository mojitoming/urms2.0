package com.dhcc.urms.module.blh;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dhcc.urms.common.entity.DTreeNodeVO;
import com.dhcc.urms.common.entity.DTreeVO;
import com.dhcc.urms.common.entity.DictEnum;
import com.dhcc.urms.module.dto.ModuleDTO;
import com.dhcc.urms.module.entity.Module;
import com.dhcc.urms.module.entity.ModuleVO;
import com.dhcc.urms.module.service.IModuleService;
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
 * 模块逻辑层
 *
 * @Author: Adam Ming
 * @Date: Jun 23, 2020 at 11:21:04 AM
 */
@Component
public class ModuleBLH implements Serializable {

    private static final long serialVersionUID = 7773705183126437209L;

    @Resource
    private IModuleService moduleService;

    @Resource
    private IRolePrivilegeService rolePrivilegeService;

    /*
     * Annotation:
     * 获取模块树
     *
     * @Author: Adam Ming
     * @Date: Jun 23, 2020 at 10:35:08 AM
     */
    public void moduleTree(ModuleDTO dto) {
        QueryWrapper<Module> qwM = new QueryWrapper<>();
        if (!StringUtils.isEmpty(dto.getStatus())) {
            qwM.eq("STATUS", dto.getStatus());
        }
        qwM.orderByAsc("ODN");
        List<Module> moduleList = moduleService.list(qwM);

        // 根据 roleId 获取 role-privilege 对应关系，转换为 map
        QueryWrapper<RolePrivilege> qwRP = new QueryWrapper<>();
        qwRP.eq("ROLE_ID", dto.getRoleId());
        qwRP.eq("PRIVI_TYPE_CODE", DictEnum.PRIVI_TYPE_MODULE.getCode());
        List<RolePrivilege> rolePrivilegeList = rolePrivilegeService.list(qwRP);
        Map<String, String> privilegeMap = rolePrivilegeList.stream().collect(Collectors.toMap(RolePrivilege::getPriviId, e -> "1"));

        // 添加一个 root 节点
        String roleName = dto.getRoleName();
        String rootTitle = "模块树";
        rootTitle = StringUtils.isEmpty(roleName) ? rootTitle : rootTitle + "-" + roleName;

        DTreeVO dTreeVO = new DTreeVO();
        DTreeNodeVO dTreeNodeVO = new DTreeNodeVO();
        dTreeNodeVO.setId("0");
        dTreeNodeVO.setTitle(rootTitle);
        dTreeVO.getData().add(dTreeNodeVO);

        String isCheck;
        for (Module module : moduleList) {
            dTreeNodeVO = new DTreeNodeVO();
            dTreeNodeVO.setId(module.getModuleId().toString());
            dTreeNodeVO.setTitle(module.getModuleName());
            dTreeNodeVO.setParentId(module.getParentId().toString());

            isCheck = StringUtils.isEmpty(privilegeMap.get(module.getModuleId().toString())) ? "0" : "1";
            dTreeNodeVO.setCheckArr(isCheck);

            dTreeVO.getData().add(dTreeNodeVO);
        }

        dto.setDTreeVO(dTreeVO);
    }

    /*
     * Annotation:
     * 获取 module
     *
     * @Author: Adam Ming
     * @Date: Jul 2, 2020 at 4:04:36 PM
     */
    public void findModule(ModuleDTO dto) {
        QueryWrapper<Module> qw = new QueryWrapper<>();
        if (!StringUtils.isEmpty(dto.getModuleId())) {
            qw.eq("MODULE_ID", dto.getModuleId());
        }
        qw.orderByAsc("ODN");
        List<Module> moduleList = moduleService.list(qw); // raw data
        List<ModuleVO> moduleVOList; // handle data

        moduleVOList = moduleList.stream().map(e -> {
            // copy module to moduleVO
            ModuleVO vo = new ModuleVO();
            BeanUtils.copyProperties(e, vo);
            vo.setStatusName(Objects.requireNonNull(DictEnum.getDictEnumByCode(e.getStatus())).getName());
            vo.setModuleTypeName(Objects.requireNonNull(DictEnum.getDictEnumByCode(e.getModuleType())).getName());

            return vo;
        }).collect(Collectors.toList());

        dto.setModuleVOList(moduleVOList);
    }

    /*
     * Annotation:
     * 根据 module_id 获取直接子节点
     *
     * @Author: Adam Ming
     * @Date: Jul 3, 2020 at 3:10:31 PM
     */
    public void findSubModule(ModuleDTO dto) {
        QueryWrapper<Module> qw = new QueryWrapper<>();
        qw.eq("PARENT_ID", dto.getModuleId());
        qw.orderByAsc("ODN");
        List<Module> moduleList = moduleService.list(qw);

        dto.setModuleList(moduleList);
    }

    /*
     * Annotation:
     * 模块顺序保存
     *
     * @Author: Adam Ming
     * @Date: Jul 3, 2020 at 4:11:33 PM
     */
    public void updateModuleOdn(ModuleDTO dto) {
        List<String> moduleIdList = dto.getModuleIdList();

        List<Module> moduleList = new ArrayList<>();
        Module module;
        for (int i = 0; i < moduleIdList.size(); i++) {
            module = new Module();

            module.setModuleId(Long.parseLong(moduleIdList.get(i)));
            module.setOdn((long) i);

            moduleList.add(module);
        }

        moduleService.updateBatchById(moduleList);
    }

    /*
     * Annotation:
     * 新建模块
     *
     * @Author: Adam Ming
     * @Date: Jul 2, 2020 at 5:20:56 PM
     */
    public void addModule(ModuleDTO dto) {
        Module module = dto.getModule();
        convertStatus(module);

        moduleService.save(module);
    }

    /*
     * Annotation:
     * 修改模块
     *
     * @Author: Adam Ming
     * @Date: Jul 2, 2020 at 5:21:27 PM
     */
    public void updateModule(ModuleDTO dto) {
        Module module = dto.getModule();
        convertStatus(module);

        moduleService.updateById(module);
    }

    /*
     * Annotation:
     * 删除模块
     *
     * @Author: Adam Ming
     * @Date: Jul 2, 2020 at 5:21:45 PM
     */
    public void deleteModule(ModuleDTO dto) {
        moduleService.deleteModule(dto);
    }

    // =================== 私有方法分割线 ===================

    /*
     * Annotation:
     * Role Status 状态转变
     *
     * @Author: Adam Ming
     * @Date: Jun 22, 2020 at 5:33:29 PM
     */
    private void convertStatus(Module module) {
        if ("on".equals(module.getStatus())) {
            module.setStatus(DictEnum.STATUS_ACTIVE.getCode());
        } else {
            module.setStatus(DictEnum.STATUS_INACTIVE.getCode());
        }
    }
}
