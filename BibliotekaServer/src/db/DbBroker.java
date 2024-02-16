/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import config.Config;
import domen.Beletristika;
import domen.Knjiga;
import domen.Korisnik;
import domen.OpstiDomenskiObjekat;
import java.sql.*;
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
            if(lista.isEmpty()){
                System.out.println("BUDAALLLLLLAAAAAA");
            }
            else{
                System.out.println("&&&&&&&&&&&&&&& " + "ipak nije do tebe");
            }
            statement.close();
            System.out.println("ResultSet uspesno postavljen!");
            return lista;

        } catch (SQLException ex) {
            System.out.println("Greska u postavljanju ResultSet-a na klasu " + o.getNazivTabele());
            ex.printStackTrace();
            throw ex;
        }

    }

    public synchronized boolean sacuvajOpstiDomenskiObjekat(OpstiDomenskiObjekat o) throws SQLException {

        String query = "INSERT INTO " + o.getNazivTabele() + "(" + o.getNaziveParametara() + ") VALUES("
                + o.getParametre() + ")";
        try {
            Statement statement = connection.createStatement();
            int a = statement.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            System.out.println("Objekat ne moze da se sacuva!");
            throw ex;
        }

    }

    public synchronized OpstiDomenskiObjekat vratiOpstiDomenskiObjekatPrimarniKljuc(OpstiDomenskiObjekat o, long id) throws SQLException {

        String query = "";
        if (o.getSlozeniPrimarniKljuc() == null) {
            query = "SELECT * FROM " + o.getNazivTabele() + " WHERE " + o.getNazivPrimarnogKljuca() + "=" + id;
        } else {
            query = "SELECT * FROM " + o.getNazivTabele() + " WHERE " + o.getSlozeniPrimarniKljuc();

        }

        Statement statement = connection.createStatement();
        System.out.println("|||||||||||||||||||||||: " + query);
        ResultSet rs = statement.executeQuery(query);

        List<OpstiDomenskiObjekat> lista = o.konvertujRSUListu(rs);

        return lista.get(0);

    }

    public synchronized boolean obrisiOpstiDomenskiObjekat(OpstiDomenskiObjekat o) throws SQLException {

        try {
            String query = "";

            if (o.getSlozeniPrimarniKljuc() == null) {
                query = "DELETE FROM " + o.getNazivTabele() + " WHERE " + o.getNazivPrimarnogKljuca() + "=" + o.getVrednostPrimarnogKljuca();
                System.out.println("!!!!!!!!!!!!: " + query);
            } else {
                query = "DELETE FROM " + o.getNazivTabele() + " WHERE " + o.getSlozeniPrimarniKljuc();

            }
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            commit();
            return true;
        } catch (Exception e) {

            System.out.println("Ne moze da se izvrsi brisanje Opsteg domenskog objekta!");
            throw e;
        }

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
            System.out.println("**********Uspelo");
            return true;
        } catch (SQLException ex) {
            System.out.println("********** Nije uspelo");
            return false;
        }

    }
    
    public synchronized Korisnik vratiKorisnika(Korisnik korisnik){
        
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
    
    
    public synchronized Knjiga vratiKnjiguBezPrimarnog(Knjiga knjiga){
        
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
    
    
    public boolean insertujVrstuKnjige(OpstiDomenskiObjekat o){
        
        uspostaviKonekciju();
        String query = "INSERT INTO " + o.getNazivTabele() + " VALUES(" + o.getParametre() + ")";
        System.out.println("---------- " + query);
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

}
