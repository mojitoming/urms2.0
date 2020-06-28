package com.dhcc.urms.condfilter.dto;

import com.dhcc.urms.common.entity.BaseAbstractDTO;
import com.dhcc.urms.condfilter.entity.DictFilterKeyword;

import java.util.List;

public class CondFilterDTO extends BaseAbstractDTO {
    private static final long serialVersionUID = -8364258010616569453L;

    private List<DictFilterKeyword> keywordList;
    private String moduleCode;
    private String condKwValue; // condition-keyword-value

    public List<DictFilterKeyword> getKeywordList() {
        return keywordList;
    }

    public void setKeywordList(List<DictFilterKeyword> keywordList) {
        this.keywordList = keywordList;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getCondKwValue() {
        return condKwValue;
    }

    public void setCondKwValue(String condKwValue) {
        this.condKwValue = condKwValue;
    }
}