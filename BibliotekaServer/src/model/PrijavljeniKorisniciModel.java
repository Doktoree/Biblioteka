/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import domen.Korisnik;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import liste.ListaPrijavljenih;

/**
 *
 * @author Lav
 */
public class PrijavljeniKorisniciModel extends AbstractTableModel {

    String[] kolone = {"Korisnicko ime", "Ime", "Prezime"};
    List<Korisnik> listaKorisnika = ListaPrijavljenih.getInstanca().getPrijavljeni();
    
    @Override
    public int getRowCount() {
        return listaKorisnika.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        Korisnik korisnik = listaKorisnika.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return korisnik.getKorisnickoIme();
            case 1: return korisnik.getIme();
            case 2: return korisnik.getPrezime();
            default: return "n/a";
        }
        
    }

    @Override
    public String getColumnName(int column) {
        
        switch (column) {
            case 0: return kolone[0];
            case 1: return kolone[1];
            case 2: return kolone[2];
            default: return "n/a";
        }
        
    }

    
    
    
    
}
