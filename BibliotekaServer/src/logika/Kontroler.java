/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logika;

import config.Config;
import db.DbBroker;
import domen.Beletristika;
import domen.Clan;
import domen.Knjiga;
import domen.Korisnik;
import domen.OpstiDomenskiObjekat;
import domen.StavkaZaduzenja;
import domen.StrucnaLiteratura;
import domen.Zaduzenje;
import forme.ServerskaForma;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.remote.JMXConnectorFactory;
import liste.ListaPrijavljenih;
import niti.PokretanjeServera;
import sistemske_operacije.clan.SOKreirajClana;
import sistemske_operacije.clan.SONadjiClanove;
import sistemske_operacije.clan.SOObrisiClana;
import sistemske_operacije.clan.SOUcitajClana;
import sistemske_operacije.clan.SOZapamtiClana;
import sistemske_operacije.knjiga.*;
import sistemske_operacije.zaduzenje.SOKreirajZaduzenje;
import sistemske_operacije.zaduzenje.SONadjiZaduzenja;
import sistemske_operacije.zaduzenje.SOZapamtiZaduzenje;

/**
 *
 * @author Lav
 */
public class Kontroler {

    private static Kontroler instanca;
    private static PokretanjeServera ps;

    private Kontroler() {
    }

    public static Kontroler getInstanca() {
        if (instanca == null) {
            instanca = new Kontroler();
        }
        return instanca;
    }

    public static void main(String[] args) {

        ServerskaForma sf = new ServerskaForma();
        sf.setVisible(true);

    }

    public void pokreniServer() {

        ps = new PokretanjeServera(Config.getInstanca().getPort());
        ps.start();

    }

    public void zaustaviServer() {

        ps.ugasiServer();

    }

    public void uspostaviKonekciju() {

        DbBroker.getInstanca().uspostaviKonekciju();

    }

    public void commit() {

    }

    public synchronized List<OpstiDomenskiObjekat> nadjiKnjige(Knjiga knjiga) throws Exception {

        SONadjiKnjige soNadjiKnjige = new SONadjiKnjige(knjiga);
        soNadjiKnjige.executeOperation();
        return soNadjiKnjige.getRezultat();

    }

    public synchronized OpstiDomenskiObjekat ucitajKnjigu(Knjiga knjiga) throws Exception {

        SOUcitajKnjigu soUcitajKnjigu = new SOUcitajKnjigu(knjiga);
        soUcitajKnjigu.executeOperation();
        if (soUcitajKnjigu.isJeBeletristika()) {
            return (Beletristika) soUcitajKnjigu.getRezultat();
        }
        return (StrucnaLiteratura) soUcitajKnjigu.getRezultat();

    }

    public synchronized boolean kreirajClana() throws Exception {

        SOKreirajClana k = new SOKreirajClana();
        k.executeOperation();
        return k.isUspesno();

    }

    public synchronized boolean zapamtiClana(Clan clan) throws Exception {

        SOZapamtiClana z = new SOZapamtiClana(clan);
        z.executeOperation();
        return z.isUspesno();

    }

    public synchronized Korisnik proveriKorisnika(Korisnik korisnik) {

        Korisnik rezultat = DbBroker.getInstanca().vratiKorisnika(korisnik);
        if (rezultat != null) {
            ListaPrijavljenih.getInstanca().dodaj(korisnik);
        }
        return rezultat;

    }

    public synchronized boolean sacuvajKnjigu(OpstiDomenskiObjekat o) throws Exception {

        Beletristika bele;
        StrucnaLiteratura strucna;

        if (o instanceof Beletristika) {

            bele = (Beletristika) o;
            Knjiga k = bele.getKnjiga();
            SOZapamtiKnjigu zapamti = new SOZapamtiKnjigu(k);
            zapamti.executeOperation();
            if (zapamti.isUspesno()) {
                System.out.println("Uspelo je cuvanje samo knjige!");
            } else {
                System.out.println("Nije uspelo cuvanje samo knjige!");
            }

            Knjiga m = DbBroker.getInstanca().vratiKnjiguBezPrimarnog(k);
            bele.setKnjiga(m);
            if (DbBroker.getInstanca().insertujVrstuKnjige(bele)) {
                System.out.println("Uspelo je insertovanje knjige!");
                return true;
            } else {
                System.out.println("Nije uspelo insertovanje knjige!");
            }

        } else if (o instanceof StrucnaLiteratura) {

            strucna = (StrucnaLiteratura) o;

            Knjiga k = strucna.getKnjiga();
            SOZapamtiKnjigu zapamti = new SOZapamtiKnjigu(k);
            zapamti.executeOperation();
            if (zapamti.isUspesno()) {
                System.out.println("Uspelo je cuvanje samo knjige!");
            } else {
                System.out.println("Nije uspelo cuvanje samo knjige!");
            }

            Knjiga m = DbBroker.getInstanca().vratiKnjiguBezPrimarnog(k);
            strucna.setKnjiga(m);
            if (DbBroker.getInstanca().insertujVrstuKnjige(strucna)) {
                System.out.println("Uspelo je insertovanje knjige!");
                return true;
            } else {
                System.out.println("Nije uspelo insertovanje knjige!");
            }

        }

        return false;

    }

    public synchronized boolean obrisiKnjigu(Knjiga knjiga) throws Exception {

        SOObrisiKnjigu soObrisiKnjigu = new SOObrisiKnjigu(knjiga);
        soObrisiKnjigu.executeOperation();
        return soObrisiKnjigu.isUspesno();

    }

    public synchronized List<Clan> nadjiClanove(Clan clan) throws Exception {

        List<OpstiDomenskiObjekat> clanovi = DbBroker.getInstanca().vratiOpsteDomenskeObjekte(new Clan());
        SONadjiClanove soNadjiClanove = new SONadjiClanove(clanovi, clan);
        soNadjiClanove.executeOperation();
        return soNadjiClanove.getClanovi();

    }

    public synchronized Clan ucitajClana(Clan clan) throws Exception {

        SOUcitajClana soUcitajClana = new SOUcitajClana(clan);
        soUcitajClana.executeOperation();
        return soUcitajClana.getClan();

    }

    public synchronized boolean obrisiClana(Clan clan) throws Exception {

        SOObrisiClana soObrisiClana = new SOObrisiClana(clan);
        soObrisiClana.executeOperation();
        return soObrisiClana.isUspesno();

    }

    public synchronized boolean kreirajClana(Clan clan) throws Exception {

        SOKreirajClana sOKreirajClana = new SOKreirajClana();
        sOKreirajClana.executeOperation();
        sOKreirajClana.setClan(clan);
        SOZapamtiClana soZapamtiClana = new SOZapamtiClana(sOKreirajClana.getClan());
        soZapamtiClana.executeOperation();
        return soZapamtiClana.isUspesno();

    }

    public synchronized boolean izmeniClana(Clan clan) throws Exception {

        SOZapamtiClana soZapamtiClana = new SOZapamtiClana(clan);
        soZapamtiClana.executeOperation();
        return soZapamtiClana.isUspesno();

    }

    public synchronized boolean kreirajZaduzenje(Zaduzenje zaduzenje) throws Exception {

        SOKreirajZaduzenje soKreirajZaduzenje = new SOKreirajZaduzenje();
        soKreirajZaduzenje.executeOperation();
        soKreirajZaduzenje.setZaduzenje(zaduzenje);
        SOZapamtiZaduzenje soZapamtiZaduzenje = new SOZapamtiZaduzenje(soKreirajZaduzenje.getZaduzenje());
        soZapamtiZaduzenje.executeOperation();
        return soZapamtiZaduzenje.isUspesno();

    }

    public synchronized Zaduzenje vratiPoslednjeZaduzenjeClana(Zaduzenje zaduzenje) {

        return DbBroker.getInstanca().vratiPoslednjeZaduzenjeClana(zaduzenje);

    }

    public synchronized boolean dodajStavkuZaduzenja(StavkaZaduzenja stavka) {

        if (DbBroker.getInstanca().knjigaJeZauzeta(stavka.getKnjiga())) {
            sacuvajStavkuZaduzenja(stavka);
            return true;
        }

        return false;

    }

    public synchronized Zaduzenje updateBrojKnjigaZaduzenje(Zaduzenje zad) {

        DbBroker.getInstanca().uspostaviKonekciju();
        Zaduzenje zaduzenje = DbBroker.getInstanca().updateBrojKnjigaZaduzenje(zad);
        System.out.println("Broj knjiga: " + zaduzenje);
        DbBroker.getInstanca().commit();
        DbBroker.getInstanca().zatvoriKonekciju();
        return zaduzenje;
    }

     public synchronized boolean updateZaduzenjeRok(Zaduzenje zad) {

        DbBroker.getInstanca().uspostaviKonekciju();
        boolean b;
        try {
            b = DbBroker.getInstanca().updateOpstiDomenskiObjekat(zad);
            DbBroker.getInstanca().commit();
            DbBroker.getInstanca().zatvoriKonekciju();
            return b;
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
     
    public synchronized List<StavkaZaduzenja> vratiStavkeZaduzenja(Zaduzenje zaduzenje){
        
        DbBroker.getInstanca().uspostaviKonekciju();
        List<StavkaZaduzenja> stavkaZaduzenje = DbBroker.getInstanca().vratiStavkeZaduzenja(zaduzenje);
        DbBroker.getInstanca().zatvoriKonekciju();
        return stavkaZaduzenje;
        
    }
    public List<Clan> vratiClanove(Clan clan) {

        List<OpstiDomenskiObjekat> lista;
        try {
            DbBroker.getInstanca().uspostaviKonekciju();
            lista = DbBroker.getInstanca().vratiOpsteDomenskeObjekte(clan);
            List<Clan> clanovi = new ArrayList<>();

            for (OpstiDomenskiObjekat o : lista) {

                Clan c = (Clan) o;
                clanovi.add(c);

            }
            DbBroker.getInstanca().zatvoriKonekciju();

            return clanovi;

        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

    public synchronized List<OpstiDomenskiObjekat> vratiSvaZaduzenja() {

        List<OpstiDomenskiObjekat> lista;

        try {
            DbBroker.getInstanca().uspostaviKonekciju();
            lista = DbBroker.getInstanca().vratiOpsteDomenskeObjekte(new Zaduzenje());

            Zaduzenje z = (Zaduzenje) lista.get(0);
            DbBroker.getInstanca().zatvoriKonekciju();

            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public synchronized boolean sacuvajStavkeZaduzenja(List<StavkaZaduzenja> stavke) {

        for (StavkaZaduzenja s : stavke) {

            try {
                DbBroker.getInstanca().sacuvajOpstiDomenskiObjekat(s);
            } catch (SQLException ex) {
                System.out.println("Nije moguce sacuvati stavku!");
                return false;
            }

        }
        System.out.println("Stavke su uspesno sacuvane!");
        return true;
    }

    public synchronized boolean sacuvajStavkuZaduzenja(StavkaZaduzenja stavka) {

        
        try {
            DbBroker.getInstanca().uspostaviKonekciju();
            Knjiga knjiga = (Knjiga) DbBroker.getInstanca().vratiOpstiDomenskiObjekatPrimarniKljuc(new Knjiga(), stavka.getKnjiga().getSifraKnjige());
            if(knjiga == null){
                System.out.println("Knjiga ne postoji!");
                return false;
            }
            stavka.setKnjiga(knjiga);
            if(stavka.getKnjiga().isJeZauzeta()){
                System.out.println("!!!!!!!!!!!!!!!!!!!!! " + "Knjiga je zauzeta");
                return false;
            }
            boolean b = DbBroker.getInstanca().sacuvajOpstiDomenskiObjekat(stavka);
            DbBroker.getInstanca().commit();
            DbBroker.getInstanca().zatvoriKonekciju();
            DbBroker.getInstanca().knjigaZauzeta(stavka);
            return b;
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    
    public synchronized boolean obrisiZaduzenje(Zaduzenje zaduzenje){
        
        DbBroker.getInstanca().uspostaviKonekciju();
        try {
            boolean b = DbBroker.getInstanca().obrisiOpstiDomenskiObjekat(zaduzenje);
            DbBroker.getInstanca().commit();
            return b;
        } catch (SQLException ex) {
            System.out.println("Nije moguce obrisati zaduzenje!");
            return false;
        }
        
    }

   

}
