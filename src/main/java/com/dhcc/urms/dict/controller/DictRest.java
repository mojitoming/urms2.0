package com.dhcc.urms.dict.controller;

import com.dhcc.urms.dict.blh.DictBLH;
import com.dhcc.urms.dict.dto.DictDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/dict-api")
public class DictRest {
    @Resource
    private DictBLH dictBLH;

    @GetMapping("/dict")
    public DictDTO findDict(DictDTO dto) {
        dictBLH.findDict(dto);

        return dto;
    }

    @GetMapping("/dict/cascade")
    public DictDTO findDictCascade(DictDTO dto) {
        dictBLH.findDictCascade(dto);

        return dto;
    }
}
