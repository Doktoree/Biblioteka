/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemske_operacije;

import db.DbBroker;

/**
 *
 * @author Lav
 */
public abstract class OpstaSO {

    public final void executeOperation() throws Exception {
        try {
            DbBroker.getInstanca().uspostaviKonekciju();
            executeSpecificOperation();
            DbBroker.getInstanca().commit();
            DbBroker.getInstanca().zatvoriKonekciju();
        } catch (Exception e) {
            DbBroker.getInstanca().rollback();
            DbBroker.getInstanca().zatvoriKonekciju();
        }
    }

    protected abstract void executeSpecificOperation() throws Exception;

}
