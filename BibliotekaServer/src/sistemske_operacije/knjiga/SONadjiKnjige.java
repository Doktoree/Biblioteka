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

        if (!knjiga.getNazivKnjige().isEmpty() && knjiga.getNazivKnjige().equalsIgnoreCase(k.getNazivKnjige())) {
            if (!knjiga.getAutorKnjige().isEmpty()) {
                if (knjiga.getAutorKnjige().equalsIgnoreCase(k.getAutorKnjige())) {
                    if (knjiga.getGodina() != -1) {
                        if (knjiga.getGodina() == k.getGodina()) {
                            return true;
                        }
                        return false;
                    }
                    return true;
                }
                return false;
            }

            if (knjiga.getGodina() != -1) {
                if (knjiga.getGodina() == k.getGodina()) {
                    return true;
                }
                return false;
            }
            return true;
        }

        if (!knjiga.getAutorKnjige().isEmpty() && knjiga.getAutorKnjige().equalsIgnoreCase(k.getAutorKnjige())) {
            if (!knjiga.getNazivKnjige().isEmpty()) {
                if (knjiga.getNazivKnjige().equalsIgnoreCase(k.getNazivKnjige())) {
                    if (knjiga.getGodina() != -1) {
                        if (knjiga.getGodina() == k.getGodina()) {
                            return true;
                        }
                        return false;
                    }
                    return true;
                }
                return false;
            }

            if (knjiga.getGodina() != -1) {
                if (knjiga.getGodina() == k.getGodina()) {
                    return true;
                }
                return false;
            }
            return true;
        }
        
        if (knjiga.getGodina() !=-1 && knjiga.getGodina() == k.getGodina()) {
            if (!knjiga.getAutorKnjige().isEmpty()) {
                if (knjiga.getAutorKnjige().equalsIgnoreCase(k.getAutorKnjige())) {
                    if (!knjiga.getNazivKnjige().isEmpty()) {
                        return knjiga.getNazivKnjige().equalsIgnoreCase(k.getNazivKnjige());
                    }
                    return true;
                }
                return false;
            }

            if (!knjiga.getNazivKnjige().isEmpty()) {
                if (knjiga.getNazivKnjige().equalsIgnoreCase(k.getNazivKnjige())) {
                    return true;
                }
                return false;
            }
            return true;
        }
        

        return false;
    }

    public List<OpstiDomenskiObjekat> getRezultat() {
        return rezultat;
    }

}
