/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lav
 */
public class Knjiga extends OpstiDomenskiObjekat {

    private Long sifraKnjige;
    private String nazivKnjige;
    private String autorKnjige;
    private int godina;
    private boolean jeZauzeta;
    
    public Knjiga() {
    }
    
    public Knjiga(Long sifraKnjige) {
        this.sifraKnjige = sifraKnjige;
    }

    public Knjiga(Long sifraKnjige, String nazivKnjige, String autorKnjige, int godina,boolean jeZauzeta) {
        this.sifraKnjige = sifraKnjige;
        this.nazivKnjige = nazivKnjige;
        this.autorKnjige = autorKnjige;
        this.godina = godina;
        this.jeZauzeta = jeZauzeta;
    }

    public int getGodina() {
        return godina;
    }

    public void setDatumIzdavanja(int godina) {
        this.godina = godina;
    }

    public Long getSifraKnjige() {
        return sifraKnjige;
    }

    public void setSifraKnjige(Long sifraKnjige) {
        this.sifraKnjige = sifraKnjige;
    }

    public String getNazivKnjige() {
        return nazivKnjige;
    }

    public void setNazivKnjige(String nazivKnjige) {
        this.nazivKnjige = nazivKnjige;
    }

    public String getAutorKnjige() {
        return autorKnjige;
    }

    public void setAutorKnjige(String autorKnjige) {
        this.autorKnjige = autorKnjige;
    }
    
    

    @Override
    public String getNazivTabele() {
        return "knjiga";
    }

    @Override
    public String getParametre() {
        return String.format("'%s', '%s', %s, %s", nazivKnjige, autorKnjige, godina,jeZauzeta);
    }

    @Override
    public String getNaziveParametara() {
        return "naziv_knjige, autor_knjige, datum_izdavanja, je_zauzeta";
    }

    @Override
    public String getNazivPrimarnogKljuca() {
        return "sifra_knjige";
    }

    @Override
    public Long getVrednostPrimarnogKljuca() {
        return sifraKnjige;
    }

    @Override
    public List<OpstiDomenskiObjekat> konvertujRSUListu(ResultSet rs) {

        List<OpstiDomenskiObjekat> lista = new ArrayList<>();

        try {
            while (rs.next()) {

                Long sifra = rs.getLong("sifra_knjige");
                String naziv = rs.getString("naziv_knjige");
                String autor = rs.getString("autor_knjige");
                int godina = rs.getInt("datum_izdavanja");
                boolean zauzeta = rs.getBoolean("je_zauzeta");
                Knjiga knjiga = new Knjiga(sifra, naziv, autor, godina,zauzeta);
                lista.add(knjiga);

            }
        } catch (SQLException ex) {
            System.out.println("Greska u klasi Knjiga, kod ResultSeta!");
            Logger.getLogger(Knjiga.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
    }

    @Override
    public String getUpdateUpit() {
        
       return "sifra_knjige = " + sifraKnjige + "naziv_knjige = '" + nazivKnjige + "'" + "autor_knjige = '" + autorKnjige + "'"
                + "datum_izdavanja = '" + godina + "'" + "je_zauzeta = " + jeZauzeta;
        
    }

    @Override
    public String getSlozeniPrimarniKljuc() {
        return null;
    }

    @Override
    public String toString() {
        
        return "sifra knjige: " + sifraKnjige + " naziv knjige: " + nazivKnjige + " autor knjige: " + autorKnjige + 
                " datum izdavanja: " + godina + " jeZauzeta = " + jeZauzeta;
        
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = (int) (89 * hash + this.sifraKnjige);
        return hash;
    }

    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Knjiga other = (Knjiga) obj;
        if (this.sifraKnjige != other.sifraKnjige) {
            return false;
        }
        return true;
    }

    public boolean isJeZauzeta() {
        return jeZauzeta;
    }

    public void setJeZauzeta(boolean jeZauzeta) {
        this.jeZauzeta = jeZauzeta;
    }

}
