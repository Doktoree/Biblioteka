/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package niti;

import db.DbBroker;
import domen.Beletristika;
import domen.Clan;
import domen.Knjiga;
import domen.Korisnik;
import domen.OpstiDomenskiObjekat;
import domen.StavkaZaduzenja;
import domen.StrucnaLiteratura;
import domen.Zaduzenje;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import konstante.Operacija;
import liste.ListaPrijavljenih;
import logika.Kontroler;
import sistemske_operacije.clan.SONadjiClanove;
import sistemske_operacije.clan.SOUcitajClana;
import sistemske_operacije.knjiga.SONadjiKnjige;
import sistemske_operacije.knjiga.SOObrisiKnjigu;
import sistemske_operacije.knjiga.SOUcitajKnjigu;
import sistemske_operacije.zaduzenje.SONadjiZaduzenja;
import transfer.Odgovor;
import transfer.Zahtev;
import sistemske_operacije.clan.*;
import sistemske_operacije.zaduzenje.*;
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

    public void izvrsiOperaciju() throws SQLException, Exception {

        while (flag) {

            Zahtev zahtev = primiZahtev();
            Odgovor odgovor = new Odgovor();
            Operacija operacija = zahtev.getOperacija();

            switch (operacija) {

                case NADJI_KNJIGE:
                    Knjiga k = (Knjiga) zahtev.getParametar();
                    odgovor.setOdgovor(Kontroler.getInstanca().nadjiKnjige(k));
                    break;

                case UCITAJ_KNJIGU:
                    Knjiga knjigaZaUcitavanje = (Knjiga) zahtev.getParametar();
                    odgovor.setOdgovor(Kontroler.getInstanca().ucitajKnjigu(knjigaZaUcitavanje));
                    break;
                    
                case LOGIN:
                    Korisnik korisnik = (Korisnik) zahtev.getParametar();                  
                    Korisnik rez = Kontroler.getInstanca().proveriKorisnika(korisnik);
                    odgovor.setOdgovor(rez);
                    break;
                 //!!! kreiraj knjigu pogledati
                case KREIRAJ_KNJIGU:
                    OpstiDomenskiObjekat ob = (OpstiDomenskiObjekat) zahtev.getParametar();
                    boolean b = Kontroler.getInstanca().sacuvajKnjigu(ob);
                    System.out.println("Booolean b: " + b);
                    if(b){
                        odgovor.setOdgovor("ok");
                    }
                    else{
                        odgovor.setOdgovor("ne");
                    }
            
                    break;

                case OBRISI_KNJIGU:
                    Knjiga knjigaZaBrisanje = (Knjiga) zahtev.getParametar();
                    odgovor.setOdgovor(Kontroler.getInstanca().obrisiKnjigu(knjigaZaBrisanje));
                    break;
                    
                case NADJI_CLANOVE:
                    Clan clanZaPronalazenje = (Clan) zahtev.getParametar();
                    odgovor.setOdgovor(Kontroler.getInstanca().nadjiClanove(clanZaPronalazenje));
                    break;

                //!
                case VRATI_SVA_ZADUZENJA_CLANA:
                    Clan primClan = (Clan) zahtev.getParametar();
                    Zaduzenje zaduzenje = new Zaduzenje();
                    zaduzenje.setClan(primClan);
                    SONadjiZaduzenja nadjiZaduzenja = new SONadjiZaduzenja(Kontroler.getInstanca().vratiSvaZaduzenja(), zaduzenje);
                    nadjiZaduzenja.executeOperation();                   
                    odgovor.setOdgovor(nadjiZaduzenja.getRezultat());
                    break;
                    
                case UCITAJ_CLANA:
                    Clan clanZaUcitavanje = (Clan) zahtev.getParametar();
                    odgovor.setOdgovor(Kontroler.getInstanca().ucitajClana(clanZaUcitavanje));
                    break;
                    
                case OBRISI_CLANA:
                    Clan clanZaBrisanje = (Clan) zahtev.getParametar();
                    odgovor.setOdgovor(Kontroler.getInstanca().obrisiClana(clanZaBrisanje));
                    break;
                    
                case KREIRAJ_CLANA:
                    Clan clanZaKreiranje = (Clan) zahtev.getParametar();
                    odgovor.setOdgovor(Kontroler.getInstanca().kreirajClana(clanZaKreiranje));
                    break;
                
                case IZMENI_CLANA:
                    Clan clanZaIzmenu = (Clan) zahtev.getParametar();
                    SOZapamtiClana zapamtiClana2 = new SOZapamtiClana(clanZaIzmenu);
                    zapamtiClana2.executeOperation();
                    odgovor.setOdgovor(zapamtiClana2.isUspesno());
                    break;
                                 
                    
                case DODAJ_ZADUZENJE:
                    Zaduzenje zaduzenje2 = (Zaduzenje) zahtev.getParametar();
                    SOKreirajZaduzenje kreirajZad = new SOKreirajZaduzenje();
                    kreirajZad.executeOperation();
                    kreirajZad.setZaduzenje(zaduzenje2);
                    SOZapamtiZaduzenje zapamtiZaduzenje = new SOZapamtiZaduzenje(kreirajZad.getZaduzenje());
                    zapamtiZaduzenje.executeOperation();
                    odgovor.setOdgovor(zapamtiZaduzenje.isUspesno());
                    break;
                 
                case VRATI_ZADUZENJE:
                    Zaduzenje zaduzenjeVrati = (Zaduzenje) zahtev.getParametar();
                    SONadjiZaduzenja nadjiZaduzenja2 = new SONadjiZaduzenja(Kontroler.getInstanca().vratiSvaZaduzenja(), zaduzenjeVrati);
                    nadjiZaduzenja2.executeOperation();
                    Zaduzenje vratiZaduzenje = (Zaduzenje) nadjiZaduzenja2.getRezultat().get(nadjiZaduzenja2.getRezultat().size()-1);
                    odgovor.setOdgovor(vratiZaduzenje);
                    break;
                 
                case DODAJ_STAVKU_ZADUZENJA:
                    StavkaZaduzenja stavkaZaduzenja = (StavkaZaduzenja) zahtev.getParametar();
                    if(DbBroker.getInstanca().knjigaJeZauzeta(stavkaZaduzenja.getKnjiga())){
                        System.out.println("^^^^^^^^^^^^^^^^^^^");
                        odgovor.setOdgovor(Kontroler.getInstanca().sacuvajStavkuZaduzenja(stavkaZaduzenja));
                    }
                    else{
                        odgovor.setOdgovor(false);
                    }
                    break;
                    
                case DODAJ_BROJ_KNJIGA:
                    Zaduzenje zaduzenjeBrojKnjiga = (Zaduzenje) zahtev.getParametar();
                    SONadjiZaduzenja nadjiZaduzenja3 = new SONadjiZaduzenja(Kontroler.getInstanca().vratiSvaZaduzenja(), zaduzenjeBrojKnjiga);
                    nadjiZaduzenja3.executeOperation();
                    odgovor.setOdgovor(Kontroler.getInstanca().updateBrojKnjigaZaduzenje((Zaduzenje) nadjiZaduzenja3.getRezultat().get(0)));
                    break;
                    
                case PRODUZI_ROK:
                    Zaduzenje zaduzenjeProduziRok = (Zaduzenje) zahtev.getParametar();
                    boolean rok = Kontroler.getInstanca().updateZaduzenjeRok(zaduzenjeProduziRok);
                    odgovor.setOdgovor(rok);
                    break;
                    
                case VRATI_STAVKE:
                    Zaduzenje zaduzenjeVratiStavku = (Zaduzenje) zahtev.getParametar();
                    List<StavkaZaduzenja> listaStavki = DbBroker.getInstanca().vratiStavkeZaduzenja(zaduzenjeVratiStavku);
                    odgovor.setOdgovor(listaStavki);
                    break;
                    
                case KNJIGA_NIJE_AKTIVNA:
                    StavkaZaduzenja stavkaAktivna = (StavkaZaduzenja) zahtev.getParametar();
                    if(DbBroker.getInstanca().knjigaZauzeta(stavkaAktivna) && DbBroker.getInstanca().promeniStavkuNeAktivna(stavkaAktivna)){
                        odgovor.setOdgovor(true);
                    }
                    else{
                        odgovor.setOdgovor(false);
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

    public void posaljiOdgovor(Odgovor odgovor) {

        try {
            ObjectOutputStream oos = new ObjectOutputStream(soket.getOutputStream());
            oos.writeObject(odgovor);
        } catch (IOException ex) {
            Logger.getLogger(KlijentskaNit.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
