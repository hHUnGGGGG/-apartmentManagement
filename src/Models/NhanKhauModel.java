package Models;

import java.util.Date;

public class NhanKhauModel {
    private int maNhanKhau;
    private String CCCD;
    private String hoTenNhanKhau;
    private Date ngaySinh;
    private String SDT;
    private int maHoKhau;
    private String quanHeVoiChuHo;
    private String trangThai;
    private int soPhong;
    private boolean tamVang;
    private String tamVangHienThi;

    // Default constructor
    public NhanKhauModel() {}

    public NhanKhauModel(int maHoKhau, String hoTenNhanKhau, String CCCD, String SDT, int soPhong) {
        this.maHoKhau = maHoKhau;
        this.hoTenNhanKhau = hoTenNhanKhau;
        this.CCCD = CCCD;
        this.SDT = SDT;
        this.soPhong = soPhong;
    }

    public NhanKhauModel(int maNhanKhau, String CCCD, String hoTenNhanKhau, Date ngaySinh, String SDT, int maHoKhau, String quanHeVoiChuHo, String trangThai) {
        this.maNhanKhau = maNhanKhau;
        this.CCCD = CCCD;
        this.hoTenNhanKhau = hoTenNhanKhau;
        this.ngaySinh = ngaySinh;
        this.SDT = SDT;
        this.maHoKhau = maHoKhau;
        this.quanHeVoiChuHo = quanHeVoiChuHo;
        this.trangThai = trangThai;
    }

    public NhanKhauModel(String CCCD, String hoTenNhanKhau, Date ngaySinh, String SDT, int maHoKhau, String quanHeVoiChuHo, String trangThai, boolean tamVang) {
        this.CCCD = CCCD;
        this.hoTenNhanKhau = hoTenNhanKhau;
        this.ngaySinh = ngaySinh;
        this.SDT = SDT;
        this.maHoKhau = maHoKhau;
        this.quanHeVoiChuHo = quanHeVoiChuHo;
        this.trangThai = trangThai;
        this.tamVang = tamVang;
    }


    public NhanKhauModel(String CCCD, String hoTenNhanKhau, Date ngaySinh, String SDT, int maHoKhau, String quanHeVoiChuHo, String trangThai) {
        this.CCCD = CCCD;
        this.hoTenNhanKhau = hoTenNhanKhau;
        this.ngaySinh = ngaySinh;
        this.SDT = SDT;
        this.maHoKhau = maHoKhau;
        this.quanHeVoiChuHo = quanHeVoiChuHo;
        this.trangThai = trangThai;
    }

    public NhanKhauModel(int maNhanKhau, String CCCD, String hoTenNhanKhau, Date ngaySinh, String SDT, int maHoKhau, String quanHeVoiChuHo, String trangThai, boolean tamVang) {
        this.maNhanKhau = maNhanKhau;
        this.CCCD = CCCD;
        this.hoTenNhanKhau = hoTenNhanKhau;
        this.ngaySinh = ngaySinh;
        this.SDT = SDT;
        this.maHoKhau = maHoKhau;
        this.quanHeVoiChuHo = quanHeVoiChuHo;
        this.trangThai = trangThai;
        this.tamVang = tamVang;
    }

    // Constructor đầy đủ
    public NhanKhauModel(int maNhanKhau, String CCCD, String hoTenNhanKhau, Date ngaySinh, String SDT, int maHoKhau, String quanHeVoiChuHo, String trangThai, int soPhong, boolean tamVang) {
        this.maNhanKhau = maNhanKhau;
        this.CCCD = CCCD;
        this.hoTenNhanKhau = hoTenNhanKhau;
        this.ngaySinh = ngaySinh;
        this.SDT = SDT;
        this.maHoKhau = maHoKhau;
        this.quanHeVoiChuHo = quanHeVoiChuHo;
        this.trangThai = trangThai;
        this.soPhong = soPhong;
        this.tamVang = tamVang;
    }

    public int getMaNhanKhau() {
        return maNhanKhau;
    }

    public void setMaNhanKhau(int maNhanKhau) {
        this.maNhanKhau = maNhanKhau;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public String getHoTenNhanKhau() {
        return hoTenNhanKhau;
    }

    public void setHoTenNhanKhau(String hoTenNhanKhau) {
        this.hoTenNhanKhau = hoTenNhanKhau;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public int getMaHoKhau() {
        return maHoKhau;
    }

    public void setMaHoKhau(int maHoKhau) {
        this.maHoKhau = maHoKhau;
    }

    public String getQuanHeVoiChuHo() {
        return quanHeVoiChuHo;
    }

    public void setQuanHeVoiChuHo(String quanHeVoiChuHo) {
        this.quanHeVoiChuHo = quanHeVoiChuHo;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public int getSoPhong() {
        return soPhong;
    }

    public void setSoPhong(int soPhong) {
        this.soPhong = soPhong;
    }

    public boolean isTamVang() {
        return tamVang;
    }

    public void setTamVang(boolean tamVang) {
        this.tamVang = tamVang;
    }

    public String getTamVangHienThi() {
        return tamVang ? "Vắng" : "Không";
    }


}
