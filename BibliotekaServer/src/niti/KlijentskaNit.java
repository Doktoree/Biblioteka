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
import liste.ListaPrijavljenih;
import logika.Kontroler;
import sistemske_operacije.clan.SONadjiClanove;
import sistemske_operacije.knjiga.SONadjiKnjige;
import sistemske_operacije.knjiga.SOObrisiKnjigu;
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

    public void izvrsiOperaciju() throws SQLException, Exception {

        while (flag) {

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
                    if (ucitaj.isJeBeletristika()) {
                        bele = (Beletristika) ucitaj.getRezultat();
                        odgovor.setOdgovor(bele);

                    } else {
                        strucna = (StrucnaLiteratura) ucitaj.getRezultat();
                        odgovor.setOdgovor(strucna);

                    }
                    break;
                    
                case LOGIN:
                    Korisnik prima = (Korisnik) zahtev.getParametar();
                    
                    Korisnik rez = Kontroler.getInstanca().proveriKorisnika(prima);
                    
                    if(rez == null){
                        odgovor.setOdgovor(null);
                    }
                    else{
                        odgovor.setOdgovor(rez);
                        ListaPrijavljenih.getInstanca().dodaj(rez);
                    }
                    break;
                    
                case KREIRAJ_KNJIGU:
                    OpstiDomenskiObjekat ob = (OpstiDomenskiObjekat) zahtev.getParametar();
                    boolean b = Kontroler.getInstanca().sacuvajKnjigu(ob);
                    if(b){
                        odgovor.setOdgovor("ok");
                    }
                    else{
                        odgovor.setOdgovor("ne");
                    }
            
                    break;

                case OBRISI_KNJIGU:
                    Knjiga primKnjiga = (Knjiga) zahtev.getParametar();
                    SOObrisiKnjigu obrisiKnjigu = new SOObrisiKnjigu(primKnjiga);
                    obrisiKnjigu.executeOperation();
                    boolean bl = obrisiKnjigu.isUspesno();
                    odgovor.setOdgovor(bl);
                    break;
                    
                case VRATI_DETALJE_CLANA:
                    Clan clan = (Clan) zahtev.getParametar();
                    List<Clan> clanovi = Kontroler.getInstanca().vratiClanove(clan);
                    SONadjiClanove nadjiClanove = new SONadjiClanove(clanovi, clan);
                    nadjiClanove.executeOperation();
                    List<Clan> rezultatClanovi = nadjiClanove.getClanovi();
                    odgovor.setOdgovor(rezultatClanovi);
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
