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
    private LocalDate datumIzdavanja;

    public Knjiga() {
    }
    
    public Knjiga(Long sifraKnjige) {
        this.sifraKnjige = sifraKnjige;
    }

    public Knjiga(Long sifraKnjige, String nazivKnjige, String autorKnjige, LocalDate datumIzdavanja) {
        this.sifraKnjige = sifraKnjige;
        this.nazivKnjige = nazivKnjige;
        this.autorKnjige = autorKnjige;
        this.datumIzdavanja = datumIzdavanja;
    }

    public LocalDate getDatumIzdavanja() {
        return datumIzdavanja;
    }

    public void setDatumIzdavanja(LocalDate datumIzdavanja) {
        this.datumIzdavanja = datumIzdavanja;
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
        return String.format("%s, '%s', '%s', '%s'", sifraKnjige, nazivKnjige, autorKnjige, datumIzdavanja);
    }

    @Override
    public String getNaziveParametara() {
        return "sifra_knjige, naziv_knjige, autor_knjige, datum_izdavanja";
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
                LocalDate datum = rs.getDate("datum_izdavanja").toLocalDate();

                Knjiga knjiga = new Knjiga(sifraKnjige, nazivKnjige, autorKnjige, datumIzdavanja);
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
                + "datum_izdavanja = '" + datumIzdavanja + "'";
        
    }

    @Override
    public String getSlozeniPrimarniKljuc() {
        return null;
    }

    @Override
    public String toString() {
        
        return "sifra knjige: " + sifraKnjige + " naziv knjige: " + nazivKnjige + " autor knjige: " + autorKnjige + 
                " datum izdavanja: " + datumIzdavanja;
        
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

}
