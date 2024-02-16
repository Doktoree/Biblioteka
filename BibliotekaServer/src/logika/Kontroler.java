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
import domen.StrucnaLiteratura;
import forme.ServerskaForma;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import niti.PokretanjeServera;
import sistemske_operacije.clan.SOKreirajClana;
import sistemske_operacije.clan.SOZapamtiClana;
import sistemske_operacije.knjiga.*;

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

    public List<Knjiga> vratiKnjige(Knjiga knjiga) throws SQLException {

        try {
            DbBroker.getInstanca().uspostaviKonekciju();
            List<OpstiDomenskiObjekat> knjige = DbBroker.getInstanca().vratiOpsteDomenskeObjekte(knjiga);
            List<Knjiga> rezultat = new ArrayList<>();

            for (OpstiDomenskiObjekat o : knjige) {

                Knjiga k = (Knjiga) o;
                rezultat.add(k);

            }
            DbBroker.getInstanca().zatvoriKonekciju();
            return rezultat;

        } catch (Exception e) {
            DbBroker.getInstanca().rollback();
            DbBroker.getInstanca().zatvoriKonekciju();
        }

        return null;
    }

    public boolean kreirajClana() throws Exception {

        SOKreirajClana k = new SOKreirajClana();
        k.executeOperation();
        return k.isUspesno();

    }

    public boolean zapamtiClana(Clan clan) throws Exception {

        SOZapamtiClana z = new SOZapamtiClana(clan);
        z.executeOperation();
        return z.isUspesno();

    }

    public Korisnik proveriKorisnika(Korisnik korisnik) {

        return DbBroker.getInstanca().vratiKorisnika(korisnik);

    }

    public boolean sacuvajKnjigu(OpstiDomenskiObjekat o) throws Exception {

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
            } else {
                System.out.println("Nije uspelo insertovanje knjige!");
            }

        }

        return false;

    }

    public List<Clan> vratiClanove(Clan clan) {

        List<OpstiDomenskiObjekat> lista;
        try {
            DbBroker.getInstanca().uspostaviKonekciju();
            lista = DbBroker.getInstanca().vratiOpsteDomenskeObjekte(clan);
            List<Clan> clanovi = new ArrayList<>();

            for (OpstiDomenskiObjekat o : lista) {

                Clan c = (Clan) o;
                System.out.println("_________________: " + c.getPrezimeClana());
                clanovi.add(c);

            }
            DbBroker.getInstanca().zatvoriKonekciju();

            return clanovi;
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

}
