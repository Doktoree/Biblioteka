/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemske_operacije.knjiga;

import db.DbBroker;
import domen.Knjiga;
import domen.OpstiDomenskiObjekat;
import java.util.ArrayList;
import java.util.List;
import sistemske_operacije.OpstaSO;

/**
 *
 * @author Lav
 */
public class SONadjiKnjige extends OpstaSO {

    Knjiga knjiga;
    List<OpstiDomenskiObjekat> knjige;
    List<OpstiDomenskiObjekat> rezultat;

    public SONadjiKnjige(Knjiga knjiga) {
        this.knjiga = knjiga;
        rezultat = new ArrayList<>();
    }

    @Override
    protected void executeSpecificOperation() throws Exception {

        knjige = DbBroker.getInstanca().vratiOpsteDomenskeObjekte(new Knjiga());
        for (OpstiDomenskiObjekat o : knjige) {

            Knjiga knj = (Knjiga) o;

            if (vratiPoUslovu(knj)) {
                rezultat.add(knj);
            }

        }

    }

    private boolean vratiPoUslovu(Knjiga k) {

        if (knjiga.getNazivKnjige().isEmpty() && knjiga.getAutorKnjige().isEmpty() && knjiga.getGodina() == -1) {
            return false;

        }

        if (!knjiga.getNazivKnjige().isEmpty() && !knjiga.getNazivKnjige().equalsIgnoreCase(k.getNazivKnjige())) {
            return false;
        } else if (!knjiga.getAutorKnjige().isEmpty() && !knjiga.getAutorKnjige().equalsIgnoreCase(k.getAutorKnjige())) {
            return false;
        } else if (knjiga.getGodina() != -1 && knjiga.getGodina() != k.getGodina()) {
            return false;
        }

        return true;
    }

    public List<OpstiDomenskiObjekat> getRezultat() {
        return rezultat;
    }

}
