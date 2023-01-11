/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apotik;

import java.sql.ResultSet;
import koneksi.Connection;
import koneksi.Parameter;
import koneksi.SetTable;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.ResultSet;
import javax.swing.ButtonGroup;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

/**
 *
 * @author Acer
 */
public class User extends javax.swing.JInternalFrame {

    ResultSet st;
    Connection con;

    /**
     * Creates new form Obat
     */
    public User() {
        initComponents();

        con =   new Connection(new koneksi.Parameter().HOST_DB,
                new koneksi.Parameter().USERNAME_DB,
                new koneksi.Parameter().PASSWORD_DB,
                new koneksi.Parameter().IPHOST,
                new koneksi.Parameter().PORT);
        
        getButtonGroup();
        getTabel();
        Lakilaki.setSelected(true);

    }
    
    public void getTabel() {
        String namaKolom[] = {"id_user","nama","jenis_kelamin","username","password","akses","alamat","email","no_hp"};
        st = con.querySelect(namaKolom, "user");
        TableUser.setModel(new SetTable(st));
    }
    
    private void getButtonGroup(){
        ButtonGroup bg = new ButtonGroup();
        bg.add(Lakilaki);
        bg.add(Perempuan);
    }
    
    private void getTambah() {
        String jk = (Lakilaki.isSelected() ? "Laki-Laki" : "Perempuan"); 
        if (Nama.getText().equals("")||
                Username.getText().equals("")||Password.getText().equals("")
                || ComboBoxAkses.getSelectedItem().equals("Akses")) {
            JOptionPane.showMessageDialog(this, "Lengkapi data anda");
        } else {
            String[] column = {"nama","jenis_kelamin","username","password","akses","alamat","email","no_hp"};
            String[] value = {Nama.getText(),jk,Username.getText(), Password.getText(), 
                ComboBoxAkses.getSelectedItem().toString(),Alamat.getText(),Email.getText(),NoHp.getText()};
            System.out.println(con.queryInsert("user", column, value));
            getTabel();
            //JOptionPane.showMessageDialog(this, "Data berhasil disimpan");
            getRefresh();
        }  
    }
      
    public void getEdit(){
        String jk = (Lakilaki.isSelected() ? "Laki-Laki" : "Perempuan");
        if (Nama.getText().equals("")||Username.getText().equals("") || Password.getText().equals("")
                || ComboBoxAkses.getSelectedItem().equals("Akses")) {
            JOptionPane.showMessageDialog(this, "Lengkapi data anda");
        } else {
            String[] column = {"nama","jenis_kelamin","username","password","akses","alamat","email","no_hp"};
            String[] value = {Nama.getText(),jk,Username.getText(), Password.getText(), 
                ComboBoxAkses.getSelectedItem().toString(),Alamat.getText(),Email.getText(),NoHp.getText()};
            String id = String.valueOf(TableUser.getValueAt(TableUser.getSelectedRow(), 0));
            System.out.println(con.queryUpdate("user", column, value, "id_user='" + id + "'"));
            getTabel();
            JOptionPane.showMessageDialog(this, "Data berhasil dirubah");
            getRefresh();
        }
    }
    
    public void getHapus(){
        String id = String.valueOf(TableUser.getValueAt(TableUser.getSelectedRow(), 0));
        if (JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data", "Peringatan!!!", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            con.queryDelete("user", "id_user=" + id);
        } else {
            return;
        }
        getTabel();
        JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
        getRefresh();
    }
    
    public void getCari(){
        if (Search.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Masukkan yang anda cari !!!");
        } else {
            if (ComboBoxSearch.getSelectedItem().equals("Nama")) {
                st = con.querySelectAll("user", "nama LIKE '%" + Search.getText() + "%' ");
                TableUser.setModel(new SetTable(st));
            } else if (ComboBoxSearch.getSelectedItem().equals("Username")) {
                st = con.querySelectAll("user", "username LIKE '%" + Search.getText() + "%' ");
                TableUser.setModel(new SetTable(st));
            } else if (ComboBoxSearch.getSelectedItem().equals("Akses")) {
                st = con.querySelectAll("user", "akses LIKE '%" + Search.getText() + "%' ");
                TableUser.setModel(new SetTable(st));
            } 
        }
    }
    
    public void getKlik(){
        Nama.setText(String.valueOf(TableUser.getValueAt(TableUser.getSelectedRow(), 1)));
        //ComboBoxJenisKelamin.setSelectedItem(String.valueOf(TableUser.getValueAt(TableUser.getSelectedRow(),2)));
        Username.setText(String.valueOf(TableUser.getValueAt(TableUser.getSelectedRow(), 3)));
        Password.setText(String.valueOf(TableUser.getValueAt(TableUser.getSelectedRow(), 4)));
        ComboBoxAkses.setSelectedItem(String.valueOf(TableUser.getValueAt(TableUser.getSelectedRow(), 5)));
        Alamat.setText(String.valueOf(TableUser.getValueAt(TableUser.getSelectedRow(), 6))); 
        Email.setText(String.valueOf(TableUser.getValueAt(TableUser.getSelectedRow(), 7)));
        NoHp.setText(String.valueOf(TableUser.getValueAt(TableUser.getSelectedRow(), 8)));
        int baris = TableUser.getSelectedRow();
        int kolom = TableUser.getSelectedColumn();
        String data = TableUser.getValueAt(baris, kolom).toString();
        String kolom2 = TableUser.getValueAt(baris, 2).toString();
           try {
               if (kolom2.equals("Laki-Laki")) {
                Lakilaki.setSelected(true);
                Perempuan.setSelected(false);
               }if (kolom2.equals("Perempuan")) {
                Perempuan.setSelected(true);
                Lakilaki.setSelected(false);
               }
           } catch (Exception e) {
        }
    }
    
    public void getRefresh() {
        Nama.setText(null);
        Lakilaki.setSelected(true);
        Username.setText(null);
        Password.setText(null);
        ComboBoxAkses.setSelectedItem("Akses");
        Alamat.setText(null);
        Email.setText(null);
        NoHp.setText(null);
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
        jScrollPane2 = new javax.swing.JScrollPane();
        TableUser = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        Nama = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        Username = new javax.swing.JTextField();
        Password = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        ComboBoxAkses = new javax.swing.JComboBox();
        Lakilaki = new javax.swing.JRadioButton();
        Perempuan = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Alamat = new javax.swing.JTextArea();
        Email = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        NoHp = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        ComboBoxSearch = new javax.swing.JComboBox();
        Tambah = new javax.swing.JButton();
        Edit = new javax.swing.JButton();
        Hapus = new javax.swing.JButton();
        Refresh = new javax.swing.JButton();
        Search = new javax.swing.JTextField();
        Cari = new javax.swing.JButton();

        TableUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nama", "Jenis Kelamin", "Username", "Password", "Akses", "Alamat", "Email", "No Hp"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        TableUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableUserMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TableUser);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Nama");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Username");

        Password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PasswordActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Password");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Jenis Kelamin");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Akses Aplikasi");

        ComboBoxAkses.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ComboBoxAkses.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Akses", "Admin" }));
        ComboBoxAkses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxAksesActionPerformed(evt);
            }
        });

        Lakilaki.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Lakilaki.setText("Laki-Laki");

        Perempuan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Perempuan.setText("Perempuan");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Alamat");

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        Alamat.setColumns(20);
        Alamat.setRows(5);
        jScrollPane1.setViewportView(Alamat);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Email");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("No.HP");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Search Kategori");

        ComboBoxSearch.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nama", "Username", "Akses" }));

        Tambah.setText("Tambah");
        Tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TambahActionPerformed(evt);
            }
        });

        Edit.setText("Edit");
        Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditActionPerformed(evt);
            }
        });

        Hapus.setText("Hapus");
        Hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HapusActionPerformed(evt);
            }
        });

        Refresh.setText("Refresh");
        Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshActionPerformed(evt);
            }
        });

        Cari.setText("Cari");
        Cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Nama))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Username))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Password))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Lakilaki)
                                .addGap(18, 18, 18)
                                .addComponent(Perempuan))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Email)
                                    .addComponent(jScrollPane1)
                                    .addComponent(NoHp))))
                        .addGap(98, 98, 98)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(Tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(Edit, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Cari, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Search, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ComboBoxSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ComboBoxAkses, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(Hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(Refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(78, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(Nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ComboBoxAkses)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(Username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(ComboBoxSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(Password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Cari))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(Lakilaki)
                    .addComponent(Perempuan))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NoHp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(Tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Edit, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void TableUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableUserMouseClicked
        getKlik();
    }//GEN-LAST:event_TableUserMouseClicked

    private void ComboBoxAksesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxAksesActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_ComboBoxAksesActionPerformed

    private void TambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TambahActionPerformed
        // TODO add your handling code here:
        getTambah();
        
        
    }//GEN-LAST:event_TambahActionPerformed

    private void EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditActionPerformed
        // TODO add your handling code here:
        getEdit();
        
    }//GEN-LAST:event_EditActionPerformed

    private void HapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HapusActionPerformed
        // TODO add your handling code here:
        getHapus();
        
    }//GEN-LAST:event_HapusActionPerformed

    private void RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshActionPerformed
        // TODO add your handling code here:
        getRefresh();
        getTabel();
    }//GEN-LAST:event_RefreshActionPerformed

    private void PasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PasswordActionPerformed

    private void CariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CariActionPerformed
        // TODO add your handling code here:
        getCari();
    }//GEN-LAST:event_CariActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea Alamat;
    private javax.swing.JButton Cari;
    private javax.swing.JComboBox ComboBoxAkses;
    private javax.swing.JComboBox ComboBoxSearch;
    private javax.swing.JButton Edit;
    private javax.swing.JTextField Email;
    private javax.swing.JButton Hapus;
    private javax.swing.JRadioButton Lakilaki;
    private javax.swing.JTextField Nama;
    private javax.swing.JTextField NoHp;
    private javax.swing.JTextField Password;
    private javax.swing.JRadioButton Perempuan;
    private javax.swing.JButton Refresh;
    private javax.swing.JTextField Search;
    private javax.swing.JTable TableUser;
    private javax.swing.JButton Tambah;
    private javax.swing.JTextField Username;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
