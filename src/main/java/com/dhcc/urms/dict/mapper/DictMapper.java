package com.dhcc.urms.dict.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dhcc.urms.dict.dto.DictDTO;
import com.dhcc.urms.dict.entity.Dict;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Annotation:
 * 字典
 *
 * @Author: Adam Ming
 * @Date: Mar 24, 2020 at 3:47:04 PM
 */
public interface DictMapper extends BaseMapper<Dict> {

    @Select("<script>" +
            "select ${dictValue} as value, ${dictTitle} as title " +
            "<if test=\"dictValueChild != null and dictValueChild != ''\">" +
            "       , ${dictValueChild} as value_child " +
            "</if>" +
            "<if test=\"dictTitleChild != null and dictTitleChild != ''\">" +
            "       , ${dictTitleChild} as title_child " +
            "</if>" +
            "  from ${dictTable} where 1 = 1 " +
            "<if test=\"dictWhere != null and dictWhere != ''\">" +
            "   and ${dictWhere} " +
            "</if>" +
            "<if test=\"dictOrderBy != null and dictOrderBy != ''\">" +
            " order by ${dictOrderBy} " +
            "</if>" +
            "</script>")
    List<Dict> findDict(DictDTO dto);
}
