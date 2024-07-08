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
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author odygunadiksa
 */
public class riwayatSaku extends javax.swing.JFrame {
         private String loggedInUsername;
         private int userId;
         private Connection conn;
            
       
            /**
         * Creates new form berandaSaku
         */
        public riwayatSaku(String username) {
            initComponents();
            this.loggedInUsername = username; 
            usernameProfile.setText(loggedInUsername);
            this.userId = userId;
           retrieveTransactions(); 
            
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

            while (resultSet.next()) {
                Object[] row = {
                     resultSet.getString("jenis"),
                    resultSet.getDate("tanggal"),
                    resultSet.getDouble("jumlah"),
                    resultSet.getString("deskripsi")
                };
                model.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat mengambil data transaksi: " + ex.getMessage());
        }
    }
         
         
       public void filterTransactions(String option) {
    try {
        Connection conn = koneksi.koneksi();
        System.out.println("Option selected: " + option); // Debugging

        String query = ""; // Query SQL untuk filtering transaksi sesuai dengan opsi yang dipilih

        // Berdasarkan opsi yang dipilih, atur query SQL untuk filtering
        switch (option) {
            case "Terbaru":
                query = "SELECT t.jenis, t.tanggal, t.jumlah, t.deskripsi " +
                        "FROM Transaksi t " +
                        "WHERE t.id_pengguna = ? " +
                        "ORDER BY t.tanggal DESC";
                break;
            case "Terlama":
                query = "SELECT t.jenis, t.tanggal, t.jumlah, t.deskripsi " +
                        "FROM Transaksi t " +
                        "WHERE t.id_pengguna = ? " +
                        "ORDER BY t.tanggal ASC";
                break;
            case "Pengeluaran":
                query = "SELECT t.jenis, t.tanggal, t.jumlah, t.deskripsi " +
                        "FROM Transaksi t " +
                        "WHERE t.id_pengguna = ? AND t.jenis = 'pengeluaran' " +
                        "ORDER BY t.jumlah DESC";
                break;
            case "Pemasukkan":
                query = "SELECT t.jenis, t.tanggal, t.jumlah, t.deskripsi " +
                        "FROM Transaksi t " +
                        "WHERE t.id_pengguna = ? " +
                        "ORDER BY t.jumlah ASC";
                break;
            case "Terbesar":
                query = "SELECT t.jenis, t.tanggal, t.jumlah, t.deskripsi " +
                        "FROM Transaksi t " +
                        "WHERE t.id_pengguna = ? " +
                        "ORDER BY t.jumlah DESC";
                break;
            case "Terkecil":
                query = "SELECT t.jenis, t.tanggal, t.jumlah, t.deskripsi " +
                        "FROM Transaksi t " +
                        "WHERE t.id_pengguna = ? " +
                        "ORDER BY t.jumlah ASC";
                break;
            default:
                // Default query
                query = "SELECT t.jenis, t.tanggal, t.jumlah, t.deskripsi " +
                        "FROM Transaksi t " +
                        "WHERE t.id_pengguna = ?";
                break;
        }

        System.out.println("Query: " + query); // Debugging

        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, loggedInUserId); // Pastikan loggedInUserId telah diinisialisasi sebelumnya
        ResultSet resultSet = statement.executeQuery();

        DefaultTableModel model = (DefaultTableModel) transaksiTable.getModel();
        model.setRowCount(0); // Bersihkan tabel sebelum menambahkan data baru

        while (resultSet.next()) {
            Object[] row = {
                resultSet.getString("jenis"),
                resultSet.getDate("tanggal"),
                resultSet.getDouble("jumlah"),
                resultSet.getString("deskripsi")
            };
            model.addRow(row);
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat filtering data transaksi: " + ex.getMessage());
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
        riwayat = new javax.swing.JLabel();
        urutkanText = new javax.swing.JLabel();
        urutkanPil = new javax.swing.JComboBox();
        hapusButton = new javax.swing.JPanel();
        hapusText = new javax.swing.JLabel();
        editContainer = new javax.swing.JPanel();
        kategori = new javax.swing.JLabel();
        tfKatagoriEdit = new javax.swing.JComboBox();
        nominal = new javax.swing.JLabel();
        tfNominalEdit = new javax.swing.JFormattedTextField();
        date = new javax.swing.JLabel();
        TfTanggalEdit = new javax.swing.JFormattedTextField();
        deskripsi = new javax.swing.JLabel();
        TfDeskripsiEdit = new javax.swing.JTextField();
        editTransaksi = new javax.swing.JLabel();
        editButton = new javax.swing.JPanel();
        simpanBTn = new javax.swing.JLabel();
        tableContainer = new javax.swing.JScrollPane();
        transaksiTable = new javax.swing.JTable();
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

        riwayat.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        riwayat.setText("Riwayat Transaksi");

        urutkanText.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        urutkanText.setText("Urut Berdasarkan");

        urutkanPil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Terbaru", "Terlama", "Pengeluaran", "Pemasukkan", "Terbesar", "Terkecil" }));
        urutkanPil.setBorder(null);
        urutkanPil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                urutkanPilActionPerformed(evt);
            }
        });

        hapusButton.setBackground(new java.awt.Color(255, 126, 165));

        hapusText.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        hapusText.setForeground(new java.awt.Color(255, 255, 255));
        hapusText.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/finansaku/assets/delete 1.png"))); // NOI18N
        hapusText.setText("Hapus");

        javax.swing.GroupLayout hapusButtonLayout = new javax.swing.GroupLayout(hapusButton);
        hapusButton.setLayout(hapusButtonLayout);
        hapusButtonLayout.setHorizontalGroup(
            hapusButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, hapusButtonLayout.createSequentialGroup()
                .addContainerGap(57, Short.MAX_VALUE)
                .addComponent(hapusText)
                .addGap(52, 52, 52))
        );
        hapusButtonLayout.setVerticalGroup(
            hapusButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(hapusText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
        );

        editContainer.setBackground(new java.awt.Color(255, 255, 255));

        kategori.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        kategori.setText("Kategori");

        tfKatagoriEdit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tfKatagoriEdit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pemasukkan", "Pengeluaran" }));

        nominal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        nominal.setText("Nominal");

        tfNominalEdit.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00;(#,##0.00)"))));
        tfNominalEdit.setPreferredSize(new java.awt.Dimension(105, 23));

        date.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        date.setText("Tanggal");

        TfTanggalEdit.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM))));
        TfTanggalEdit.setText("Mar 9, 2024");
        TfTanggalEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TfTanggalEditActionPerformed(evt);
            }
        });

        deskripsi.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        deskripsi.setText("Deskripsi");

        TfDeskripsiEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TfDeskripsiEditActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout editContainerLayout = new javax.swing.GroupLayout(editContainer);
        editContainer.setLayout(editContainerLayout);
        editContainerLayout.setHorizontalGroup(
            editContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editContainerLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(editContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kategori)
                    .addComponent(tfKatagoriEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(editContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editContainerLayout.createSequentialGroup()
                        .addComponent(date)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(TfTanggalEdit, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(editContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nominal)
                    .addComponent(tfNominalEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(editContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(deskripsi)
                    .addComponent(TfDeskripsiEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49))
        );
        editContainerLayout.setVerticalGroup(
            editContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(editContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editContainerLayout.createSequentialGroup()
                        .addComponent(kategori)
                        .addGap(12, 12, 12)
                        .addComponent(tfKatagoriEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(editContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(TfTanggalEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(editContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(editContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(nominal)
                                .addComponent(date))
                            .addGroup(editContainerLayout.createSequentialGroup()
                                .addComponent(deskripsi)
                                .addGap(11, 11, 11)
                                .addGroup(editContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(TfDeskripsiEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfNominalEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        editTransaksi.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        editTransaksi.setText("Edit Transaksi");

        editButton.setBackground(new java.awt.Color(167, 201, 87));
        editButton.setPreferredSize(new java.awt.Dimension(175, 44));

        simpanBTn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        simpanBTn.setForeground(new java.awt.Color(255, 255, 255));
        simpanBTn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        simpanBTn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/finansaku/assets/edit 1.png"))); // NOI18N
        simpanBTn.setText("Edit");
        simpanBTn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                simpanBTnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout editButtonLayout = new javax.swing.GroupLayout(editButton);
        editButton.setLayout(editButtonLayout);
        editButtonLayout.setHorizontalGroup(
            editButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(simpanBTn, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
        );
        editButtonLayout.setVerticalGroup(
            editButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(simpanBTn, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                .addContainerGap())
        );

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
        transaksiTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                transaksiTableMouseClicked(evt);
            }
        });
        tableContainer.setViewportView(transaksiTable);

        javax.swing.GroupLayout mainContentLayout = new javax.swing.GroupLayout(mainContent);
        mainContent.setLayout(mainContentLayout);
        mainContentLayout.setHorizontalGroup(
            mainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainContentLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(mainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(mainContentLayout.createSequentialGroup()
                            .addComponent(riwayat)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(urutkanText)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(urutkanPil, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(mainContentLayout.createSequentialGroup()
                            .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(hapusButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(editContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(editTransaksi))
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(mainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(mainContentLayout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(tableContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 940, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(20, Short.MAX_VALUE)))
        );
        mainContentLayout.setVerticalGroup(
            mainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainContentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(riwayat)
                    .addComponent(urutkanText)
                    .addComponent(urutkanPil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 505, Short.MAX_VALUE)
                .addComponent(editTransaksi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(editContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(mainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hapusButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
            .addGroup(mainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(mainContentLayout.createSequentialGroup()
                    .addGap(29, 29, 29)
                    .addComponent(tableContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(249, Short.MAX_VALUE)))
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
        dashboardText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashboardTextMouseClicked(evt);
            }
        });

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
        catatText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                catatTextMouseClicked(evt);
            }
        });

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

        laporanOption.setBackground(new java.awt.Color(33, 25, 81));
        laporanOption.setForeground(new java.awt.Color(255, 255, 255));

        laporanText.setFont(new java.awt.Font("PT Sans", 0, 16)); // NOI18N
        laporanText.setForeground(new java.awt.Color(166, 163, 185));
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

        riwayatOption.setBackground(new java.awt.Color(55, 48, 98));
        riwayatOption.setForeground(new java.awt.Color(255, 255, 255));

        riwayatText.setBackground(new java.awt.Color(166, 163, 185));
        riwayatText.setFont(new java.awt.Font("PT Sans", 1, 16)); // NOI18N
        riwayatText.setForeground(new java.awt.Color(255, 255, 255));
        riwayatText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        riwayatText.setText("Riwayat Transaksi");
        riwayatText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                riwayatTextMouseClicked(evt);
            }
        });

        riwayatIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/finansaku/assets/historyIcon.png"))); // NOI18N

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
                        .addGap(0, 63, Short.MAX_VALUE)))
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

        setSize(new java.awt.Dimension(1398, 807));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void riwayatTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_riwayatTextMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_riwayatTextMouseClicked

    private void TfTanggalEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TfTanggalEditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TfTanggalEditActionPerformed

    private void TfDeskripsiEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TfDeskripsiEditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TfDeskripsiEditActionPerformed

    private void urutkanPilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_urutkanPilActionPerformed
        // TODO add your handling code here:
         String selectedOption = (String) urutkanPil.getSelectedItem();
          filterTransactions(selectedOption);
    }//GEN-LAST:event_urutkanPilActionPerformed

    private void simpanBTnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_simpanBTnMouseClicked
       
       int selectedRow = transaksiTable.getSelectedRow();

    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Pilih transaksi yang ingin diubah.");
        return;
    }

   
    String jenisBaru = tfKatagoriEdit.getSelectedItem().toString();
    String tanggalBaru = TfTanggalEdit.getText();
    String jumlahBaru = tfNominalEdit.getText();
    String deskripsiBaru = TfDeskripsiEdit.getText();

    
    DefaultTableModel model = (DefaultTableModel) transaksiTable.getModel();
    model.setValueAt(jenisBaru, selectedRow, 0);
    model.setValueAt(tanggalBaru, selectedRow, 1);
    model.setValueAt(jumlahBaru, selectedRow, 2);
    model.setValueAt(deskripsiBaru, selectedRow, 3);

    
    JOptionPane.showMessageDialog(this, "Transaksi berhasil diubah.");
      transaksiTable.clearSelection();
   
    }//GEN-LAST:event_simpanBTnMouseClicked

    private void catatTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_catatTextMouseClicked
        // TODO add your handling code here:
         catatSaku catatFrame = new catatSaku(loggedInUsername); 
        catatFrame.setVisible(true); 
        this.dispose();
    }//GEN-LAST:event_catatTextMouseClicked

    private void dashboardTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashboardTextMouseClicked
        // TODO add your handling code here:
        berandaSaku BerandaFrame = new berandaSaku(loggedInUsername); 
        BerandaFrame.setVisible(true); 
        this.dispose();
    }//GEN-LAST:event_dashboardTextMouseClicked

    private void transaksiTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_transaksiTableMouseClicked
        // TODO add your handling code here:
        int selectedRow = transaksiTable.getSelectedRow();

        // Mendapatkan nilai dari setiap kolom dalam baris yang dipilih
        String jenis = transaksiTable.getValueAt(selectedRow, 0).toString();
        String tanggal = transaksiTable.getValueAt(selectedRow, 1).toString();
        String jumlah = transaksiTable.getValueAt(selectedRow, 2).toString();
        String deskripsi = transaksiTable.getValueAt(selectedRow, 3).toString();

        // Menetapkan nilai-nilai ini ke komponen teks atau area teks yang sesuai
        tfKatagoriEdit.setSelectedItem(jenis);
        TfTanggalEdit.setText(tanggal);
        tfNominalEdit.setText(jumlah);
        TfDeskripsiEdit.setText(deskripsi);
    }//GEN-LAST:event_transaksiTableMouseClicked
               

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
                java.util.logging.Logger.getLogger(riwayatSaku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(riwayatSaku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(riwayatSaku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(riwayatSaku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>
            //</editor-fold>
            //</editor-fold>
            //</editor-fold>

            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
            new riwayatSaku("Username").setVisible(true);
        }
    });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Logo;
    private javax.swing.JPanel Sidebar;
    private javax.swing.JTextField TfDeskripsiEdit;
    private javax.swing.JFormattedTextField TfTanggalEdit;
    private javax.swing.JLabel catatIcon;
    private javax.swing.JPanel catatOption;
    private javax.swing.JLabel catatText;
    private javax.swing.JLabel dashboardIcon;
    private javax.swing.JPanel dashboardOption;
    private javax.swing.JLabel dashboardText;
    private javax.swing.JLabel date;
    private javax.swing.JLabel deskripsi;
    private javax.swing.JPanel editButton;
    private javax.swing.JPanel editContainer;
    private javax.swing.JLabel editTransaksi;
    private javax.swing.JPanel hapusButton;
    private javax.swing.JLabel hapusText;
    private javax.swing.JLabel kategori;
    private javax.swing.JLabel laporanIcon;
    private javax.swing.JPanel laporanOption;
    private javax.swing.JLabel laporanText;
    private javax.swing.JPanel logout;
    private javax.swing.JLabel logoutIcon;
    private javax.swing.JPanel mainContent;
    private javax.swing.JLabel nominal;
    private javax.swing.JLabel riwayat;
    private javax.swing.JLabel riwayatIcon;
    private javax.swing.JPanel riwayatOption;
    private javax.swing.JLabel riwayatText;
    private javax.swing.JLabel simpanBTn;
    private javax.swing.JScrollPane tableContainer;
    private javax.swing.JComboBox tfKatagoriEdit;
    private javax.swing.JFormattedTextField tfNominalEdit;
    private javax.swing.JTable transaksiTable;
    private javax.swing.JComboBox urutkanPil;
    private javax.swing.JLabel urutkanText;
    private javax.swing.JLabel userPhoto;
    private javax.swing.JLabel usernameProfile;
    // End of variables declaration//GEN-END:variables
}
