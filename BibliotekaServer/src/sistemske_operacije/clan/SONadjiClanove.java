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
    List<Clan> rezultat;
    
    public SONadjiClanove(List<OpstiDomenskiObjekat> clanovi, Clan clan) {
        this.clanovi = clanovi;
        this.clan = clan;
        rezultat = new ArrayList<>();
        
    }

    @Override
    protected void executeSpecificOperation() throws Exception {

        for(OpstiDomenskiObjekat k: clanovi){
            Clan cl = (Clan) k;
            
            if(vratiPoUslovu(cl)){
                rezultat.add(cl);
            }
            
        }

    }

    public List<Clan> getClanovi() {
        return rezultat;
    }
    
    private boolean vratiPoUslovu(Clan c){
        
      if (clan.getImeClana().isEmpty() && clan.getPrezimeClana().isEmpty() && clan.getSifraClana() == -1l) {
            return false;

        }

        if (!clan.getImeClana().isEmpty() && clan.getImeClana().equalsIgnoreCase(c.getImeClana())) {
            if (!clan.getPrezimeClana().isEmpty()) {
                if (clan.getPrezimeClana().equalsIgnoreCase(c.getPrezimeClana())) {
                    if (clan.getSifraClana() != -1l) {
                        if (clan.getSifraClana().compareTo(c.getSifraClana()) == 0) {
                            return true;
                        }
                        return false;
                    }
                    return true;
                }
                return false;
            }

            if (clan.getSifraClana() != -1l) {
                if (clan.getSifraClana().compareTo(c.getSifraClana()) == 0) {
                    return true;
                }
                return false;
            }
            return true;
        }

        if (!clan.getPrezimeClana().isEmpty() && clan.getPrezimeClana().equalsIgnoreCase(c.getPrezimeClana())) {
            if (!clan.getImeClana().isEmpty()) {
                if (clan.getImeClana().equalsIgnoreCase(c.getImeClana())) {
                    if (clan.getSifraClana() != -1l) {
                         if (clan.getSifraClana() == c.getSifraClana()) {
                            return true;
                        }
                        return false;
                    }
                    return true;
                }
                return false;
            }

            if (clan.getSifraClana() != -1l) {
                if (clan.getSifraClana().compareTo(c.getSifraClana()) == 0) {
                    return true;
                }
                return false;
            }
            return true;
        }
        
        if (clan.getSifraClana() != -1l && (clan.getSifraClana().compareTo(c.getSifraClana()) == 0)) {
            if (!clan.getPrezimeClana().isEmpty()) {
                if (clan.getPrezimeClana().equalsIgnoreCase(c.getPrezimeClana())) {
                    if (!clan.getImeClana().isEmpty()) {
                        return clan.getImeClana().equalsIgnoreCase(c.getImeClana());
                    }
                    return true;
                }
                return false;
            }

            if (!clan.getImeClana().isEmpty()) {
                if (clan.getImeClana().equalsIgnoreCase(c.getImeClana())) {
                    return true;
                }
                return false;
            }
            return true;
        }
        
        System.out.println("SSSSSSSSSSSSSSSSSSS");
        return false;
        
        
    }

}
