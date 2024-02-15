/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemske_operacije.knjiga;

import domen.Knjiga;
import sistemske_operacije.OpstaSO;

/**
 *
 * @author Lav
 */
public class SOKreirajKnjigu extends OpstaSO {
    
    Knjiga knjiga;
    boolean uspesno;
    
    public SOKreirajKnjigu() {
        uspesno = false;
    }

    @Override
    protected void executeSpecificOperation() throws Exception {
        
        knjiga = new Knjiga();
        uspesno = true;
    }

    public Knjiga getKnjiga() {
        return knjiga;
    }

    public boolean isUspesno() {
        return uspesno;
    }
    
    
    
    
    
    
    
}
