/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package forme;

import domen.Korisnik;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import konstante.Operacija;
import logika.Kontroler;
import transfer.Odgovor;
import transfer.Zahtev;

/**
 *
 * @author Lav
 */
public class LoginForm extends javax.swing.JDialog {

    JFrame forma;
    /**
     * Creates new form LoginForm
     */
    public LoginForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        forma = (JFrame) parent;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtKorisnicko = new javax.swing.JTextField();
        passLozinka = new javax.swing.JPasswordField();
        btnUloguj = new javax.swing.JButton();
        btnIzadji = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Korisnicko ime:");

        jLabel2.setText("Lozinka:");

        btnUloguj.setText("Uloguj se");
        btnUloguj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUlogujActionPerformed(evt);
            }
        });

        btnIzadji.setText("Zatvori");
        btnIzadji.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIzadjiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtKorisnicko, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(passLozinka))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnUloguj, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnIzadji, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtKorisnicko, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(passLozinka, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUloguj)
                    .addComponent(btnIzadji))
                .addGap(27, 27, 27))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIzadjiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIzadjiActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnIzadjiActionPerformed

    private void btnUlogujActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUlogujActionPerformed
        // TODO add your handling code here:
        
        String korisnicko = txtKorisnicko.getText();
        String lozinka = String.valueOf(passLozinka.getPassword());
        Korisnik k = new Korisnik(-1l, korisnicko, lozinka, "", "");
        Zahtev zahtev = new Zahtev(k, Operacija.LOGIN);
        Kontroler.getInstanca().posaljiZahtev(zahtev);
        Odgovor odgovor = Kontroler.getInstanca().primiOdgovor();
        Korisnik rez = (Korisnik) odgovor.getOdgovor();
        
        if(rez == null){
            JOptionPane.showMessageDialog(rootPane, "Nisu uneti ispravni podaci!");
            return;
        }
        
        JOptionPane.showMessageDialog(rootPane, "Dobro dosli!");
        new BibliotekarForm(rez).setVisible(true);
        forma.dispose();
        this.dispose();
                
        

    }//GEN-LAST:event_btnUlogujActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIzadji;
    private javax.swing.JButton btnUloguj;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPasswordField passLozinka;
    private javax.swing.JTextField txtKorisnicko;
    // End of variables declaration//GEN-END:variables
}
