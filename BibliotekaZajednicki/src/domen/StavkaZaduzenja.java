/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lav
 */
public class StavkaZaduzenja extends OpstiDomenskiObjekat {
    
    private Long sifraStavkeZaduzenja;
    private Zaduzenje zaduzenje;
    private String stanje;
    private Knjiga knjiga;

    public StavkaZaduzenja() {
    }

    public StavkaZaduzenja(Long sifraStavkeZaduzenja, Zaduzenje zaduzenje, String stanje, Knjiga knjiga) {
        this.sifraStavkeZaduzenja = sifraStavkeZaduzenja;
        this.zaduzenje = zaduzenje;
        this.stanje = stanje;
        this.knjiga = knjiga;
    }

    public Knjiga getKnjiga() {
        return knjiga;
    }

    public void setKnjiga(Knjiga knjiga) {
        this.knjiga = knjiga;
    }

    public Long getSifraStavkeZaduzenja() {
        return sifraStavkeZaduzenja;
    }

    public void setSifraStavkeZaduzenja(Long sifraStavkeZaduzenja) {
        this.sifraStavkeZaduzenja = sifraStavkeZaduzenja;
    }

    public Zaduzenje getZaduzenje() {
        return zaduzenje;
    }

    public void setZaduzenje(Zaduzenje zaduzenje) {
        this.zaduzenje = zaduzenje;
    }

    public String getStanje() {
        return stanje;
    }

    public void setStanje(String stanje) {
        this.stanje = stanje;
    }

    @Override
    public String getNazivTabele() {
        return "stavka_zaduzenja";
    }

    @Override
    public String getParametre() {
        
        return String.format("%s, '%s',%s", zaduzenje.getSifraZaduzenja(), 
                stanje, knjiga.getSifraKnjige());
    }

    @Override
    public String getNaziveParametara() {
        
        return "sifra_zaduzenja, stanje, sifra_knjige";
    }

    @Override
    public String getNazivPrimarnogKljuca() {
        return null;
    }

    @Override
    public Long getVrednostPrimarnogKljuca() {
        return null;
    }

    @Override
    public List<OpstiDomenskiObjekat> konvertujRSUListu(ResultSet rs) {
        
        List<OpstiDomenskiObjekat> lista = new ArrayList<>();
        
        try {
            while(rs.next()){
                
                Long sifra = rs.getLong("sifra_stavke_zaduzenja");
                Long sifraZaduzenja = rs.getLong("sifra_zaduzenja");
                String stanje = rs.getString("stanje");
                Long sifraKnjige = rs.getLong("sifra_knjige");
                
                Zaduzenje zad = new Zaduzenje(sifraZaduzenja);
                Knjiga knjiga = new Knjiga(sifraKnjige);
                
                StavkaZaduzenja stavka = new StavkaZaduzenja(sifra, zad, stanje, knjiga);
                
                lista.add(stavka);
                
            }
        } catch (SQLException ex) {
            System.out.println("Greska u klasi StavkaZaduzenja, kod ResultSeta!");
            Logger.getLogger(StavkaZaduzenja.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }

    @Override
    public String getUpdateUpit() {
        
        return "sifra_stavke_zaduzenja = " + sifraStavkeZaduzenja + "sifra_zaduzenja = " + zaduzenje.getSifraZaduzenja()
                + "stanje = '" + stanje + "'"
                + "sifra_knjige = " + knjiga.getSifraKnjige();
        
    }

    @Override
    public String getSlozeniPrimarniKljuc() {
        
        return "WHERE sifra_stavke_zaduzenja="+sifraStavkeZaduzenja+" AND sifra_zaduzenja="+zaduzenje.getSifraZaduzenja();
        
    }
    
    
    
    
    
}
