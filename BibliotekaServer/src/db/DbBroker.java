/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import config.Config;
import domen.Beletristika;
import domen.Clan;
import domen.Knjiga;
import domen.Korisnik;
import domen.OpstiDomenskiObjekat;
import domen.StavkaZaduzenja;
import domen.Zaduzenje;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lav
 */
public class DbBroker {

    private static DbBroker instanca;
    private Connection connection;

    private DbBroker() {
    }

    public static DbBroker getInstanca() {
        if (instanca == null) {
            instanca = new DbBroker();
        }
        return instanca;
    }

    public boolean uspostaviKonekciju() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Drajver ucitan!");
            String url = Config.getInstanca().getDbUrl();
            String username = Config.getInstanca().getUsername();
            String password = Config.getInstanca().getPassword();
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
            System.out.println("Povezivanje sa bazom je uspelo!");
            return true;
        } catch (ClassNotFoundException ex) {
            System.out.println("Drajver nije nadjen!");
            return false;
        } catch (SQLException ex) {
            System.out.println("Nije uspelo konektovanje sa bazom!");
            return false;
        }

    }

    public void zatvoriKonekciju() {

        try {
            connection.close();
            System.out.println("Konekcija je zatvorena!");
        } catch (SQLException ex) {
            System.out.println("Konekcija ne moze da se zatvori!");
        }

    }

    public void commit() {

        try {
            connection.commit();
            System.out.println("Commit je izvrsen!");
        } catch (SQLException ex) {
            System.out.println("Commit ne moze da se izvrsi!");
        }

    }

    public void rollback() {

        try {
            connection.rollback();
            System.out.println("Rollback je izvrsen!");
        } catch (SQLException ex) {
            System.out.println("Rollback ne moze da se izvrsi!");
        }

    }

    public synchronized List<OpstiDomenskiObjekat> vratiOpsteDomenskeObjekte(OpstiDomenskiObjekat o) throws SQLException {

        try {
            String query = "SELECT * FROM " + o.getNazivTabele();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            List<OpstiDomenskiObjekat> lista = o.konvertujRSUListu(rs);
            statement.close();
            System.out.println("ResultSet uspesno postavljen!");
            return lista;

        } catch (SQLException ex) {
            System.out.println("Greska u postavljanju ResultSet-a na klasu " + o.getNazivTabele());
            ex.printStackTrace();
            throw ex;
        }

    }

    public synchronized boolean updateOpstiDomenskiObjekat(OpstiDomenskiObjekat o) throws SQLException {
        try {
            String query = "";
            if (o.getSlozeniPrimarniKljuc() == null) {
                query = "UPDATE " + o.getNazivTabele() + " SET " + o.getUpdateUpit() + " WHERE " + o.getNazivPrimarnogKljuca() + "=" + o.getVrednostPrimarnogKljuca();
            } else {
                query = "UPDATE " + o.getNazivTabele() + " SET " + o.getUpdateUpit() + " WHERE " + o.getSlozeniPrimarniKljuc();
            }
            System.out.println(query);
            Statement s = (Statement) connection.createStatement();
            int i = s.executeUpdate(query);
            s.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("Ne moze da se azurira objekat: " + o.getNazivTabele());
            throw ex;
        }
    }

    public synchronized boolean sacuvajOpstiDomenskiObjekat(OpstiDomenskiObjekat o) throws SQLException {

        String query = "INSERT INTO " + o.getNazivTabele() + "(" + o.getNaziveParametara() + ") VALUES("
                + o.getParametre() + ")";
        System.out.println("+++++++: query: " + query);
        try {
            Statement statement = connection.createStatement();
            int a = statement.executeUpdate(query);
            System.out.println("Objekat uspesno sacuvan! " + a);
            return true;
        } catch (SQLException ex) {
            System.out.println("Objekat ne moze da se sacuva!");
            throw ex;
        }

    }

    public synchronized OpstiDomenskiObjekat vratiOpstiDomenskiObjekatPrimarniKljuc(OpstiDomenskiObjekat o, Long id) throws SQLException {

        String query = "";
        if (id.compareTo(-1l) == 0) {
            return null;
        }
        if (o.getSlozeniPrimarniKljuc() == null) {
            query = "SELECT * FROM " + o.getNazivTabele() + " WHERE " + o.getNazivPrimarnogKljuca() + "=" + id;
        } else {
            query = "SELECT * FROM " + o.getNazivTabele() + " WHERE " + o.getSlozeniPrimarniKljuc();

        }

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        List<OpstiDomenskiObjekat> lista = o.konvertujRSUListu(rs);
        if (lista.isEmpty()) {
            return null;
        }

        return lista.get(0);

    }

    public synchronized boolean obrisiOpstiDomenskiObjekat(OpstiDomenskiObjekat o) throws SQLException {

        try {
            String query = "";

            if (o.getSlozeniPrimarniKljuc() == null) {
                query = "DELETE FROM " + o.getNazivTabele() + " WHERE " + o.getNazivPrimarnogKljuca() + "=" + o.getVrednostPrimarnogKljuca();
            } else {
                query = "DELETE FROM " + o.getNazivTabele() + " WHERE " + o.getSlozeniPrimarniKljuc();

            }
            System.out.println("Query: " + query);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            commit();
            return true;
        } catch (Exception e) {

            System.out.println("Ne moze da se izvrsi brisanje Opsteg domenskog objekta!");
            throw e;
        }

    }

    public Zaduzenje vratiPoslednjeZaduzenjeClana(Zaduzenje zaduzenje) {

        uspostaviKonekciju();
        String query = "SELECT * FROM zaduzenje WHERE sifra_clana = " + zaduzenje.getClan().getSifraClana() + " ORDER BY sifra_zaduzenja DESC";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            List<OpstiDomenskiObjekat> zaduzenja = new Zaduzenje().konvertujRSUListu(rs);
            System.out.println("Uspesno vraceno poslednje zaduzenje!");
            zatvoriKonekciju();
            return (Zaduzenje) zaduzenja.get(0);
        } catch (SQLException ex) {
            System.out.println("Nije moguce vratiti poslednje zaduzenje!");
            Logger.getLogger(DbBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public synchronized boolean vratiTipKnjige(long id) {

        String query1 = "SELECT * FROM beletristika WHERE sifra_knjige = " + id;

        try {
            OpstiDomenskiObjekat ob = new Beletristika();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query1);
            List<OpstiDomenskiObjekat> lista = ob.konvertujRSUListu(rs);
            if (lista.isEmpty()) {
                return false;
            }
            return true;
        } catch (SQLException ex) {
            return false;
        }

    }

    public synchronized Korisnik vratiKorisnika(Korisnik korisnik) {

        uspostaviKonekciju();
        List<OpstiDomenskiObjekat> lista = new ArrayList<>();
        String query = "SELECT * FROM korisnik WHERE korisnicko_ime = ? AND lozinka = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, korisnik.getKorisnickoIme());
            ps.setString(2, korisnik.getLozinka());
            ResultSet rs = ps.executeQuery();
            lista = korisnik.konvertujRSUListu(rs);
            Korisnik vrati = (Korisnik) lista.get(0);
            System.out.println("Izvrsena je provera korisnika!");
            return vrati;
        } catch (SQLException ex) {
            System.out.println("Nije izvrsena provera korisnika!");
            Logger.getLogger(DbBroker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public synchronized Knjiga vratiKnjiguBezPrimarnog(Knjiga knjiga) {

        uspostaviKonekciju();
        List<OpstiDomenskiObjekat> lista = new ArrayList<>();
        String query = "SELECT * FROM knjiga WHERE naziv_knjige = ? AND autor_knjige = ? AND datum_izdavanja = ? AND "
                + "je_zauzeta = ? ORDER BY sifra_knjige DESC";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, knjiga.getNazivKnjige());
            ps.setString(2, knjiga.getAutorKnjige());
            ps.setInt(3, knjiga.getGodina());
            ps.setBoolean(4, knjiga.isJeZauzeta());
            ResultSet rs = ps.executeQuery();

            lista = knjiga.konvertujRSUListu(rs);

            return (Knjiga) lista.get(0);

        } catch (SQLException ex) {
            Logger.getLogger(DbBroker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

    public boolean insertujVrstuKnjige(OpstiDomenskiObjekat o) {

        uspostaviKonekciju();
        String query = "INSERT INTO " + o.getNazivTabele() + " VALUES(" + o.getParametre() + ")";
        try {
            Statement statement = connection.createStatement();
            int broj = statement.executeUpdate(query);
            commit();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DbBroker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;

    }

    public synchronized List<Zaduzenje> vratiSvaZaduzenjaClana(Clan clan) {

        uspostaviKonekciju();
        List<OpstiDomenskiObjekat> lista = new ArrayList<>();
        List<Zaduzenje> rezultat = new ArrayList<>();
        String query = "SELECT * FROM zaduzenje WHERE sifra_clana = " + clan.getSifraClana();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            lista = new Zaduzenje().konvertujRSUListu(rs);

            for (OpstiDomenskiObjekat o : lista) {

                Zaduzenje z = (Zaduzenje) o;
                rezultat.add(z);

            }
            zatvoriKonekciju();
            return rezultat;

        } catch (SQLException ex) {
            Logger.getLogger(DbBroker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public synchronized Zaduzenje updateBrojKnjigaZaduzenje(Zaduzenje zad) {

        int broj = zad.getBrojKnjiga() + 1;
        String query = "UPDATE zaduzenje SET broj_knjiga = " + broj + " WHERE sifra_zaduzenja = " + zad.getSifraZaduzenja();
        System.out.println("query");
        zad.setBrojKnjiga(broj);
        Zaduzenje zaduzenje = zad;
        Statement statement;
        try {
            statement = connection.createStatement();
            int r = statement.executeUpdate(query);
            System.out.println("query update: " + query);
            System.out.println("Uspeno izvrsen update knjiga!");
            return zaduzenje;
        } catch (SQLException ex) {
            System.out.println("Nije uspeno izvrsen update knjiga!");
            return null;
        }

    }

    public synchronized List<StavkaZaduzenja> vratiStavkeZaduzenja(Zaduzenje zaduzenje) {

        DbBroker.getInstanca().uspostaviKonekciju();

        String query = "SELECT * FROM stavka_zaduzenja WHERE sifra_zaduzenja = " + zaduzenje.getSifraZaduzenja() + " AND stanje ='aktivno' ";

        System.out.println("Query: " + query);
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            List<OpstiDomenskiObjekat> lista = new StavkaZaduzenja().konvertujRSUListu(rs);
            List<StavkaZaduzenja> listaStavki = new ArrayList<>();
            for (OpstiDomenskiObjekat o : lista) {

                StavkaZaduzenja s = (StavkaZaduzenja) o;
                listaStavki.add(s);
            }

            System.out.println("Uspesno vracena lista Stavki!");
            return listaStavki;
        } catch (SQLException ex) {
            System.out.println("Nije uspesno vracena lista Stavki!");
            return null;
        }

    }

    public boolean knjigaZauzeta(StavkaZaduzenja s) {

        String query = "UPDATE knjiga SET je_zauzeta = TRUE WHERE sifra_knjige = " + s.getKnjiga().getSifraKnjige();
        try {
            DbBroker.getInstanca().uspostaviKonekciju();
            Statement statement = connection.createStatement();
            int broj = statement.executeUpdate(query);
            System.out.println("Uspesno izvrsen update knjige");
            DbBroker.getInstanca().commit();
            DbBroker.getInstanca().zatvoriKonekciju();
            return true;
        } catch (SQLException ex) {

            System.out.println("Nije uspesno izvrsen update knjige");
            return false;
        }

    }

    public boolean promeniStavkuNeAktivna(StavkaZaduzenja s) {

        String query = "UPDATE stavka_zaduzenja SET stanje = 'neaktivno' WHERE sifra_stavke_zaduzenja = " + s.getSifraStavkeZaduzenja();
        try {
            DbBroker.getInstanca().uspostaviKonekciju();
            Statement statement = connection.createStatement();
            int broj = statement.executeUpdate(query);
            System.out.println("Uspesno izvrsen update stavke");
            DbBroker.getInstanca().commit();
            DbBroker.getInstanca().zatvoriKonekciju();
            return true;
        } catch (SQLException ex) {

            System.out.println("Nije uspesno izvrsen update stavke");
            return false;
        }

    }

    public boolean knjigaJeZauzeta(Knjiga knjiga) {

        uspostaviKonekciju();
        String query = "SELECT * FROM knjiga WHERE sifra_knjige = " + knjiga.getSifraKnjige() + " AND je_zauzeta = TRUE";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            List<OpstiDomenskiObjekat> lista = knjiga.konvertujRSUListu(rs);
            zatvoriKonekciju();
            if (lista.size() > 0) {
                return false;
            }
            return true;
        } catch (SQLException ex) {
            return true;
        }

    }
}
