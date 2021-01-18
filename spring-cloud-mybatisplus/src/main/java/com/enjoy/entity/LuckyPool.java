package com.enjoy.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author LiZhaofu
 * @since 2021-01-18
 */
public class LuckyPool extends Model<LuckyPool> {

    private static final long serialVersionUID=1L;

    /**
     * 数字
     */
    private Integer num;

    /**
     * 位数之和
     */
    private Integer sum;

    /**
     * 奇数标志
     */
    private Boolean oddFlag;

    /**
     * 奇偶标志位
     */
    private String oddEven;


    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Boolean getOddFlag() {
        return oddFlag;
    }

    public void setOddFlag(Boolean oddFlag) {
        this.oddFlag = oddFlag;
    }

    public String getOddEven() {
        return oddEven;
    }

    public void setOddEven(String oddEven) {
        this.oddEven = oddEven;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "LuckyPool{" +
        "num=" + num +
        ", sum=" + sum +
        ", oddFlag=" + oddFlag +
        ", oddEven=" + oddEven +
        "}";
    }
}
