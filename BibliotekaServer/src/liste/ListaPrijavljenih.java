/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package liste;

import domen.Korisnik;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lav
 */
public class ListaPrijavljenih {
    
    private static ListaPrijavljenih instanca;
    private List<Korisnik> prijavljeni;

    private ListaPrijavljenih() {
        
        prijavljeni = new ArrayList<>();
    }

    public static ListaPrijavljenih getInstanca() {
        if(instanca == null){
            instanca = new ListaPrijavljenih();
        }
        return instanca;
    }
    
    public void dodaj(Korisnik k){
        
        prijavljeni.add(k);
        
    }
    
    public void obrisi(Korisnik k){
        
        for(Korisnik o: prijavljeni){
            
            if(o.getSifraKorisnika() == k.getSifraKorisnika()){
                prijavljeni.remove(o);
                System.out.println("Korisnik je izbrisan iz liste korisnika!");
                return;
            }
            
        }
    }

    public List<Korisnik> getPrijavljeni() {
        return prijavljeni;
    }
    
    
}
