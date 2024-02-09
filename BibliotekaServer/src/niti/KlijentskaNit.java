/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package niti;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import konstante.Operacija;
import transfer.Odgovor;
import transfer.Zahtev;

/**
 *
 * @author Lav
 */
public class KlijentskaNit extends Thread {
    
    Socket soket;
    boolean flag;
    
    public KlijentskaNit(Socket soket) {
        this.soket = soket;
        flag = true;
    }
    
    public Socket getSoket() {
        return soket;
    }

    @Override
    public void run() {
        
        izvrsiOperaciju();
        
        
    }

    
    public void izvrsiOperaciju(){
        
        while(flag){
            
            Zahtev zahtev = primiZahtev();
            Odgovor odgovor = new Odgovor();
            Operacija operacija = zahtev.getOperacija();
            
            switch (operacija) {
                
            }
            
            posaljiOdgovor(odgovor);
        }
        
    }

    public Zahtev primiZahtev() {
        
        try {
            ObjectInputStream ois = new ObjectInputStream(soket.getInputStream());
            return (Zahtev) ois.readObject();
        } catch (IOException ex) {
            Logger.getLogger(KlijentskaNit.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(KlijentskaNit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void posaljiOdgovor(Odgovor odgovor){
        
        try {
            ObjectOutputStream oos = new ObjectOutputStream(soket.getOutputStream());
            oos.writeObject(odgovor);
        } catch (IOException ex) {
            Logger.getLogger(KlijentskaNit.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
}
