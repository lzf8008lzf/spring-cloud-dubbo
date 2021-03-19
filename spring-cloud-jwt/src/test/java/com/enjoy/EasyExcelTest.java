package com.enjoy;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.enjoy.component.CellWriteComponent;
import com.enjoy.model.AdBillInfoPO;
import com.enjoy.utils.FileUtil;

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
        // 方法2 如果写到不同的sheet 同一个对象
        String fileName = FileUtil.getPath() + "repeatedWrite" + System.currentTimeMillis() + ".xlsx";
        ExcelWriter excelWriter = null;

        try {
            // 这里 指定文件
            excelWriter = EasyExcel.write(fileName, AdBillInfoPO.class).useDefaultStyle(false).registerWriteHandler(new CellWriteComponent()).build();
            // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来。这里最终会写到5个sheet里面
            for (int i = 0; i < 5; i++) {
                // 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样
                WriteSheet writeSheet = EasyExcel.writerSheet(i, "模板" + i).build();
                // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
                List<AdBillInfoPO> data = data();
                excelWriter.write(data, writeSheet);

                // 合计部分
                List<List<String>> totalListList = new ArrayList<List<String>>();
                List<String> totalList = new ArrayList<String>();
                totalListList.add(totalList);
                totalList.add("合计：");
                totalList.add("80000000000");
                excelWriter.write(totalListList, writeSheet);
            }
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
}
