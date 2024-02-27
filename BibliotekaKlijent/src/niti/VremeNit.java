package niti;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javax.swing.JLabel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Lav
 */
public class VremeNit extends Thread {
    
    JLabel labelaDatum;
    JLabel labelaVreme;

    public VremeNit(JLabel labelaDatum, JLabel labelaVreme) {
        this.labelaDatum = labelaDatum;
        this.labelaVreme = labelaVreme;
    }
    
    

    @Override
    public void run() {
        
        while(true){
            
            LocalDate datum = LocalDate.now();
            labelaDatum.setText(String.valueOf(datum));
            DateFormat df = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            labelaVreme.setText(df.format(date));
                    
            
        }
        
    }
    
    
    
    
}
