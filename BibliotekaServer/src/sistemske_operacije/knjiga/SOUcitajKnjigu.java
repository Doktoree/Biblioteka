/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemske_operacije.knjiga;

import db.DbBroker;
import domen.Beletristika;
import domen.Knjiga;
import domen.OpstiDomenskiObjekat;
import domen.StrucnaLiteratura;
import java.util.ArrayList;
import java.util.List;
import sistemske_operacije.OpstaSO;

/**
 *
 * @author Lav
 */
public class SOUcitajKnjigu extends OpstaSO {

    Knjiga knjiga;
    OpstiDomenskiObjekat rezultat;
    boolean jeBeletristika;

    public SOUcitajKnjigu(Knjiga knjiga) {
        this.knjiga = knjiga;
    }

    @Override
    protected void executeSpecificOperation() throws Exception {

        if(DbBroker.getInstanca().vratiTipKnjige(knjiga.getSifraKnjige())){
            rezultat = (OpstiDomenskiObjekat) DbBroker.getInstanca().vratiOpstiDomenskiObjekatPrimarniKljuc(new Beletristika(), knjiga.getSifraKnjige());
            jeBeletristika = true;
            Beletristika b = (Beletristika) rezultat;
            System.out.println("+++++++++++++: " + b.getOsvojeneNagrade());
            System.out.println("+++++++++++++: " + b.getZanr());
        }
        else{
            rezultat = (OpstiDomenskiObjekat) DbBroker.getInstanca().vratiOpstiDomenskiObjekatPrimarniKljuc(new StrucnaLiteratura(), knjiga.getSifraKnjige());
            jeBeletristika = false;
        }

       
    }

    public OpstiDomenskiObjekat getRezultat() {
        return rezultat;
    }

    public boolean isJeBeletristika() {
        return jeBeletristika;
    }

    
}
