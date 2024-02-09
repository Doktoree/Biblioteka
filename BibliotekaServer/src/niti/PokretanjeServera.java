/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package niti;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lav
 */
public class PokretanjeServera extends Thread {
    
    ServerSocket ss;
    ArrayList<KlijentskaNit> klijenti;
    private boolean flag = true;
    
    public PokretanjeServera(int port) {
        
        try {
            flag = true;
            klijenti = new ArrayList<>();
            ss = new ServerSocket(port);
            System.out.println("Serverski soket je pokrenut!");
        } catch (IOException ex) {
            System.out.println("Serverski soket nije pokrenut!");
            Logger.getLogger(PokretanjeServera.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void run() {
        
        while(flag){
            
            try {
                Socket klijent = ss.accept();
                KlijentskaNit kn = new KlijentskaNit(klijent);
                kn.start();
                klijenti.add(kn);
                int brojac = klijenti.size();
                System.out.println("Klijent broj " + brojac + " se povezao!");
            } catch (IOException ex) {
                Logger.getLogger(PokretanjeServera.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }

    
    public void ugasiServer(){
        
        flag = false;
        for(KlijentskaNit kn: klijenti){
            
            try {
                kn.getSoket().close();
                System.out.println("Klijent se diskonektovao!");
            } catch (IOException ex) {
                System.out.println("Klijent ne moze da se diskonektuje!");
                Logger.getLogger(PokretanjeServera.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        try {
            ss.close();
            System.out.println("Server se ugasio!");
        } catch (IOException ex) {
            System.out.println("Server ne moze da se ugasi!");
            Logger.getLogger(PokretanjeServera.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void ugasiKlijenta(KlijentskaNit kn){
        
        
        try {
            kn.getSoket().close();
            System.out.println("Klijent se ugasio!");
        } catch (IOException ex) {
            System.out.println("Klijent ne moze da se ugasi");
            Logger.getLogger(PokretanjeServera.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    
}
