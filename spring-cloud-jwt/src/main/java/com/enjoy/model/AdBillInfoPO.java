package com.enjoy.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 
 * 
 */
@Data
public class AdBillInfoPO implements Serializable {

    /**
     * 广告ID
     */
    @ExcelProperty(value = "广告ID",index = 0)
    private Integer advertId;

    /**
     * 广告标题
     */
    @ExcelProperty(value = "广告标题",index = 1)
    private String adTitle;

    /**
     * 媒体名称
     */
    @ExcelProperty(value = "媒体名称",index = 2)
    private String adMedium;

    /**
     * 广告位
     */
    @ExcelProperty(value = "广告位",index = 3)
    private String adName;

    /**
     * 广告类型
     */
    @ExcelProperty(value = "广告类型",index = 4)
    private String advertType;

    /**
     * 账单日期
     */
    @ExcelProperty(value = "账单日期",index = 5)
    private Date billDate;

    /**
     * 曝光量
     */
    @ExcelProperty(value = "曝光量",index = 6)
    private Integer impressions;

    /**
     * CPM单价
     */
    @ExcelProperty(value = "CPM单价",index = 7)
    private Long cpm;

    /**
     * 结算价格
     */
    @ExcelProperty(value = "结算价格",index = 8)
    private BigDecimal amount;

    private static final long serialVersionUID = 1L;
    
}