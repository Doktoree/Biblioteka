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
public class Korisnik extends OpstiDomenskiObjekat{
    
    private long sifraKorisnika;
    private String korisnickoIme;
    private String lozinka;
    private String ime;
    private String prezime;

    public Korisnik() {
    }

    public Korisnik(long sifraKorisnika, String korisnickoIme, String lozinka, String ime, String prezime) {
        this.sifraKorisnika = sifraKorisnika;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.ime = ime;
        this.prezime = prezime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public long getSifraKorisnika() {
        return sifraKorisnika;
    }

    public void setSifraKorisnika(long sifraKorisnika) {
        this.sifraKorisnika = sifraKorisnika;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    @Override
    public String getNazivTabele() {
        return "korisnik";
    }

    @Override
    public String getParametre() {
        return String.format("%s, '%s', '%s', '%s', '%s'", sifraKorisnika,korisnickoIme,lozinka,ime,prezime);
    }

    @Override
    public String getNaziveParametara() {
        return "sifra_korisnika, korisnicko_ime, lozinka, ime, prezime";
    }

    @Override
    public String getNazivPrimarnogKljuca() {
        return "sifra_korisnika";
    }

    @Override
    public Long getVrednostPrimarnogKljuca() {
        return sifraKorisnika;
    }

    @Override
    public List<OpstiDomenskiObjekat> konvertujRSUListu(ResultSet rs) {
        
        List<OpstiDomenskiObjekat> lista = new ArrayList<>();
        
        try {
            while(rs.next()){
                
               Long sifra = rs.getLong("sifra_korisnika");
               String korisnicko = rs.getString("korisnicko_ime");
               String l = rs.getString("lozinka");
               String i = rs.getString("ime");
               String p = rs.getString("prezime");
               
               Korisnik k = new Korisnik(sifra, korisnicko, l, i, p);
               
               lista.add(k);
                
            }
        } catch (SQLException ex) {
            System.out.println("Greska u klasi Korisnik, kod ResultSeta!");
            Logger.getLogger(StavkaZaduzenja.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
        
    }

    @Override
    public String getUpdateUpit() {
        
        return "sifra_korisnika = " + sifraKorisnika + " korisnicko_ime = '" + korisnickoIme + "'" + " lozinka = '" + lozinka + "'"
                + " ime = '" + ime + "'" + " prezime = '" + prezime + "'";
        
    }

    @Override
    public String getSlozeniPrimarniKljuc() {
        return null;
    }
    
    
    
}
