package com.swing;

import com.config.Config;
import com.config.Transport;
import com.disp.Disp;
import com.disp.disp.control.DispControl;
import com.disp.disp.control.loadExcell.Report;
import com.disp.disp.control.saveExcell.Pinter;
import com.disp.disp.control.saveExcell.SaveExclell;
import com.disp.disp.control.saveExcell.TransportExcell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by disp.chimc on 12.02.15.
 */
public class Test {
    private static Date data_rounding(Date date){
        int switchVariable = 0;
        double minute = date.getMinutes();
        if(date.getSeconds()>=30) minute+=1;
        if( minute>=0 && minute <7.5) switchVariable = 5;
        else if( minute>=7.5 && minute <=15) switchVariable = 10;
        else if( minute>15 && minute <22.5) switchVariable = 20;
        else if( minute>=22.5 && minute <=30) switchVariable = 25;
        else if( minute>30 && minute <37.5) switchVariable = 35;
        else if( minute>=37.5 && minute <=45) switchVariable = 40;
        else if( minute>45 && minute <52.5) switchVariable = 50;
        else if( minute>52.5 && minute <60) switchVariable = 55;
        switch (switchVariable)
        {
            case 5: date.setMinutes(0);date.setSeconds(0); break;
            case 10: date.setMinutes(15);date.setSeconds(0); break;
            case 20: date.setMinutes(15);date.setSeconds(0); break;
            case 25: date.setMinutes(30);date.setSeconds(0); break;
            case 35: date.setMinutes(30);date.setSeconds(0); break;
            case 40: date.setMinutes(45);date.setSeconds(0); break;
            case 50: date.setMinutes(45);date.setSeconds(0); break;
            case 55: date.setHours(date.getHours()+1);date.setMinutes(0);date.setSeconds(0); break;
            default: date.setMinutes(0);date.setSeconds(0); break;
        }
        return (Date)date.clone();
    }
    public static int  get_num_cell(Date date){
        date = (Date)data_rounding(date).clone();
        Date countdata = new Date();
        countdata.setHours(7);
        countdata.setMinutes(0);
        countdata.setSeconds(0);
        int cell=5;
        for(int i =0;i<101;i++){
            if(date.getHours()==countdata.getHours()&& date.getMinutes()==countdata.getMinutes()) {
                return cell;
            } countdata.setMinutes(countdata.getMinutes()+15);
            cell++;
        }
        return -1;
    }
    public static void main(String []args){

        Disp disp = new DispControl();
        try {
            disp.load_report("Document.xlsx");
            disp.load_config("config/config.xlsx");
            disp.load_departmetn("config/config.xlsx");
        } catch (IOException e) {
            System.out.print("Какое то небалово! где конфиги?");
        }
        ArrayList<TransportExcell> transportExcell = new ArrayList<TransportExcell>();
        for(Report re: disp.getReport()){
            transportExcell.add(new TransportExcell(re,disp.getConfigs(),disp.getDepartMap()));
        }

        for (TransportExcell tr: transportExcell){
            System.out.println(tr);
             convert(tr);
            }
    }
    public static void convert(TransportExcell tr){
        for(Pinter p : tr.getPintersList()){
            System.out.println("    " + p);
        }
    }
}
