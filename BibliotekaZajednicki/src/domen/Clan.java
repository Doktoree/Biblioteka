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
import java.sql.*;

/**
 *
 * @author Lav
 */
public class Clan extends OpstiDomenskiObjekat {

    private Long sifraClana;
    private String imeClana;
    private String prezimeClana;
    private String adresaClana;
    private String brojTelefonaClana;
    private LocalDate datumRodjenjaClana;

    public Clan() {
    }

    public Clan(long sifraClana) {
        this.sifraClana = sifraClana;
    }

    public Clan(Long sifraClana, String imeClana, String prezimeClana, String adresaClana, String brojTelefonaClana, LocalDate datumRodjenjaClana) {
        this.sifraClana = sifraClana;
        this.imeClana = imeClana;
        this.prezimeClana = prezimeClana;
        this.adresaClana = adresaClana;
        this.brojTelefonaClana = brojTelefonaClana;
        this.datumRodjenjaClana = datumRodjenjaClana;
    }

    public Long getSifraClana() {
        return sifraClana;
    }

    public void setSifraClana(Long sifraClana) {
        this.sifraClana = sifraClana;
    }

    public String getImeClana() {
        return imeClana;
    }

    public void setImeClana(String imeClana) {
        this.imeClana = imeClana;
    }

    public String getPrezimeClana() {
        return prezimeClana;
    }

    public void setPrezimeClana(String prezimeClana) {
        this.prezimeClana = prezimeClana;
    }

    public String getAdresaClana() {
        return adresaClana;
    }

    public void setAdresaClana(String adresaClana) {
        this.adresaClana = adresaClana;
    }

    public String getBrojTelefonaClana() {
        return brojTelefonaClana;
    }

    public void setBrojTelefonaClana(String brojTelefonaClana) {
        this.brojTelefonaClana = brojTelefonaClana;
    }

    public LocalDate getDatumRodjenjaClana() {
        return datumRodjenjaClana;
    }

    public void setDatumRodjenjaClana(LocalDate datumRodjenjaClana) {
        this.datumRodjenjaClana = datumRodjenjaClana;
    }

    @Override
    public String getNazivTabele() {
        return "clan";
    }

    @Override
    public String getParametre() {

        return String.format("'%s', '%s', '%s', '%s','%s' ", imeClana,
                prezimeClana, adresaClana, brojTelefonaClana, datumRodjenjaClana.toString());
    }

    @Override
    public String getNaziveParametara() {
        return "ime_clana, prezime_clana, adresa_clana, broj_telefona_clana, datum_rodjenja_clana";
    }

    @Override
    public String getNazivPrimarnogKljuca() {
        return "sifra_clana";
    }

    @Override
    public Long getVrednostPrimarnogKljuca() {
        return sifraClana;
    }

    @Override
    public List<OpstiDomenskiObjekat> konvertujRSUListu(ResultSet rs) {

        List<OpstiDomenskiObjekat> clanovi = new ArrayList<>();

        try {
            while (rs.next()) {

                Long sifra = rs.getLong("sifra_clana");
                String ime = rs.getString("ime_clana");
                String prezime = rs.getString("prezime_clana");
                String adresa = rs.getString("adresa_clana");
                String telefon = rs.getString("broj_telefona_clana");
                LocalDate datum = rs.getDate("datum_rodjenja_clana").toLocalDate();
                Clan clan = new Clan(sifra, ime, prezime, adresa, telefon, datum);
                clanovi.add(clan);

            }
        } catch (SQLException ex) {
            System.out.println("Greska u Clan klasi, ResultSet!");
            Logger.getLogger(Clan.class.getName()).log(Level.SEVERE, null, ex);
        }

        return clanovi;

    }

    @Override
    public String getUpdateUpit() {

        return " ime_clana = '" + imeClana + "'" + "," + " prezime_clana = '" + prezimeClana + "'" + ","
                + " adresa_clana = '" + adresaClana + "'" + "," + " broj_telefona_clana = '" + brojTelefonaClana + "'"
                + "," + " datum_rodjenja_clana = '" + datumRodjenjaClana + "'";
    }

    @Override
    public String getSlozeniPrimarniKljuc() {
        return null;
    }

    @Override
    public String toString() {

        return "Clan- " + " sifra clana: " + sifraClana + "," + " ime clana: " + imeClana + "," + " prezime clana: " + prezimeClana
                + "," + " adresa clana: " + adresaClana + "," + " broj telefona clana: " + brojTelefonaClana + ","
                + " datum rodjenja clana: " + datumRodjenjaClana;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = (int) (89 * hash + this.sifraClana);
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
        final Clan other = (Clan) obj;
        if (this.sifraClana != other.sifraClana) {
            return false;
        }
        return true;
    }

}
