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
        
        uspesno = DbBroker.getInstanca().sacuvajOpstiDomenskiObjekat(clan);
        
    }

    public boolean isUspesno() {
        return uspesno;
    }
    
    
    
}
