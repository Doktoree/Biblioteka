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
public class Beletristika extends OpstiDomenskiObjekat {
    
    private Knjiga knjiga;
    private String zanr;
    private String tema;
    private String osvojeneNagrade;

    public Beletristika() {
    }

    public Beletristika(Knjiga knjiga, String zanr, String tema, String osvojeneNagrade) {
        this.knjiga = knjiga;
        this.zanr = zanr;
        this.tema = tema;
        this.osvojeneNagrade = osvojeneNagrade;
    }

    public String getOsvojeneNagrade() {
        return osvojeneNagrade;
    }

    public void setOsvojeneNagrade(String osvojeneNagrade) {
        this.osvojeneNagrade = osvojeneNagrade;
    }

    public Knjiga getKnjiga() {
        return knjiga;
    }

    public void setKnjiga(Knjiga knjiga) {
        this.knjiga = knjiga;
    }

    public String getZanr() {
        return zanr;
    }

    public void setZanr(String zanr) {
        this.zanr = zanr;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    @Override
    public String getNazivTabele() {
        
        return "beletristika";
        
    }

    @Override
    public String getParametre() {
        
        return String.format("%s, '%s', '%s', '%s'", knjiga.getSifraKnjige(), zanr, tema, osvojeneNagrade);
        
    }

    @Override
    public String getNaziveParametara() {
        
        return "sifra_knjige, zanr, tema, osvojeneNagrade";
    }

    @Override
    public String getNazivPrimarnogKljuca() {
        return "sifra_knjige";
    }

    @Override
    public Long getVrednostPrimarnogKljuca() {
        
        return knjiga.getSifraKnjige();
    }

    @Override
    public List<OpstiDomenskiObjekat> konvertujRSUListu(ResultSet rs) {
        
        List<OpstiDomenskiObjekat> lista = new ArrayList<>();
        
        try {
            while(rs.next()){
                
                Long sifra = rs.getLong("sifra_knjige");
                String zanr = rs.getString("zanr");
                String tema = rs.getString("tema");
                String nagrade = rs.getString("osvojene_nagrade");
                Knjiga knj = new Knjiga(sifra);
                
                Beletristika be = new Beletristika(knj, zanr, tema, nagrade);
                
                lista.add(be);
            }
        } catch (SQLException ex) {
            System.out.println("Greska u klasi Beletristika, kod ResultSeta!");
            Logger.getLogger(Beletristika.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }

    @Override
    public String getUpdateUpit() {
        
        return "sifra_knjige = " + knjiga.getSifraKnjige() + "zanr = '" + zanr + "'"
                + "tema = '" + tema + "'"
                + "osvojene_nagrade = '" + osvojeneNagrade + "'";
        
    }

    @Override
    public String getSlozeniPrimarniKljuc() {
        return null;
    }
    
    
            
            
    
}
