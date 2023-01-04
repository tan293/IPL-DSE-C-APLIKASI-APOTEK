/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apotik;

import apotik.Laporan;
import koneksi.Connection;
import koneksi.Parameter;
import koneksi.SetTable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Acer
 */
public class Penjualan extends javax.swing.JInternalFrame {

    ResultSet st;
    Connection con;
    int old, dec, now;
    String newstock;

    /**
     * Creates new form Obat
     */
//Menampilkan Tabel
    

    public Penjualan() {
        initComponents();
              
    }
 public void getTabel() {
        String column[] = {"kode_obat", "nama_obat", "kategori_obat",
            "jenis_obat", "merek_obat", "harga_beli_obat", "harga_jual_obat",
            "jumlah_obat", "tanggal_masuk", "expired"};
        st = con.querySelect(column, "medicine");
       
    }
 
 
 
 private void prosestambah(){
        try {
            DefaultTableModel tableModel =(DefaultTableModel)TPINJAM.getModel();
            String[]data = new String[7];
          
            data[0]= Kodeku.getText();
            data[1]= NamaPembeli.getText();
            data[2]= KodeObat.getText();
            data[3]= NamaObat.getText();
            data[4]= HargaJualObat.getText();
            data[5]= JumlahBeli.getText();
            data[6]= LSubTotal1.getText();
            tableModel.addRow(data);            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error masukkan data \n"+e.getMessage());
        }
    }    

    public String KodeOb, NamaOb, HargaJu, Expire;

    public String KodeOb() {
        return KodeOb;
    }

    ;
    public String NamaOb() {
        return NamaOb;
    }

    ;
    public String HargaJu() {
        return HargaJu;
    }

    ;
    public String Expire() {
        return Expire;
    }

    public boolean getCheck_stock() throws SQLException {
        boolean checkstock;
        st = con.querySelectAll("medicine", "kode_obat='" + KodeObat.getText() + "'");
        while (st.next()) {
            old = st.getInt("jumlah_obat");
        }
        dec = Integer.parseInt(JumlahBeli.getText());
        if (old < dec) {
            checkstock = false;
        } else {
            checkstock = true;
        }
        return checkstock;
    }

    public void getMin() throws SQLException {
        st = con.querySelectAll("medicine", "kode_obat='" + KodeObat.getText() + "'");
        while (st.next()) {
            old = st.getInt("jumlah_obat");
        }
        dec = Integer.parseInt(JumlahBeli.getText());
        now = old - dec;
        newstock = Integer.toString(now);
        String a = String.valueOf(newstock);
        String[] kolom = {"jumlah_obat"};
        String[] isi = {a};
        System.out.println(con.queryUpdate("medicine", kolom, isi, "kode_obat='" + KodeObat.getText() + "'"));
    }

    public void getSubTotal() {
        Integer a = Integer.parseInt(HargaJualObat.getText());
        Integer b = Integer.parseInt(JumlahBeli.getText());
        Integer c = a * b;
        LSubTotal1.setText(String.valueOf(c));
       
    }

    public void getTotal() {
        st = con.eksekusiQuery("SELECT SUM(total_harga)"
                + " as total_harga FROM penjualan WHERE"
                + " kode_transaksi = '" + Kodeku.getText() + "'");
        try {
            st.next();
            lSubTotal.setText(st.getString("total_harga"));
        } catch (SQLException ex) {
            Logger.getLogger(Penjualan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getPaymen() {
        int a = Integer.parseInt(lSubTotal.getText());
        int b = Integer.parseInt(Tunai.getText());
        int c = b - a;
        Kembalian.setText(Integer.toString(c));
    }
    
    
     public void getTambah() {
        if (    NamaPembeli.getText().equals("")
                ||KodeObat.getText().equals("")
                || NamaObat.getText().equals("") 
                || HargaJualObat.getText().equals("") 
                || JumlahBeli.getText().equals("") 
                || LSubTotal1.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Silahkan lengkapi data terlebih dahulu !!! ");
        } else if (Kodeku.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Silahkan Masukan Kode Transaksi Secara Unik !!!");
        } else {
            String[] column = {"kode_transaksi",
                                "Nama_Pembeli",
                                "kode_obat", 
                                "nama_obat",  
                                "harga_jual", 
                                "jumlah_beli", 
                                "total_harga"};
            String[] value = {Kodeku.getText(),NamaPembeli.getText(), 
                KodeObat.getText(), NamaObat.getText(), 
                 HargaJualObat.getText(), 
                JumlahBeli.getText(), LSubTotal1.getText()};
            System.out.println(con.queryInsert("penjualan", column, value));
           
            getSubTotal();
            getTotal();
           
            /*getCancel();*/
        }
    }

    //Setelah Tampil Tabel Lanjut Ke Kodingan Tambah dll
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        NamaPembeli = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        HargaJualObat = new javax.swing.JTextField();
        KodeObat = new javax.swing.JTextField();
        Pilih = new javax.swing.JButton();
        JumlahBeli = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        NamaObat = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TPINJAM = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        Kembalian = new javax.swing.JTextField();
        Tunai = new javax.swing.JTextField();
        lSubTotal = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        TPINJAM1 = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        AddCart = new javax.swing.JButton();
        Cancel = new javax.swing.JButton();
        Delete = new javax.swing.JButton();
        Cetak = new javax.swing.JButton();
        Kodeku = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        Expired = new com.toedter.calendar.JDateChooser();
        LSubTotal1 = new javax.swing.JTextField();

        jLabel7.setText("No Nota");

        jLabel8.setText("Nama Pembeli");

        jLabel9.setText("Kode Obat");

        jLabel10.setText("Nama Obat");

        jLabel11.setText("Harga Jual");

        Pilih.setText("Pilih");
        Pilih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PilihActionPerformed(evt);
            }
        });

        JumlahBeli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JumlahBeliKeyReleased(evt);
            }
        });

        jLabel12.setText("Jumlah Beli");

        TPINJAM.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No Nota", "Nama Pembeli", "Kode Obat", "Nama Obat", "Jumlah Beli", "Harga Jual", "Total "
            }
        ));
        jScrollPane1.setViewportView(TPINJAM);

        jLabel2.setText("Total");

        jLabel3.setText("Tunai");

        jLabel4.setText("Kembalian");

        Tunai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TunaiKeyReleased(evt);
            }
        });

        TPINJAM1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No Nota", "Nama Pembeli", "Kode Obat", "Nama Obat", "Jumlah Beli", "Harga Jual", "Total "
            }
        ));
        jScrollPane2.setViewportView(TPINJAM1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Tunai, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(Tunai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(Kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jLabel14.setText("Tanggal Expired");

        AddCart.setText("Tambah");
        AddCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddCartActionPerformed(evt);
            }
        });

        Cancel.setText("Cancel");

        Delete.setText("Hapus");

        Cetak.setText("Print");

        jLabel1.setText("Rp. ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(HargaJualObat, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(NamaPembeli)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(KodeObat, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(Pilih, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(JumlahBeli, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(NamaObat)
                                .addComponent(Kodeku)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(Cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(AddCart))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(Delete, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(Cetak, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(1, 1, 1)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(LSubTotal1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 218, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Expired, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(220, 220, 220)))
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(Kodeku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(NamaPembeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(KodeObat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Pilih))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(NamaObat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(HargaJualObat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(JumlahBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(Expired, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LSubTotal1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(AddCart)
                            .addComponent(Delete))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Cetak)
                            .addComponent(Cancel))))
                .addContainerGap(70, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PilihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PilihActionPerformed
        // TODO add your handling code here:
        boolean closable = true;
        Panggil fDB = new Panggil(null, closable);
        fDB.fAB = this;
        fDB.setVisible(true);
        fDB.setResizable(true);
        KodeObat.setText(KodeOb);
        NamaObat.setText(NamaOb);
        HargaJualObat.setText(HargaJu);
        
         
        try {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date d = f.parse(Expire);
            Expired.setDate(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
     

    }//GEN-LAST:event_PilihActionPerformed

    private void AddCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddCartActionPerformed
        // TODO add your handling code here:
        prosestambah();
    }//GEN-LAST:event_AddCartActionPerformed

    private void JumlahBeliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JumlahBeliKeyReleased
        // TODO add your handling code here:
        getSubTotal();
    }//GEN-LAST:event_JumlahBeliKeyReleased

    private void TunaiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TunaiKeyReleased
        // TODO add your handling code here:
        getPaymen();
    }//GEN-LAST:event_TunaiKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddCart;
    private javax.swing.JButton Cancel;
    private javax.swing.JButton Cetak;
    private javax.swing.JButton Delete;
    private com.toedter.calendar.JDateChooser Expired;
    private javax.swing.JTextField HargaJualObat;
    private javax.swing.JTextField JumlahBeli;
    private javax.swing.JTextField Kembalian;
    private javax.swing.JTextField KodeObat;
    private javax.swing.JTextField Kodeku;
    private javax.swing.JTextField LSubTotal1;
    private javax.swing.JTextField NamaObat;
    private javax.swing.JTextField NamaPembeli;
    private javax.swing.JButton Pilih;
    private javax.swing.JTable TPINJAM;
    private javax.swing.JTable TPINJAM1;
    private javax.swing.JTextField Tunai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField lSubTotal;
    // End of variables declaration//GEN-END:variables
}
