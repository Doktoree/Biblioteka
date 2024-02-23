/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeli;

import domen.Zaduzenje;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Lav
 */
public class PrikazZaduzenjaModel extends AbstractTableModel {

    
    List<Zaduzenje> zaduzenja;

    public PrikazZaduzenjaModel(List<Zaduzenje> zaduzenja) {
        this.zaduzenja = zaduzenja;
    }
    
    
    
    @Override
    public int getRowCount() {
        return zaduzenja.size();
    }

    @Override
    public int getColumnCount() {
        
        return 4;
        
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        Zaduzenje z = zaduzenja.get(rowIndex);
        
        switch(columnIndex){
            
            case 0: return z.getSifraZaduzenja();
            case 1: return z.getDatumPocetkaZaduzenja();
            case 2: return z.getDatumZavrsetkaZaduzenja();
            case 3: return z.getBrojKnjiga();
            default: return "n/a";
            
        }
        
    }

    @Override
    public String getColumnName(int column) {
        
        switch(column){
            
            case 0: return "Sifra zaduzenja";
            case 1: return "Datum pocetka zaduzenja";
            case 2: return "Datum zavrsetka zaduzenja";
            case 3: return "Broj knjiga";
            default: return "n/a";
        }
        
    }
    
    public void updateTabele(){
        
        fireTableDataChanged();
    }
    
    
    
    
}
