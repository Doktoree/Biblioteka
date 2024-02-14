/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemske_operacije.zaduzenje;

import db.DbBroker;
import domen.OpstiDomenskiObjekat;
import java.util.List;
import sistemske_operacije.OpstaSO;

/**
 *
 * @author Lav
 */
public class SOUcitajListuClanova extends OpstaSO {
    
    List<OpstiDomenskiObjekat> listaClanova;

    public SOUcitajListuClanova(List listaClanova) {
        this.listaClanova = listaClanova;
    }

    @Override
    protected void executeSpecificOperation() throws Exception {
        
        listaClanova = DbBroker.getInstanca().vratiOpsteDomenskeObjekte(listaClanova.get(0));
        
    }

    public List<OpstiDomenskiObjekat> getListaClanova() {
        return listaClanova;
    }
    
    
    
    
}
