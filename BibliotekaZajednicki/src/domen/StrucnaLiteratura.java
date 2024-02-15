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
public class StrucnaLiteratura extends OpstiDomenskiObjekat {

    private Knjiga knjiga;
    private String naucnaOblast;

    public StrucnaLiteratura() {
    }

    public StrucnaLiteratura(Knjiga knjiga, String naucnaOblast) {
        this.knjiga = knjiga;
        this.naucnaOblast = naucnaOblast;
    }

    public String getNaucnaOblast() {
        return naucnaOblast;
    }

    public void setNaucnaOblast(String naucnaOblast) {
        this.naucnaOblast = naucnaOblast;
    }

    public Knjiga getKnjiga() {
        return knjiga;
    }

    public void setKnjiga(Knjiga knjiga) {
        this.knjiga = knjiga;
    }

    @Override
    public String getNazivTabele() {

        return "strucna_literatura";

    }

    @Override
    public String getParametre() {

        return String.format("%s, '%s'", knjiga.getSifraKnjige(), naucnaOblast);
    }

    @Override
    public String getNaziveParametara() {

        return "sifra_knjige, naucna_oblast";
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
            while (rs.next()) {

                Long sifra = rs.getLong("sifra_knjige");
                Knjiga kn = new Knjiga(sifra);
                String naucna = rs.getString("naucna_oblast");
               
                StrucnaLiteratura strucna = new StrucnaLiteratura(knjiga, naucna);

                lista.add(strucna);
            }
        } catch (SQLException ex) {
            System.out.println("Greska u klasi StrucnaLiteratura, kod ResultSeta!");
            Logger.getLogger(StrucnaLiteratura.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;

    }

    @Override
    public String getUpdateUpit() {
        return "sifra_knjige = " + knjiga.getSifraKnjige() + "naucna_oblast = '" + naucnaOblast + "'";
               
    }

    @Override
    public String getSlozeniPrimarniKljuc() {
        return null;
    }

}
