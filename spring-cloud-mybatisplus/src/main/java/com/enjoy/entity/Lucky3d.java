package com.enjoy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 福彩3D开奖情况
 * </p>
 *
 * @author LiZhaofu
 * @since 2021-01-18
 */
public class Lucky3d extends Model<Lucky3d> {

    private static final long serialVersionUID=1L;

    /**
     * 开奖时间
     */
    @TableId(value = "opening_time", type = IdType.AUTO)
    private LocalDate openingTime;

    /**
     * 期号
     */
    private String issueNo;

    /**
     * 试机号
     */
    private String testNo;

    /**
     * 开奖号码
     */
    private String lotteryNo;


    public LocalDate getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalDate openingTime) {
        this.openingTime = openingTime;
    }

    public String getIssueNo() {
        return issueNo;
    }

    public void setIssueNo(String issueNo) {
        this.issueNo = issueNo;
    }

    public String getTestNo() {
        return testNo;
    }

    public void setTestNo(String testNo) {
        this.testNo = testNo;
    }

    public String getLotteryNo() {
        return lotteryNo;
    }

    public void setLotteryNo(String lotteryNo) {
        this.lotteryNo = lotteryNo;
    }

    @Override
    public String toString() {
        return "Lucky3d{" +
        "openingTime=" + openingTime +
        ", issueNo=" + issueNo +
        ", testNo=" + testNo +
        ", lotteryNo=" + lotteryNo +
        "}";
    }
}
