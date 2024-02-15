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
public class SOObrisiKnjigu extends OpstaSO {
    
    Knjiga knjiga;
    boolean uspesno;

    public SOObrisiKnjigu(Knjiga knjiga) {
        this.knjiga = knjiga;       
    }

    @Override
    protected void executeSpecificOperation() throws Exception {
        
        uspesno = DbBroker.getInstanca().obrisiOpstiDomenskiObjekat(knjiga);
        
    }

    public boolean isUspesno() {
        return uspesno;
    }
    
    
    
    
    
}
