/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logika;

import config.Config;
import db.DbBroker;
import domen.Clan;
import domen.Knjiga;
import domen.OpstiDomenskiObjekat;
import forme.ServerskaForma;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import niti.PokretanjeServera;
import sistemske_operacije.clan.SOKreirajClana;
import sistemske_operacije.clan.SOZapamtiClana;

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

}
