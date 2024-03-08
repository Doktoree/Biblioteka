/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logika;

import domen.Knjiga;
import domen.OpstiDomenskiObjekat;
import domen.Zaduzenje;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import konstante.Operacija;
import transfer.Odgovor;
import transfer.Zahtev;

/**
 *
 * @author Lav
 */
public class Kontroler {

    private Socket soket;
    private static Kontroler instanca;

    private Kontroler() {

        try {
            soket = new Socket("localhost", 9000);
            System.out.println("Klijentski soket se podigao...");

        } catch (IOException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static Kontroler getInstanca() {
        if (instanca == null) {
            instanca = new Kontroler();
        }
        return instanca;
    }

    public List<Knjiga> vratiKnjige(Knjiga k) {

        Zahtev zahtev = new Zahtev(k, Operacija.NADJI_KNJIGE);
        posaljiZahtev(zahtev);
        Odgovor odgovor = primiOdgovor();
        List<Knjiga> rezultat = (List<Knjiga>) odgovor.getOdgovor();

        return rezultat;

    }
    
    public OpstiDomenskiObjekat vratiDetaljeKnjige(Knjiga knjiga){
        
        Zahtev zahtev = new Zahtev(knjiga, Operacija.UCITAJ_KNJIGU);
        posaljiZahtev(zahtev);
        Odgovor odgovor = primiOdgovor();
        OpstiDomenskiObjekat opsti = (OpstiDomenskiObjekat) odgovor.getOdgovor();
        
        
        return opsti;
    }
    
    public void zatvoriKonekciju() throws IOException{
        
        System.out.println("Soket se zatvara...");
        soket.close();
        
    }

    public Odgovor primiOdgovor() {

        try {
            ObjectInputStream ois = new ObjectInputStream(soket.getInputStream());
            return (Odgovor) ois.readObject();
        } catch (IOException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void posaljiZahtev(Zahtev zahtev) {

        try {
            ObjectOutputStream oos = new ObjectOutputStream(soket.getOutputStream());
            oos.writeObject(zahtev);
        } catch (IOException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    

}
