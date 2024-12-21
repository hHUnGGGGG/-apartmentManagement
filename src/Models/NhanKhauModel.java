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
    private boolean trangThai; // Trạng thái ô tick (true nếu tick, false nếu không)
    private int soPhong;

    // Default constructor
    public NhanKhauModel() {}

    public NhanKhauModel(int maHoKhau, int maNhanKhau, String hoTenNhanKhau, String CCCD, String SDT, int soPhong) {
        this.maNhanKhau = maNhanKhau;
        this.CCCD = CCCD;
        this.hoTenNhanKhau = hoTenNhanKhau;
        this.SDT = SDT;
        this.maHoKhau = maHoKhau;
        this.soPhong = soPhong;
    }

    public NhanKhauModel(int maHoKhau, String CCCD, String hoTenNhanKhau, Date ngaySinh, String SDT, String quanHeVoiChuHo, boolean trangThai) {
        this.ngaySinh = ngaySinh;
        this.CCCD = CCCD;
        this.hoTenNhanKhau = hoTenNhanKhau;
        this.SDT = SDT;
        this.maHoKhau = maHoKhau;
        this.quanHeVoiChuHo = quanHeVoiChuHo;
        this.trangThai = trangThai;
    }


    // Constructor đầy đủ
    public NhanKhauModel(int maHoKhau, int maNhanKhau,String CCCD, String hoTenNhanKhau, Date ngaySinh, String SDT, String quanHeVoiChuHo, boolean trangThai) {
        this.maNhanKhau = maNhanKhau;
        this.CCCD = CCCD;
        this.hoTenNhanKhau = hoTenNhanKhau;
        this.ngaySinh = ngaySinh;
        this.SDT = SDT;
        this.maHoKhau = maHoKhau;
        this.quanHeVoiChuHo = quanHeVoiChuHo;
        this.trangThai = trangThai;
    }

    // Getter và Setter
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

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public int getSoPhong() {
        return soPhong;
    }

    public void setSoPhong(int soPhong) {
        this.soPhong = soPhong;
    }

    // Thêm phương thức toString để debug
    @Override
    public String toString() {
        return "NhanKhauModel{" +
                "maNhanKhau=" + maNhanKhau +
                ", CCCD='" + CCCD + '\'' +
                ", hoTenNhanKhau='" + hoTenNhanKhau + '\'' +
                ", ngaySinh=" + ngaySinh +
                ", SDT='" + SDT + '\'' +
                ", maHoKhau=" + maHoKhau +
                ", quanHeVoiChuHo='" + quanHeVoiChuHo + '\'' +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
