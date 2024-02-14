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
    

    @Override
    protected void executeSpecificOperation() throws Exception {
        
        zaduzenje = new Zaduzenje();
        
    }

    public Zaduzenje getZaduzenje() {
        return zaduzenje;
    }
    
    
    
    
    
}
