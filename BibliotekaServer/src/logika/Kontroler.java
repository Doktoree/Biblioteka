/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logika;

import config.Config;
import forme.ServerskaForma;
import niti.PokretanjeServera;

/**
 *
 * @author Lav
 */
public class Kontroler {
    
    
    private static Kontroler instanca;
    private static PokretanjeServera ps;

    private Kontroler() {
    }

    public static Kontroler getInstanca() {
        if(instanca == null){
            instanca = new Kontroler();
        }
        return instanca;
    }
    
    public static void main(String[] args) {
        
        ServerskaForma sf = new ServerskaForma();
        sf.setVisible(true);
        
    }
    
    public void pokreniServer(){
        
        ps = new PokretanjeServera(Config.getInstanca().getPort());
        ps.start();
        
    }
    
    public void zaustaviServer(){
        
        ps.ugasiServer();
        
    }
    
    
}
