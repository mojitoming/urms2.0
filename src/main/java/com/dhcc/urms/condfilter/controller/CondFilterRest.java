package com.dhcc.urms.condfilter.controller;

import com.alibaba.fastjson.JSONObject;
import com.dhcc.urms.condfilter.blh.CondFilterBLH;
import com.dhcc.urms.condfilter.dto.CondFilterDTO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 过滤器关键字 前端控制器
 * </p>
 *
 * @author Adam Ming
 * @since 2020-04-16
 */
@RestController
@RequestMapping("/cond-filter-api")
public class CondFilterRest {

    @Resource
    private CondFilterBLH condFilterBLH;

    @GetMapping("/keywords")
    public CondFilterDTO getKeywords(CondFilterDTO dto) {
        condFilterBLH.findKeywords(dto);

        return dto;
    }

    @PostMapping("/data")
    public CondFilterDTO getData(@RequestBody JSONObject jsonObject) {
        CondFilterDTO dto = new CondFilterDTO();
        // TODO business
        System.out.println("Query !!!~~~");

        return dto;
    }

    @PostMapping("/conditions/mark")
    public CondFilterDTO saveConditionMark(CondFilterDTO dto) {
        condFilterBLH.saveConditionMark(dto);

        return dto;
    }

    @GetMapping("/conditions/mark")
    public CondFilterDTO getConditionMark(CondFilterDTO dto) {
        condFilterBLH.getConditionMark(dto);

        return dto;
    }
}
