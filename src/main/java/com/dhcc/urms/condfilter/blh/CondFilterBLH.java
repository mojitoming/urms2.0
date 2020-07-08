package com.dhcc.urms.condfilter.blh;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dhcc.urms.common.redis.RedisUtils;
import com.dhcc.urms.condfilter.dto.CondFilterDTO;
import com.dhcc.urms.condfilter.entity.CondTypeEnum;
import com.dhcc.urms.condfilter.entity.DictFilterKeyword;
import com.dhcc.urms.condfilter.service.ICondFilterService;
import com.mojitoming.casclient.util.UserInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Component
public class CondFilterBLH {
    @Resource
    private ICondFilterService condFilterService;

    @Resource
    private RedisUtils redisUtils;

    // 获取关键字信息
    public void findKeywords(CondFilterDTO dto) {
        String condKwValue = dto.getCondKwValue();
        String[] kwArr = condKwValue.split(";");
        QueryWrapper<DictFilterKeyword> qw = new QueryWrapper<>();
        if (kwArr.length > 0) {
            qw.in("kw_code", Arrays.asList(kwArr));
        }
        qw.orderByAsc("odn");
        List<DictFilterKeyword> dictFilterKeywordList = condFilterService.list(qw);

        dto.setKeywordList(dictFilterKeywordList);
    }

    /*
     * Annotation:
     * 条件操作留痕
     * 存入redis
     *
     * @Author: Adam Ming
     * @Date: Apr 23, 2020 at 11:23:18 AM
     */
    public void saveConditionMark(CondFilterDTO dto) {
        String moduleCode = dto.getModuleCode();
        String condKwValue = dto.getCondKwValue();

        String kwKey = getConditionKey(moduleCode, CondTypeEnum.KEYWORD.name());
        redisUtils.set(kwKey, condKwValue);
    }

    /*
     * Annotation:
     * 条件获取
     *
     * @Author: Adam Ming
     * @Date: Apr 23, 2020 at 8:02:01 PM
     */
    public void getConditionMark(CondFilterDTO dto) {
        String moduleCode = dto.getModuleCode();
        String kwKey = getConditionKey(moduleCode, CondTypeEnum.KEYWORD.name());

        String condKwValue = (String) redisUtils.get(kwKey);
        dto.setCondKwValue(condKwValue);
    }

    /*
     * Annotation:
     * 组装条件 key
     *
     * @Author: Adam Ming
     * @Date: Apr 24, 2020 at 9:55:07 AM
     */
    private String getConditionKey(String moduleCode, String type) {
        long userId = UserInfo.getUser().getUserId();

        return userId + ":" + moduleCode + ":" + type;
    }
}
