package com.swing;

import com.config.Config;
import com.disp.Disp;
import com.disp.disp.control.DispControl;
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
    public static void main(String []args){


        try {
            Disp disp = new DispControl();
            disp.load_config("config/config.xlsx");
                for(Config c: disp.getConfigs())
                    System.out.println(c.toString());

        }catch (FileNotFoundException e){
            System.out.println("File not found");
        }catch (Exception e) {
            System.out.print("это какое-то наебалово, ёуу");
        }

    }
}
