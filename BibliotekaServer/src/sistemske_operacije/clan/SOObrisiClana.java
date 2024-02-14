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
public class SOObrisiClana extends OpstaSO {
    
    Clan clan;
    boolean uspesno;
    
    public SOObrisiClana(Clan clan) {
        this.clan = clan;
        uspesno = false;
        
    }

    @Override
    protected void executeSpecificOperation() throws Exception {
        
        uspesno = DbBroker.getInstanca().obrisiOpstiDomenskiObjekat(clan);
        
    }

    public boolean isUspesno() {
        return uspesno;
    }
    
    
    
    
}
