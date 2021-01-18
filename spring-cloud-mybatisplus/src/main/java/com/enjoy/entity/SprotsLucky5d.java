package com.enjoy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author LiZhaofu
 * @since 2021-01-18
 */
public class SprotsLucky5d extends Model<SprotsLucky5d> {

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
     * 开奖号码
     */
    private String lotteryNo;

    /**
     * 3D号码
     */
    private String lottery3d;


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

    public String getLotteryNo() {
        return lotteryNo;
    }

    public void setLotteryNo(String lotteryNo) {
        this.lotteryNo = lotteryNo;
    }

    public String getLottery3d() {
        return lottery3d;
    }

    public void setLottery3d(String lottery3d) {
        this.lottery3d = lottery3d;
    }

    @Override
    protected Serializable pkVal() {
        return this.openingTime;
    }

    @Override
    public String toString() {
        return "SprotsLucky5d{" +
        "openingTime=" + openingTime +
        ", issueNo=" + issueNo +
        ", lotteryNo=" + lotteryNo +
        ", lottery3d=" + lottery3d +
        "}";
    }
}
