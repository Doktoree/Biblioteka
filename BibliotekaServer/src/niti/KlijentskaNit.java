/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package niti;

import domen.Beletristika;
import domen.Knjiga;
import domen.StrucnaLiteratura;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import konstante.Operacija;
import logika.Kontroler;
import sistemske_operacije.knjiga.SONadjiKnjige;
import sistemske_operacije.knjiga.SOUcitajKnjigu;
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
        
        try {
            izvrsiOperaciju();
        } catch (SQLException ex) {
            Logger.getLogger(KlijentskaNit.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(KlijentskaNit.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    
    public void izvrsiOperaciju() throws SQLException, Exception{
        
        while(flag){
            
            Zahtev zahtev = primiZahtev();
            Odgovor odgovor = new Odgovor();
            Operacija operacija = zahtev.getOperacija();
            
            switch (operacija) {
                
                case VRATI_KNJIGE:
                    Knjiga k = (Knjiga) zahtev.getParametar();
                    List<Knjiga> knjige = Kontroler.getInstanca().vratiKnjige(k);
                    SONadjiKnjige nadji = new SONadjiKnjige(k, knjige);
                    nadji.executeOperation();
                    List<Knjiga> rezultat = nadji.getRezultat();
                    odgovor.setOdgovor(rezultat);
                    break;
                    
                case VRATI_DETALJE_KNJIGE:
                    Knjiga ko = (Knjiga) zahtev.getParametar();
                    SOUcitajKnjigu ucitaj = new SOUcitajKnjigu(ko);
                    ucitaj.executeOperation();
                    Beletristika bele;
                    StrucnaLiteratura strucna;
                    System.out.println("**********************");
                    if(ucitaj.isJeBeletristika()){
                        bele = (Beletristika) ucitaj.getRezultat();
                        odgovor.setOdgovor(bele);
                        System.out.println("---------------------------------");
                    }
                    else{
                        strucna = (StrucnaLiteratura) ucitaj.getRezultat();
                        odgovor.setOdgovor(strucna);
                        System.out.println("1---------------------------------");
                    }
                    break;
                
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
