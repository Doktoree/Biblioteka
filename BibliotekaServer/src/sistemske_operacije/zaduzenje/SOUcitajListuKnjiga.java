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
public class SOUcitajListuKnjiga extends OpstaSO {
    
    List<OpstiDomenskiObjekat> listaKnjiga;

    public SOUcitajListuKnjiga(List listaKnjiga) {
        this.listaKnjiga = listaKnjiga;
    }

    @Override
    protected void executeSpecificOperation() throws Exception {
        
        listaKnjiga = DbBroker.getInstanca().vratiOpsteDomenskeObjekte(listaKnjiga.get(0));
        
    }

    public List<OpstiDomenskiObjekat> getListaClanova() {
        return listaKnjiga;
    }
    
}
