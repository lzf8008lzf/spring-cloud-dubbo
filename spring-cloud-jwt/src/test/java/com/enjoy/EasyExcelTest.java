package com.enjoy;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.enjoy.component.CellWriteComponent;
import com.enjoy.model.AdBillInfoPO;
import com.enjoy.utils.FileUtil;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2021-03-10 16:01
 **/

public class EasyExcelTest {

    public static void main(String[] args){
        writeFileHeader();
    }

    private static void writeStream(){
//        EasyExcel.write(response.getOutputStream(), AdBillInfoPO.class).useDefaultStyle(false).head(AdBillInfoPO.class).registerWriteHandler(new CellWriteComponent()).sheet(sheetName).doWrite(data);
    }

    private static List<List<String>> getHeader(String title, String agentName, String billDate) {
        /**
         * 打算展示成如下样子
         * |代理商名称 XXX
         * |月份 XX月
         * |账单信息
         * |广告标题|媒体名称|广告位|采购类型|执行时间|曝光量|CPM单价/元|日净价
         */
        String agent="代理商名称";
        String month="月份";
        List<List<String>> list = new ArrayList<List<String>>();

        List<String> head1 = new ArrayList<String>();
        head1.add(agent);
        head1.add(month);
        head1.add(title);
        head1.add("广告标题");

        List<String> head2 = new ArrayList<String>();
        head2.add(agentName);
        head2.add(billDate);
        head2.add(title);
        head2.add("媒体名称");

        List<String> head3 = new ArrayList<String>();
        head3.add(agentName);
        head3.add(billDate);
        head3.add(title);
        head3.add("广告位");

        List<String> head4 = new ArrayList<String>();
        head4.add(agentName);
        head4.add(billDate);
        head4.add(title);
        head4.add("采购类型");

        List<String> head5 = new ArrayList<String>();
        head5.add(agentName);
        head5.add(billDate);
        head5.add(title);
        head5.add("执行时间");

        List<String> head6 = new ArrayList<String>();
        head6.add(agentName);
        head6.add(billDate);
        head6.add(title);
        head6.add("曝光量");

        List<String> head7 = new ArrayList<String>();
        head7.add(agentName);
        head7.add(billDate);
        head7.add(title);
        head7.add("CPM单价/元");

        List<String> head8 = new ArrayList<String>();
        head8.add(agentName);
        head8.add(billDate);
        head8.add(title);
        head8.add("日净价");

        list.add(head1);
        list.add(head2);
        list.add(head3);
        list.add(head4);
        list.add(head5);
        list.add(head6);
        list.add(head7);
        list.add(head8);

        return list;
    }

    private static void writeFileHeader(){
        List<String> excludeColumns = new ArrayList<>();
        excludeColumns.add("memo");

        String fileName = FileUtil.getPath() + "repeatedWrite" + DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN) + ".xlsx";
        ExcelWriter excelWriter = null;

        try {
            // 这里 指定文件
            excelWriter = EasyExcel.write(fileName).useDefaultStyle(false)
                    .registerWriteHandler(new CellWriteComponent())
                    .registerWriteHandler(getStyleStrategy())
                    .build();

            WriteSheet writeSheet = EasyExcel.writerSheet( "模板" ).head(getHeader("账单信息","悦享商城App运营部","2月")).build();
            List<AdBillInfoPO> data = data();
            excelWriter.write(data, writeSheet);
            // 合计部分
            List<List<String>> totalListList = new ArrayList<List<String>>();
            List<String> totalList = new ArrayList<String>();
            totalListList.add(totalList);
            for(int j=0;j<6;j++) {
                totalList.add("");
            }
            totalList.add("合计：");
            totalList.add("80000000000");
            excelWriter.write(totalListList, writeSheet);

        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    private static void writeFile(){
        String fileName = FileUtil.getPath() + "repeatedWrite" + DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN) + ".xlsx";
        ExcelWriter excelWriter = null;

        try {
            // 这里 指定文件
            excelWriter = EasyExcel.write(fileName, AdBillInfoPO.class).useDefaultStyle(false).registerWriteHandler(new CellWriteComponent())
                    .registerWriteHandler(getStyleStrategy()).build();

            WriteSheet writeSheet = EasyExcel.writerSheet( "模板" ).build();
            List<AdBillInfoPO> data = data();
            excelWriter.write(data, writeSheet);
            // 合计部分
            List<List<String>> totalListList = new ArrayList<List<String>>();
            List<String> totalList = new ArrayList<String>();
            totalListList.add(totalList);
            for(int j=0;j<7;j++) {
                totalList.add("");
            }
            totalList.add("合计：");
            totalList.add("80000000000");
            excelWriter.write(totalListList, writeSheet);
//            // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来。这里最终会写到5个sheet里面
//            for (int i = 0; i < 5; i++) {
//                // 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样
//                WriteSheet writeSheet = EasyExcel.writerSheet(i, "模板" + i).build();
//                // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
//                List<AdBillInfoPO> data = data();
//                excelWriter.write(data, writeSheet);
//
//                // 合计部分
//                List<List<String>> totalListList = new ArrayList<List<String>>();
//                List<String> totalList = new ArrayList<String>();
//                totalListList.add(totalList);
//                for(int j=0;j<7;j++) {
//                    totalList.add("");
//                }
//                totalList.add("合计：");
//                totalList.add("80000000000");
//                excelWriter.write(totalListList, writeSheet);
//            }
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    private static List<AdBillInfoPO> data() {
        List<AdBillInfoPO> list = new ArrayList<AdBillInfoPO>();
        for (int i = 0; i < 10; i++) {
            AdBillInfoPO data = new AdBillInfoPO();
            data.setAdTitle("流利说");
            data.setAdMedium("悦享视频APP");
            data.setAdName("开屏");
            data.setAdvertType("购买");
            data.setBillDate(new Date());
            data.setImpressions(10000000);
            data.setCpm(40L);
            data.setAmount(new BigDecimal(40000000000l));
            list.add(data);
        }
        return list;
    }

    private static HorizontalCellStyleStrategy getStyleStrategy() {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 设置对齐
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 背景色, 设置为白色，也是默认颜色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        // 字体
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 12);
        headWriteCellStyle.setWriteFont(headWriteFont);

        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
        // contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);

        // 背景绿色
        //contentWriteCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        // 字体策略
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontHeightInPoints((short) 12);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        //设置 自动换行
        contentWriteCellStyle.setWrapped(true);
        //设置 垂直居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置 水平居中
        // contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //设置边框样式
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);

        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        return horizontalCellStyleStrategy;
    }
}
