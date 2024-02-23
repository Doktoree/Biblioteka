/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemske_operacije.clan;

import db.DbBroker;
import domen.Clan;
import domen.OpstiDomenskiObjekat;
import java.util.List;
import sistemske_operacije.OpstaSO;

/**
 *
 * @author Lav
 */
public class SOUcitajClana extends OpstaSO{

    List<OpstiDomenskiObjekat> clanovi;
    Clan clan;
    Clan rez;
    
    public SOUcitajClana(Clan clan) {
        this.clan = clan;
        
    }

    @Override
    protected void executeSpecificOperation() throws Exception {
        
       rez = (Clan) DbBroker.getInstanca().vratiOpstiDomenskiObjekatPrimarniKljuc(clan, clan.getSifraClana());
        
    }

    public Clan getClan() {
        return rez;
    }
    
    
    
    

}
