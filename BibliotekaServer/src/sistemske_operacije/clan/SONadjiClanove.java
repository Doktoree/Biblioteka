/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemske_operacije.clan;

import db.DbBroker;
import domen.Clan;
import domen.OpstiDomenskiObjekat;
import java.util.ArrayList;
import java.util.List;
import logika.Kontroler;
import sistemske_operacije.OpstaSO;

/**
 *
 * @author Lav
 */
public class SONadjiClanove extends OpstaSO {

    List<OpstiDomenskiObjekat> clanovi;
    Clan clan;
    boolean uspesno;
    List<OpstiDomenskiObjekat> rezultat;
    
    public SONadjiClanove(List<OpstiDomenskiObjekat> clanovi, Clan clan) {
        this.clanovi = clanovi;
        this.clan = clan;
        
    }

    @Override
    protected void executeSpecificOperation() throws Exception {

        rezultat = new ArrayList<>();
        
        for(OpstiDomenskiObjekat k: clanovi){
            Clan cl = (Clan) k;
            
            if(vratiPoUslovu(cl)){
                rezultat.add(cl);
            }
            
        }

    }

    public List<OpstiDomenskiObjekat> getClanovi() {
        return rezultat;
    }
    
    private boolean vratiPoUslovu(Clan c){
        
        if(c.getImeClana().equals(clan.getImeClana()) || c.getPrezimeClana().equals(clan.getPrezimeClana())
                || c.getSifraClana() == clan.getSifraClana()){
            return true;
        }
        
        return false;
        
        
    }

}
