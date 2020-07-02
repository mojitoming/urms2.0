package com.dhcc.urms.dict.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dhcc.urms.dict.dto.DictDTO;
import com.dhcc.urms.dict.entity.Dict;
import com.dhcc.urms.dict.mapper.DictMapper;
import com.dhcc.urms.dict.service.IDictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("dictService")
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {
    @Resource
    DictMapper dictMapper;

    @Override
    public List<Dict> findDict(DictDTO dto) {
        return dictMapper.findDict(dto);
    }
}
