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
     * 广告标题
     */
    @ExcelProperty(value = "广告标题",index = 0)
    private String adTitle;

    /**
     * 媒体名称
     */
    @ExcelProperty(value = "媒体名称",index = 1)
    private String adMedium;

    /**
     * 广告位
     */
    @ExcelProperty(value = "广告位",index = 2)
    private String adName;

    /**
     * 广告类型
     */
    @ExcelProperty(value = "采购类型",index = 3)
    private String advertType;

    /**
     * 账单日期
     */
    @ExcelProperty(value = "执行时间",index = 4)
    private Date billDate;

    /**
     * 曝光量
     */
    @ExcelProperty(value = "曝光量",index = 5)
    private Integer impressions;

    /**
     * CPM单价
     */
    @ExcelProperty(value = "CPM单价/元",index = 6)
    private Long cpm;

    /**
     * 结算价格
     */
    @ExcelProperty(value = "日净价",index = 7)
    private BigDecimal amount;

    private static final long serialVersionUID = 1L;

}