package com.dhcc.urms.dict.blh;

import com.dhcc.urms.dict.dto.DictDTO;
import com.dhcc.urms.dict.entity.Dict;
import com.dhcc.urms.dict.entity.DictGroup;
import com.dhcc.urms.dict.service.IDictService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * Annotation:
 * 字典
 *
 * @Author: Adam Ming
 * @Date: Mar 24, 2020 at 3:58:46 PM
 */
@Component
public class DictBLH {
    @Resource
    private IDictService dictService;

    /*
     * Annotation:
     * 字典数据获取
     *
     * @Author: Adam Ming
     * @Date: Apr 15, 2020 at 5:02:40 PM
     */
    public void findDict(DictDTO dto) {
        List<Dict> dictList = dictService.findDict(dto);
        dto.setDictList(dictList);
    }

    /*
     * Annotation:
     * 级联数据组装
     *
     * @Author: Adam Ming
     * @Date: Apr 22, 2020 at 10:52:09 AM
     */
    public void findDictCascade(DictDTO dto) {
        List<Dict> dictList = dictService.findDict(dto);
        String value, title, valueChild, titleChild;
        DictGroup dictGroup;
        Map<String, DictGroup> dictGroupMap = new HashMap<>();
        for (Dict dict : dictList) {
            value = dict.getValue();
            title = dict.getTitle();
            valueChild = dict.getValueChild();
            titleChild = dict.getTitleChild();

            if (dictGroupMap.containsKey(value)) {
                dictGroup = dictGroupMap.get(value);
                dictGroup.getChildList().add(new Dict(valueChild, titleChild));
            } else {
                dictGroup = new DictGroup(value, title, new ArrayList<>(Collections.singletonList(new Dict(valueChild, titleChild))));
                dictGroupMap.put(value, dictGroup);
            }
        }
        // 处理 Map 结果
        List<DictGroup> dictGroupList = new ArrayList<>(dictGroupMap.values());
        dto.setDictGroupList(dictGroupList);
    }
}
