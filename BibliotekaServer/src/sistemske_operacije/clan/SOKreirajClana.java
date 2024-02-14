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
public class SOKreirajClana extends OpstaSO {

    private Clan clan;
    private boolean uspesno;

    @Override
    protected void executeSpecificOperation() throws Exception {
        
        Clan clan = new Clan();
        uspesno = true;
        
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }

    public Clan getClan() {
        return clan;
    }

    public boolean isUspesno() {
        return uspesno;
    }

    
           
    
    
    
}
