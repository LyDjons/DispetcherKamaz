package com.swing;

import com.disp.disp.control.saveExcell.Pinter;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by disp.chimc on 16.02.15.
 */
public class Test2 {

    public static void main(String []args){
        ArrayList<Pinter> list = new ArrayList<Pinter>();
        list.add(new Pinter(1,3,new Color(0,0,0),"d",2));
        list.add(new Pinter(4,5,new Color(0,0,0),"d",1));
        list.add(new Pinter(6,18,new Color(0,0,0),"stop",12));
        list.add(new Pinter(19,25,new Color(0,0,0),"dwig",2));
        list.add(new Pinter(25,77,new Color(0,0,0),"dwig",2));
        list.add(new Pinter(77,79,new Color(0,0,0),"dwig",2));
        list.add(new Pinter(79,85,new Color(0,0,0),"stop",7));
        list.add(new Pinter(86,90,new Color(0,0,0),"stop",2));
        list.add(new Pinter(92,100,new Color(0,0,0),"stop",2));
        for(Pinter p: list){
            System.out.println(p);
        } System.out.println("-----------------------------------------");

        ArrayList<Pinter> superlist = new ArrayList<Pinter>();
       Pinter p = null;
        for (int i =0; i < list.size();i++){
          if(list.get(i).getPlace().contains("stop")){
              if(p!=null){
                  p.setEnd(list.get(i).getEnd());
                int l= p.getMin()+list.get(i).getMin();
                  p.setMin(l);

                  continue;
              }
              p = list.get(i);
              continue;
          }
              if(p!=null){
              superlist.add(p);
                  p=null;

                  continue;

              }
            else{
                  superlist.add(list.get(i));
              }

        }
        superlist.add(p);


for(Pinter q: superlist){
    System.out.println(q);
}

    }
}
