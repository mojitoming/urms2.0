package com.dhcc.urms.dict.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dhcc.urms.dict.dto.DictDTO;
import com.dhcc.urms.dict.entity.Dict;
import java.util.List;

public interface IDictService extends IService<Dict> {

    /*
     * Annotation:
     * 字典数据获取
     *
     * @Author: Adam Ming
     * @Date: Apr 15, 2020 at 5:02:40 PM
     */
    List<Dict> findDict(DictDTO dto);
}
