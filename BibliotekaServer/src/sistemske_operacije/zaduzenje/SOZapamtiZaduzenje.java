/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemske_operacije.zaduzenje;

import domen.Zaduzenje;
import sistemske_operacije.OpstaSO;
import db.DbBroker;
/**
 *
 * @author Lav
 */
public class SOZapamtiZaduzenje extends OpstaSO{

    Zaduzenje zaduzenje;
    boolean uspesno;
    
    public SOZapamtiZaduzenje(Zaduzenje zaduzenje) {
        this.zaduzenje = zaduzenje;
        uspesno = false;
    }
    
    
    
    
    @Override
    protected void executeSpecificOperation() throws Exception {
        
        System.out.println("+++++++++ " + zaduzenje.getBrojKnjiga());
        uspesno = DbBroker.getInstanca().sacuvajOpstiDomenskiObjekat(zaduzenje);
    }

    public boolean isUspesno() {
        return uspesno;
    }
    
    
}
