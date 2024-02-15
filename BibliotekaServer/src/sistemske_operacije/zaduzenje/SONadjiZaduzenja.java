/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemske_operacije.zaduzenje;

import domen.OpstiDomenskiObjekat;
import domen.Zaduzenje;
import java.util.ArrayList;
import java.util.List;
import sistemske_operacije.OpstaSO;

/**
 *
 * @author Lav
 */
public class SONadjiZaduzenja extends OpstaSO {

    List<OpstiDomenskiObjekat> zaduzenja;
    Zaduzenje zaduzenje;
    List<OpstiDomenskiObjekat> rezultat;

    public SONadjiZaduzenja(List<OpstiDomenskiObjekat> zaduzenja, Zaduzenje zaduzenje) {
        this.zaduzenja = zaduzenja;
        this.zaduzenje = zaduzenje;
    }

    @Override
    protected void executeSpecificOperation() throws Exception {
        
        rezultat = new ArrayList<>();
        
        for(OpstiDomenskiObjekat o: zaduzenja){
            
            Zaduzenje za = (Zaduzenje) o;
            
            if(vratiPoUslovu(za)){
                rezultat.add(za);
            }
        }
        
    }

    private boolean vratiPoUslovu(Zaduzenje zad) {

        if(zad.getClan().getSifraClana()== zaduzenje.getClan().getSifraClana()){
            return true;
        }
        
        return false;
    }

    public List<OpstiDomenskiObjekat> getRezultat() {
        return rezultat;
    }
    
    

}
