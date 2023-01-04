/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apotik;

import koneksi.Connection;
import koneksi.Parameter;
import koneksi.SetTable;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author Acer
 */
public class Obat extends javax.swing.JInternalFrame {
ResultSet st;
Connection con;

    /**
     * Creates new form Obat
     */
//Menampilkan Tabel

    public Obat() {
        initComponents();
        con = new Connection(Parameter.HOST_DB, Parameter.USERNAME_DB, Parameter.PASSWORD_DB, Parameter.IPHOST, Parameter.PORT);
        initComponents();
        getTabel();
    }
    public void getTabel() {
        String namaKolom[] = {"id_obat", "kode_obat", "nama_obat", "kategori_obat", "jenis_obat", "merek_obat", "harga_beli_obat", "harga_jual_obat", "jumlah_obat", "tanggal_masuk", "expired"};
        st = con.querySelect(namaKolom, "medicine");
        TabelObat.setModel(new SetTable(st));
    }
    
    //Setelah Tampil Tabel Lanjut Ke Kodingan Tambah dll
    
    private void getTambah() {
        //java.util.Date tgl=(java.util.Date)this.TanggalMasuk.getDate();
        java.util.Date ex = (java.util.Date) this.Expired.getDate();
        if (KodeObat.getText().equals("") || NamaObat.getText().equals("") || ComboBoxKategoriObat.getSelectedItem().equals("Kategori Obat")
                || ComboBoxJenisObat.getSelectedItem().equals("Jenis Obat") || MerekObat.getText().equals("") || HargaBeliObat.getText().equals("")
                || HargaJualObat.getText().equals("") || JumlahObat.getText().equals("")) {
            //||new java.sql.Date(ex.getTime()).equals("")
            JOptionPane.showMessageDialog(this, "Lengkapi data obat");
        } else {
            String[] colom = {"kode_obat", "nama_obat", "kategori_obat", "jenis_obat", "merek_obat", "harga_beli_Obat", "harga_jual_obat", "jumlah_obat", "expired"};
            String[] value = {KodeObat.getText(), NamaObat.getText(), ComboBoxKategoriObat.getSelectedItem().toString(), ComboBoxJenisObat.getSelectedItem().toString(),
                MerekObat.getText(), HargaBeliObat.getText(), HargaJualObat.getText(), JumlahObat.getText(), new java.sql.Date(ex.getTime()).toString()};
            System.out.println(con.queryInsert("medicine", colom, value));
            getTabel();
            //JOptionPane.showMessageDialog(this, "Data berhasil di simpan");
            getRefresh();
        }
    }
    
    private void getEdit() { 
        java.util.Date ex = (java.util.Date) this.Expired.getDate();
        if (KodeObat.getText().equals("") || NamaObat.getText().equals("") || ComboBoxKategoriObat.getSelectedItem().equals("Kategori Obat")
                || ComboBoxJenisObat.getSelectedItem().equals("Jenis Obat") || MerekObat.getText().equals("") || HargaBeliObat.getText().equals("")
                || HargaJualObat.getText().equals("") || JumlahObat.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Lengkapi data anda");
        } else {
            String[] column = {"kode_obat", "nama_obat", "kategori_obat", "jenis_obat", "merek_obat", "harga_beli_obat", "harga_jual_obat", "jumlah_obat", "expired"};
            String[] value = {KodeObat.getText(), NamaObat.getText(), ComboBoxKategoriObat.getSelectedItem().toString(), ComboBoxJenisObat.getSelectedItem().toString(),
                MerekObat.getText(), HargaBeliObat.getText(), HargaJualObat.getText(), JumlahObat.getText(), new java.sql.Date(ex.getTime()).toString()};
            String id = String.valueOf(TabelObat.getValueAt(TabelObat.getSelectedRow(), 0));
            System.out.println(con.queryUpdate("medicine", column, value, "id_obat='" + id + "'"));
            getTabel();
            JOptionPane.showMessageDialog(this, "Data berhasil dirubah");
            getRefresh();
        }}
    

    
     public void getHapus() {
        String id = String.valueOf(TabelObat.getValueAt(TabelObat.getSelectedRow(), 0));
        if (JOptionPane.showConfirmDialog(this, "Anda yakin ingin menghapus ?", "Peringatan!!!", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            con.queryDelete("medicine", "id_obat=" + id);
        } else {
            return;
        }
        getTabel();
        JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
        getRefresh();
    }
     
     
     public void getCari() {
        if (TextSearch.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Masukkan yang anda cari !!!");
        } else {
            if (ComboBoxSearch.getSelectedItem().equals("Kode Obat")) {
                st = con.querySelectAll("medicine", "kode_obat LIKE '%" + TextSearch.getText() + "%' ");
                TabelObat.setModel(new SetTable(st));
            } else if (ComboBoxSearch.getSelectedItem().equals("Nama Obat")) {
                st = con.querySelectAll("medicine", "nama_obat LIKE '%" + TextSearch.getText() + "%' ");
                TabelObat.setModel(new SetTable(st));
            } else if (ComboBoxSearch.getSelectedItem().equals("Kategori Obat")) {
                st = con.querySelectAll("medicine", "kategori_obat LIKE '%" + TextSearch.getText() + "%' ");
                TabelObat.setModel(new SetTable(st));
            } else if (ComboBoxSearch.getSelectedItem().equals("Jenis Obat")) {
                st = con.querySelectAll("medicine", "jenis_obat LIKE '%" + TextSearch.getText() + "%' ");
                TabelObat.setModel(new SetTable(st));
            } else if (ComboBoxSearch.getSelectedItem().equals("Merek Obat")) {
                st = con.querySelectAll("medicine", "merek_obat LIKE '%" + TextSearch.getText() + "%' ");
                TabelObat.setModel(new SetTable(st));
            }
        }
    }

     
      public void getKlik() {
        //java.util.Date ex=(java.util.Date)this.Expired.getDate();
        KodeObat.setText(String.valueOf(TabelObat.getValueAt(TabelObat.getSelectedRow(), 1)));
        NamaObat.setText(String.valueOf(TabelObat.getValueAt(TabelObat.getSelectedRow(), 2)));
        ComboBoxKategoriObat.setSelectedItem(String.valueOf(TabelObat.getValueAt(TabelObat.getSelectedRow(), 3)));
        ComboBoxJenisObat.setSelectedItem(String.valueOf(TabelObat.getValueAt(TabelObat.getSelectedRow(), 4)));
        MerekObat.setText(String.valueOf(TabelObat.getValueAt(TabelObat.getSelectedRow(), 5)));
        HargaBeliObat.setText(String.valueOf(TabelObat.getValueAt(TabelObat.getSelectedRow(), 6)));
        HargaJualObat.setText(String.valueOf(TabelObat.getValueAt(TabelObat.getSelectedRow(), 7)));
        JumlahObat.setText(String.valueOf(TabelObat.getValueAt(TabelObat.getSelectedRow(), 8)));
        String dateValue = String.valueOf(TabelObat.getValueAt(TabelObat.getSelectedRow(), 9));
        java.util.Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateValue);
        } catch (ParseException ex) {
            System.out.println(ex);
        }
        Expired.setDate(date);
    }
      
      public void getRefresh() {
        KodeObat.setText(null);
        NamaObat.setText(null);
        ComboBoxKategoriObat.setSelectedItem("Kategori Obat");
        ComboBoxJenisObat.setSelectedItem("Jenis Obat");
        MerekObat.setText(null);
        HargaBeliObat.setText(null);
        HargaJualObat.setText(null);
        JumlahObat.setText(null);
        Expired.setDate(null);
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelObat = new javax.swing.JTable();
        Add = new javax.swing.JButton();
        Edit = new javax.swing.JButton();
        Refresh = new javax.swing.JButton();
        Delete = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        ComboBoxSearch = new javax.swing.JComboBox();
        jButton5 = new javax.swing.JButton();
        TextSearch = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        HargaBeliObat = new javax.swing.JTextField();
        HargaJualObat = new javax.swing.JTextField();
        JumlahObat = new javax.swing.JTextField();
        KodeObat = new javax.swing.JTextField();
        NamaObat = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        MerekObat = new javax.swing.JTextField();
        ComboBoxKategoriObat = new javax.swing.JComboBox();
        ComboBoxJenisObat = new javax.swing.JComboBox();
        Expired = new com.toedter.calendar.JDateChooser();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("DATA OBAT");

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        TabelObat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Kode Obat", "Nama Obat", "Kategori", "Jenis", "Merek", "Harga Beli", "Harga Jual", "Stok", " Tanggal Masuk", "Kadaluarsa"
            }
        ));
        TabelObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelObatMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TabelObat);

        Add.setText("Tambah");
        Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddActionPerformed(evt);
            }
        });

        Edit.setText("Edit");
        Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditActionPerformed(evt);
            }
        });

        Refresh.setText("Refresh");
        Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshActionPerformed(evt);
            }
        });

        Delete.setText("Hapus");
        Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteActionPerformed(evt);
            }
        });

        jLabel2.setText("Cari Kategori ");

        ComboBoxSearch.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kode Obat", "Nama Obat", "Kategori Obat", "Jenis Obat", "Merek Obat" }));
        ComboBoxSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxSearchActionPerformed(evt);
            }
        });

        jButton5.setText("Cari");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel3.setText("Harga Beli");

        jLabel4.setText("Harga Jual");

        jLabel5.setText("Jumlah Stok");

        jLabel6.setText("Expired");

        jLabel7.setText("Kode Obat");

        jLabel8.setText("Nama");

        jLabel9.setText("Kategori");

        jLabel10.setText("Jenis");

        jLabel11.setText("Merek");

        ComboBoxKategoriObat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kategori Obat", "Obat Dalam", "Obat Luar" }));

        ComboBoxJenisObat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Jenis Obat", "Kaplet", "Pil", "Kapsul", "Botol", "Sirup", "Salep" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(535, 535, 535)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(149, 149, 149)
                        .addComponent(Add, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(Edit, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(Refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(Delete, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(MerekObat)
                            .addComponent(KodeObat)
                            .addComponent(ComboBoxKategoriObat, 0, 205, Short.MAX_VALUE)
                            .addComponent(ComboBoxJenisObat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(NamaObat))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 234, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Expired, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JumlahObat)
                    .addComponent(HargaJualObat)
                    .addComponent(HargaBeliObat)
                    .addComponent(ComboBoxSearch, 0, 203, Short.MAX_VALUE)
                    .addComponent(TextSearch))
                .addGap(88, 88, 88))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Add, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Edit, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Delete, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(KodeObat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(NamaObat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(ComboBoxKategoriObat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(ComboBoxJenisObat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(MerekObat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(57, 57, 57)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TextSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ComboBoxSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(HargaBeliObat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(HargaJualObat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(JumlahObat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Expired, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ComboBoxSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboBoxSearchActionPerformed

    private void AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddActionPerformed
        // TODO add your handling code here:
        getTambah();
    }//GEN-LAST:event_AddActionPerformed

    private void EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditActionPerformed
        // TODO add your handling code here:
        getEdit();
    }//GEN-LAST:event_EditActionPerformed

    private void RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshActionPerformed
        // TODO add your handling code here:
        getRefresh();
        getTabel();
    }//GEN-LAST:event_RefreshActionPerformed

    private void DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteActionPerformed
        // TODO add your handling code here:
        getHapus();
    }//GEN-LAST:event_DeleteActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        getCari();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void TabelObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelObatMouseClicked
        // TODO add your handling code here:
        getKlik();
    }//GEN-LAST:event_TabelObatMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Add;
    private javax.swing.JComboBox ComboBoxJenisObat;
    private javax.swing.JComboBox ComboBoxKategoriObat;
    private javax.swing.JComboBox ComboBoxSearch;
    private javax.swing.JButton Delete;
    private javax.swing.JButton Edit;
    private com.toedter.calendar.JDateChooser Expired;
    private javax.swing.JTextField HargaBeliObat;
    private javax.swing.JTextField HargaJualObat;
    private javax.swing.JTextField JumlahObat;
    private javax.swing.JTextField KodeObat;
    private javax.swing.JTextField MerekObat;
    private javax.swing.JTextField NamaObat;
    private javax.swing.JButton Refresh;
    private javax.swing.JTable TabelObat;
    private javax.swing.JTextField TextSearch;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
