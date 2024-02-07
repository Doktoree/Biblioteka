/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transfer;

import java.io.Serializable;
import konstante.Operacija;

/**
 *
 * @author Lav
 */
public class Zahtev implements Serializable{
    
    private Object parametar;
    private Operacija operacija;

    public Zahtev() {
    }

    public Zahtev(Object parametar, Operacija operacija) {
        this.parametar = parametar;
        this.operacija = operacija;
    }

    public Operacija getOperacija() {
        return operacija;
    }

    public void setOperacija(Operacija operacija) {
        this.operacija = operacija;
    }

    public Object getParametar() {
        return parametar;
    }

    public void setParametar(Object parametar) {
        this.parametar = parametar;
    }
    
    
}
