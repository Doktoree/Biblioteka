/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemske_operacije.zaduzenje;

import db.DbBroker;
import domen.Zaduzenje;
import sistemske_operacije.OpstaSO;

/**
 *
 * @author Lav
 */
public class SOUcitajZaduzenje extends OpstaSO {
    
    Zaduzenje zaduzenje;
    Zaduzenje rezultat;
    
    public SOUcitajZaduzenje(Zaduzenje zaduzenje) {
        this.zaduzenje = zaduzenje;
    }

    @Override
    protected void executeSpecificOperation() throws Exception {
        
        rezultat = (Zaduzenje) DbBroker.getInstanca().vratiOpstiDomenskiObjekatPrimarniKljuc(zaduzenje, zaduzenje.getSifraZaduzenja());
        
    }

    public Zaduzenje getRezultat() {
        return rezultat;
    }
    
    
    
}
