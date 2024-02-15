/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemske_operacije.knjiga;

import db.DbBroker;
import domen.Knjiga;
import sistemske_operacije.OpstaSO;

/**
 *
 * @author Lav
 */
public class SOZapamtiKnjigu extends OpstaSO{
    
    Knjiga knjiga;
    boolean uspesno;
    
    public SOZapamtiKnjigu(Knjiga knjiga) {
        this.knjiga = knjiga;
        uspesno = false;
    }

    @Override
    protected void executeSpecificOperation() throws Exception {
        
        uspesno = DbBroker.getInstanca().sacuvajOpstiDomenskiObjekat(knjiga);
        
    }

    public boolean isUspesno() {
        return uspesno;
    }
    
    
    
    
}
