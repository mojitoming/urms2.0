package com.dhcc.urms.dict.dto;

import com.dhcc.urms.common.entity.BaseAbstractDTO;
import com.dhcc.urms.dict.entity.Dict;
import com.dhcc.urms.dict.entity.DictGroup;

import java.util.List;

public class DictDTO extends BaseAbstractDTO {
    private String dictTable;
    private String dictValue;
    private String dictTitle;
    private String dictValueChild;
    private String dictTitleChild;
    private String dictWhere;
    private String dictOrderBy;
    private List<Dict> dictList;
    private List<DictGroup> dictGroupList;

    public String getDictTable() {
        return dictTable;
    }

    public void setDictTable(String dictTable) {
        this.dictTable = dictTable;
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public String getDictTitle() {
        return dictTitle;
    }

    public void setDictTitle(String dictTitle) {
        this.dictTitle = dictTitle;
    }

    public String getDictValueChild() {
        return dictValueChild;
    }

    public void setDictValueChild(String dictValueChild) {
        this.dictValueChild = dictValueChild;
    }

    public String getDictTitleChild() {
        return dictTitleChild;
    }

    public void setDictTitleChild(String dictTitleChild) {
        this.dictTitleChild = dictTitleChild;
    }

    public String getDictWhere() {
        return dictWhere;
    }

    public void setDictWhere(String dictWhere) {
        this.dictWhere = dictWhere;
    }

    public String getDictOrderBy() {
        return dictOrderBy;
    }

    public void setDictOrderBy(String dictOrderBy) {
        this.dictOrderBy = dictOrderBy;
    }

    public List<Dict> getDictList() {
        return dictList;
    }

    public void setDictList(List<Dict> dictList) {
        this.dictList = dictList;
    }

    public List<DictGroup> getDictGroupList() {
        return dictGroupList;
    }

    public void setDictGroupList(List<DictGroup> dictGroupList) {
        this.dictGroupList = dictGroupList;
    }
}
