package com.zb.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zb.entity.Score;
import com.zb.entity.StuNumber;
import com.zb.entity.StuSubject;
import com.zb.entity.Subject;
import com.zb.mapper.NumberMapper;
import com.zb.mapper.ScoreMapper;
import com.zb.mapper.StuSubjectMapper;
import com.zb.mapper.SubjectMapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExportUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExportUtil.class.getName());

    @Resource
    private ScoreMapper scoreMapper;

    @Resource
    private StuSubjectMapper stuSubjectMapper;

    @Resource
    private NumberMapper numberMapper;

    @Resource
    private SubjectMapper subjectMapper;
    
    //根据分数编号获取考试信息
    public String ExportExcel(String scoreId){
        Score score=scoreMapper.getScoreByScoreId(scoreId);
        String path = "C://Users//ASUS//Desktop//"+score.getExamName()+".xlsx";
        String name = score.getExamName();
        List<String>titles=new ArrayList<>();
        titles.add("学号");
        titles.add("姓名");
        List<Subject>subjects=subjectMapper.getSubjectByScoreId(scoreId);
        for (Subject subject:subjects) {
            titles.add(subject.getType());
        }
        List<Map<String, Object>> values =new ArrayList<>();
        List<StuNumber>numbers=numberMapper.getNumberByGradeId(score.getGradeId());
        for (StuNumber stuNumber:numbers) {
            Map<String, Object> map = new HashMap<>();
            map.put("学号",stuNumber.getStuNo());
            map.put("姓名",stuNumber.getStuName());
            for (Subject subject:subjects) {
                StuSubject stuSubject=stuSubjectMapper.getStuSubjectBySubjectAndNumberId(subject.getSubjectId(),stuNumber.getNumberId());
                map.put(subject.getType(),stuSubject.getScore());
            }
            values.add(map);
        }
        if (!writerExcel(path, name, titles, values)){
            return "当前路径下已存在相同名称的文件";
        };
        return "下载文件成功";
    }

    /**
     * 数据写入Excel文件
     *
     * @param path 文件路径，包含文件全名，例如：D://file//demo.xls
     * @param name sheet名称
     * @param titles 行标题列
     * @param values 数据集合，key为标题，value为数据
     * @return True\False
     */
    public static boolean writerExcel(String path, String name, List<String> titles, List<Map<String, Object>> values) {
        LOGGER.info("path : {}", path);
        String style = path.substring(path.lastIndexOf("."), path.length()).toUpperCase(); // 从文件路径中获取文件的类型
        return generateWorkbook(path, name, style, titles, values);
    }

    /**
     * 将数据写入指定path下的Excel文件中
     *
     * @param path   文件存储路径
     * @param name   sheet名
     * @param style  Excel类型
     * @param titles 标题串
     * @param values 内容集
     * @return True\False
     */
    private static boolean generateWorkbook(String path, String name, String style, List<String> titles, List<Map<String, Object>> values) {
        LOGGER.info("file style : {}", style);
        Workbook workbook;
        if ("XLS".equals(style.toUpperCase())) {
            workbook = new HSSFWorkbook();
        } else {
            workbook = new XSSFWorkbook();
        }
        // 生成一个表格
        Sheet sheet;
        if (null == name || "".equals(name)) {
            sheet = workbook.createSheet(); // name 为空则使用默认值
        } else {
            sheet = workbook.createSheet(name);
        }
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
//        // 生成样式
//        Map<String, CellStyle> styles = createStyles(workbook);
        /*
         * 创建标题行
         */
        Row row = sheet.createRow(0);
        // 存储标题在Excel文件中的序号
        Map<String, Integer> titleOrder = Maps.newHashMap();
        for (int i = 0; i < titles.size(); i++) {
            Cell cell = row.createCell(i);
//            cell.setCellStyle(styles.get("header"));
            String title = titles.get(i);
            cell.setCellValue(title);
            titleOrder.put(title, i);
        }
        /*
         * 写入正文
         */
        Iterator<Map<String, Object>> iterator = values.iterator();
        int index = 0; // 行号
        while (iterator.hasNext()) {
            index++; // 出去标题行，从第一行开始写
            row = sheet.createRow(index);
            Map<String, Object> value = iterator.next();
            for (Map.Entry<String, Object> map : value.entrySet()) {
                // 获取列名
                String title = map.getKey();
                // 根据列名获取序号
                int i = titleOrder.get(title);
                // 在指定序号处创建cell
                Cell cell = row.createCell(i);
                // 设置cell的样式
//                if (index % 2 == 1) {
//                    cell.setCellStyle(styles.get("cellA"));
//                } else {
//                    cell.setCellStyle(styles.get("cellB"));
//                }
                // 获取列的值
                Object object = map.getValue();
                // 判断object的类型
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (object instanceof Double) {
                    cell.setCellValue((Double) object);
                } else if (object instanceof Date) {
                    String time = simpleDateFormat.format((Date) object);
                    cell.setCellValue(time);
                } else if (object instanceof Calendar) {
                    Calendar calendar = (Calendar) object;
                    String time = simpleDateFormat.format(calendar.getTime());
                    cell.setCellValue(time);
                } else if (object instanceof Boolean) {
                    cell.setCellValue((Boolean) object);
                } else {
                    cell.setCellValue(object.toString());
                }
            }
        }
        /*
         * 写入到文件中
         */
        boolean isCorrect = false;
        try {
            File file = new File(path);
            OutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            outputStream.close();
            isCorrect = true;
        } catch (IOException e) {
            isCorrect = false;
            LOGGER.error("write Excel file error : {}", e.getMessage());
        }
        try {
            workbook.close();
        } catch (IOException e) {
            isCorrect = false;
            LOGGER.error("workbook closed error : {}", e.getMessage());
        }
        return isCorrect;
    }

    /**
     * Create a library of cell styles
     */
    /**
     * @param wb
     * @return
     */
    private static Map<String, CellStyle> createStyles(Workbook wb) {
        Map<String, CellStyle> styles = Maps.newHashMap();

        // 标题样式
        CellStyle titleStyle = wb.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER); // 水平对齐
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 垂直对齐
        titleStyle.setLocked(true); // 样式锁定
        titleStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBold(true);
        titleFont.setFontName("微软雅黑");
        titleStyle.setFont(titleFont);
        styles.put("title", titleStyle);

        // 文件头样式
        CellStyle headerStyle = wb.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex()); // 前景色
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); // 颜色填充方式
        headerStyle.setWrapText(true);
        headerStyle.setBorderRight(BorderStyle.THIN); // 设置边界
        headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        Font headerFont = wb.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        titleFont.setFontName("微软雅黑");
        headerStyle.setFont(headerFont);
        styles.put("header", headerStyle);

        Font cellStyleFont = wb.createFont();
        cellStyleFont.setFontHeightInPoints((short) 12);
        cellStyleFont.setColor(IndexedColors.BLUE_GREY.getIndex());
        cellStyleFont.setFontName("微软雅黑");

        // 正文样式A
        CellStyle cellStyleA = wb.createCellStyle();
        cellStyleA.setAlignment(HorizontalAlignment.CENTER); // 居中设置
        cellStyleA.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleA.setWrapText(true);
        cellStyleA.setBorderRight(BorderStyle.THIN);
        cellStyleA.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleA.setBorderLeft(BorderStyle.THIN);
        cellStyleA.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleA.setBorderTop(BorderStyle.THIN);
        cellStyleA.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleA.setBorderBottom(BorderStyle.THIN);
        cellStyleA.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleA.setFont(cellStyleFont);
        styles.put("cellA", cellStyleA);

        // 正文样式B:添加前景色为浅黄色
        CellStyle cellStyleB = wb.createCellStyle();
        cellStyleB.setAlignment(HorizontalAlignment.CENTER);
        cellStyleB.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleB.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        cellStyleB.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyleB.setWrapText(true);
        cellStyleB.setBorderRight(BorderStyle.THIN);
        cellStyleB.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleB.setBorderLeft(BorderStyle.THIN);
        cellStyleB.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleB.setBorderTop(BorderStyle.THIN);
        cellStyleB.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleB.setBorderBottom(BorderStyle.THIN);
        cellStyleB.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleB.setFont(cellStyleFont);
        styles.put("cellB", cellStyleB);

        return styles;
    }
}
