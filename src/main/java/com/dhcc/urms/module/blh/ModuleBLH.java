package com.dhcc.urms.module.blh;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dhcc.urms.common.entity.DTreeNodeVO;
import com.dhcc.urms.common.entity.DTreeVO;
import com.dhcc.urms.common.entity.DictEnum;
import com.dhcc.urms.module.dto.ModuleDTO;
import com.dhcc.urms.module.entity.Module;
import com.dhcc.urms.roleprivilege.entity.RolePrivilege;
import com.dhcc.urms.module.service.IModuleService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
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

    /*
     * Annotation:
     * 获取模块树
     *
     * @Author: Adam Ming
     * @Date: Jun 23, 2020 at 10:35:08 AM
     */
    public void moduleTree(ModuleDTO dto) {
        QueryWrapper<Module> qw = new QueryWrapper<>();
        qw.eq("STATUS", DictEnum.STATUS_ACTIVE.getValue());
        qw.orderByAsc("ODN");
        List<Module> moduleList = moduleService.list(qw);

        // 根据 roleId 获取 role-privilege 对应关系，转换为 map
        List<RolePrivilege> rolePrivilegeList = moduleService.findRolePrivilege(dto);
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

            isCheck = privilegeMap.get(module.getModuleId().toString()) == null ? "0" : "1";
            dTreeNodeVO.setCheckArr(isCheck);

            dTreeVO.getData().add(dTreeNodeVO);
        }

        dto.setDTreeVO(dTreeVO);
    }
}
