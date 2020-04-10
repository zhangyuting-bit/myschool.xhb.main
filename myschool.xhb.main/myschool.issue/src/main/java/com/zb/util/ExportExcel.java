package com.zb.util;

import com.sun.java.swing.plaf.windows.WindowsTextAreaUI;
import com.zb.entity.Score;
import com.zb.entity.StuNumber;
import com.zb.entity.StuSubject;
import com.zb.entity.Subject;
import com.zb.mapper.*;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ExportExcel {
    @Resource
    private ScoreMapper scoreMapper;

    @Resource
    private StuCommentMapper stuCommentMapper;

    @Resource
    private StuSubjectMapper stuSubjectMapper;

    @Resource
    private ScoreOneMapper scoreOneMapper;

    @Resource
    private SubjectMapper subjectMapper;

    @Resource
    private NumberMapper numberMapper;

    //总行数
    private int totalRows = 0;
    //总条数
    private int totalCells = 0;
    //错误信息接收器
    private String errorMsg;
    //获取总行数
    public int getTotalRows()  { return totalRows;}
    //获取总列数
    public int getTotalCells() {  return totalCells;}
    //获取错误信息
    public String getErrorInfo() { return errorMsg; }

    @PostMapping("/singlefile/{scoreId}/{gradeId}")
    public String singleFileUpload(HttpServletRequest request, @RequestParam(required = false,value = "file") MultipartFile file,@PathVariable("scoreId") String scoreId,@PathVariable("gradeId")String gradeId) {
        //解析excel，获取上传的事件单
        String ok=getExcelInfo(file,scoreId,gradeId);
        //至此已经将excel中的数据转换到list里面了,接下来就可以操作list,可以进行保存到数据库,或者其他操作,
        if(!ok.equals("")&&ok.equals("ok")){
            ok = "上传成功";
        }else if (ok.equals("")){
            ok = "上传失败";
        }
        return ok;
    }

    /**
     * 读EXCEL文件，获取信息集合
     * @return
     */
    public String getExcelInfo(MultipartFile mFile,String scoreId,String gradeId) {
        String ok =null;
        String fileName = mFile.getOriginalFilename();//获取文件名
        try {
            if (!validateExcel(fileName)) {// 验证文件名是否合格
                return null;
            }
            boolean isExcel2003 = true;// 根据文件名判断文件是2003版本还是2007版本
            if (isExcel2007(fileName)) {
                isExcel2003 = false;
            }
            ok = createExcel(mFile.getInputStream(), isExcel2003,scoreId,gradeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    /**
     * 根据excel里面的内容读取客户信息
     * @param is 输入流
     * @param isExcel2003 excel是2003还是2007版本
     * @return
     * @throws IOException
     */
    public String createExcel(InputStream is, boolean isExcel2003,String scoreId,String gradeId) {
        String ok =null;
        try{
            Workbook wb = null;
            if (isExcel2003) {// 当excel是2003时,创建excel2003
                wb = new HSSFWorkbook(is);
            } else {// 当excel是2007时,创建excel2007
                wb = new XSSFWorkbook(is);
            }
            ok = readExcelValue(wb,scoreId,gradeId);// 读取Excel里面客户的信息
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ok;
    }


    /**
     * 将Excel里面的成绩信息添加到数据库
     * @param wb
     * @return
     */
    @Transactional
    public String readExcelValue(Workbook wb,String scoreId,String gradeId) {
        List<StuNumber>stuNumbers=numberMapper.getNumberByGradeId(gradeId);
        // 得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        // 得到Excel的行数
        this.totalRows = sheet.getPhysicalNumberOfRows();
        // 得到Excel的列数(前提是有行数)
        if (totalRows > 1 && sheet.getRow(0) != null) {
            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }

        // 循环Excel行数
        for (int r = 0; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            if (r>0){
                Cell cell = row.getCell(1);
                String name=cell.getRow().getCell(1).toString();
                Cell cell1 = row.getCell(0);
                String stuno=cell1.getRow().getCell(0).toString();
                stuno=stuno.substring(0, stuno.length()-2>0?stuno.length()-2:1);
                Integer count=0;
                for (StuNumber stuNumber:stuNumbers) {
                    if (stuNumber.getStuName().equals(name)&&stuNumber.getStuNo().equals(stuno)){
                        count++;
                        break;
                    }
                }
                if (count==0){
                    return "表中有学生姓名存在或学号不匹配或学号不存在!";
                }
            }
            // 循环Excel的列
            for (int c = 0; c < this.totalCells; c++) {
                Cell cell = row.getCell(c);
                if (r>0&&c>1){
                    String ok=cell.getRow().getCell(c).toString();
                    if (NumberUtils.isNumber(ok)==false&&!ok.equals("缺考")){
                        return "如有同学缺考,请填缺考!";
                    }
                }
            }

        }
        // 循环Excel行数
        for (int r = 0; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null){
                continue;
            }
            // 循环Excel的列
            for (int c = 0; c < this.totalCells; c++) {
                Cell cell = row.getCell(c);
                if (r==0&&c>1){
                    Subject subject=new Subject();
                    subject.setSubjectId(IdWorker.getId());
                    subject.setScoreId(scoreId);
                    subject.setType(cell.getRow().getCell(c).toString());
                    subjectMapper.addSubject(subject);
                }
            }
            Cell cell1 = row.getCell(1);
            String name=cell1.getRow().getCell(1).toString();
            StuNumber stuNumber=numberMapper.getNumberByName(name,gradeId);
            List<Subject>subjects=subjectMapper.getSubjectByScoreId(scoreId);
            // 循环Excel的列
            Integer count=0;
            for (int c = 0; c < this.totalCells; c++) {
                Cell cell = row.getCell(c);
                if (r>0&&c>1){
                    Subject subject=subjects.get(count);
                    StuSubject stuSubject=new StuSubject();
                    stuSubject.setSjId(IdWorker.getId());
                    stuSubject.setSubjectId(subject.getSubjectId());
                    if (cell.getRow().getCell(c).toString().equals("缺考")){
                        stuSubject.setScore("0");
                        stuSubject.setStatus("缺考");
                    }else {
                        stuSubject.setScore(cell.getRow().getCell(c).toString());
                    }
                    stuSubject.setNumberId(stuNumber.getNumberId());
                    stuSubjectMapper.addStuSubject(stuSubject);
                    count++;
                }
            }
        }
        return "ok";
    }

    /**
     * 验证EXCEL文件
     *
     * @param filePath
     * @return
     */
    public boolean validateExcel(String filePath) {
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
            errorMsg = "文件名不是excel格式";
            return false;
        }
        return true;
    }

    // @描述：是否是2003的excel，返回true是2003
    public static boolean isExcel2003(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }
    //@描述：是否是2007的excel，返回true是2007
    public static boolean isExcel2007(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }
}
