package com.dhcc.urms.org.mapper;

import com.dhcc.urms.org.dto.OrgDTO;
import com.dhcc.urms.org.entity.DictOrg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dhcc.urms.org.entity.OrgVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 机构表 Mapper 接口
 * </p>
 *
 * @author Adam Ming
 * @since 2020-07-06
 */
public interface DictOrgMapper extends BaseMapper<DictOrg> {

    @Select("<script>" +
                "select ts.ORG_CODE, o.ORG_NAME, ts.ORG_TYPE_CODE, t.org_type_name " +
                "  from t_dict_org o, " +
                "       t_dict_org_type t, " +
                "       T_DICT_ORG_TYPE_SUB ts " +
                " where o.org_code = ts.org_code " +
                "   and t.org_type_code = ts.ORG_TYPE_CODE " +
                "<if test='orgTypeCode != null and orgTypeCode.trim() != &quot;&quot; '>" +
                "   and ts.org_type_code = '${orgTypeCode}' " +
                "</if>"+
                " order by t.odn, o.odn " +
                "</script>")
    List<OrgVO> findOrgInfo(OrgDTO dto);
}
