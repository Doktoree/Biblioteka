/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author Lav
 */
public abstract class OpstiDomenskiObjekat implements Serializable{
    
    public abstract String getNazivTabele();

    public abstract String getParametre();

    public abstract String getNaziveParametara();

    public abstract String getNazivPrimarnogKljuca();

    public abstract Long getVrednostPrimarnogKljuca();

    public abstract List<OpstiDomenskiObjekat> konvertujRSUListu(ResultSet rs);

    public abstract String getUpdateUpit();

    public abstract String getSlozeniPrimarniKljuc();
    
    
}
