/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.myjava.maumanya;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.text.DecimalFormat;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
/**
 *
 * @author Lenovo
 */
public class Homepage extends javax.swing.JFrame {
    private DefaultTableModel modelPesanan;
    private double grandTotal = 0.0;
    private DecimalFormat rupiahFormat = new DecimalFormat("#,##0.00");
    private javax.swing.JTextField tanggalStrukField;
    private javax.swing.JTextField namaPembeliStrukField;
    private javax.swing.JTextField totalBayarStrukField;
    private DefaultTableModel modelStrukDetail; 
    private DefaultTableModel modelRiwayat;
    private int userId;
    private String userNama;
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Homepage.class.getName());

    /**
     * Creates new form Homepage
     */
    
    public Homepage() {
    initComponents();
    inisialisasiTabelPesanan();
    setupTombolAwal();
    setupFilterCombos();
    setupTanggalSpinner();
    }

    public void setUserData(int userId, String userNama) {
    this.userId = userId;
    this.userNama = userNama;
    }

   

    private void inisialisasiTabelPesanan() {
        modelPesanan = new ReadOnlyTableModel(
            new Object[]{"ID", "Nama Menu", "Harga Satuan", "Qty", "Harga Total"}, 0
        );
        tabelTotalPesanan.setModel(modelPesanan);
        
        tabelTotalPesanan.getColumnModel().getColumn(0).setMinWidth(0);
        tabelTotalPesanan.getColumnModel().getColumn(0).setMaxWidth(0);
        tabelTotalPesanan.getColumnModel().getColumn(0).setPreferredWidth(0);
        
        tabelTotalPesanan.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    setupTombolAwal();
                }
            }
        });
    }
    
    private void inisialisasiTabelStruk() {
    modelStrukDetail = new ReadOnlyTableModel(
        new Object[]{"Nama Menu", "Harga Satuan", "Qty", "Subtotal"}, 0
    );
    strukTabel.setModel(modelStrukDetail);
    }
    
    private void inisialisasiTabelRiwayat() {
    // riwayatTabel adalah nama JTable Anda
    modelRiwayat = new ReadOnlyTableModel(
        new Object[]{"No.", "Tanggal", "Nama Pembeli", "Daftar Menu", "Total Harga"}, 0
    );
    riwayatTabel.setModel(modelRiwayat); 
    }
    
    private void resetForm(){
    namaField.setText("");

        grandTotal = 0.0;

        // Set tampilan totalField menggunakan format rupiah yang sudah dideklarasikan
        // Asumsi: rupiahFormat adalah DecimalFormat yang sudah dideklarasikan di level kelas
        if (rupiahFormat != null) {
            totalField.setText(rupiahFormat.format(grandTotal)); 
        } else {
            totalField.setText("0.00"); // Jika format belum dideklarasikan
        }

        // membersihkan model tabel pesanan
        if (modelPesanan != null) {
            modelPesanan.setRowCount(0); 
        }

        namaField.requestFocus();
        
    }
    
    private int getIdMenuDariDatabase(Connection conn, String namaMenu) throws SQLException {
        // Query ke tabel 'menu'
        String sql = "SELECT id_menu FROM menu WHERE nama_menu = ?";
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, namaMenu);
        java.sql.ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            int idMenu = rs.getInt("id_menu");
            rs.close(); 
            pst.close();
            return idMenu;
        } else {
            rs.close();
            pst.close();
            // Melempar exception agar transaksi dibatalkan jika menu tidak ditemukan
            throw new SQLException("Menu '" + namaMenu + "' tidak ditemukan di database.");
        }
    }
    
    private void setupTombolAwal() {
        boolean isSelected = tabelTotalPesanan.getSelectedRow() != -1;
        editButton.setEnabled(isSelected);
        hapusButton.setEnabled(isSelected);
    }
    private void tambahPesanan(int menuId, String nama, double hargaSatuan) {
        boolean itemDitemukan = false;
        
        for (int i = 0; i < modelPesanan.getRowCount(); i++) {
            int existingId = (int) modelPesanan.getValueAt(i, 0);
        
            if (existingId == menuId) {
                int currentQty = (int) modelPesanan.getValueAt(i, 3);
                int newQty = currentQty + 1;
                double newTotal = newQty * hargaSatuan;
                
                modelPesanan.setValueAt(newQty, i, 3);
                modelPesanan.setValueAt(newTotal, i, 4);
                itemDitemukan = true;
                break;
            }
        }
        if (!itemDitemukan) {
            modelPesanan.addRow(new Object[]{
               menuId, 
                nama, 
                hargaSatuan, 
                1, 
                hargaSatuan
            });
        }
        hitungGrandTotal();
    }
    private void hitungGrandTotal() {
        grandTotal = 0.0;
    
        for (int i = 0; i < modelPesanan.getRowCount(); i++) {
            double totalBaris = (double) modelPesanan.getValueAt(i, 4);
            grandTotal += totalBaris;
        }
        totalField.setText(rupiahFormat.format(grandTotal));
        simpanButton.setEnabled(grandTotal > 0);
    }
 

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel29 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jPanel10 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel15 = new javax.swing.JPanel();
        AGori = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        tmbhAGori = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        tmbhAGorinasi = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        tmbhAGbakar = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        tmbhAGkrispi = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        tmbhAGseltel = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        AGbakarliwet = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        tmbhAGoriLiwet = new javax.swing.JButton();
        jPanel23 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        tmbhAGkrispinasi = new javax.swing.JButton();
        jPanel24 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        tmbhAGseltelnasi = new javax.swing.JButton();
        AGBakarNasi = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        tmbhAGbakarnasi = new javax.swing.JButton();
        jPanel26 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        tmbhAGseltelliwet = new javax.swing.JButton();
        jLabel41 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        tmbhAGkrispiliwet = new javax.swing.JButton();
        jPanel28 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        tmbhAGorinakar = new javax.swing.JButton();
        jLabel50 = new javax.swing.JLabel();
        jPanel29 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        tmbhAGbakarnakar = new javax.swing.JButton();
        jPanel33 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        tmbhAGseltelnakar = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        AGori1 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        tmbhalpukat = new javax.swing.JButton();
        jPanel25 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        tmbhmangga = new javax.swing.JButton();
        jPanel30 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        tmbhapel = new javax.swing.JButton();
        jPanel32 = new javax.swing.JPanel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        tmbhsirsak = new javax.swing.JButton();
        jPanel34 = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        tmbhstrawberry = new javax.swing.JButton();
        jPanel35 = new javax.swing.JPanel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        tmbhwortel = new javax.swing.JButton();
        jPanel36 = new javax.swing.JPanel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        tmbhtomat = new javax.swing.JButton();
        jPanel37 = new javax.swing.JPanel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        tmbhjambu = new javax.swing.JButton();
        jPanel38 = new javax.swing.JPanel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        tmbhnaga = new javax.swing.JButton();
        AGBakarNasi1 = new javax.swing.JPanel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        tmbhjeruk = new javax.swing.JButton();
        jPanel31 = new javax.swing.JPanel();
        AGori2 = new javax.swing.JPanel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        tmbhtahu = new javax.swing.JButton();
        jPanel39 = new javax.swing.JPanel();
        jLabel85 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        tmbhtempe = new javax.swing.JButton();
        jPanel40 = new javax.swing.JPanel();
        jLabel88 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        tmbhSmblTomat = new javax.swing.JButton();
        jPanel41 = new javax.swing.JPanel();
        jLabel91 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        tmbhSmblTerasi = new javax.swing.JButton();
        jPanel42 = new javax.swing.JPanel();
        jLabel94 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        tmbhSmblMatah = new javax.swing.JButton();
        jPanel45 = new javax.swing.JPanel();
        jLabel103 = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        tmbhSmblBawang = new javax.swing.JButton();
        jPanel46 = new javax.swing.JPanel();
        jLabel106 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        tmbhSmblGoang = new javax.swing.JButton();
        AGBakarNasi2 = new javax.swing.JPanel();
        jLabel109 = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        tmbhSmblIjo = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelTotalPesanan = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        totalField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        hapusButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        simpanButton = new javax.swing.JButton();
        tanggalSpinner = new javax.swing.JSpinner();
        jLabel98 = new javax.swing.JLabel();
        namaField = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel89 = new javax.swing.JLabel();
        simpanStrukButton = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel97 = new javax.swing.JLabel();
        tanggalField = new javax.swing.JTextField();
        namaPembeliField = new javax.swing.JTextField();
        totalHargaField = new javax.swing.JTextField();
        jLabel92 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        strukTabel = new javax.swing.JTable();
        riwayatPanel = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        riwayatTabel = new javax.swing.JTable();
        comboTgl = new javax.swing.JComboBox<>();
        jLabel100 = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        comboBulan = new javax.swing.JComboBox<>();
        comboTahun = new javax.swing.JComboBox<>();
        buttonFilterRiwayat = new javax.swing.JButton();

        jLabel29.setText("jLabel29");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(153, 0, 0));

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Selamat bertugas! Ma'nya siap Gebug pesanan hari ini. Mari kita mulai!");

        jLabel9.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\logo2.jpeg")); // NOI18N

        jLabel101.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\logo2.jpeg")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1)
                .addGap(504, 504, 504)
                .addComponent(jLabel101)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel101)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jTabbedPane2.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Ayam Gebug Original");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("9K");

        jLabel23.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\AG.jpg")); // NOI18N

        tmbhAGori.setBackground(new java.awt.Color(153, 0, 0));
        tmbhAGori.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        tmbhAGori.setForeground(new java.awt.Color(255, 255, 255));
        tmbhAGori.setText("TAMBAH");
        tmbhAGori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhAGoriActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AGoriLayout = new javax.swing.GroupLayout(AGori);
        AGori.setLayout(AGoriLayout);
        AGoriLayout.setHorizontalGroup(
            AGoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tmbhAGori, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(AGoriLayout.createSequentialGroup()
                .addGroup(AGoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AGoriLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel23))
                    .addGroup(AGoriLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(AGoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(AGoriLayout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addComponent(jLabel6)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        AGoriLayout.setVerticalGroup(
            AGoriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AGoriLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(4, 4, 4)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tmbhAGori))
        );

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setText("AG Original + Nasi");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("12K");

        jLabel48.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\AGnasi.jpg")); // NOI18N

        tmbhAGorinasi.setBackground(new java.awt.Color(153, 0, 0));
        tmbhAGorinasi.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        tmbhAGorinasi.setForeground(new java.awt.Color(255, 255, 255));
        tmbhAGorinasi.setText("TAMBAH");
        tmbhAGorinasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhAGorinasiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(jLabel17))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel48))))
                .addContainerGap(17, Short.MAX_VALUE))
            .addComponent(tmbhAGorinasi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel48)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addGap(12, 12, 12)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tmbhAGorinasi, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel18.setPreferredSize(new java.awt.Dimension(184, 228));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Ayam Gebug Bakar");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("9K");

        jLabel42.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\ayambakar.jpg")); // NOI18N

        tmbhAGbakar.setBackground(new java.awt.Color(153, 0, 0));
        tmbhAGbakar.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        tmbhAGbakar.setForeground(new java.awt.Color(255, 255, 255));
        tmbhAGbakar.setText("TAMBAH");
        tmbhAGbakar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhAGbakarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tmbhAGbakar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(92, 92, 92))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel42)
                        .addGap(46, 46, 46))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel42)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addGap(12, 12, 12)
                .addComponent(tmbhAGbakar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel19.setPreferredSize(new java.awt.Dimension(184, 228));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Ayam Gebug Krispi");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("10K");

        jLabel44.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\AG krispi.jpg")); // NOI18N

        tmbhAGkrispi.setBackground(new java.awt.Color(153, 0, 0));
        tmbhAGkrispi.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        tmbhAGkrispi.setForeground(new java.awt.Color(255, 255, 255));
        tmbhAGkrispi.setText("TAMBAH");
        tmbhAGkrispi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhAGkrispiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tmbhAGkrispi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel44))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(jLabel13))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel44)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tmbhAGkrispi, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("AG Selimut Telor");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("10K");

        jLabel46.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\ayamSelimutTelur.jpg")); // NOI18N

        tmbhAGseltel.setBackground(new java.awt.Color(153, 0, 0));
        tmbhAGseltel.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        tmbhAGseltel.setForeground(new java.awt.Color(255, 255, 255));
        tmbhAGseltel.setText("TAMBAH");
        tmbhAGseltel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhAGseltelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tmbhAGseltel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel46))
                        .addGap(45, 45, 45))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(85, 85, 85))))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel46)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tmbhAGseltel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel25.setText("AG Bakar + Nasi Liwet");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel26.setText("14K");

        jLabel49.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\AGBakarLiwet.jpg")); // NOI18N

        AGbakarliwet.setBackground(new java.awt.Color(153, 0, 0));
        AGbakarliwet.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        AGbakarliwet.setForeground(new java.awt.Color(255, 255, 255));
        AGbakarliwet.setText("TAMBAH");
        AGbakarliwet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AGbakarliwetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel49)
                .addGap(47, 47, 47))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addGap(27, 27, 27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addGap(85, 85, 85))))
            .addComponent(AGbakarliwet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel49)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(AGbakarliwet, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel21.setText("AG Original + Nasi Liwet");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel24.setText("14K");

        jLabel47.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\AGOriLiwet.jpg")); // NOI18N

        tmbhAGoriLiwet.setBackground(new java.awt.Color(153, 0, 0));
        tmbhAGoriLiwet.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        tmbhAGoriLiwet.setForeground(new java.awt.Color(255, 255, 255));
        tmbhAGoriLiwet.setText("TAMBAH");
        tmbhAGoriLiwet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhAGoriLiwetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel21)
                .addGap(0, 27, Short.MAX_VALUE))
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(jLabel24)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(tmbhAGoriLiwet, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel47)
                .addGap(44, 44, 44))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tmbhAGoriLiwet, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setText("AG Krispi + Nasi");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setText("14K");

        jLabel45.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\AGKrispiNasi.jpg")); // NOI18N

        tmbhAGkrispinasi.setBackground(new java.awt.Color(153, 0, 0));
        tmbhAGkrispinasi.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        tmbhAGkrispinasi.setForeground(new java.awt.Color(255, 255, 255));
        tmbhAGkrispinasi.setText("TAMBAH");
        tmbhAGkrispinasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhAGkrispinasiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel22))
                    .addComponent(jLabel45))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(tmbhAGkrispinasi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel45)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tmbhAGkrispinasi, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel24.setPreferredSize(new java.awt.Dimension(184, 228));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("AG Selimut Telor + Nasi");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setText("14K");

        jLabel43.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\AGtelurnasinew.jpg")); // NOI18N

        tmbhAGseltelnasi.setBackground(new java.awt.Color(153, 0, 0));
        tmbhAGseltelnasi.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        tmbhAGseltelnasi.setForeground(new java.awt.Color(255, 255, 255));
        tmbhAGseltelnasi.setText("TAMBAH");
        tmbhAGseltelnasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhAGseltelnasiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(22, 22, 22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                        .addComponent(jLabel43)
                        .addGap(46, 46, 46))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(90, 90, 90))))
            .addComponent(tmbhAGseltelnasi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel43)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addGap(2, 2, 2)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tmbhAGseltelnasi, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        AGBakarNasi.setPreferredSize(new java.awt.Dimension(151, 228));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("AG Bakar + Nasi");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setText("12K");

        jLabel40.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\ABnasi.jpg")); // NOI18N

        tmbhAGbakarnasi.setBackground(new java.awt.Color(153, 0, 0));
        tmbhAGbakarnasi.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        tmbhAGbakarnasi.setForeground(new java.awt.Color(255, 255, 255));
        tmbhAGbakarnasi.setText("TAMBAH");
        tmbhAGbakarnasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhAGbakarnasiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AGBakarNasiLayout = new javax.swing.GroupLayout(AGBakarNasi);
        AGBakarNasi.setLayout(AGBakarNasiLayout);
        AGBakarNasiLayout.setHorizontalGroup(
            AGBakarNasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AGBakarNasiLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(AGBakarNasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel40)
                    .addComponent(jLabel16)
                    .addGroup(AGBakarNasiLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel15)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(tmbhAGbakarnasi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        AGBakarNasiLayout.setVerticalGroup(
            AGBakarNasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AGBakarNasiLayout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tmbhAGbakarnasi))
        );

        jPanel26.setPreferredSize(new java.awt.Dimension(151, 228));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel27.setText("AG Selimut Telor + Nasi Liwet");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel28.setText("16K");

        tmbhAGseltelliwet.setBackground(new java.awt.Color(153, 0, 0));
        tmbhAGseltelliwet.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        tmbhAGseltelliwet.setForeground(new java.awt.Color(255, 255, 255));
        tmbhAGseltelliwet.setText("TAMBAH");
        tmbhAGseltelliwet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhAGseltelliwetActionPerformed(evt);
            }
        });

        jLabel41.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\AGtelorliwet.jpg")); // NOI18N

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tmbhAGseltelliwet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jLabel28))
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel27))
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel41)))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(jLabel41)
                .addGap(18, 18, 18)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tmbhAGseltelliwet))
        );

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel30.setText("16K");

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel31.setText("AG Krispi +Nasi Liwet");

        jLabel39.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\AGKrispiLiwet.jpg")); // NOI18N

        tmbhAGkrispiliwet.setBackground(new java.awt.Color(153, 0, 0));
        tmbhAGkrispiliwet.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        tmbhAGkrispiliwet.setForeground(new java.awt.Color(255, 255, 255));
        tmbhAGkrispiliwet.setText("TAMBAH");
        tmbhAGkrispiliwet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhAGkrispiliwetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tmbhAGkrispiliwet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel39))
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel31))
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(jLabel30)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel39)
                .addGap(12, 12, 12)
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tmbhAGkrispiliwet))
        );

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel32.setText("AG Original +Nasi Bakar");

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel33.setText("15K");

        tmbhAGorinakar.setBackground(new java.awt.Color(153, 0, 0));
        tmbhAGorinakar.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        tmbhAGorinakar.setForeground(new java.awt.Color(255, 255, 255));
        tmbhAGorinakar.setText("TAMBAH");
        tmbhAGorinakar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhAGorinakarActionPerformed(evt);
            }
        });

        jLabel50.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\AGOriBakar.jpg")); // NOI18N

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tmbhAGorinakar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                        .addComponent(jLabel50)
                        .addGap(47, 47, 47))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addGap(88, 88, 88))))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel50)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tmbhAGorinakar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel34.setText("AG Bakar + Nasi Bakar");

        jLabel52.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\AGBakarBakar.jpg")); // NOI18N

        jLabel53.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel53.setText("15K");

        tmbhAGbakarnakar.setBackground(new java.awt.Color(153, 0, 0));
        tmbhAGbakarnakar.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        tmbhAGbakarnakar.setForeground(new java.awt.Color(255, 255, 255));
        tmbhAGbakarnakar.setText("TAMBAH");
        tmbhAGbakarnakar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhAGbakarnakarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel52)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addGap(24, 24, 24))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                        .addComponent(jLabel53)
                        .addGap(91, 91, 91))))
            .addComponent(tmbhAGbakarnakar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel52)
                .addGap(18, 18, 18)
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel53)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tmbhAGbakarnakar))
        );

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel35.setText("AG Selimut Telor + Nasi Bakar");

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel36.setText("17K");

        jLabel51.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\AGSelimutBakar.jpg")); // NOI18N

        tmbhAGseltelnakar.setBackground(new java.awt.Color(153, 0, 0));
        tmbhAGseltelnakar.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        tmbhAGseltelnakar.setForeground(new java.awt.Color(255, 255, 255));
        tmbhAGseltelnakar.setText("TAMBAH");
        tmbhAGseltelnakar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhAGseltelnakarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                        .addComponent(jLabel51)
                        .addGap(39, 39, 39))
                    .addComponent(jLabel35, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                        .addComponent(jLabel36)
                        .addGap(87, 87, 87)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(tmbhAGseltelnakar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel51)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tmbhAGseltelnakar))
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(AGBakarNasi, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                    .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                    .addComponent(AGori, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                    .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel23, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                    .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(167, 167, 167))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, 236, Short.MAX_VALUE)
                            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel29, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(104, 104, 104))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                            .addComponent(AGori, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(AGBakarNasi, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, 231, Short.MAX_VALUE)
                                .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(100, 100, 100))))
        );

        jScrollPane1.setViewportView(jPanel15);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Ayam", jPanel5);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel37.setText("Jus Alpukat");

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel38.setText("8K");

        jLabel54.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\jusAlpukat.jpg")); // NOI18N

        tmbhalpukat.setBackground(new java.awt.Color(153, 0, 0));
        tmbhalpukat.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        tmbhalpukat.setForeground(new java.awt.Color(255, 255, 255));
        tmbhalpukat.setText("TAMBAH");
        tmbhalpukat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhalpukatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AGori1Layout = new javax.swing.GroupLayout(AGori1);
        AGori1.setLayout(AGori1Layout);
        AGori1Layout.setHorizontalGroup(
            AGori1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tmbhalpukat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(AGori1Layout.createSequentialGroup()
                .addGroup(AGori1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AGori1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel54))
                    .addGroup(AGori1Layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(jLabel38))
                    .addGroup(AGori1Layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(jLabel37)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        AGori1Layout.setVerticalGroup(
            AGori1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AGori1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel54)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel37)
                .addGap(4, 4, 4)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tmbhalpukat))
        );

        jPanel25.setPreferredSize(new java.awt.Dimension(184, 228));

        jLabel55.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel55.setText("Jus Mangga");

        jLabel56.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel56.setText("8K");

        jLabel57.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\jusMangga.jpg")); // NOI18N

        tmbhmangga.setBackground(new java.awt.Color(153, 0, 0));
        tmbhmangga.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        tmbhmangga.setForeground(new java.awt.Color(255, 255, 255));
        tmbhmangga.setText("TAMBAH");
        tmbhmangga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhmanggaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tmbhmangga, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                        .addComponent(jLabel56)
                        .addGap(92, 92, 92))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                        .addComponent(jLabel57)
                        .addGap(46, 46, 46))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel57)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel55)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel56)
                .addGap(12, 12, 12)
                .addComponent(tmbhmangga, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel30.setPreferredSize(new java.awt.Dimension(184, 228));

        jLabel58.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel58.setText("Jus Apel");

        jLabel59.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel59.setText("8K");

        jLabel60.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\jusApel.jpg")); // NOI18N

        tmbhapel.setBackground(new java.awt.Color(153, 0, 0));
        tmbhapel.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        tmbhapel.setForeground(new java.awt.Color(255, 255, 255));
        tmbhapel.setText("TAMBAH");
        tmbhapel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhapelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tmbhapel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel60)
                .addContainerGap(49, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel59)
                .addGap(93, 93, 93))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel60)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel58)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel59)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(tmbhapel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel61.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel61.setText("Jus Sirsak");

        jLabel62.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel62.setText("8K");

        jLabel63.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\jusSirsak.jpg")); // NOI18N

        tmbhsirsak.setBackground(new java.awt.Color(153, 0, 0));
        tmbhsirsak.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        tmbhsirsak.setForeground(new java.awt.Color(255, 255, 255));
        tmbhsirsak.setText("TAMBAH");
        tmbhsirsak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhsirsakActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tmbhsirsak, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                        .addComponent(jLabel63)
                        .addGap(45, 45, 45))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                        .addComponent(jLabel61)
                        .addGap(66, 66, 66))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                        .addComponent(jLabel62)
                        .addGap(94, 94, 94))))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel63)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel61)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel62)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tmbhsirsak, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel64.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel64.setText("Jus Strawberry");

        jLabel65.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel65.setText("8K");

        jLabel66.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\jusStroberi.jpg")); // NOI18N

        tmbhstrawberry.setBackground(new java.awt.Color(153, 0, 0));
        tmbhstrawberry.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        tmbhstrawberry.setForeground(new java.awt.Color(255, 255, 255));
        tmbhstrawberry.setText("TAMBAH");
        tmbhstrawberry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhstrawberryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tmbhstrawberry, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel65)
                .addGap(94, 94, 94))
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel66)
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel64)))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel66)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel64)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel65)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(tmbhstrawberry, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel67.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel67.setText("Jus Wortel");

        jLabel68.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel68.setText("7K");

        jLabel69.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\jusWortel.jpg")); // NOI18N

        tmbhwortel.setBackground(new java.awt.Color(153, 0, 0));
        tmbhwortel.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        tmbhwortel.setForeground(new java.awt.Color(255, 255, 255));
        tmbhwortel.setText("TAMBAH");
        tmbhwortel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhwortelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel69)
                .addGap(47, 47, 47))
            .addComponent(tmbhwortel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                        .addComponent(jLabel67)
                        .addGap(66, 66, 66))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                        .addComponent(jLabel68)
                        .addGap(94, 94, 94))))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel69)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel67)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel68)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tmbhwortel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel70.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel70.setText("Jus Tomat");

        jLabel71.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel71.setText("7K");

        jLabel72.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\jusTomat.jpg")); // NOI18N

        tmbhtomat.setBackground(new java.awt.Color(153, 0, 0));
        tmbhtomat.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        tmbhtomat.setForeground(new java.awt.Color(255, 255, 255));
        tmbhtomat.setText("TAMBAH");
        tmbhtomat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhtomatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                        .addComponent(jLabel72)
                        .addGap(44, 44, 44))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                        .addComponent(jLabel70)
                        .addGap(71, 71, 71))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                        .addComponent(jLabel71)
                        .addGap(95, 95, 95))))
            .addComponent(tmbhtomat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel72)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel70)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel71)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tmbhtomat, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel73.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel73.setText("Jus Jambu");

        jLabel74.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel74.setText("8K");

        jLabel75.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\jusJambu.jpg")); // NOI18N

        tmbhjambu.setBackground(new java.awt.Color(153, 0, 0));
        tmbhjambu.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        tmbhjambu.setForeground(new java.awt.Color(255, 255, 255));
        tmbhjambu.setText("TAMBAH");
        tmbhjambu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhjambuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel75)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(tmbhjambu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                        .addComponent(jLabel73)
                        .addGap(71, 71, 71))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                        .addComponent(jLabel74)
                        .addGap(95, 95, 95))))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel75)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel73)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel74)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tmbhjambu, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel38.setPreferredSize(new java.awt.Dimension(184, 228));

        jLabel76.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel76.setText("Jus Buah Naga");

        jLabel77.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel77.setText("8K");

        jLabel78.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\jusNaga.jpg")); // NOI18N

        tmbhnaga.setBackground(new java.awt.Color(153, 0, 0));
        tmbhnaga.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        tmbhnaga.setForeground(new java.awt.Color(255, 255, 255));
        tmbhnaga.setText("TAMBAH");
        tmbhnaga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhnagaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tmbhnaga, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel38Layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel76))
                    .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel38Layout.createSequentialGroup()
                            .addComponent(jLabel78)
                            .addGap(46, 46, 46))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel38Layout.createSequentialGroup()
                            .addComponent(jLabel77)
                            .addGap(90, 90, 90)))))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel38Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel78)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel76)
                .addGap(2, 2, 2)
                .addComponent(jLabel77)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tmbhnaga, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        AGBakarNasi1.setPreferredSize(new java.awt.Dimension(151, 228));

        jLabel79.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel79.setText("Jus Jeruk");

        jLabel80.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel80.setText("8K");

        jLabel81.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\jusJeruk.jpg")); // NOI18N

        tmbhjeruk.setBackground(new java.awt.Color(153, 0, 0));
        tmbhjeruk.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        tmbhjeruk.setForeground(new java.awt.Color(255, 255, 255));
        tmbhjeruk.setText("TAMBAH");
        tmbhjeruk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhjerukActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AGBakarNasi1Layout = new javax.swing.GroupLayout(AGBakarNasi1);
        AGBakarNasi1.setLayout(AGBakarNasi1Layout);
        AGBakarNasi1Layout.setHorizontalGroup(
            AGBakarNasi1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AGBakarNasi1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(AGBakarNasi1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel81)
                    .addGroup(AGBakarNasi1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel79)))
                .addContainerGap(49, Short.MAX_VALUE))
            .addComponent(tmbhjeruk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AGBakarNasi1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel80)
                .addGap(96, 96, 96))
        );
        AGBakarNasi1Layout.setVerticalGroup(
            AGBakarNasi1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AGBakarNasi1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel81)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel79)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel80)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tmbhjeruk, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(AGBakarNasi1, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                    .addComponent(AGori1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                    .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel37, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel30, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                            .addComponent(AGori1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, 231, Short.MAX_VALUE)
                            .addComponent(jPanel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(AGBakarNasi1, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(251, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Minuman", jPanel9);

        jPanel31.setBackground(new java.awt.Color(255, 255, 255));

        jLabel82.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel82.setText("Tahu");

        jLabel83.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel83.setText("1K");

        jLabel84.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\tahugoreng.jpg")); // NOI18N

        tmbhtahu.setBackground(new java.awt.Color(153, 0, 0));
        tmbhtahu.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        tmbhtahu.setForeground(new java.awt.Color(255, 255, 255));
        tmbhtahu.setText("TAMBAH");
        tmbhtahu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhtahuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AGori2Layout = new javax.swing.GroupLayout(AGori2);
        AGori2.setLayout(AGori2Layout);
        AGori2Layout.setHorizontalGroup(
            AGori2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tmbhtahu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(AGori2Layout.createSequentialGroup()
                .addGroup(AGori2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AGori2Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel84))
                    .addGroup(AGori2Layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(jLabel83)))
                .addContainerGap(48, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AGori2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74))
        );
        AGori2Layout.setVerticalGroup(
            AGori2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AGori2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel84)
                .addGap(4, 4, 4)
                .addComponent(jLabel82)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel83)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tmbhtahu))
        );

        jPanel39.setPreferredSize(new java.awt.Dimension(184, 228));

        jLabel85.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel85.setText("Tempe");

        jLabel86.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel86.setText("1K");

        jLabel87.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\tempe.jpg")); // NOI18N

        tmbhtempe.setBackground(new java.awt.Color(153, 0, 0));
        tmbhtempe.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        tmbhtempe.setForeground(new java.awt.Color(255, 255, 255));
        tmbhtempe.setText("TAMBAH");
        tmbhtempe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhtempeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tmbhtempe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel39Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel39Layout.createSequentialGroup()
                        .addComponent(jLabel86)
                        .addGap(92, 92, 92))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel39Layout.createSequentialGroup()
                        .addComponent(jLabel87)
                        .addGap(46, 46, 46))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel39Layout.createSequentialGroup()
                        .addComponent(jLabel85, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54))))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel39Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel87)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel85)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel86)
                .addGap(12, 12, 12)
                .addComponent(tmbhtempe, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel40.setPreferredSize(new java.awt.Dimension(184, 228));

        jLabel88.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel88.setText("Sambal Tomat");

        jLabel90.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\sambaltomat.jpg")); // NOI18N

        tmbhSmblTomat.setBackground(new java.awt.Color(153, 0, 0));
        tmbhSmblTomat.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        tmbhSmblTomat.setForeground(new java.awt.Color(255, 255, 255));
        tmbhSmblTomat.setText("TAMBAH");
        tmbhSmblTomat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhSmblTomatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tmbhSmblTomat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel88, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel90))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel40Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel90)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel88)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(tmbhSmblTomat, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel91.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel91.setText("Sambal Terasi");

        jLabel93.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\sambalterasi.jpg")); // NOI18N

        tmbhSmblTerasi.setBackground(new java.awt.Color(153, 0, 0));
        tmbhSmblTerasi.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        tmbhSmblTerasi.setForeground(new java.awt.Color(255, 255, 255));
        tmbhSmblTerasi.setText("TAMBAH");
        tmbhSmblTerasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhSmblTerasiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tmbhSmblTerasi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel41Layout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel91))
                    .addComponent(jLabel93))
                .addGap(45, 45, 45))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel41Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel93)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel91)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tmbhSmblTerasi, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel94.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel94.setText("Sambal Matah");

        jLabel96.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\sambalmatah.jpg")); // NOI18N

        tmbhSmblMatah.setBackground(new java.awt.Color(153, 0, 0));
        tmbhSmblMatah.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        tmbhSmblMatah.setForeground(new java.awt.Color(255, 255, 255));
        tmbhSmblMatah.setText("TAMBAH");
        tmbhSmblMatah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhSmblMatahActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tmbhSmblMatah, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel96)
                    .addGroup(jPanel42Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel94)))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel42Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel96)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel94)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(tmbhSmblMatah, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel103.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel103.setText("Sambal Bawang");

        jLabel105.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\sambalbawang.jpg")); // NOI18N

        tmbhSmblBawang.setBackground(new java.awt.Color(153, 0, 0));
        tmbhSmblBawang.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        tmbhSmblBawang.setForeground(new java.awt.Color(255, 255, 255));
        tmbhSmblBawang.setText("TAMBAH");
        tmbhSmblBawang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhSmblBawangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tmbhSmblBawang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel45Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel45Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel103, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel105))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel45Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel105)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel103)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tmbhSmblBawang, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel46.setPreferredSize(new java.awt.Dimension(184, 228));

        jLabel106.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel106.setText("Sambal Goang");

        jLabel108.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\sambalgoang.jpg")); // NOI18N

        tmbhSmblGoang.setBackground(new java.awt.Color(153, 0, 0));
        tmbhSmblGoang.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        tmbhSmblGoang.setForeground(new java.awt.Color(255, 255, 255));
        tmbhSmblGoang.setText("TAMBAH");
        tmbhSmblGoang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhSmblGoangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tmbhSmblGoang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel46Layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel46Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel106))
                    .addComponent(jLabel108))
                .addGap(46, 46, 46))
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel46Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel108)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel106)
                .addGap(28, 28, 28)
                .addComponent(tmbhSmblGoang, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        AGBakarNasi2.setPreferredSize(new java.awt.Dimension(151, 228));

        jLabel109.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel109.setText("Sambal Ijo");

        jLabel111.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\sambalijo.jpg")); // NOI18N

        tmbhSmblIjo.setBackground(new java.awt.Color(153, 0, 0));
        tmbhSmblIjo.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        tmbhSmblIjo.setForeground(new java.awt.Color(255, 255, 255));
        tmbhSmblIjo.setText("TAMBAH");
        tmbhSmblIjo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tmbhSmblIjoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AGBakarNasi2Layout = new javax.swing.GroupLayout(AGBakarNasi2);
        AGBakarNasi2.setLayout(AGBakarNasi2Layout);
        AGBakarNasi2Layout.setHorizontalGroup(
            AGBakarNasi2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AGBakarNasi2Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(AGBakarNasi2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel111)
                    .addGroup(AGBakarNasi2Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel109)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(tmbhSmblIjo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        AGBakarNasi2Layout.setVerticalGroup(
            AGBakarNasi2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AGBakarNasi2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel111)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel109)
                .addGap(32, 32, 32)
                .addComponent(tmbhSmblIjo))
        );

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(AGBakarNasi2, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                    .addComponent(AGori2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel46, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                    .addComponent(jPanel39, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel40, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel39, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                            .addComponent(AGori2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel46, javax.swing.GroupLayout.PREFERRED_SIZE, 231, Short.MAX_VALUE)
                            .addComponent(jPanel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(AGBakarNasi2, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)))
                    .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(255, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Add on", jPanel31);

        tabelTotalPesanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nama Menu", "Harga per Menu", "Qty", "Harga Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tabelTotalPesanan);

        jScrollPane2.setViewportView(jScrollPane4);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel3.setText("Total:");

        totalField.setEditable(false);
        totalField.setBackground(new java.awt.Color(255, 255, 255));
        totalField.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        totalField.setBorder(null);
        totalField.setEnabled(false);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(totalField, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(totalField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel4.setText("Total Pesanan:");

        hapusButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        hapusButton.setText("HAPUS");
        hapusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusButtonActionPerformed(evt);
            }
        });

        editButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        editButton.setText("EDIT");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        simpanButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        simpanButton.setText("SIMPAN");
        simpanButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanButtonActionPerformed(evt);
            }
        });

        tanggalSpinner.setModel(new javax.swing.SpinnerDateModel());

        jLabel98.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel98.setText("Nama Pembeli :");

        namaField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        namaField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namaFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(hapusButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(simpanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel98)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(namaField, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(tanggalSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addComponent(jLabel4))
                .addContainerGap(819, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(197, 197, 197)
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jTabbedPane2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel98)
                            .addComponent(namaField)
                            .addComponent(tanggalSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(hapusButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(simpanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Menu", jPanel2);

        jLabel89.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N

        simpanStrukButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        simpanStrukButton.setText("SIMPAN");
        simpanStrukButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanStrukButtonActionPerformed(evt);
            }
        });

        jLabel97.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel97.setText("Tanggal:");

        tanggalField.setEditable(false);
        tanggalField.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        tanggalField.setBorder(null);
        tanggalField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tanggalFieldActionPerformed(evt);
            }
        });

        namaPembeliField.setEditable(false);
        namaPembeliField.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        namaPembeliField.setBorder(null);
        namaPembeliField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namaPembeliFieldActionPerformed(evt);
            }
        });

        totalHargaField.setEditable(false);
        totalHargaField.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        totalHargaField.setBorder(null);
        totalHargaField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalHargaFieldActionPerformed(evt);
            }
        });

        jLabel92.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel92.setText("Total Harga:");

        jLabel99.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel99.setText("Nama Pembeli :");

        jLabel95.setIcon(new javax.swing.ImageIcon("D:\\MaNya\\pbo-kasir-ma-nya\\MauMaNya\\image\\qrismanya.jpg")); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(72, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel97)
                            .addComponent(jLabel92)
                            .addComponent(jLabel99))
                        .addGap(85, 85, 85)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(namaPembeliField, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(totalHargaField, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tanggalField, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(199, 199, 199)
                        .addComponent(jLabel95)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(0, 6, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel97)
                    .addComponent(tanggalField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel99)
                    .addComponent(namaPembeliField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel92)
                    .addComponent(totalHargaField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(jLabel95))))
        );

        strukTabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nama Menu", "Harga per Menu", "Qty", "Total Harga"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane7.setViewportView(strukTabel);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(165, 165, 165)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel89)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(simpanStrukButton, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 647, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(1081, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel89)
                .addGap(28, 28, 28)
                .addComponent(simpanStrukButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(204, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Struk", jPanel3);

        riwayatTabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "No.", "Tanggal", "Nama", "Total Harga"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane5.setViewportView(riwayatTabel);

        jLabel100.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel100.setText("Tanggal");

        jLabel102.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel102.setText("Bulan");

        jLabel104.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel104.setText("Tahun");

        buttonFilterRiwayat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        buttonFilterRiwayat.setText("TAMPILKAN");
        buttonFilterRiwayat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonFilterRiwayatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout riwayatPanelLayout = new javax.swing.GroupLayout(riwayatPanel);
        riwayatPanel.setLayout(riwayatPanelLayout);
        riwayatPanelLayout.setHorizontalGroup(
            riwayatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(riwayatPanelLayout.createSequentialGroup()
                .addGroup(riwayatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(riwayatPanelLayout.createSequentialGroup()
                        .addGap(1137, 1137, 1137)
                        .addGroup(riwayatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel100)
                            .addComponent(comboTgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(riwayatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboBulan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel102))
                        .addGap(29, 29, 29)
                        .addGroup(riwayatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel104)
                            .addComponent(comboTahun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(riwayatPanelLayout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 1516, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(1030, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, riwayatPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(buttonFilterRiwayat)
                .addGap(1096, 1096, 1096))
        );
        riwayatPanelLayout.setVerticalGroup(
            riwayatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, riwayatPanelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(riwayatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel100)
                    .addComponent(jLabel102)
                    .addComponent(jLabel104))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(riwayatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboTgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBulan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboTahun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(buttonFilterRiwayat)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 641, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );

        jTabbedPane1.addTab("Riwayat Pesanan", riwayatPanel);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        jScrollPane6.setViewportView(jPanel10);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1842, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 987, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tmbhnagaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhnagaActionPerformed
        tambahPesanan(22, "Jus Buah Naga", 8000.00);
    }//GEN-LAST:event_tmbhnagaActionPerformed

    private void tmbhjambuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhjambuActionPerformed
        tambahPesanan(23, "Jus Jambu", 8000.00);
    }//GEN-LAST:event_tmbhjambuActionPerformed

    private void tmbhwortelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhwortelActionPerformed
        tambahPesanan(25, "Jus Wortel", 7000.00);
    }//GEN-LAST:event_tmbhwortelActionPerformed

    private void tmbhAGorinakarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhAGorinakarActionPerformed
        tambahPesanan(13, "AG Original + Nasi Bakar", 15000.00);
    }//GEN-LAST:event_tmbhAGorinakarActionPerformed

    private void tmbhAGseltelnasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhAGseltelnasiActionPerformed
        tambahPesanan(7, "AG Selimut Telor + Nasi", 14000.00);
    }//GEN-LAST:event_tmbhAGseltelnasiActionPerformed

    private void tmbhAGkrispinasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhAGkrispinasiActionPerformed
        tambahPesanan(8, "AG Krispi + Nasi", 14000.00);
    }//GEN-LAST:event_tmbhAGkrispinasiActionPerformed

    private void tmbhAGoriLiwetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhAGoriLiwetActionPerformed
        tambahPesanan(9, "AG Original + Nasi Liwet", 14000.00);
    }//GEN-LAST:event_tmbhAGoriLiwetActionPerformed

    private void AGbakarliwetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AGbakarliwetActionPerformed
        tambahPesanan(10, "AG Bakar + Nasi Liwet", 14000.00);
    }//GEN-LAST:event_AGbakarliwetActionPerformed

    private void tmbhSmblBawangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhSmblBawangActionPerformed
        tambahPesanan(33, "Sambal Bawang", 0.00);
    }//GEN-LAST:event_tmbhSmblBawangActionPerformed

    private void tmbhSmblGoangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhSmblGoangActionPerformed
        tambahPesanan(32, "Sambal Goang", 0.00);
    }//GEN-LAST:event_tmbhSmblGoangActionPerformed

    private void namaPembeliFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namaPembeliFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namaPembeliFieldActionPerformed

    private void totalHargaFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalHargaFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalHargaFieldActionPerformed

    private void tmbhtomatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhtomatActionPerformed
        tambahPesanan(24, "Jus Tomat", 7000.00);
    }//GEN-LAST:event_tmbhtomatActionPerformed

    private void namaFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namaFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namaFieldActionPerformed

    private void tanggalFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tanggalFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tanggalFieldActionPerformed

    private void tmbhAGoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhAGoriActionPerformed
        tambahPesanan(1, "Ayam Gebug Original", 9000.00);
    }//GEN-LAST:event_tmbhAGoriActionPerformed

    private void tmbhAGbakarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhAGbakarActionPerformed
        tambahPesanan(2, "Ayam Gebug Bakar", 9000.00);
    }//GEN-LAST:event_tmbhAGbakarActionPerformed

    private void tmbhAGkrispiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhAGkrispiActionPerformed
        tambahPesanan(3, "Ayam Gebug Krispi", 10000.00);
    }//GEN-LAST:event_tmbhAGkrispiActionPerformed

    private void tmbhAGseltelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhAGseltelActionPerformed
        tambahPesanan(4, "AG Selimut Telor", 10000.00);
    }//GEN-LAST:event_tmbhAGseltelActionPerformed

    private void tmbhAGorinasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhAGorinasiActionPerformed
        tambahPesanan(5, "AG Original + Nasi", 12000.00);
    }//GEN-LAST:event_tmbhAGorinasiActionPerformed

    private void tmbhAGbakarnasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhAGbakarnasiActionPerformed
        tambahPesanan(6, "AG Bakar + Nasi", 12000.00);
    }//GEN-LAST:event_tmbhAGbakarnasiActionPerformed

    private void tmbhAGseltelliwetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhAGseltelliwetActionPerformed
        tambahPesanan(11, "AG Selimut Telor + Nasi Liwet", 16000.00);
    }//GEN-LAST:event_tmbhAGseltelliwetActionPerformed

    private void tmbhAGkrispiliwetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhAGkrispiliwetActionPerformed
        tambahPesanan(12, "AG Krispi + Nasi Liwet", 16000.00);
    }//GEN-LAST:event_tmbhAGkrispiliwetActionPerformed

    private void tmbhAGseltelnakarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhAGseltelnakarActionPerformed
        tambahPesanan(14, "AG Selimut Telor + Nasi Bakar", 17000.00);
    }//GEN-LAST:event_tmbhAGseltelnakarActionPerformed

    private void tmbhAGbakarnakarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhAGbakarnakarActionPerformed
        tambahPesanan(15, "AG Bakar + Nasi Bakar", 14000.00);
    }//GEN-LAST:event_tmbhAGbakarnakarActionPerformed

    private void tmbhalpukatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhalpukatActionPerformed
        tambahPesanan(16, "Jus Alpukat", 8000.00);
    }//GEN-LAST:event_tmbhalpukatActionPerformed

    private void tmbhmanggaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhmanggaActionPerformed
        tambahPesanan(17, "Jus Mangga", 8000.00);
    }//GEN-LAST:event_tmbhmanggaActionPerformed

    private void tmbhapelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhapelActionPerformed
        tambahPesanan(18, "Jus Apel", 8000.00);
    }//GEN-LAST:event_tmbhapelActionPerformed

    private void tmbhsirsakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhsirsakActionPerformed
        tambahPesanan(19, "Jus Sirsak", 8000.00);
    }//GEN-LAST:event_tmbhsirsakActionPerformed

    private void tmbhstrawberryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhstrawberryActionPerformed
        tambahPesanan(20, "Jus Strawberry", 8000.00);
    }//GEN-LAST:event_tmbhstrawberryActionPerformed

    private void tmbhjerukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhjerukActionPerformed
        tambahPesanan(21, "Jus Alpukat", 8000.00);
    }//GEN-LAST:event_tmbhjerukActionPerformed

    private void tmbhtahuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhtahuActionPerformed
        tambahPesanan(26, "Tahu", 1000.00);
    }//GEN-LAST:event_tmbhtahuActionPerformed

    private void tmbhtempeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhtempeActionPerformed
        tambahPesanan(27, "Tempe", 1000.00);
    }//GEN-LAST:event_tmbhtempeActionPerformed

    private void tmbhSmblTomatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhSmblTomatActionPerformed
        tambahPesanan(28, "Sambal Tomat", 0.00);
    }//GEN-LAST:event_tmbhSmblTomatActionPerformed

    private void tmbhSmblTerasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhSmblTerasiActionPerformed
        tambahPesanan(29, "Sambal Terasi", 0.00);
    }//GEN-LAST:event_tmbhSmblTerasiActionPerformed

    private void tmbhSmblMatahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhSmblMatahActionPerformed
        tambahPesanan(30, "Sambal Matah", 0.00);
    }//GEN-LAST:event_tmbhSmblMatahActionPerformed

    private void tmbhSmblIjoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tmbhSmblIjoActionPerformed
        tambahPesanan(31, "Sambal Ijo", 0.00);
    }//GEN-LAST:event_tmbhSmblIjoActionPerformed

    private void hapusButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusButtonActionPerformed
        int selectedRow = tabelTotalPesanan.getSelectedRow();
        if (selectedRow != -1) {
            modelPesanan.removeRow(selectedRow);
            hitungGrandTotal();
            setupTombolAwal();
        }else {
            JOptionPane.showMessageDialog(this, "Pilih baris yang ingin dihapus terlebih dahulu.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_hapusButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        int selectedRow = tabelTotalPesanan.getSelectedRow();
    
        if (selectedRow != -1) {
            String namaMenu = (String) modelPesanan.getValueAt(selectedRow, 1);
            int currentQty = (int) modelPesanan.getValueAt(selectedRow, 3);
            double hargaSatuan = (double) modelPesanan.getValueAt(selectedRow, 2);
            
            Object input = JOptionPane.showInputDialog(this, //ini aku ganti dr string ke object ya, 05.19 AM
                "Masukkan Kuantitas (Qty) baru untuk " + namaMenu + ":", 
                "Edit Kuantitas", 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                null, 
                String.valueOf(currentQty)
            );
            if (input != null) {
           
            String inputString = input.toString();
            
            if (!inputString.trim().isEmpty()) {
                try {
                    int newQty = Integer.parseInt(inputString.trim());
                
                    if (newQty > 0) {
                        double newTotal = newQty * hargaSatuan;
                        modelPesanan.setValueAt(newQty, selectedRow, 3);
                        modelPesanan.setValueAt(newTotal, selectedRow, 4);
                        hitungGrandTotal();
                    } else {
                        modelPesanan.removeRow(selectedRow);
                        hitungGrandTotal();
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Input Qty harus berupa angka yang valid.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            }
        }
   
            /*if (input != null && !input.trim().isEmpty()) {
                try {
                   int newQty = Integer.parseInt(input.trim());
                
                    if (newQty > 0) {
                        double newTotal = newQty * hargaSatuan;
                        modelPesanan.setValueAt(newQty, selectedRow, 3);
                        modelPesanan.setValueAt(newTotal, selectedRow, 4);
                        hitungGrandTotal();
                    }else {
                        modelPesanan.removeRow(selectedRow);
                        hitungGrandTotal();
                    }
                }catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Input Qty harus berupa angka yang valid.", "Error", JOptionPane.ERROR_MESSAGE);
                }   
            }
        }*/
    }//GEN-LAST:event_editButtonActionPerformed

    private void simpanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanButtonActionPerformed
    Date selectedDate = (Date) tanggalSpinner.getValue();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
    String tanggalInput = dateFormat.format(selectedDate);
    
    String namaPembeli = namaField.getText().trim();
    
    if (namaPembeli == null || namaPembeli.length() == 0) { 
        JOptionPane.showMessageDialog(this, "Nama Pembeli harus diisi.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }
    // Asumsi: tabelTotalPesanan adalah JTable pesanan Anda
    if (grandTotal <= 0 || tabelTotalPesanan.getRowCount() == 0) { 
        JOptionPane.showMessageDialog(this, "Pesanan kosong. Tambahkan item terlebih dahulu.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    Connection conn = null;
    java.sql.PreparedStatement pst = null;
    java.sql.ResultSet rs = null;
    int idTransaksiBaru = -1;

    try {
        conn = koneksi.getkoneksi();
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Gagal terhubung ke database. Pastikan server MySQL berjalan.", "Error Koneksi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // --- KOREKSI TRANSAKSI UTAMA (tabel: transaksi) ---
        // Menggunakan total_bayar, tanggal_transaksi (CURDATE), waktu_transaksi (CURTIME), dan status.
        String sqlTransaksi = "INSERT INTO transaksi (tanggal_transaksi, waktu_transaksi, nama_pembeli, total_bayar, status) VALUES (?, CURTIME(), ?, ?, 'Selesai')";
        
        pst = conn.prepareStatement(sqlTransaksi, java.sql.Statement.RETURN_GENERATED_KEYS);
        pst.setString(1, tanggalInput);
        pst.setString(2, namaPembeli);
        pst.setDouble(3, grandTotal);
        pst.executeUpdate();
        
        rs = pst.getGeneratedKeys();
        if (rs.next()) {
            idTransaksiBaru = rs.getInt(1);
        } else {
            throw new SQLException("Gagal mendapatkan ID transaksi yang baru.");
        }

        // --- KOREKSI DETAIL TRANSAKSI (tabel: detail_transaksi) ---
        // Menggunakan id_menu, qty, subtotal
        String sqlDetail = "INSERT INTO detail_transaksi (id_transaksi, id_menu, qty, subtotal) VALUES (?, ?, ?, ?)";
        pst = conn.prepareStatement(sqlDetail);

        for (int i = 0; i < tabelTotalPesanan.getRowCount(); i++) {
            // Kolom di tabelTotalPesanan: 0=Nama Menu, 1=Harga, 2=Qty, 3=Harga Total/Subtotal
            String namaItem = tabelTotalPesanan.getValueAt(i, 1).toString().trim(); 
            // Ambil Qty dari kolom 2
            String qtyStr = tabelTotalPesanan.getValueAt(i, 3).toString();
            // Ambil Subtotal dari kolom 3
            String subTotalStr = tabelTotalPesanan.getValueAt(i, 4).toString();
            
            int jumlahBeli = Integer.parseInt(qtyStr.trim()); 
            double subTotal = Double.parseDouble(subTotalStr.trim());
            int idMenu = getIdMenuDariDatabase(conn, namaItem);

            pst.setInt(1, idTransaksiBaru);
            pst.setInt(2, idMenu);     
            pst.setInt(3, jumlahBeli);
            
            pst.setDouble(4, subTotal);

            pst.addBatch();
        }

        pst.executeBatch();

        JOptionPane.showMessageDialog(this, "Transaksi #" + idTransaksiBaru + " Berhasil Disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        resetForm();
        
        loadDetailStruk(idTransaksiBaru);
        loadRiwayatDenganDetail("SEMUA", "SEMUA", "SEMUA");
        jTabbedPane1.setSelectedIndex(1);

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error SQL: Pastikan Anda menjalankan SQL yang dikoreksi (total_bayar) dan semua kolom detail sudah benar. Detail: " + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        // Error 'For input string: "12000.0"' seperti di image_9275be.png masuk di sini.
        // Ini terjadi karena format angka di tabel Anda tidak dapat di-parse (misal ada spasi).
        JOptionPane.showMessageDialog(this, "Error Aplikasi: Gagal mengolah data tabel. Pastikan kolom di tabel TIDAK memiliki teks seperti 'Rp' atau spasi. Detail: " + e.getMessage(), "Error Umum", JOptionPane.ERROR_MESSAGE);
    } finally {
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            System.err.println("Error saat menutup resource: " + ex.getMessage());}
    
    }//GEN-LAST:event_simpanButtonActionPerformed
    }
    private void simpanStrukButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanStrukButtonActionPerformed
        
        loadRiwayatDenganDetail("SEMUA", "SEMUA", "SEMUA");
        clearStrukFields();
        jTabbedPane1.setSelectedIndex(2);
        JOptionPane.showMessageDialog(this, "Detail transaksi telah diarsipkan dan riwayat diperbarui!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_simpanStrukButtonActionPerformed

    private void buttonFilterRiwayatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonFilterRiwayatActionPerformed
        String day = (String) comboTgl.getSelectedItem();
        String month = (String) comboBulan.getSelectedItem();
        String year = (String) comboTahun.getSelectedItem();
        loadRiwayatDenganDetail(day, month, year);
    
        JOptionPane.showMessageDialog(this, "Riwayat berhasil difilter.", "Filter Sukses", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_buttonFilterRiwayatActionPerformed
    private void clearStrukFields() {
        if (modelStrukDetail != null) {
            modelStrukDetail.setRowCount(0);
        }
        if (tanggalField != null) {
            tanggalField.setText("");
        }
        if (namaPembeliField != null) {
            namaPembeliField.setText("");
        }
        if (totalHargaField != null) {
            totalHargaField.setText("");
        }
    }
    
    public void loadDetailStruk(int idTransaksi) {
        if (modelStrukDetail == null) {
        modelStrukDetail = new ReadOnlyTableModel(
            new Object[]{"Nama Menu", "Harga Satuan", "Qty", "Subtotal"}, 0
        );
        strukTabel.setModel(modelStrukDetail); 
    }
    
    // SELALU bersihkan tabel sebelum memuat data transaksi baru
    modelStrukDetail.setRowCount(0); 
    
    Connection conn = null;
    java.sql.PreparedStatement pst = null;
    java.sql.ResultSet rs = null;

    try {
        conn = koneksi.getkoneksi();
        
        String sqlHeader = "SELECT tanggal_transaksi, nama_pembeli, total_bayar " +
                           "FROM transaksi WHERE id_transaksi = ?";
        
        pst = conn.prepareStatement(sqlHeader);
        pst.setInt(1, idTransaksi);
        rs = pst.executeQuery();

        if (rs.next()) {
            String tanggal = rs.getString("tanggal_transaksi");
            String nama = rs.getString("nama_pembeli");
            double total = rs.getDouble("total_bayar");

            // --- Tampilkan ke komponen GUI Anda ---
            tanggalField.setText(tanggal); 
            namaPembeliField.setText(nama);
            // Format total harga ke format Rupiah
            totalHargaField.setText(rupiahFormat.format(total));
        }

        rs.close(); // Tutup ResultSet dari Header Query
        pst.close();
        
        // --- QUERY DETAIL STRUK MENGGUNAKAN JOIN ---
        String sql = "SELECT m.nama_menu, m.harga, d.qty, d.subtotal " +
                     "FROM detail_transaksi d " +
                     "JOIN menu m ON d.id_menu = m.id_menu " +
                     "WHERE d.id_transaksi = ?";
        
        pst = conn.prepareStatement(sql);
        pst.setInt(1, idTransaksi); // Masukkan ID Transaksi yang dicari
        rs = pst.executeQuery();

        while (rs.next()) {
            // Menambahkan baris ke model tabel
            modelStrukDetail.addRow(new Object[]{
                rs.getString("nama_menu"),
                rs.getDouble("harga"),
                rs.getInt("qty"),
                rs.getDouble("subtotal")
            });
        }
        
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, 
            "Gagal memuat detail struk. Detail: " + e.getMessage(), 
            "Error Database Struk", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "Error Umum saat memuat detail struk. Detail: " + e.getMessage(), 
            "Error Umum", JOptionPane.ERROR_MESSAGE);
    } finally {
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            // Handle penutupan koneksi
        }
    }
    }
    
    private void loadRiwayatDenganDetail(String selectedDay, String selectedMonth, String selectedYear) {
    
    // Asumsi: riwayatTabel adalah nama JTable Anda, modelRiwayat sudah dideklarasikan
    if (modelRiwayat == null) {
        // Inisialisasi model jika belum terinisialisasi
        modelRiwayat = new ReadOnlyTableModel(
            new Object[]{"No.", "Tanggal", "Nama Pembeli", "Daftar Menu", "Total Harga"}, 0
        );
        // Pastikan JTable Anda terhubung ke model ini (hanya jika belum dilakukan di inisialisasi awal)
        riwayatTabel.setModel(modelRiwayat); 
    }
    
    // Membersihkan tabel Riwayat
    modelRiwayat.setRowCount(0);
    
    Connection conn = null;
    java.sql.PreparedStatement pstHeader = null;
    java.sql.PreparedStatement pstDetail = null;
    java.sql.ResultSet rsHeader = null;
    java.sql.ResultSet rsDetail = null;

    try {
        conn = koneksi.getkoneksi();
        
        // 1. Ambil Semua Data Transaksi (Header)
        // Mengambil data transaksi diurutkan berdasarkan ID terbaru
        StringBuilder sqlHeaderBuilder = new StringBuilder();
        sqlHeaderBuilder.append("SELECT id_transaksi, tanggal_transaksi, nama_pembeli, total_bayar FROM transaksi WHERE 1=1 ");
        List<String> params = new ArrayList<>();
        
        if (!selectedYear.equals("SEMUA")) {
            sqlHeaderBuilder.append("AND YEAR(tanggal_transaksi) = ? ");
            params.add(selectedYear);
        }
        if (!selectedMonth.equals("SEMUA")) {
            sqlHeaderBuilder.append("AND MONTH(tanggal_transaksi) = ? ");
            params.add(selectedMonth);
        }
        if (!selectedDay.equals("SEMUA")) {
            sqlHeaderBuilder.append("AND DAY(tanggal_transaksi) = ? ");
            params.add(selectedDay);
        }
        
        sqlHeaderBuilder.append("ORDER BY id_transaksi DESC");
        pstHeader = conn.prepareStatement(sqlHeaderBuilder.toString());
        
        for (int i = 0; i < params.size(); i++) {
            pstHeader.setString(i + 1, params.get(i));
        }
       
        rsHeader = pstHeader.executeQuery();

        int noUrut = 1; 
        
        // LOOP 1: Mengambil setiap Transaksi satu per satu
        while (rsHeader.next()) {
            int idTransaksi = rsHeader.getInt("id_transaksi");
            String tanggal = rsHeader.getString("tanggal_transaksi");
            String namaPembeli = rsHeader.getString("nama_pembeli");
            double totalBayar = rsHeader.getDouble("total_bayar");
            
            // 2. Ambil Detail Menu untuk ID Transaksi ini
            // Menggunakan JOIN antara detail_transaksi dan menu
            String sqlDetail = "SELECT m.nama_menu, d.qty " +
                               "FROM detail_transaksi d " +
                               "JOIN menu m ON d.id_menu = m.id_menu " +
                               "WHERE d.id_transaksi = ?";
            
            pstDetail = conn.prepareStatement(sqlDetail);
            pstDetail.setInt(1, idTransaksi);
            rsDetail = pstDetail.executeQuery();
            
            StringBuilder daftarMenu = new StringBuilder();
            
            // LOOP 2: Mengumpulkan Nama Menu dan Qty ke dalam String yang diformat
            while (rsDetail.next()) {
                String menu = rsDetail.getString("nama_menu");
                int qty = rsDetail.getInt("qty");
                
                // Format: Nama Makanan (Qty)
                if (daftarMenu.length() > 0) {
                    daftarMenu.append(", "); // Tambahkan koma jika bukan item pertama
                }
                daftarMenu.append(menu).append("(").append(qty).append(")");
            }
            
            // Tutup resource detail sebelum loop header berlanjut
            if (rsDetail != null) rsDetail.close();
            if (pstDetail != null) pstDetail.close();
            
            // 3. Masukkan data ke Tabel Riwayat
            modelRiwayat.addRow(new Object[]{
                noUrut++, 
                tanggal, 
                namaPembeli,
                daftarMenu.toString(), // Kolom 'Daftar Menu' yang sudah diformat
                rupiahFormat.format(totalBayar) // Kolom 'Total Harga'
            });
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Gagal memuat riwayat transaksi. Detail: " + e.getMessage(), 
            "Error Database Riwayat", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error Umum saat memuat riwayat. Detail: " + e.getMessage(), 
            "Error Umum", JOptionPane.ERROR_MESSAGE);
    } finally {
        try {
            if (rsHeader != null) rsHeader.close();
            if (pstHeader != null) pstHeader.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            System.err.println("Error saat menutup resource: " + ex.getMessage());
        }
    }
}
    
    private class ReadOnlyTableModel extends DefaultTableModel {

      public ReadOnlyTableModel(Object[] columnNames, int rowCount) {
          super(columnNames, rowCount);
      }

      @Override
      // Method ini mengembalikan FALSE untuk semua kolom
      public boolean isCellEditable(int row, int column) {
          return false; 
      }
    }
    
    private void setupFilterCombos() {
        comboTahun.addItem("SEMUA");
        int tahunSekarang = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = tahunSekarang; i >= tahunSekarang - 5; i--) {
            comboTahun.addItem(String.valueOf(i));
        }
        comboBulan.addItem("SEMUA");
        for (int i = 1; i <= 12; i++) {
            comboBulan.addItem(String.valueOf(i));
        }
        comboTgl.addItem("SEMUA");
        for (int i = 1; i <= 31; i++) {
            comboTgl.addItem(String.valueOf(i));
        }
    }
    private void setupTanggalSpinner() {
        // Mengatur Spinner Model ke mode Tanggal
        SpinnerDateModel model = new SpinnerDateModel();
        tanggalSpinner.setModel(model);

        // Mengatur editor agar hanya menampilkan Tanggal (bukan jam)
        JSpinner.DateEditor editor = new JSpinner.DateEditor(tanggalSpinner, "yyyy-MM-dd"); // Format MySQL
        tanggalSpinner.setEditor(editor);
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Homepage().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AGBakarNasi;
    private javax.swing.JPanel AGBakarNasi1;
    private javax.swing.JPanel AGBakarNasi2;
    private javax.swing.JButton AGbakarliwet;
    private javax.swing.JPanel AGori;
    private javax.swing.JPanel AGori1;
    private javax.swing.JPanel AGori2;
    private javax.swing.JButton buttonFilterRiwayat;
    private javax.swing.JComboBox<String> comboBulan;
    private javax.swing.JComboBox<String> comboTahun;
    private javax.swing.JComboBox<String> comboTgl;
    private javax.swing.JButton editButton;
    private javax.swing.JButton hapusButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField namaField;
    private javax.swing.JTextField namaPembeliField;
    private javax.swing.JPanel riwayatPanel;
    private javax.swing.JTable riwayatTabel;
    private javax.swing.JButton simpanButton;
    private javax.swing.JButton simpanStrukButton;
    private javax.swing.JTable strukTabel;
    private javax.swing.JTable tabelTotalPesanan;
    private javax.swing.JTextField tanggalField;
    private javax.swing.JSpinner tanggalSpinner;
    private javax.swing.JButton tmbhAGbakar;
    private javax.swing.JButton tmbhAGbakarnakar;
    private javax.swing.JButton tmbhAGbakarnasi;
    private javax.swing.JButton tmbhAGkrispi;
    private javax.swing.JButton tmbhAGkrispiliwet;
    private javax.swing.JButton tmbhAGkrispinasi;
    private javax.swing.JButton tmbhAGori;
    private javax.swing.JButton tmbhAGoriLiwet;
    private javax.swing.JButton tmbhAGorinakar;
    private javax.swing.JButton tmbhAGorinasi;
    private javax.swing.JButton tmbhAGseltel;
    private javax.swing.JButton tmbhAGseltelliwet;
    private javax.swing.JButton tmbhAGseltelnakar;
    private javax.swing.JButton tmbhAGseltelnasi;
    private javax.swing.JButton tmbhSmblBawang;
    private javax.swing.JButton tmbhSmblGoang;
    private javax.swing.JButton tmbhSmblIjo;
    private javax.swing.JButton tmbhSmblMatah;
    private javax.swing.JButton tmbhSmblTerasi;
    private javax.swing.JButton tmbhSmblTomat;
    private javax.swing.JButton tmbhalpukat;
    private javax.swing.JButton tmbhapel;
    private javax.swing.JButton tmbhjambu;
    private javax.swing.JButton tmbhjeruk;
    private javax.swing.JButton tmbhmangga;
    private javax.swing.JButton tmbhnaga;
    private javax.swing.JButton tmbhsirsak;
    private javax.swing.JButton tmbhstrawberry;
    private javax.swing.JButton tmbhtahu;
    private javax.swing.JButton tmbhtempe;
    private javax.swing.JButton tmbhtomat;
    private javax.swing.JButton tmbhwortel;
    private javax.swing.JTextField totalField;
    private javax.swing.JTextField totalHargaField;
    // End of variables declaration//GEN-END:variables
}
