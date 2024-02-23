/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemske_operacije.zaduzenje;

import domen.Zaduzenje;
import sistemske_operacije.OpstaSO;

/**
 *
 * @author Lav
 */
public class SOKreirajZaduzenje extends OpstaSO {

    Zaduzenje zaduzenje;
    boolean uspesno;

    public SOKreirajZaduzenje() {
        uspesno = false;
    }

    
    
    @Override
    protected void executeSpecificOperation() throws Exception {
        
        zaduzenje = new Zaduzenje();
        uspesno = true;
        
        
    }

    public void setZaduzenje(Zaduzenje zaduzenje) {
        this.zaduzenje = zaduzenje;
    }

    
    
    public Zaduzenje getZaduzenje() {
        return zaduzenje;
    }

    public boolean isUspesno() {
        return uspesno;
    }
    
    
    
    
    
    
}
