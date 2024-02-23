/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemske_operacije.clan;

import db.DbBroker;
import domen.Clan;
import sistemske_operacije.OpstaSO;

/**
 *
 * @author Lav
 */
public class SOZapamtiClana extends OpstaSO {

    private Clan clan;
    private boolean uspesno;

    public SOZapamtiClana(Clan clan) {
        this.clan = clan;
    }
    
    
    
    @Override
    protected void executeSpecificOperation() throws Exception {
        
        if(DbBroker.getInstanca().vratiOpstiDomenskiObjekatPrimarniKljuc(clan, clan.getSifraClana()) == null){
            uspesno = DbBroker.getInstanca().sacuvajOpstiDomenskiObjekat(clan);
            System.out.println("Uspesno je izvrseno kreiranje clana!");
        }
        else{
            uspesno = DbBroker.getInstanca().updateOpstiDomenskiObjekat(clan);
            System.out.println("Uspesno je izvrseno azuriranje clana!");
        }
        
        
    }

    public boolean isUspesno() {
        return uspesno;
    }
    
    
    
}
