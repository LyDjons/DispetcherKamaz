package com.disp.disp.control.saveExcell;

import com.config.Transport;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.text.html.parser.Entity;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

/**
 * Created by disp.chimc on 31.10.14.
 */
public class SaveExclell {
    private  FileOutputStream fos;
    private  FileInputStream inputStream;
    private XSSFWorkbook workbook;
    private Sheet sheet;

    //установка размеров колонок
    private  void setSizeColumn(Sheet sheet){
        sheet.setColumnWidth(0,1462);
        sheet.setColumnWidth(1,4700);
        sheet.setColumnWidth(2,2800);
        sheet.setColumnWidth(3,4388);
        sheet.setColumnWidth(4,1828);
        for(int i = 5 ; i < 101;i++)
            sheet.setColumnWidth(i,420);
    }

    //зарисовать ячейку любым цветом
    private  void driwing_cell(int row, int cell,Color color){
            XSSFCellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(new XSSFColor(color)); //цвет ячейки
            style.setFillPattern(CellStyle.SOLID_FOREGROUND); //?? установить цвет
            style.setBorderTop(CellStyle.BORDER_THIN);
             style.setBorderBottom(CellStyle.BORDER_THIN);
            sheet.getRow(row).getCell(cell).setCellStyle(style); //применить стиль
    }

    //конструктор класса path-путь к файлу  createlistname- имя листа
    public SaveExclell(String path,String createlistname) {
        try {
            inputStream = new FileInputStream(path);
            workbook  = new XSSFWorkbook(inputStream);
            fos = new FileOutputStream(path);
            sheet = workbook.createSheet(createlistname);
        } catch (FileNotFoundException e) {
            System.out.println("Can not find file!!!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error SaveExcel Constructor");
            e.printStackTrace();
        }

    }

    //создание шапки листа
    public void createHatList() throws IOException {
//установка размера колонок
        setSizeColumn(sheet);
        sheet.createRow(1).createCell(0).setCellValue("Робота Камазів");
        XSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER); //выровняли по центру
        style.setFillForegroundColor(new XSSFColor(new Color(255,255,0))); //цвет ячейки
        style.setFillPattern(CellStyle.SOLID_FOREGROUND); //?? установить цвет
        sheet.getRow(1).getCell(0).setCellStyle(style); //применить стиль
//выделяем и объединяем Ячейки
        CellRangeAddress region = new CellRangeAddress(1, 1, 0, 1);//(firstRow,lastRow,firstCol,lastCol)
        sheet.addMergedRegion(region);
//Проворачивает дела с шапкой
        Row cap_row = workbook.getSheet(sheet.getSheetName()).createRow(2);
        int count =7;
        XSSFCellStyle style2 = workbook.createCellStyle();
        for(int i = 5; i < 101; i=i+4){
            if(count==25) count=1;
            cap_row.createCell(i).setCellValue(count++);
            region = new CellRangeAddress(2, 2, i, i+3);//(firstRow,lastRow,firstCol,lastCol)
            RegionUtil.setBorderTop(CellStyle.BORDER_MEDIUM, region, sheet, workbook);
            RegionUtil.setBorderLeft(CellStyle.BORDER_MEDIUM, region, sheet, workbook);
            RegionUtil.setBorderRight(CellStyle.BORDER_MEDIUM, region, sheet, workbook);
            RegionUtil.setBorderBottom(CellStyle.BORDER_MEDIUM, region, sheet, workbook);
            sheet.addMergedRegion(region);
            style2.setAlignment(CellStyle.ALIGN_CENTER); //выровняли по центру
            style2.setFillForegroundColor(new XSSFColor(new Color(191,191,191))); //цвет ячейки
            style2.setFillPattern(CellStyle.SOLID_FOREGROUND); //?? установить цвет
            sheet.getRow(2).getCell(i).setCellStyle(style2); //применить стиль
        }
        cap_row.createCell(0).setCellValue("№п/п");
        cap_row.createCell(1).setCellValue("Марка ТЗ");
        cap_row.createCell(2).setCellValue("Держ. №");
        cap_row.createCell(3).setCellValue("Водій");
        cap_row.createCell(4).setCellValue("Трек");
        for(int i=0;i<5;i++) sheet.getRow(2).getCell(i).setCellStyle(style2);
        for(int i=0; i<6;i++){
            CellStyle cellStyle = sheet.getRow(2).getCell(i).getCellStyle();
            cellStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
            cellStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
            cellStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
            cellStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        }
    }

    //нарисовать отчет
    private void driwing_report(Color work,Color load,Color unload,ArrayList<TransportExcell> transportlist){
        try{
            int row1 =4;

            //рисуем движение
            for(TransportExcell tr: transportlist){
                    int start = 5;
                if(tr.getStart().getHours()>=7) {
                    start = tr.get_num_cell(tr.getStart());
                }
              int end = tr.get_num_cell(tr.getEnd());


                for(int i = start; i<end;i++){
                    driwing_cell(row1,i,new Color(0,176,80));
                }
                for(Pinter p : tr.getPintersList()){
                    if(tr.find_indep(p)){
                        try{
                        CellRangeAddress region = new CellRangeAddress(row1, row1, p.getStart(), p.getEnd()-1);//(firstRow,lastRow,firstCol,lastCol)
                        sheet.addMergedRegion(region);
                        }catch (Exception e){
                            System.out.println("Do bot range region");


                        }
                        for(int i = p.getStart();i<p.getEnd();i++){
                            sheet.getRow(row1).getCell(p.getStart()).setCellValue(p.getMin());
                            driwing_cell(row1,i,new Color(255,192,0));
                        }
                    }
                    if(!tr.find_indep(p)){
                        if(p.getEnd()==p.getStart())
                        {driwing_cell(row1,p.getStart()-1,new Color(255,255,0));}else
                        {
                            for(int i = p.getStart()-1;i<p.getEnd();i++){

                                driwing_cell(row1,i,new Color(255,255,0));
                            }
                        }
                    }
                }


                row1++;


            }
            row1 =4;




        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //заполнение таблици
    public void create(ArrayList<TransportExcell> transportlist) throws IOException {
        int row = 4;
        //заполняем  инф. таблицу
        int count = 1;
        for(TransportExcell tr: transportlist){
            sheet.createRow(row).createCell(1).setCellValue(tr.getTransport_mark());
            sheet.getRow(row).createCell(0).setCellValue(Integer.toString(count++));
            sheet.getRow(row).createCell(2).setCellValue(tr.getGos());
            sheet.getRow(row).createCell(3).setCellValue(tr.getFio());
            sheet.getRow(row).createCell(4).setCellValue(tr.getTracker());
            row++;

        }

        //создаем сетку
        for(int r = 3;r<transportlist.size()+4;r++){
            for(int i = 0;i<101;i++){
            CellRangeAddress region = new CellRangeAddress(r, r, i, i);//(firstRow,lastRow,firstCol,lastCol)
                RegionUtil.setBorderTop(CellStyle.BORDER_THIN, region, sheet, workbook);
                RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, region, sheet, workbook);

                if (i<5){
                RegionUtil.setBorderRight(CellStyle.BORDER_THIN, region, sheet, workbook);
                    RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, region, sheet, workbook);
                }
            }

        }

        //исуем отчет
       driwing_report(new Color(0,176,240), new Color(255, 188, 0),new Color(255,255,0), transportlist);

    }

    public void update() throws IOException {
        workbook.write(fos);
    }


}
