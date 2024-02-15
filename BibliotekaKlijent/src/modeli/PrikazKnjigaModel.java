/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeli;

import domen.Knjiga;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Lav
 */
public class PrikazKnjigaModel extends AbstractTableModel {

    List<Knjiga> knjige;

    public PrikazKnjigaModel(List<Knjiga> knjige) {
        this.knjige = knjige;
    }
    
    
    
    
    @Override
    public int getRowCount() {
        
        return knjige.size();
    }

    @Override
    public int getColumnCount() {
        
        return 3;
        
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        Knjiga knjiga = knjige.get(rowIndex);
        
        switch(columnIndex){
            
            case 0: return knjiga.getNazivKnjige();
            case 1: return knjiga.getAutorKnjige();
            case 2: return knjiga.getGodina();
            default: return "n/a";
            
        }
        
    }

    @Override
    public String getColumnName(int column) {
        
        switch(column){
            
            case 0: return "Naziv knjige";
            case 1: return "Autor knjige";
            case 2: return "Godina izdavanja";
            default: return "n/a";
                
            
        }
    }
    
    
    
}
