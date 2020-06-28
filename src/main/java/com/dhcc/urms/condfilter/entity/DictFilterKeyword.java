package com.dhcc.urms.condfilter.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 过滤器关键字
 * </p>
 *
 * @author Adam Ming
 * @since 2020-04-22
 */
@TableName("T_DICT_FILTER_KEYWORD")
public class DictFilterKeyword implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 关键字代码
     */
    @TableId("KW_CODE")
    private String kwCode;

    /**
     * 关键字
     */
    @TableField("KW_NAME")
    private String kwName;

    /**
     * 关键字类型。select：下拉框；multi-select: 多选下拉框；checkbox：复选框；radio：单选框；text：文本框；number：数字框；
     */
    @TableField("TYPE")
    private String type;

    /**
     * 字典表
     */
    @TableField("DICT_TABLE")
    private String dictTable;

    /**
     * 字典值
     */
    @TableField("DICT_VALUE")
    private String dictValue;

    /**
     * 字典标题
     */
    @TableField("DICT_TITLE")
    private String dictTitle;

    /**
     * 字典排序字段
     */
    @TableField("DICT_ORDER_BY")
    private String dictOrderBy;

    /**
     * 字典-子值：DICT_VALUE <---- DICT_VALUE_CHILD
     */
    @TableField("DICT_VALUE_CHILD")
    private String dictValueChild;

    /**
     * 字典-子标题：DICT_TITLE <---- DICT_TITLE_CHILD
     */
    @TableField("DICT_TITLE_CHILD")
    private String dictTitleChild;

    /**
     * 字典-条件
     */
    @TableField("DICT_WHERE")
    private String dictWhere;

    /**
     * 顺序
     */
    @TableField("ODN")
    private String odn;

    public String getKwCode() {
        return kwCode;
    }

    public void setKwCode(String kwCode) {
        this.kwCode = kwCode;
    }

    public String getKwName() {
        return kwName;
    }

    public void setKwName(String kwName) {
        this.kwName = kwName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public String getDictOrderBy() {
        return dictOrderBy;
    }

    public void setDictOrderBy(String dictOrderBy) {
        this.dictOrderBy = dictOrderBy;
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

    public String getOdn() {
        return odn;
    }

    public void setOdn(String odn) {
        this.odn = odn;
    }

    @Override
    public String toString() {
        return "DictFilterKeyword{" +
                "kwCode=" + kwCode +
                ", kwName=" + kwName +
                ", type=" + type +
                ", dictTable=" + dictTable +
                ", dictValue=" + dictValue +
                ", dictTitle=" + dictTitle +
                ", dictOrderBy=" + dictOrderBy +
                ", dictValueChild=" + dictValueChild +
                ", dictTitleChild=" + dictTitleChild +
                ", dictWhere=" + dictWhere +
                ", odn=" + odn +
                "}";
    }
}
