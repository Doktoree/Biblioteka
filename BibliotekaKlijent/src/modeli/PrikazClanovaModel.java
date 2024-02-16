/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeli;

import domen.Clan;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Lav
 */
public class PrikazClanovaModel extends AbstractTableModel {

    List<Clan> clanovi;

    public PrikazClanovaModel(List<Clan> clanovi) {
        this.clanovi = clanovi;
    }
    
    
    
    
    @Override
    public int getRowCount() {
        
        return clanovi.size();
    }

    @Override
    public int getColumnCount() {
        
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        Clan clan = clanovi.get(rowIndex);
        
        switch(columnIndex){
            
            case 0: return clan.getSifraClana();
            case 1: return clan.getImeClana();
            case 2: return clan.getPrezimeClana();
            default: return "n/a";
            
        }
        
    }

    @Override
    public String getColumnName(int column) {
        
        switch(column){
            
            case 0: return "Sifra clana";
            case 1: return "Ime clana";
            case 2: return "Prezime clana";
            default: return "n/a";
                    
                    
            
        }
        
    }
    
    
    
    
}
