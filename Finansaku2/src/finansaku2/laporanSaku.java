/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package finansaku2;
import static finansaku2.loginSaku.loggedInUserId;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.text.SimpleDateFormat;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;


/**
 *
 * @author odygunadiksa
 */
public class laporanSaku extends javax.swing.JFrame {
     private String loggedInUsername;
     private int userId;
     private Connection conn;
     XSSFWorkbook workbook = new XSSFWorkbook();
    /**
     * Creates new form berandaSaku
     */
    public laporanSaku (String username) {
            initComponents();
            this.loggedInUsername = username; 
            usernameProfile.setText(loggedInUsername);
            this.userId = userId;
             String[] namaLengkap = getNamaLengkap(loggedInUsername);
            labelNamaUser.setText(namaLengkap[0] + " " + namaLengkap[1] + "!");
            updateTotalLabels();
              this.conn = koneksi.koneksi();
        }
      
    
    private String[] getNamaLengkap(String username) {
        String[] namaLengkap = new String[2];
        try {
            Connection conn = koneksi.koneksi(); 
            String query = "SELECT nama_depan, nama_belakang FROM Pengguna WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                namaLengkap[0] = rs.getString("nama_depan");
                namaLengkap[1] = rs.getString("nama_belakang");
            }
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return namaLengkap;
    }
    
     private double hitungTotalTransaksi() {
        double total = 0.0;
        try {
               Connection conn = koneksi.koneksi(); 
            String query = "SELECT SUM(jumlah) AS total FROM Transaksi WHERE id_pengguna = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, loggedInUserId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("total");
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return total;
    }

    // Fungsi untuk menghitung total pemasukan
    private double hitungTotalPemasukan() {
        double total = 0.0;
        try {
               Connection conn = koneksi.koneksi(); 
            String query = "SELECT SUM(jumlah) AS total FROM Transaksi WHERE id_pengguna = ? AND jenis = 'pemasukan'";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, loggedInUserId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("total");
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return total;
    }

    // Fungsi untuk menghitung total pengeluaran
    private double hitungTotalPengeluaran() {
        double total = 0.0;
        try {
               Connection conn = koneksi.koneksi(); 
            String query = "SELECT SUM(jumlah) AS total FROM Transaksi WHERE id_pengguna = ? AND jenis = 'pengeluaran'";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, loggedInUserId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("total");
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return total;
    }
    
    private void updateTotalLabels() {
     double totalTransaksi = hitungTotalTransaksi();
    double totalPemasukan = hitungTotalPemasukan();
    double totalPengeluaran = hitungTotalPengeluaran();

    NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    totalTransaksiNumber.setText(rupiahFormat.format(totalTransaksi));
    totalPemasukanNumber.setText(rupiahFormat.format(totalPemasukan));
    totalPengeluaranNumber.setText(rupiahFormat.format(totalPengeluaran));
}

   public void retrieveTransactions() {
    try {
        Connection conn = koneksi.koneksi();
        String query = "SELECT t.tanggal, t.jenis, t.jumlah, t.deskripsi " +
                       "FROM Transaksi t " +
                       "WHERE t.id_pengguna = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, loggedInUserId); // Pastikan loggedInUserId telah diinisialisasi sebelumnya
        ResultSet resultSet = statement.executeQuery();

        DefaultTableModel model = (DefaultTableModel) transaksiTable.getModel();
        model.setRowCount(0); // Bersihkan tabel sebelum menambahkan data baru

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID")); // Format untuk mata uang IDR

        while (resultSet.next()) {
            Object[] row = {
                resultSet.getString("jenis"),
                resultSet.getDate("tanggal"),
                currencyFormatter.format(resultSet.getDouble("jumlah")), // Format jumlah transaksi ke format rupiah
                resultSet.getString("deskripsi")
            };
            model.addRow(row);
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat mengambil data transaksi: " + ex.getMessage());
    }
    
}
  
   private void writeDataToExcel() {
    try {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Simpan File Excel");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel files", "xlsx"));
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            // Pastikan ekstensi file adalah .xlsx
            if (!fileToSave.getAbsolutePath().endsWith(".xlsx")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".xlsx");
            }

            // Inisialisasi workbook dan sheet
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Data Transaksi");
            
            // Style untuk judul kolom dan nomor
            XSSFCellStyle headerTitleStyle = workbook.createCellStyle();
            headerTitleStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerTitleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerTitleStyle.setAlignment(HorizontalAlignment.CENTER);
            headerTitleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            XSSFFont headerTitleFont = workbook.createFont();
            headerTitleFont.setBold(true);
            headerTitleFont.setFontHeightInPoints((short) 14); // Ukuran font 14 untuk judul
            headerTitleStyle.setFont(headerTitleFont);

            // Style untuk nomor
            XSSFCellStyle numberStyle = workbook.createCellStyle();
            numberStyle.setAlignment(HorizontalAlignment.CENTER);
            numberStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            numberStyle.setBorderTop(BorderStyle.THIN);
            numberStyle.setBorderBottom(BorderStyle.THIN);
            numberStyle.setBorderLeft(BorderStyle.THIN);
            numberStyle.setBorderRight(BorderStyle.THIN);
            numberStyle.setWrapText(true); // Mengaktifkan pembungkus teks untuk nomor

            // Style untuk data
            XSSFCellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setAlignment(HorizontalAlignment.LEFT);
            dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            dataStyle.setBorderTop(BorderStyle.THIN);
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);
            dataStyle.setWrapText(true); // Mengaktifkan pembungkus teks untuk data

            // Header judul
            XSSFRow headerTitleRow = sheet.createRow(0);
            XSSFCell noCell = headerTitleRow.createCell(0); // Kolom nomor di sebelah kiri
            noCell.setCellValue("No");
            noCell.setCellStyle(headerTitleStyle);
            
            XSSFCell dateCell = headerTitleRow.createCell(1); // Kolom tanggal
            dateCell.setCellValue("Tanggal");
            dateCell.setCellStyle(headerTitleStyle);
            
            XSSFCell categoryCell = headerTitleRow.createCell(2); // Kolom kategori
            categoryCell.setCellValue("Kategori");
            categoryCell.setCellStyle(headerTitleStyle);
            
            XSSFCell descriptionCell = headerTitleRow.createCell(3); // Kolom deskripsi
            descriptionCell.setCellValue("Deskripsi");
            descriptionCell.setCellStyle(headerTitleStyle);
            
            XSSFCell nominalCell = headerTitleRow.createCell(4); // Kolom nominal
            nominalCell.setCellValue("Nominal");
            nominalCell.setCellStyle(headerTitleStyle);

            // Menyesuaikan lebar kolom Tanggal, Nominal, dan Deskripsi
            int columnWidth = 20; // Lebar kolom dalam satuan karakter
            sheet.setColumnWidth(1, columnWidth * 256); // Tanggal
            sheet.setColumnWidth(2, columnWidth * 256); // Kategori
            sheet.setColumnWidth(3, columnWidth * 256); // Deskripsi
            sheet.setColumnWidth(4, columnWidth * 256); // Nominal

            // Data
                for (int row = 0; row < transaksiTable.getRowCount(); row++) {
                XSSFRow excelRow = sheet.createRow(row + 1);
                XSSFCell numberCell = excelRow.createCell(0); // Kolom nomor
                numberCell.setCellValue(row + 1); // Nomor dimulai dari 1
                numberCell.setCellStyle(numberStyle);
                for (int col = 0; col < transaksiTable.getColumnCount(); col++) {
                    XSSFCell cell = excelRow.createCell(col + 1); // Kolom data dimulai dari indeks 1 karena nomor di kolom pertama
                    Object value = transaksiTable.getValueAt(row, col);
                    if (value instanceof java.sql.Date) {
                        // Mengonversi java.sql.Date menjadi String menggunakan SimpleDateFormat
                        String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(value);
                        cell.setCellValue(formattedDate);
                    } else {
                        // Menggunakan nilai langsung jika bukan instance dari java.sql.Date
                        cell.setCellValue(value.toString());
                    }
                    cell.setCellStyle(dataStyle);
                }
            }

            // Tambahkan total transaksi, total pemasukan, dan total pengeluaran di baris terakhir
            XSSFRow totalRow = sheet.createRow(transaksiTable.getRowCount() + 1);
            XSSFCell totalLabelCell = totalRow.createCell(0);
            totalLabelCell.setCellValue("Total Transaksi:");
            totalLabelCell.setCellStyle(headerTitleStyle);

            XSSFCell totalTransaksiCell = totalRow.createCell(1);
            totalTransaksiCell.setCellValue(totalTransaksiNumber.getText());
            totalTransaksiCell.setCellStyle(dataStyle);

            XSSFCell totalPemasukanLabelCell = totalRow.createCell(2);
            totalPemasukanLabelCell.setCellValue("Total Pemasukan:");
            totalPemasukanLabelCell.setCellStyle(headerTitleStyle);

            XSSFCell totalPemasukanCell = totalRow.createCell(3);
            totalPemasukanCell.setCellValue(totalPemasukanNumber.getText());
            totalPemasukanCell.setCellStyle(dataStyle);

            XSSFCell totalPengeluaranLabelCell = totalRow.createCell(4);
            totalPengeluaranLabelCell.setCellValue("Total Pengeluaran:");
            totalPengeluaranLabelCell.setCellStyle(headerTitleStyle);

            XSSFCell totalPengeluaranCell = totalRow.createCell(5);
            totalPengeluaranCell.setCellValue(totalPengeluaranNumber.getText());
            totalPengeluaranCell.setCellStyle(dataStyle);

            // Simpan workbook ke dalam file Excel
            FileOutputStream fileOut = new FileOutputStream(fileToSave);
            workbook.write(fileOut);
            fileOut.close();
            System.out.println("File Excel berhasil disimpan di: " + fileToSave.getAbsolutePath());
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

   





    
   
   
  
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainContent = new javax.swing.JPanel();
        editContainer = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        totalKeluarText = new javax.swing.JLabel();
        totalTsText = new javax.swing.JLabel();
        totalMasukText = new javax.swing.JLabel();
        totalPengeluaranNumber = new javax.swing.JLabel();
        totalTransaksiNumber = new javax.swing.JLabel();
        totalPemasukanNumber = new javax.swing.JLabel();
        greetDesc = new javax.swing.JLabel();
        labelNamaUser = new javax.swing.JLabel();
        greetText = new javax.swing.JLabel();
        editContainer19 = new javax.swing.JPanel();
        tableContainer = new javax.swing.JScrollPane();
        transaksiTable = new javax.swing.JTable();
        riwayat1 = new javax.swing.JLabel();
        cetakButton = new javax.swing.JPanel();
        cetakTexttoexcel = new javax.swing.JLabel();
        Sidebar = new javax.swing.JPanel();
        Logo = new javax.swing.JLabel();
        dashboardOption = new javax.swing.JPanel();
        dashboardText = new javax.swing.JLabel();
        dashboardIcon = new javax.swing.JLabel();
        catatOption = new javax.swing.JPanel();
        catatText = new javax.swing.JLabel();
        catatIcon = new javax.swing.JLabel();
        laporanOption = new javax.swing.JPanel();
        laporanText = new javax.swing.JLabel();
        laporanIcon = new javax.swing.JLabel();
        riwayatOption = new javax.swing.JPanel();
        riwayatText = new javax.swing.JLabel();
        riwayatIcon = new javax.swing.JLabel();
        logout = new javax.swing.JPanel();
        userPhoto = new javax.swing.JLabel();
        usernameProfile = new javax.swing.JLabel();
        logoutIcon = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mainContent.setBackground(new java.awt.Color(233, 233, 243));

        editContainer.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(249, 249, 249));
        jPanel1.setPreferredSize(new java.awt.Dimension(3, 100));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel7.setBackground(new java.awt.Color(249, 249, 249));
        jPanel7.setPreferredSize(new java.awt.Dimension(3, 100));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        totalKeluarText.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        totalKeluarText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalKeluarText.setText("Total Pengeluaran");

        totalTsText.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        totalTsText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalTsText.setText("Total Transaksi");

        totalMasukText.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        totalMasukText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalMasukText.setText("Total Pemasukkan");

        totalPengeluaranNumber.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        totalPengeluaranNumber.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalPengeluaranNumber.setText("0");

        totalTransaksiNumber.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        totalTransaksiNumber.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalTransaksiNumber.setText("0");

        totalPemasukanNumber.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        totalPemasukanNumber.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalPemasukanNumber.setText("0");

        javax.swing.GroupLayout editContainerLayout = new javax.swing.GroupLayout(editContainer);
        editContainer.setLayout(editContainerLayout);
        editContainerLayout.setHorizontalGroup(
            editContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(totalTsText, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(totalMasukText, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(editContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editContainerLayout.createSequentialGroup()
                        .addComponent(totalKeluarText, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editContainerLayout.createSequentialGroup()
                        .addComponent(totalPengeluaranNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))))
            .addGroup(editContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(editContainerLayout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(totalTransaksiNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(443, Short.MAX_VALUE)))
            .addGroup(editContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(editContainerLayout.createSequentialGroup()
                    .addGap(215, 215, 215)
                    .addComponent(totalPemasukanNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(239, Short.MAX_VALUE)))
        );
        editContainerLayout.setVerticalGroup(
            editContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editContainerLayout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(editContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(totalMasukText)
                    .addComponent(totalTsText)
                    .addGroup(editContainerLayout.createSequentialGroup()
                        .addComponent(totalKeluarText)
                        .addGap(27, 27, 27)
                        .addComponent(totalPengeluaranNumber))
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
            .addGroup(editContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editContainerLayout.createSequentialGroup()
                    .addContainerGap(67, Short.MAX_VALUE)
                    .addComponent(totalTransaksiNumber)
                    .addGap(53, 53, 53)))
            .addGroup(editContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editContainerLayout.createSequentialGroup()
                    .addContainerGap(67, Short.MAX_VALUE)
                    .addComponent(totalPemasukanNumber)
                    .addGap(53, 53, 53)))
        );

        greetDesc.setBackground(new java.awt.Color(0, 0, 0));
        greetDesc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        greetDesc.setText("Ini adalah rekapan hasil catatan kamu selama sebulan");

        labelNamaUser.setBackground(new java.awt.Color(0, 0, 0));
        labelNamaUser.setFont(new java.awt.Font("Helvetica Neue", 1, 36)); // NOI18N
        labelNamaUser.setText("Sutha!");

        greetText.setBackground(new java.awt.Color(0, 0, 0));
        greetText.setFont(new java.awt.Font("PT Sans", 0, 36)); // NOI18N
        greetText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        greetText.setText("Hai,");

        editContainer19.setBackground(new java.awt.Color(33, 25, 81));

        javax.swing.GroupLayout editContainer19Layout = new javax.swing.GroupLayout(editContainer19);
        editContainer19.setLayout(editContainer19Layout);
        editContainer19Layout.setHorizontalGroup(
            editContainer19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 293, Short.MAX_VALUE)
        );
        editContainer19Layout.setVerticalGroup(
            editContainer19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 142, Short.MAX_VALUE)
        );

        tableContainer.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tableContainerPropertyChange(evt);
            }
        });

        transaksiTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Kategori", "Tanggal", "Nominal", "Deskripsi"
            }
        ));
        transaksiTable.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                transaksiTableAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        transaksiTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                transaksiTableMouseClicked(evt);
            }
        });
        tableContainer.setViewportView(transaksiTable);

        riwayat1.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        riwayat1.setText("Riwayat Transaksi ( 1 bulan terakhir )");
        riwayat1.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentRemoved(java.awt.event.ContainerEvent evt) {
                riwayat1ComponentRemoved(evt);
            }
        });

        cetakButton.setBackground(new java.awt.Color(167, 201, 87));

        cetakTexttoexcel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cetakTexttoexcel.setForeground(new java.awt.Color(255, 255, 255));
        cetakTexttoexcel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cetakTexttoexcel.setText("Cetak");
        cetakTexttoexcel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cetakTexttoexcelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout cetakButtonLayout = new javax.swing.GroupLayout(cetakButton);
        cetakButton.setLayout(cetakButtonLayout);
        cetakButtonLayout.setHorizontalGroup(
            cetakButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cetakButtonLayout.createSequentialGroup()
                .addComponent(cetakTexttoexcel, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        cetakButtonLayout.setVerticalGroup(
            cetakButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cetakButtonLayout.createSequentialGroup()
                .addComponent(cetakTexttoexcel, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout mainContentLayout = new javax.swing.GroupLayout(mainContent);
        mainContent.setLayout(mainContentLayout);
        mainContentLayout.setHorizontalGroup(
            mainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainContentLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(mainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cetakButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(riwayat1)
                    .addGroup(mainContentLayout.createSequentialGroup()
                        .addComponent(greetText)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelNamaUser))
                    .addComponent(greetDesc)
                    .addGroup(mainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(mainContentLayout.createSequentialGroup()
                            .addComponent(editContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(18, 18, 18)
                            .addComponent(editContainer19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(tableContainer, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 930, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        mainContentLayout.setVerticalGroup(
            mainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainContentLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(mainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(greetText)
                    .addComponent(labelNamaUser))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(greetDesc)
                .addGap(18, 18, 18)
                .addGroup(mainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(editContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editContainer19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(riwayat1)
                .addGap(18, 18, 18)
                .addComponent(tableContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cetakButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(42, 42, 42))
        );

        getContentPane().add(mainContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 0, 980, 770));

        Sidebar.setBackground(new java.awt.Color(33, 25, 81));

        Logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/finansaku/assets/FinanSaku_1_-removebg-preview 1 (1).png"))); // NOI18N

        dashboardOption.setBackground(new java.awt.Color(33, 25, 81));
        dashboardOption.setForeground(new java.awt.Color(255, 255, 255));

        dashboardText.setBackground(new java.awt.Color(166, 163, 185));
        dashboardText.setFont(new java.awt.Font("PT Sans", 0, 16)); // NOI18N
        dashboardText.setForeground(new java.awt.Color(166, 163, 185));
        dashboardText.setText("Dashboard");

        dashboardIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/finansaku/assets/dashboardIcon.png"))); // NOI18N

        javax.swing.GroupLayout dashboardOptionLayout = new javax.swing.GroupLayout(dashboardOption);
        dashboardOption.setLayout(dashboardOptionLayout);
        dashboardOptionLayout.setHorizontalGroup(
            dashboardOptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardOptionLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(dashboardIcon)
                .addGap(27, 27, 27)
                .addComponent(dashboardText)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dashboardOptionLayout.setVerticalGroup(
            dashboardOptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardOptionLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(dashboardText, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(dashboardIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        catatOption.setBackground(new java.awt.Color(33, 25, 81));
        catatOption.setForeground(new java.awt.Color(255, 255, 255));

        catatText.setFont(new java.awt.Font("PT Sans", 0, 16)); // NOI18N
        catatText.setForeground(new java.awt.Color(166, 163, 185));
        catatText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        catatText.setText("Catat Transaksi");

        catatIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/finansaku/assets/Rectangle 5(1).png"))); // NOI18N

        javax.swing.GroupLayout catatOptionLayout = new javax.swing.GroupLayout(catatOption);
        catatOption.setLayout(catatOptionLayout);
        catatOptionLayout.setHorizontalGroup(
            catatOptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, catatOptionLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(catatIcon)
                .addGap(27, 27, 27)
                .addComponent(catatText)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        catatOptionLayout.setVerticalGroup(
            catatOptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(catatIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(catatText, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );

        laporanOption.setBackground(new java.awt.Color(55, 48, 98));
        laporanOption.setForeground(new java.awt.Color(255, 255, 255));

        laporanText.setFont(new java.awt.Font("PT Sans", 1, 16)); // NOI18N
        laporanText.setForeground(new java.awt.Color(255, 255, 255));
        laporanText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        laporanText.setText("Laporan");

        laporanIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/finansaku/assets/Rectangle 5(2).png"))); // NOI18N

        javax.swing.GroupLayout laporanOptionLayout = new javax.swing.GroupLayout(laporanOption);
        laporanOption.setLayout(laporanOptionLayout);
        laporanOptionLayout.setHorizontalGroup(
            laporanOptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(laporanOptionLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(laporanIcon)
                .addGap(27, 27, 27)
                .addComponent(laporanText)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        laporanOptionLayout.setVerticalGroup(
            laporanOptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(laporanIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(laporanText, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );

        riwayatOption.setBackground(new java.awt.Color(33, 25, 81));
        riwayatOption.setForeground(new java.awt.Color(255, 255, 255));

        riwayatText.setBackground(new java.awt.Color(166, 163, 185));
        riwayatText.setFont(new java.awt.Font("PT Sans", 0, 16)); // NOI18N
        riwayatText.setForeground(new java.awt.Color(166, 163, 185));
        riwayatText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        riwayatText.setText("Riwayat Transaksi");
        riwayatText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                riwayatTextMouseClicked(evt);
            }
        });

        riwayatIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/finansaku/assets/Rectangle 5.png"))); // NOI18N

        javax.swing.GroupLayout riwayatOptionLayout = new javax.swing.GroupLayout(riwayatOption);
        riwayatOption.setLayout(riwayatOptionLayout);
        riwayatOptionLayout.setHorizontalGroup(
            riwayatOptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, riwayatOptionLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(riwayatIcon)
                .addGap(27, 27, 27)
                .addComponent(riwayatText)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        riwayatOptionLayout.setVerticalGroup(
            riwayatOptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(riwayatText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(riwayatIcon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        logout.setBackground(new java.awt.Color(55, 48, 98));

        userPhoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/finansaku/assets/Ellipse 1.png"))); // NOI18N
        userPhoto.setText(" ");

        usernameProfile.setFont(new java.awt.Font("PT Sans", 1, 16)); // NOI18N
        usernameProfile.setForeground(new java.awt.Color(255, 255, 255));
        usernameProfile.setText("Sutha");

        logoutIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/finansaku/assets/Rectangle 8.png"))); // NOI18N

        javax.swing.GroupLayout logoutLayout = new javax.swing.GroupLayout(logout);
        logout.setLayout(logoutLayout);
        logoutLayout.setHorizontalGroup(
            logoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logoutLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(userPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(usernameProfile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logoutIcon)
                .addGap(21, 21, 21))
        );
        logoutLayout.setVerticalGroup(
            logoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(logoutIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(logoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(userPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(usernameProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout SidebarLayout = new javax.swing.GroupLayout(Sidebar);
        Sidebar.setLayout(SidebarLayout);
        SidebarLayout.setHorizontalGroup(
            SidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dashboardOption, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(riwayatOption, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SidebarLayout.createSequentialGroup()
                .addGroup(SidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(SidebarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(logout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, SidebarLayout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(Logo, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 59, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(catatOption, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(laporanOption, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        SidebarLayout.setVerticalGroup(
            SidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SidebarLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(Logo, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(dashboardOption, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(riwayatOption, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(catatOption, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(laporanOption, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 211, Short.MAX_VALUE)
                .addComponent(logout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(Sidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 390, 770));

        setSize(new java.awt.Dimension(1382, 807));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void riwayatTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_riwayatTextMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_riwayatTextMouseClicked

    private void riwayat1ComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_riwayat1ComponentRemoved
        // TODO add your handling code here:
    }//GEN-LAST:event_riwayat1ComponentRemoved

    private void transaksiTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_transaksiTableMouseClicked
        // TODO add your handling code here:
       
    }//GEN-LAST:event_transaksiTableMouseClicked

    private void transaksiTableAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_transaksiTableAncestorAdded
        // TODO add your handling code here:
          retrieveTransactions();
    }//GEN-LAST:event_transaksiTableAncestorAdded

    private void tableContainerPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tableContainerPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tableContainerPropertyChange

    private void cetakTexttoexcelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cetakTexttoexcelMouseClicked
        // TODO add your handling code here:
       writeDataToExcel();
    }//GEN-LAST:event_cetakTexttoexcelMouseClicked
     

      
    
   
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(laporanSaku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(laporanSaku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(laporanSaku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(laporanSaku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new laporanSaku("Username").setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Logo;
    private javax.swing.JPanel Sidebar;
    private javax.swing.JLabel catatIcon;
    private javax.swing.JPanel catatOption;
    private javax.swing.JLabel catatText;
    private javax.swing.JPanel cetakButton;
    private javax.swing.JLabel cetakTexttoexcel;
    private javax.swing.JLabel dashboardIcon;
    private javax.swing.JPanel dashboardOption;
    private javax.swing.JLabel dashboardText;
    private javax.swing.JPanel editContainer;
    private javax.swing.JPanel editContainer19;
    private javax.swing.JLabel greetDesc;
    private javax.swing.JLabel greetText;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JLabel labelNamaUser;
    private javax.swing.JLabel laporanIcon;
    private javax.swing.JPanel laporanOption;
    private javax.swing.JLabel laporanText;
    private javax.swing.JPanel logout;
    private javax.swing.JLabel logoutIcon;
    private javax.swing.JPanel mainContent;
    private javax.swing.JLabel riwayat1;
    private javax.swing.JLabel riwayatIcon;
    private javax.swing.JPanel riwayatOption;
    private javax.swing.JLabel riwayatText;
    private javax.swing.JScrollPane tableContainer;
    private javax.swing.JLabel totalKeluarText;
    private javax.swing.JLabel totalMasukText;
    private javax.swing.JLabel totalPemasukanNumber;
    private javax.swing.JLabel totalPengeluaranNumber;
    private javax.swing.JLabel totalTransaksiNumber;
    private javax.swing.JLabel totalTsText;
    private javax.swing.JTable transaksiTable;
    private javax.swing.JLabel userPhoto;
    private javax.swing.JLabel usernameProfile;
    // End of variables declaration//GEN-END:variables
}
