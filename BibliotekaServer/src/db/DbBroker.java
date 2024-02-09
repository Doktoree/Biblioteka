/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;


import config.Config;
import domen.OpstiDomenskiObjekat;
import java.sql.*;
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
        if(instanca == null){
            instanca = new DbBroker();
        }
        return instanca;
    }
    
    public boolean uspostaviKonekciju(){
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
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
    
    
    public void zatvoriKonekciju(){
        
        try {
            connection.close();
            System.out.println("Konekcija je zatvorena!");
        } catch (SQLException ex) {
            System.out.println("Konekcija ne moze da se zatvori!");
        }
        
    }
    
    public void commit(){
        
        try {
            connection.commit();
            System.out.println("Commit je izvrsen!");
        } catch (SQLException ex) {
            System.out.println("Commit ne moze da se izvrsi!");
        }
        
    }
    
    public void rollback(){
        
        try {
            connection.rollback();
            System.out.println("Rollback je izvrsen!");
        } catch (SQLException ex) {
            System.out.println("Rollback ne moze da se izvrsi!");
        }
        
    }
    
    public synchronized List<OpstiDomenskiObjekat> vratiOpsteDomenskeObjekte(OpstiDomenskiObjekat o) throws SQLException{
        
        
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
    
}
