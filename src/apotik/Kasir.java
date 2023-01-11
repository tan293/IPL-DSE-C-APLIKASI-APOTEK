/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apotik;

import java.awt.HeadlessException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import koneksi.Connection;
import koneksi.Parameter;
import koneksi.SetTable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Lenovo
 */
public class Kasir extends javax.swing.JInternalFrame {

    ResultSet st;
    Connection con;
    Statement rs;
    PreparedStatement ps;
    String sql;
    int old, dec, now;
    String newstock;
    public String date;

    /**
     * Creates new form Obat
     */
    private void otomatis() {
        try {
            DateFormat vblnth = new SimpleDateFormat("ddMMYYYY");
            String blnth = vblnth.format(Calendar.getInstance().getTime());

            DateFormat hari = new SimpleDateFormat("dd-MM-YYYY");
            String a = hari.format(Calendar.getInstance().getTime());
             st = con.eksekusiQuery("SELECT MAX(right(kode_transaksi,6)) AS kode_transaksi  "
                    + "FROM penjualan Where tanggal_transaksi like '" +a+ "'");

            while (st.next()) {
                if (st.first() == false) {
                    KodeTransaksi.setText("AP/" +blnth+"/"+ "000001");
                } else {
                    st.last();
                    int auto_id = st.getInt(1) + 1;
                    String no = String.valueOf(auto_id);
                    int NomorJual = no.length();
                    //MENGATUR jumlah 0
                    for (int j = 0; j < 6 - NomorJual; j++) {
                        no = "0" + no;
                    }
                    KodeTransaksi.setText("AP/"+blnth+"/" + no);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error : " + e.getMessage());

        }
    }
    
     private void noterakhir() 
       
    {
        try {
            st = con.eksekusiQuery("SELECT *from penjualan order by kode_transaksi desc limit 1 ");
            DateFormat vblnth = new SimpleDateFormat("dd-MM-YYYY");
            String blnth = vblnth.format(Calendar.getInstance().getTime());
            if (!st.next()) {
                KodeTransaksi.setText("UPB/" + blnth + "/" + "000000");
            } else {
                String no = st.getString("kode_transaksi");
                String kosong = "";
                String b = no.substring(0, 10);
                String c = no.substring(11, 17);
                KodeTransaksi.setText(b + "/" + c);
            }
            otomatis();

        } catch (SQLException e) {
            System.out.println("Error : " + e.getMessage());

        }
    }
 
    
    

    public Kasir() {
        con = new koneksi.Connection(new Parameter().HOST_DB,
                new Parameter().USERNAME_DB,
                new Parameter().PASSWORD_DB,
                new Parameter().IPHOST,
                new Parameter().PORT);
        initComponents();
        getTabel();
        getView();
        otomatis();
        Waktu();
     
    }

    public void getTabel() {
        String column[] = {"kode_obat", "nama_obat", "kategori_obat",
            "jenis_obat", "merek_obat", "harga_beli_obat", "harga_jual_obat",
            "jumlah_obat", "tanggal_masuk", "expired"};
        st = con.querySelect(column, "medicine");
        TableMedicine.setModel(new SetTable(st));
    }

    public void getView() {
        String column[] = {"kode_transaksi", "Nama_Pembeli", "kode_obat", "nama_obat",
            "merek_obat", "harga_jual", "jumlah_beli", "total_harga", "Tunai","Kembali","tanggal_transaksi","Nomor"};
        st = con.querySelect(column, "penjualan");
        TableTransaksi.setModel(new SetTable(st));
    }

    public void getCari() {
        if (TextSearch.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Masukkan yang anda cari !!!");
        } else {
            if (ComboBoxSearch.getSelectedItem().equals("Kode Obat")) {
                st = con.querySelectAll("medicine", "kode_obat LIKE '%" + TextSearch.getText() + "%' ");
                TableMedicine.setModel(new SetTable(st));
            } else if (ComboBoxSearch.getSelectedItem().equals("Nama Obat")) {
                st = con.querySelectAll("medicine", "nama_obat LIKE '%" + TextSearch.getText() + "%' ");
                TableMedicine.setModel(new SetTable(st));
            } else if (ComboBoxSearch.getSelectedItem().equals("Kategori Obat")) {
                st = con.querySelectAll("medicine", "kategori_obat LIKE '%" + TextSearch.getText() + "%' ");
                TableMedicine.setModel(new SetTable(st));
            } else if (ComboBoxSearch.getSelectedItem().equals("Jenis Obat")) {
                st = con.querySelectAll("medicine", "jenis_obat LIKE '%" + TextSearch.getText() + "%' ");
                TableMedicine.setModel(new SetTable(st));
            } else if (ComboBoxSearch.getSelectedItem().equals("Merek Obat")) {
                st = con.querySelectAll("medicine", "merek_obat LIKE '%" + TextSearch.getText() + "%' ");
                TableMedicine.setModel(new SetTable(st));
            }
        }
    }

    public void getTambah() {
        if (KodeObat.getText().equals("")
                || NamaObat.getText().equals("")
                || MerekObat.getText().equals("")
                || HargaJualObat.getText().equals("")
                || JumlahBeli.getText().equals("")
                || LSubTotal.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Silahkan lengkapi data terlebih dahulu !!! ");

        } else if (KodeTransaksi.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Silahkan Masukan Kode Transaksi Secara Unik !!!");

        } else {
            String[] column = {
                "kode_transaksi",
                "Nama_Pembeli",
                "kode_obat",
                "nama_obat",
                "merek_obat",
                "harga_jual",
                "jumlah_beli",
                "total_harga",
                "tanggal_transaksi",
                "Tunai",
                "Kembali"
               };
            String[] value = {KodeTransaksi.getText(), NamaPembeli.getText(),
                KodeObat.getText(), NamaObat.getText(),
                MerekObat.getText(), HargaJualObat.getText(),
                JumlahBeli.getText(), LSubTotal.getText(),date,Tunai.getText(),Kembalian1.getText()};
            System.out.println(con.queryInsert("penjualan", column, value));
            try {
                if (!getCheck_stock()) {
                    JOptionPane.showMessageDialog(this, "Stock obat sudah habis");
                } else {
                    getMin();
                    getTabel();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Kasir.class.getName()).log(Level.SEVERE, null, ex);
            }
            getSubTotal();
            getTotal();
            getTotal1();
            getTotal2();
            JumlahBeli.setText("");
            LSubTotal.setText("");
            Kembalian1.setText("");
            Tunai.setText("");
            getView();
           
            /*getCancel();*/
        }
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
        System.out.println(con.queryUpdate("medicine", kolom, isi, "kode_obat='"
                + KodeObat.getText() + "'"));
    }

    public void getMouseClick() {
        //KodeTransaksi.setText(String.valueOf(TableMedicine.getValueAt(TableMedicine.getSelectedRow(), 0)));
        KodeObat.setText(String.valueOf(TableMedicine.getValueAt(TableMedicine.getSelectedRow(), 0)));
        NamaObat.setText(String.valueOf(TableMedicine.getValueAt(TableMedicine.getSelectedRow(), 1)));
        MerekObat.setText(String.valueOf(TableMedicine.getValueAt(TableMedicine.getSelectedRow(), 4)));
        HargaJualObat.setText(String.valueOf(TableMedicine.getValueAt(TableMedicine.getSelectedRow(), 6)));
        JumlahBeli.setText("");
        LSubTotal.setText("");
        Kembalian1.setText("");
        Tunai.setText("");
    }

    //  Hitungan Hitungannya
    public void getSubTotal() {
        Integer a = Integer.parseInt(HargaJualObat.getText());
        Integer b = Integer.parseInt(JumlahBeli.getText());
        Integer c = a * b;
        LSubTotal.setText(String.valueOf(c));
    }

    public void getTotal1() {
        st = con.eksekusiQuery("SELECT SUM(Tunai) as Tunai FROM"
                + " penjualan WHERE Kode_Transaksi= '" + KodeTransaksi.getText() + "'");
        try {
            st.next();
            Tunai2.setText(st.getString("Tunai"));
        } catch (SQLException ex) {
            Logger.getLogger(Kasir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void getTotal2() {
        st = con.eksekusiQuery("SELECT SUM(Kembali) as Kembali FROM"
                + " penjualan WHERE Kode_Transaksi= '" + KodeTransaksi.getText() + "'");
        try {
            st.next();
            Kembalian.setText(st.getString("Kembali"));
        } catch (SQLException ex) {
            Logger.getLogger(Kasir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public void getPaymen1() {
        int a = Integer.parseInt(LSubTotal.getText());
        int b = Integer.parseInt(Tunai.getText());
        int c = b - a;
        Kembalian1.setText(String.valueOf(c));
    }
    public void getTotal() {
        st = con.eksekusiQuery("SELECT SUM(total_harga) as total_harga FROM"
                + " penjualan WHERE Kode_Transaksi= '" + KodeTransaksi.getText() + "'");
        try {
            st.next();
            LSubTotal1.setText(st.getString("total_harga"));
        } catch (SQLException ex) {
            Logger.getLogger(Kasir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getPaymen() {
        int a = Integer.parseInt(LSubTotal1.getText());
        int b = Integer.parseInt(Tunai2.getText());
        int c = b - a;
        Kembalian.setText(Integer.toString(c));
    }
public void getCan(){
    NamaPembeli.setText(null);
        NamaPembeli.setText(null);
        KodeObat.setText(null);
        NamaObat.setText(null);
        MerekObat.setText(null);
        HargaJualObat.setText(null);
        JumlahBeli.setText(null);
        Tanggalan.setDate(null);
        LSubTotal.setText(null);
        LSubTotal1.setText(null);
        Tunai2.setText(null);
        Kembalian.setText(null);
        otomatis();
        Waktu();
}
    public void getCancel() {
        NamaPembeli.setText(null);
        NamaPembeli.setText(null);
        KodeObat.setText(null);
        NamaObat.setText(null);
        MerekObat.setText(null);
        HargaJualObat.setText(null);
        JumlahBeli.setText(null);
        Tanggalan.setDate(null);
        LSubTotal.setText(null);
        LSubTotal1.setText(null);
        Tunai2.setText(null);
        Kembalian.setText(null);
        Tunai.setText(null);
        Kembalian1.setText(null);
        Waktu();
    }

    public void getDelete() {
        String id = String.valueOf(TableTransaksi.getValueAt(TableTransaksi.getSelectedRow(), 11));
        if (JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini", "Peringatan!!!", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            con.queryDelete("penjualan", "Nomor=" + id);
        } else {
            return;
        }
        getTabel();
        getView();
        JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
        getTotal();
        getTotal1();
        getTotal2();
        Waktu();
       
         
    
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        NamaPembeli = new javax.swing.JTextField();
        JumlahBeli = new javax.swing.JTextField();
        HargaJualObat = new javax.swing.JTextField();
        MerekObat = new javax.swing.JTextField();
        NamaObat = new javax.swing.JTextField();
        KodeObat = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        LSubTotal = new javax.swing.JLabel();
        Tanggalan = new com.toedter.calendar.JDateChooser();
        jLabel13 = new javax.swing.JLabel();
        KodeTransaksi = new javax.swing.JTextField();
        Cancel = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        Tunai = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        Kembalian1 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        LSubTotal1 = new javax.swing.JTextField();
        Tunai2 = new javax.swing.JTextField();
        Kembalian = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableTransaksi = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableMedicine = new javax.swing.JTable();
        ComboBoxSearch = new javax.swing.JComboBox();
        TextSearch = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        Delete1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        Delete2 = new javax.swing.JButton();
        AddCart = new javax.swing.JButton();
        txtCetak = new javax.swing.JTextField();

        jPanel2.setBackground(new java.awt.Color(0, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Input Transaction"));
        jPanel2.setForeground(new java.awt.Color(0, 204, 153));

        jLabel1.setText("Nama Pembeli");

        jLabel2.setText("Kode Obat");

        jLabel3.setText("Nama Obat");

        jLabel4.setText("Merek");

        jLabel5.setText("Harga Jual");

        jLabel6.setText("Jumlah Beli");

        jLabel7.setText("Tanggal ");

        NamaPembeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NamaPembeliActionPerformed(evt);
            }
        });
        NamaPembeli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaPembeliKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                NamaPembeliKeyReleased(evt);
            }
        });

        JumlahBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JumlahBeliActionPerformed(evt);
            }
        });
        JumlahBeli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JumlahBeliKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                JumlahBeliKeyReleased(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Rp. ");

        Tanggalan.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                TanggalanPropertyChange(evt);
            }
        });

        jLabel13.setText("Kode_Transaksi");

        KodeTransaksi.setEditable(false);
        KodeTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KodeTransaksiActionPerformed(evt);
            }
        });

        Cancel.setText("Simpan");
        Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelActionPerformed(evt);
            }
        });

        jLabel14.setText("Tunai");

        Tunai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TunaiKeyReleased(evt);
            }
        });

        jLabel15.setText("Kembalian");

        jPanel3.setBackground(new java.awt.Color(153, 255, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Summary"));

        jLabel9.setText("Total Harga");

        jLabel10.setText("Total Pembayaran");

        jLabel11.setText("Total Kembalian");

        Tunai2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Tunai2KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 15, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Tunai2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LSubTotal1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(LSubTotal1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Tunai2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(MerekObat))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(NamaObat))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(NamaPembeli))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(KodeObat))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(KodeTransaksi))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JumlahBeli)
                            .addComponent(HargaJualObat)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Tunai, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(Kembalian1)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Tanggalan, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(LSubTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(Cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(KodeTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(NamaPembeli)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(KodeObat)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(NamaObat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(MerekObat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(HargaJualObat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JumlahBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(Tanggalan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Tunai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Kembalian1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        TableTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "id_jual", "Nama Pembeli", "Kode Transaksi", "Kode Obat", "Nama Obat", "Harga Obat", "Jumlah Beli", "Total Harga", "Total Beli", "Total Bayar", "Kembali"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        TableTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableTransaksiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TableTransaksi);

        TableMedicine.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Kode Obat", "Nama Obat", "Kategori Obat", "Jenis Obat", "Merek Obat", "Harga Jual Obat", "Stock Obat", "Expired"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        TableMedicine.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableMedicineMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TableMedicine);

        ComboBoxSearch.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kode Obat", "Nama Obat", "Kategori Obat", "Jenis Obat", "Merek Obat" }));

        jLabel12.setText("Kategori");

        jButton4.setText("Cari");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        Delete1.setText("Hapus");
        Delete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Delete1ActionPerformed(evt);
            }
        });

        jButton3.setText("Cetak");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        Delete2.setText("Refresh");
        Delete2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Delete2ActionPerformed(evt);
            }
        });

        AddCart.setText("Tambah");
        AddCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddCartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Delete1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(AddCart, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Delete2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 363, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(TextSearch)
                                    .addComponent(ComboBoxSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane1))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3)
                            .addComponent(txtCetak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(ComboBoxSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton4)
                            .addComponent(TextSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Delete1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(AddCart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Delete2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
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

    private void TableTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableTransaksiMouseClicked
        // TODO add your handling code here:
        
        //KodeTransaksi.setText(String.valueOf(TableMedicine.getValueAt(TableMedicine.getSelectedRow(), 0)));
        KodeTransaksi.setText(String.valueOf(TableTransaksi.getValueAt(TableTransaksi.getSelectedRow(), 0)));
        NamaPembeli.setText(String.valueOf(TableTransaksi.getValueAt(TableTransaksi.getSelectedRow(), 1)));
        KodeObat.setText(String.valueOf(TableTransaksi.getValueAt(TableTransaksi.getSelectedRow(), 2)));
        NamaObat.setText(String.valueOf(TableTransaksi.getValueAt(TableTransaksi.getSelectedRow(), 3)));
        MerekObat.setText(String.valueOf(TableTransaksi.getValueAt(TableTransaksi.getSelectedRow(), 4)));
        HargaJualObat.setText(String.valueOf(TableTransaksi.getValueAt(TableTransaksi.getSelectedRow(), 5)));
        JumlahBeli.setText(String.valueOf(TableTransaksi.getValueAt(TableTransaksi.getSelectedRow(), 6)));
       Tunai.setText(String.valueOf(TableTransaksi.getValueAt(TableTransaksi.getSelectedRow(), 8)));
        Kembalian1.setText(String.valueOf(TableTransaksi.getValueAt(TableTransaksi.getSelectedRow(), 9)));

 Waktu();
    
    }//GEN-LAST:event_TableTransaksiMouseClicked

    private void TableMedicineMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableMedicineMouseClicked
        // TODO add your handling code here:
        getMouseClick();
    }//GEN-LAST:event_TableMedicineMouseClicked

    private void Delete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Delete1ActionPerformed
        // TODO add your handling code here:
        getDelete();
        Waktu();
        getTabel();
        getView();
    
        getCancel();
        
        
    }//GEN-LAST:event_Delete1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        getCari();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void Tunai2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tunai2KeyReleased
        // TODO add your handling code here:
        getPaymen();
    }//GEN-LAST:event_Tunai2KeyReleased

    private void CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelActionPerformed
        // TODO add your handling code here:
            getTambah();
    }//GEN-LAST:event_CancelActionPerformed

    private void JumlahBeliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JumlahBeliKeyReleased
        // TODO add your handling code here:
       getSubTotal();
        
    }//GEN-LAST:event_JumlahBeliKeyReleased

    private void AddCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddCartActionPerformed
        // TODO add your handling code here:
      getCan();
        getTabel();
         otomatis();
         Waktu();

    }//GEN-LAST:event_AddCartActionPerformed

    private void NamaPembeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NamaPembeliActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_NamaPembeliActionPerformed

    private void NamaPembeliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaPembeliKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_ENTER) {
            
            JumlahBeli.requestFocus();
        }
    }//GEN-LAST:event_NamaPembeliKeyPressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        java.sql.Connection con = null;
        try {
            String jdbcDriver = "com.mysql.jdbc.Driver";
            Class.forName(jdbcDriver);

            String url = "jdbc:mysql://localhost:3306/apotek";
            String user = "root";
            String pass = "";

            con = DriverManager.getConnection(url, user, pass);
            Statement stm = con.createStatement();

            try {

                String report = ("D:\\Unjani_INFORMATIKA\\Semester_5\\IPL\\TubesIPL\\Apoteku\\src\\apotik\\report1.jrxml");
                
                 HashMap hash = new HashMap();
                //Mengambil parameter dari ireport
                hash.put("kode", txtCetak.getText());
                JasperReport JRpt = JasperCompileManager.compileReport(report);
                JasperPrint JPrint = JasperFillManager.fillReport(JRpt, hash, con);
                JasperViewer.viewReport(JPrint, false);
            } catch (Exception rptexcpt) {
                System.out.println("Report Can't view because : " + rptexcpt);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }//GEN-LAST:event_jButton3ActionPerformed
 public void Waktu() {
        Date tgl = new Date();
        Tanggalan.setDate(tgl);
    }
    
    private void TanggalanPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_TanggalanPropertyChange
        // TODO add your handling code here:
        try {
            if (Tanggalan.getDate() != null) {
                String pattern = "dd-MM-YYYY";
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                date = String.valueOf(format.format(Tanggalan.getDate()));
            }
        } catch (Exception e) {
        }

    }//GEN-LAST:event_TanggalanPropertyChange

    private void JumlahBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JumlahBeliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JumlahBeliActionPerformed

    private void NamaPembeliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaPembeliKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_NamaPembeliKeyReleased

    private void Delete2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Delete2ActionPerformed
        // TODO add your handling code here:
         NamaPembeli.setText(null);
        NamaPembeli.setText(null);
        KodeObat.setText(null);
        NamaObat.setText(null);
        MerekObat.setText(null);
        HargaJualObat.setText(null);
        JumlahBeli.setText(null);
        Tanggalan.setDate(null);
        LSubTotal.setText(null);
        LSubTotal1.setText(null);
        Tunai2.setText(null);
        Kembalian.setText(null);
     otomatis();
     Waktu();
    }//GEN-LAST:event_Delete2ActionPerformed

    private void TunaiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TunaiKeyReleased
        // TODO add your handling code here:
          getPaymen1();
    }//GEN-LAST:event_TunaiKeyReleased

    private void KodeTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KodeTransaksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KodeTransaksiActionPerformed

    private void JumlahBeliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JumlahBeliKeyPressed
        // TODO add your handling code here:
         if (evt.getKeyCode() == evt.VK_ENTER) {
            
            Tunai.requestFocus();
        }
    }//GEN-LAST:event_JumlahBeliKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddCart;
    private javax.swing.JButton Cancel;
    private javax.swing.JComboBox ComboBoxSearch;
    private javax.swing.JButton Delete1;
    private javax.swing.JButton Delete2;
    private javax.swing.JTextField HargaJualObat;
    private javax.swing.JTextField JumlahBeli;
    private javax.swing.JTextField Kembalian;
    private javax.swing.JTextField Kembalian1;
    private javax.swing.JTextField KodeObat;
    private javax.swing.JTextField KodeTransaksi;
    private javax.swing.JLabel LSubTotal;
    private javax.swing.JTextField LSubTotal1;
    private javax.swing.JTextField MerekObat;
    private javax.swing.JTextField NamaObat;
    private javax.swing.JTextField NamaPembeli;
    private javax.swing.JTable TableMedicine;
    private javax.swing.JTable TableTransaksi;
    private com.toedter.calendar.JDateChooser Tanggalan;
    private javax.swing.JTextField TextSearch;
    private javax.swing.JTextField Tunai;
    private javax.swing.JTextField Tunai2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txtCetak;
    // End of variables declaration//GEN-END:variables
}
