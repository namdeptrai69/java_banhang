/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import OBJ.HoaDon;
import OBJ.KhachHang;
import OBJ.SanPham;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author tuanc
 */
public class frmBanHang extends javax.swing.JPanel {

    /**
     * Creates new form frmBanHang
     */
    private DefaultTableModel defaultTableModel;
    KhachHang kh = new KhachHang();
    SanPham sp = new SanPham();
    ArrayList<HoaDon> listGioHang = new ArrayList<>();
    NumberFormat formatter = new DecimalFormat("###,###");
    int index;
//    frmDangNhap dn = new frmDangNhap();

    public frmBanHang() {
        initComponents();
        getDataKhachHang(loadKH());
        getDataSanPham(loadSP());
        
        jlb_nhanVien.setText(frmDangNhap.maDN);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    public ArrayList<KhachHang> loadKH() {
        frmKhachHang frm_KH = new frmKhachHang();
        frm_KH.loadFile();
        return frm_KH.listKH;
    }

    public ArrayList<SanPham> loadSP() {
        frmSanPham frm_SP = new frmSanPham();
        frm_SP.loadFile();
        return frm_SP.listSP;

    }

    public void maHienThi() {
        jlb_nhanVien.setText(frmDangNhap.maDN);
    }

    public void saveFile() {

        try {
            BufferedWriter bw = null; //Khởi tạo
            FileWriter fw = null; //Khởi tạo
            String data = ""; //Tạo một string data bằng rỗng.

            for (int i = 0; i < listGioHang.size(); i++) {
                String row = ""; //tạo hàng rỗng
                row = row + listGioHang.get(i).getMaHD() + "\t";
                row = row + listGioHang.get(i).getMaNV() + "\t";
                row = row + listGioHang.get(i).getMaKH() + "\t";
                row = row + listGioHang.get(i).getMaSP() + "\t";
                row = row + listGioHang.get(i).getSoLuong() + "\t";
                row = row + listGioHang.get(i).getDonGia() + "\t";
                row = row + listGioHang.get(i).getThanhTien() + "\t";
                row = row + listGioHang.get(i).getNgayBan() + "\n";
                data += row;
            }
            fw = new FileWriter("hoadon.txt", true);
            bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(frmBanHang.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void getDataSanPham(ArrayList<SanPham> listSP) {
        defaultTableModel = new DefaultTableModel();
        defaultTableModel.addColumn("Mã SP");
        defaultTableModel.addColumn("Tên SP");
        defaultTableModel.addColumn("Đơn vị tính");
        defaultTableModel.addColumn("Tên NSX");
        defaultTableModel.addColumn("Giá bán");
        defaultTableModel.addColumn("Giá nhập");
        defaultTableModel.addColumn("Số lượng");

        for (SanPham obj : listSP) {
            Vector vector = new Vector();
            vector.add(obj.getMaSanPham());
            vector.add(obj.getTenSP());
            vector.add(obj.getDonViTinh());
            vector.add(obj.getTenNSX());
            vector.add(obj.getGiaBan());
            vector.add(obj.getGiaNhap());
            vector.add(obj.getSoLuong());
            defaultTableModel.addRow(vector);
        }
        bangsanpham.setModel(defaultTableModel);
    }

    private void getDataKhachHang(ArrayList<KhachHang> listKH) {
        defaultTableModel = new DefaultTableModel();
        defaultTableModel.addColumn("Mã KH");
        defaultTableModel.addColumn("Họ tên HK");
        defaultTableModel.addColumn("Giới tính");
        defaultTableModel.addColumn("Email");
        defaultTableModel.addColumn("SĐT");
        defaultTableModel.addColumn("Địa chỉ");
        defaultTableModel.addColumn("Trạng Thái");

        for (KhachHang obj : listKH) {
            Vector vector = new Vector();
            vector.add(obj.getMaKhachHang());
            vector.add(obj.getHoTen());
            vector.add(obj.getGioiTinh());
            vector.add(obj.getEmail());
            vector.add(obj.getSoDienThoai());
            vector.add(obj.getDiaChi());
            vector.add(obj.getTrangThai() == 1 ? "Hoạt động" : "Đã khóa");
            defaultTableModel.addRow(vector);
        }
        bangkhachhang.setModel(defaultTableModel);
    }

    public SanPham getSPByCode(String maSP) {
        ArrayList<SanPham> listSP = loadSP();
        for (int i = 0; i < listSP.size(); i++) {
            if (maSP.equals(listSP.get(i).getMaSanPham())) {
                return listSP.get(i);
            }
        }
        return null;
    }

    public void loadGioHang(ArrayList<HoaDon> listHD) {
        defaultTableModel = new DefaultTableModel();
        defaultTableModel.addColumn("Mã SP");
        defaultTableModel.addColumn("Tên sản phẩm");
        defaultTableModel.addColumn("ĐV tính");
        defaultTableModel.addColumn("SL mua");
        defaultTableModel.addColumn("Đơn giá");
        defaultTableModel.addColumn("Thành tiền");
        for (HoaDon hd : listHD) {
            frmBanHang frmBanHang = new frmBanHang();
            SanPham sp = frmBanHang.getSPByCode(hd.getMaSP());
            Vector vector = new Vector();
            vector.add(hd.getMaSP());
            vector.add(sp.getTenSP());
            vector.add(sp.getDonViTinh());
            vector.add(formatter.format(hd.getSoLuong()));
            vector.add(formatter.format(hd.getDonGia()));
            vector.add(formatter.format(hd.getThanhTien()));
            defaultTableModel.addRow(vector);
        }
        banggiohang.setModel(defaultTableModel);
        if (listHD.size() > 0) {
            thanhToan.setEnabled(true);
        } else {
            thanhToan.setEnabled(false);
        }
    }

    int indexMaSPTrungTrongGioHang;

    public boolean checkSPGioHang(String maSP, ArrayList<HoaDon> listHD) {
        for (int i = 0; i < listHD.size(); i++) {
            if (maSP.equals(listHD.get(i).getMaSP())) {
                indexMaSPTrungTrongGioHang = i;
                return true;
            }
        }
        return false;
    }

//    public void sendmail(String tenSP, int tien, String emailKH) {
//        try {
//            Email email = new SimpleEmail();
//
//            // Cấu hình thông tin Email Server
//            email.setHostName("smtp.googlemail.com");
//            email.setSmtpPort(465);
//            email.setAuthenticator(new DefaultAuthenticator("lamhoa6699@gmail.com", "matkhauemailcuaban")); //Nhớ nhập đúng với tài khoản thật nhé :))
//
//            // Với gmail cái này là bắt buộc.
//            email.setSSLOnConnect(true);
//
//            // Người gửi
//            email.setFrom("lamhoa6699@gmail.com", "shop@shop.com");
//
//            // Tiêu đề
//            email.setSubject("EMAIL RESET PASSWORD"); //Tiêu đề khi gửi email
//
//            // Nội dung email
////            String covert = String.valueOf(rand);
//            email.setMsg("Cam on ban da mua san pham " + tenSP + " voi gia tien " + tien); //Nội dung email bạn muốn gửi.
//            // Người nhận
//            email.addTo(emailKH); //Đia chỉ email người nhận
//            email.send(); //Thực hiện gửi.
//            System.err.println("Gửi email thành công ! Vui lòng kiểm tra email !");
//            System.out.println("\n");
//        } catch (Exception e) {
//            System.out.println("Gửi không thành công !");
//        }
//    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        thanhToan = new javax.swing.JButton();
        Thoat = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txt_timKiemKhachHang = new javax.swing.JTextField();
        timKiemKhachHang = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        bangkhachhang = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txt_timKiemSanPham = new javax.swing.JTextField();
        timKiemSanPham = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        bangsanpham = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        themVaoGioHang = new javax.swing.JButton();
        txt_soLuong = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        banggiohang = new javax.swing.JTable();
        xoaGioHang = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txt_maKhachHang = new javax.swing.JLabel();
        txt_sdt = new javax.swing.JLabel();
        txt_diaChi = new javax.swing.JLabel();
        txt_hoTen = new javax.swing.JLabel();
        txt_email = new javax.swing.JLabel();
        txt_tongTien = new javax.swing.JLabel();
        jlb_nhanVien = new javax.swing.JLabel();

        setLayout(null);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel2.setText("Zent coding school");
        add(jLabel2);
        jLabel2.setBounds(47, 11, 105, 13);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/content-writing.png"))); // NOI18N
        jLabel1.setText("Hóa Đơn Bán Hàng");
        add(jLabel1);
        jLabel1.setBounds(410, 11, 193, 24);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jLabel3.setText("Nhân Viên: ");
        add(jLabel3);
        jLabel3.setBounds(794, 11, 70, 13);

        thanhToan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/shopping-cart.png"))); // NOI18N
        thanhToan.setText("Thanh Toán");
        thanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thanhToanActionPerformed(evt);
            }
        });
        add(thanhToan);
        thanhToan.setBounds(659, 491, 117, 33);

        Thoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/turn-on.png"))); // NOI18N
        Thoat.setText("Thoát");
        Thoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThoatActionPerformed(evt);
            }
        });
        add(Thoat);
        Thoat.setBounds(833, 491, 108, 33);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setText("Khách Hàng");

        timKiemKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Magnifier-icon.png"))); // NOI18N
        timKiemKhachHang.setText("Tìm Kiếm");
        timKiemKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timKiemKhachHangActionPerformed(evt);
            }
        });

        bangkhachhang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        bangkhachhang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bangkhachhangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(bangkhachhang);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(txt_timKiemKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(timKiemKhachHang)
                .addGap(55, 55, 55))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_timKiemKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timKiemKhachHang))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(jPanel1);
        jPanel1.setBounds(18, 60, 482, 226);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setText("Sản Phẩm");

        timKiemSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Magnifier-icon.png"))); // NOI18N
        timKiemSanPham.setText("Tìm Kiếm");
        timKiemSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timKiemSanPhamActionPerformed(evt);
            }
        });

        bangsanpham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        bangsanpham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bangsanphamMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(bangsanpham);

        jLabel15.setText("Số Lượng:");

        themVaoGioHang.setText("Thêm Vào Giỏ Hàng");
        themVaoGioHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themVaoGioHangActionPerformed(evt);
            }
        });

        txt_soLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_soLuongActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(txt_timKiemSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(timKiemSanPham)
                .addGap(51, 51, 51))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addComponent(txt_soLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(themVaoGioHang)
                .addGap(64, 64, 64))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_timKiemSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timKiemSanPham))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(themVaoGioHang)
                    .addComponent(txt_soLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(jPanel2);
        jPanel2.setBounds(18, 289, 482, 240);

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setText("Thông Tin Hóa Đơn");

        jLabel7.setText("Mã KH:");

        jLabel8.setText("Số Điện Thoại:");

        jLabel9.setText("Địa Chỉ:");

        jLabel10.setText("Họ Và Tên:");

        jLabel11.setText("Email:");

        jLabel12.setText("Giỏ Hàng:");

        banggiohang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        banggiohang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                banggiohangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(banggiohang);

        xoaGioHang.setText("Xóa Giỏ Hàng");
        xoaGioHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoaGioHangActionPerformed(evt);
            }
        });

        jLabel13.setText("Tổng Tiền:");

        jLabel14.setText("VND");

        txt_maKhachHang.setText("...........................");

        txt_sdt.setText("...........................");

        txt_diaChi.setText("...........................");

        txt_hoTen.setText("...........................");

        txt_email.setText("...........................");

        txt_tongTien.setText("...........................");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel12))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(xoaGioHang)
                .addGap(68, 68, 68))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(205, 205, 205)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(txt_tongTien)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addGap(30, 30, 30))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(38, 38, 38)
                        .addComponent(txt_diaChi)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_maKhachHang)
                            .addComponent(txt_sdt))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_email))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addComponent(txt_hoTen)))
                        .addGap(64, 64, 64))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel10)
                    .addComponent(txt_maKhachHang)
                    .addComponent(txt_hoTen))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txt_sdt)
                    .addComponent(txt_email)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txt_diaChi))
                .addGap(35, 35, 35)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(xoaGioHang)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(txt_tongTien))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        add(jPanel3);
        jPanel3.setBounds(509, 60, 479, 396);
        add(jlb_nhanVien);
        jlb_nhanVien.setBounds(860, 10, 80, 20);
    }// </editor-fold>//GEN-END:initComponents

    private void ThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ThoatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ThoatActionPerformed
    int tongTien;
    
    public int soLuongSPTrongListGioHang(String maSP){
        for (int i = 0; i < listGioHang.size(); i++) {
            if (maSP.equals(listGioHang.get(i).getMaSP())) {
                return listGioHang.get(i).getSoLuong();
            }
        }
        return 0;
    }
    
    private void themVaoGioHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themVaoGioHangActionPerformed
        //lấy ra số lượng cần mua
        int soLuongMua = Integer.parseInt(txt_soLuong.getText());
        
        if (soLuongMua <= 0) {
            JOptionPane.showMessageDialog(this, "Số lượng sản phẩm phải lớn hơn 0 !");
        } else if (soLuongMua + soLuongSPTrongListGioHang(sp.getMaSanPham()) > Integer.parseInt(sp.getSoLuong())) {
            JOptionPane.showMessageDialog(this, "Số lượng sản phẩm lớn hơn trong kho !");
        } else {
            //thỏa mãn
            HoaDon hd = new HoaDon();
            hd.setMaSP(sp.getMaSanPham());
            hd.setSoLuong(soLuongMua);
            hd.setDonGia(Double.parseDouble(sp.getGiaBan()));
            hd.setThanhTien(soLuongMua * Double.parseDouble(sp.getGiaBan()));
            if (checkSPGioHang(hd.getMaSP(), listGioHang)) {
                HoaDon hoaDon = listGioHang.get(indexMaSPTrungTrongGioHang);
                hoaDon.setSoLuong(Integer.parseInt(txt_soLuong.getText()) + hoaDon.getSoLuong());
                hoaDon.setThanhTien(hoaDon.getDonGia() * hoaDon.getSoLuong());
                listGioHang.set(indexMaSPTrungTrongGioHang, hoaDon);
            } else {
                listGioHang.add(hd);
            }

            loadGioHang(listGioHang);
            JOptionPane.showMessageDialog(this, "Thêm vào giỏ hàng thành công !");
            tongTien = 0;
            for (int i = 0; i < listGioHang.size(); i++) {
                tongTien += listGioHang.get(i).getThanhTien();
            }
            txt_tongTien.setText(formatter.format(tongTien));
        }
    }//GEN-LAST:event_themVaoGioHangActionPerformed

    private void bangkhachhangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bangkhachhangMouseClicked
        int row = bangkhachhang.getSelectedRow();
        String maKH = bangkhachhang.getValueAt(row, 0).toString();
        ArrayList<KhachHang> listKH = loadKH();
        for (int i = 0; i < listKH.size(); i++) {
            if (maKH.equals(listKH.get(i).getMaKhachHang())) {
                kh = listKH.get(i);
                index = i;
                break;
            }
        }
        txt_maKhachHang.setText(kh.getMaKhachHang());
        txt_email.setText(kh.getEmail());
        txt_hoTen.setText(kh.getHoTen());
        txt_sdt.setText(kh.getSoDienThoai());
        txt_diaChi.setText(kh.getDiaChi());
    }//GEN-LAST:event_bangkhachhangMouseClicked

    private void bangsanphamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bangsanphamMouseClicked
        int row = bangsanpham.getSelectedRow();
        String maSP = bangsanpham.getValueAt(row, 0).toString();
        ArrayList<SanPham> listSP = loadSP();
        for (int i = 0; i < listSP.size(); i++) {
            if (maSP.equals(listSP.get(i).getMaSanPham())) {
                sp = listSP.get(i);
                index = i;
                break;
            }
        }
        txt_soLuong.setText("1");
    }//GEN-LAST:event_bangsanphamMouseClicked

    private void timKiemKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timKiemKhachHangActionPerformed
        String tuKhoa = txt_timKiemKhachHang.getText();
        if (tuKhoa == "") {
            getDataKhachHang(loadKH());
        } else {
            ArrayList<KhachHang> listKH_timKiem = new ArrayList<>();
            for (int i = 0; i < loadKH().size(); i++) {
                if (loadKH().get(i).getMaKhachHang().contains(tuKhoa) || loadKH().get(i).getSoDienThoai().contains(tuKhoa) || loadKH().get(i).getEmail().contains(tuKhoa)) {
                    listKH_timKiem.add(loadKH().get(i));
                }
            }
            getDataKhachHang(listKH_timKiem);
        }

    }//GEN-LAST:event_timKiemKhachHangActionPerformed

    private void thanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thanhToanActionPerformed
        if (kh.getMaKhachHang() == null || kh.getMaKhachHang().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng trước !");
            return;
        } else {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
            String maHD = kh.getMaKhachHang() + "-" + sdf.format(date);
            for (int i = 0; i < listGioHang.size(); i++) {
                HoaDon hd = listGioHang.get(i);
                hd.setMaHD(maHD);
                hd.setMaKH(kh.getMaKhachHang());
                hd.setMaNV(frmDangNhap.maDN);
                hd.setNgayBan(sdf1.format(date));
                listGioHang.set(i, hd);
            }

            frmSanPham frmSP = new frmSanPham();
            frmSP.loadFile();
            for (int i = 0; i < listGioHang.size(); i++) {
                updateSoLuong(listGioHang.get(i).getMaSP(), listGioHang.get(i).getSoLuong(), frmSP.listSP);
            }
            if (hieu < 0) {
                JOptionPane.showMessageDialog(this, "Số lượng " + sp.getTenSP() + " lớn hơn trong kho");
                return;
            }
            frmSP.saveFile();
            listGioHang = new ArrayList<>();

            JTextField txtSoTien = new JTextField(10);
            JPanel myPanel = new JPanel();
            myPanel.add(Box.createHorizontalStrut(10)); // a spacer
            myPanel.add(new JLabel("Số tiền khách đưa:"));
            myPanel.add(txtSoTien);
            int result = JOptionPane.showConfirmDialog(null, myPanel, "Số tiền khách thanh toán", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                double soTienKhachDua = Double.parseDouble(txtSoTien.getText());
                if (soTienKhachDua < tongTien) {
                    JOptionPane.showMessageDialog(this, "Số tiền khách đưa chưa đủ !");
                    return;
                } else if (soTienKhachDua > tongTien) {
                    JOptionPane.showMessageDialog(this, "Nhân viên trả lại cho khách hàng " + (soTienKhachDua - tongTien) + " VND");
                }

                saveFile();
                JOptionPane.showMessageDialog(this, "Thanh toán thành công !");

//                sendmail(sp.getTenSP(), tongTien, kh.getEmail());
                reset();
            }

        }
    }//GEN-LAST:event_thanhToanActionPerformed
    int hieu;

    public void updateSoLuong(String maSP, int soLuongMua, ArrayList<SanPham> listSP) {
        for (int i = 0; i < listSP.size(); i++) {
            if (maSP.equals(listSP.get(i).getMaSanPham())) {
                SanPham sp = listSP.get(i);
                hieu = Integer.parseInt(sp.getSoLuong()) - soLuongMua;
                String hieu1 = String.valueOf(hieu);

                sp.setSoLuong(hieu1);
                listSP.set(i, sp);
            }
        }
    }

    public void reset() {
        getDataKhachHang(loadKH());
        getDataSanPham(loadSP());
        loadGioHang(listGioHang);
        txt_maKhachHang.setText("...........................");
        txt_hoTen.setText("...........................");
        txt_sdt.setText("...........................");
        txt_diaChi.setText("...........................");
        txt_email.setText("...........................");
        txt_tongTien.setText("...........................");
        tongTien = 0;
    }

    String maSP;
    private void banggiohangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_banggiohangMouseClicked
        int row = banggiohang.getSelectedRow();
        maSP = banggiohang.getValueAt(row, 0).toString();
    }//GEN-LAST:event_banggiohangMouseClicked

    private void xoaGioHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoaGioHangActionPerformed
        double tienGiam;
        for (int i = 0; i < listGioHang.size(); i++) {
            if (maSP.equals(listGioHang.get(i).getMaSP())) {
                tienGiam = listGioHang.get(i).getThanhTien();
                listGioHang.remove(i);
                tongTien = (int) (tongTien - tienGiam);
                txt_tongTien.setText(String.valueOf(tongTien));
            }
        }
        loadGioHang(listGioHang);
    }//GEN-LAST:event_xoaGioHangActionPerformed

    private void timKiemSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timKiemSanPhamActionPerformed
        String tuKhoa = txt_timKiemSanPham.getText();
        if (tuKhoa == "") {
            getDataSanPham(loadSP());
        } else {
            ArrayList<SanPham> listSP_timKiem = new ArrayList<>();
            for (int i = 0; i < loadSP().size(); i++) {
                if (loadSP().get(i).getMaSanPham().contains(tuKhoa)) {
                    listSP_timKiem.add(loadSP().get(i));
                }
            }
            getDataSanPham(listSP_timKiem);
        }
    }//GEN-LAST:event_timKiemSanPhamActionPerformed

    private void txt_soLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_soLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_soLuongActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Thoat;
    private javax.swing.JTable banggiohang;
    private javax.swing.JTable bangkhachhang;
    private javax.swing.JTable bangsanpham;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel jlb_nhanVien;
    private javax.swing.JButton thanhToan;
    private javax.swing.JButton themVaoGioHang;
    private javax.swing.JButton timKiemKhachHang;
    private javax.swing.JButton timKiemSanPham;
    private javax.swing.JLabel txt_diaChi;
    private javax.swing.JLabel txt_email;
    private javax.swing.JLabel txt_hoTen;
    private javax.swing.JLabel txt_maKhachHang;
    private javax.swing.JLabel txt_sdt;
    private javax.swing.JTextField txt_soLuong;
    private javax.swing.JTextField txt_timKiemKhachHang;
    private javax.swing.JTextField txt_timKiemSanPham;
    private javax.swing.JLabel txt_tongTien;
    private javax.swing.JButton xoaGioHang;
    // End of variables declaration//GEN-END:variables
}
