package com.disp.disp.control;

import com.config.Config;
import com.disp.Disp;
import com.disp.disp.control.loadExcell.ExcellLoader;
import com.disp.disp.control.loadExcell.Report;
import com.disp.disp.control.saveExcell.SaveExclell;
import com.disp.disp.control.saveExcell.TransportExcell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Array;
import java.util.*;

/**
 * Created by disp.chimc on 28.10.14.
 */
public class DispControl implements Disp {
    private ArrayList<Report> reports;
    private ArrayList<Config> configs;
    private Map<String,String> departMap;
    @Override
    public ArrayList<Report> load_report(String path) {
        ExcellLoader excellLoader = new ExcellLoader();
        try {
            excellLoader.loadExcell(path);
            ArrayList<Report> report =excellLoader.getReports();
         reports=report;
            return report;
        } catch (IOException e) {
            System.out.println("You have a problem with file: " + path);
            e.printStackTrace();
            return null;
        }

    }

    public void loadReport(String path) throws IOException {
        ExcellLoader excellLoader = new ExcellLoader();
        try {
            excellLoader.loadExcell(path);
            ArrayList<Report> report =excellLoader.getReports();

            reports=report;
        } catch (Exception e) {
            System.out.println("You have a problem with file: " + path);
            throw new IOException("You have a problem with file: \n"+e.getMessage());
        }

    }

    public void save_report(ArrayList<Report> reports,String path,ArrayList<Config>configs)  {
        //получаем имя листа(дата)
        Date d = (Date)reports.get(0).getTime_stop().clone();
        String list_name = ""+d.getDate()+"."+(d.getMonth()+1);


        ArrayList<TransportExcell> transportExcell = new ArrayList<TransportExcell>();
        for(Report re: reports){
            transportExcell.add(new TransportExcell(re,configs,departMap));
        }
        //сортируем список по отделениям
       for(int i = 0;i<transportExcell.size();i++){
            transportExcell.get(i).setDepartment(transportExcell.get(i).getDepartment()+" ");
           System.out.println(transportExcell.get(i).getDepartment());
        }
    /*    Collections.sort(transportExcell, new Comparator<TransportExcell>() {
            @Override
            public int compare(TransportExcell o1, TransportExcell o2) {
                if (o1.getPintersList() == null || o2.getPintersList() == null) return 0;
                String s1 = o1.getDepartment().substring(o1.getDepartment().indexOf(" "), o1.getDepartment().length());
                String s2 = o2.getDepartment().substring(o2.getDepartment().indexOf(" "), o2.getDepartment().length());
                return s2.toString().compareTo(s1.toString());
            }
        });
*/
        try{
            SaveExclell saveExclell = new SaveExclell(path,list_name);
            saveExclell.createHatList();
            saveExclell.create(transportExcell);
            saveExclell.update();
        }catch(Exception e){
            System.out.println("Error! Can not write report to excell");
            throw new RuntimeException(e);
        }
    }

    @Override
    public  ArrayList<Config> load_config(String path) throws IOException {
        ArrayList<Config> configList = new ArrayList<Config>();
        FileInputStream inputStream = new FileInputStream(path);

        XSSFWorkbook workbook  = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheet("kamaz");
        int row_total = sheet.getLastRowNum();

        for(int i = 1; i < row_total+1; i++){
            String tracker;

            String mark;
            String gos;
            String name;
            //String phone;

            try{
                tracker = sheet.getRow(i).getCell(0).toString();
                tracker = tracker.substring(0,tracker.indexOf("."));
            }catch(Exception e){tracker ="0";}
            try{
                mark = sheet.getRow(i).getCell(1).toString();
            }catch(Exception e){mark = "-";}
            try{
                gos = sheet.getRow(i).getCell(2).toString();
            }catch (Exception e){gos = "-";}
            try{
                gos = gos.substring(0,gos.indexOf("."));
            }catch (Exception e){}

            try {
                name = sheet.getRow(i).getCell(3).toString();
            }catch(Exception e){name = "-";}
           /* try {
                phone = sheet.getRow(i).getCell(4).toString();
            }catch (Exception e){phone = "-";}
            */


            try{
                configList.add(new Config(tracker,mark,gos,name));
            }catch (Exception e){
                System.out.print("Can not add element to configList");
            }

        }
        configs = configList;
        return configList;
    }

    @Override
    public void load_departmetn(String path) throws IOException {
        FileInputStream inputStream = new FileInputStream(path);
      departMap = new HashMap<String, String>();

        XSSFWorkbook workbook  = new XSSFWorkbook(inputStream);

        XSSFSheet sheet = workbook.getSheet("elevator");
        int row_total = sheet.getLastRowNum();
        String zone="";

        for(int i = 1 ; i < row_total+1;i ++){
            try{
            zone=sheet.getRow(i).getCell(0).toString();
            }catch(Exception e){zone="";continue;}


           departMap.put(zone, "");
        }

    }

    @Override
    public Map<String, String> getDepartMap() {
        return departMap;
    }

    @Override
    public ArrayList<Report> getReport() {
        return reports;
    }
    @Override
    public ArrayList<Config> getConfigs() {
        return configs;
    }
}
