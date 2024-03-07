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
                    if (b) {
                        odgovor.setOdgovor("ok");
                    } else {
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
                    odgovor.setOdgovor(Kontroler.getInstanca().izmeniClana(clanZaIzmenu));
                    break;

                case KREIRAJ_ZADUZENJE:
                    Zaduzenje zaduzenjeZaKreiranje = (Zaduzenje) zahtev.getParametar();
                    odgovor.setOdgovor(Kontroler.getInstanca().kreirajZaduzenje(zaduzenjeZaKreiranje));
                    break;

                case VRATI_POSLEDNJE_ZADUZENJE:
                    Zaduzenje zaduzenjeVrati = (Zaduzenje) zahtev.getParametar();
                    odgovor.setOdgovor(Kontroler.getInstanca().vratiPoslednjeZaduzenjeClana(zaduzenjeVrati));
                    break;

                case DODAJ_STAVKU_ZADUZENJA:
                    StavkaZaduzenja stavkaZaduzenja = (StavkaZaduzenja) zahtev.getParametar();
                    odgovor.setOdgovor(Kontroler.getInstanca().sacuvajStavkuZaduzenja(stavkaZaduzenja));
                    break;

                case DODAJ_BROJ_KNJIGA:
                    Zaduzenje zaduzenjeBrojKnjiga = (Zaduzenje) zahtev.getParametar();
                    System.out.println("------------------------------------------");
                    System.out.println("Broj knjiga: ");
                    odgovor.setOdgovor(Kontroler.getInstanca().updateBrojKnjigaZaduzenje(zaduzenjeBrojKnjiga));
                    break;

                case PRODUZI_ROK:
                    Zaduzenje zaduzenjeProduziRok = (Zaduzenje) zahtev.getParametar();
                    odgovor.setOdgovor(Kontroler.getInstanca().updateZaduzenjeRok(zaduzenjeProduziRok));
                    break;

                case VRATI_STAVKE:
                    Zaduzenje zaduzenjeVratiStavku = (Zaduzenje) zahtev.getParametar();
                    odgovor.setOdgovor(Kontroler.getInstanca().vratiStavkeZaduzenja(zaduzenjeVratiStavku));
                    break;

                case VRATI_KNJIGU:
                    StavkaZaduzenja stavkaAktivna = (StavkaZaduzenja) zahtev.getParametar();
                    odgovor.setOdgovor(Kontroler.getInstanca().vratiKnjigu(stavkaAktivna));
                    break;
                    
                case OBRISI_ZADUZENJE:
                    Zaduzenje zaduzenjeZaBrisanje = (Zaduzenje) zahtev.getParametar();
                    odgovor.setOdgovor(Kontroler.getInstanca().obrisiZaduzenje(zaduzenjeZaBrisanje));
                    break;
                 
                case ODJAVA:
                    Korisnik korisnikOdjava = (Korisnik) zahtev.getParametar();
                    Kontroler.getInstanca().obrisiKorisnika(korisnikOdjava);
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
