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
public class Zaduzenje extends OpstiDomenskiObjekat{
    
    private Long sifraZaduzenja;
    private LocalDate datumPocetkaZaduzenja;
    private LocalDate datumZavrsetkaZaduzenja;
    private int brojKnjiga;
    private Clan clan;

    public Zaduzenje() {
    }
    
    public Zaduzenje(Long sifraZaduzenja) {
        this.sifraZaduzenja = sifraZaduzenja;
    }

    public Zaduzenje(Long sifraZaduzenja, LocalDate datumPocetkaZaduzenja, LocalDate datumZavrsetkaZaduzenja, int brojKnjiga, Clan clan) {
        this.sifraZaduzenja = sifraZaduzenja;
        this.datumPocetkaZaduzenja = datumPocetkaZaduzenja;
        this.datumZavrsetkaZaduzenja = datumZavrsetkaZaduzenja;
        this.brojKnjiga = brojKnjiga;
        this.clan = clan;
    }

    public Clan getClan() {
        return clan;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }

    public Long getSifraZaduzenja() {
        return sifraZaduzenja;
    }

    public void setSifraZaduzenja(Long sifraZaduzenja) {
        this.sifraZaduzenja = sifraZaduzenja;
    }

    public LocalDate getDatumPocetkaZaduzenja() {
        return datumPocetkaZaduzenja;
    }

    public void setDatumPocetkaZaduzenja(LocalDate datumPocetkaZaduzenja) {
        this.datumPocetkaZaduzenja = datumPocetkaZaduzenja;
    }

    public LocalDate getDatumZavrsetkaZaduzenja() {
        return datumZavrsetkaZaduzenja;
    }

    public void setDatumZavrsetkaZaduzenja(LocalDate datumZavrsetkaZaduzenja) {
        this.datumZavrsetkaZaduzenja = datumZavrsetkaZaduzenja;
    }

    public int getBrojKnjiga() {
        return brojKnjiga;
    }

    public void setBrojKnjiga(int brojKnjiga) {
        this.brojKnjiga = brojKnjiga;
        
    }

    @Override
    public String getNazivTabele() {
        return "zaduzenje";
    }

    @Override
    public String getParametre() {
        return String.format("%s, '%s', '%s', %s, %s", sifraZaduzenja, datumPocetkaZaduzenja, datumZavrsetkaZaduzenja,
                brojKnjiga, clan.getSifraClana());
    }

    @Override
    public String getNaziveParametara() {
        
        return "sifra_zaduzenja, datum_pocetka_zaduzenja, datum_zavrsetka_zaduzenja, broj_knjiga, sifra_clana";
    }

    @Override
    public String getNazivPrimarnogKljuca() {
        return "sifra_zaduzenja";
    }

    @Override
    public Long getVrednostPrimarnogKljuca() {
        return sifraZaduzenja;
    }

    @Override
    public List<OpstiDomenskiObjekat> konvertujRSUListu(ResultSet rs) {
        
        List<OpstiDomenskiObjekat> lista = new ArrayList<>();
        
        try {
            while(rs.next()){
                
                long sifra = rs.getLong("sifra_zaduzenja");
                LocalDate od = rs.getDate("datum_pocetka_zaduzenja").toLocalDate();
                LocalDate kraj = rs.getDate("datum_zavrsetka_zaduzenja").toLocalDate();
                int broj = rs.getInt("broj_knjiga");
                long sifraClana = rs.getLong("sifra_clana");
                
                Clan clan = new Clan(sifraClana);
                
                Zaduzenje zaduzenje = new Zaduzenje(sifraZaduzenja, datumPocetkaZaduzenja, datumZavrsetkaZaduzenja, brojKnjiga, clan);
                lista.add(zaduzenje);
                
            }
        } catch (SQLException ex) {
            System.out.println("Greska u klasi Zaduzenje, kod ResultSeta!");
            Logger.getLogger(Zaduzenje.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }

    @Override
    public String getUpdateUpit() {
        
        return "sifra_zaduzenja = " + sifraZaduzenja + "datum_pocetka_zaduzenja = '" + datumPocetkaZaduzenja + "'" + "datum_zavrsetka_zaduzenja = '" + datumZavrsetkaZaduzenja + "'"
                + "broj_knjiga = " + brojKnjiga + "sifra_clana = " + clan.getSifraClana();
    }

    @Override
    public String getSlozeniPrimarniKljuc() {
        return null;
    }

    @Override
    public String toString() {
        
        return "sifra zaduzenja: " + sifraZaduzenja + " datum pocetka zaduzenja: " + datumPocetkaZaduzenja + 
                " datum zavrsetka zaduzenja: " + datumZavrsetkaZaduzenja + " broj knjiga: " + brojKnjiga + " sifra_clana: " + 
                clan.getSifraClana();
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = (int) (89 * hash + this.sifraZaduzenja);
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
        final Zaduzenje other = (Zaduzenje) obj;
        if (this.sifraZaduzenja != other.sifraZaduzenja) {
            return false;
        }
        return true;
    }
    
            
    
}
