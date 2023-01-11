/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apotik;

import koneksi.Loading;
import java.awt.Dialog;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RemoteException {
        Loading ld = new Loading();
        ld.setVisible(true);
        for (int i = 0; i <= 100; i++) {
            try {
                ld.getProgressBar().setValue(i);
                Thread.sleep(40);
            } catch (InterruptedException ex) {
                Logger.getLogger(Loading.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ld.dispose();
        Login l= new Login();
        l.setVisible(true);
        // TODO code application logic here
    }
}
