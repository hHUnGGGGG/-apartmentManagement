package Models;

import java.util.Date;

public class KhoanThuModel {
    private int maKhoanThu;
    private String tenKhoanThu;
    private double soTien;
    private String loaiKhoanThu;
    private Date hanNop;

    public KhoanThuModel(String tenKhoanThu, double soTien, String loaiKhoanThu, Date hanNop) {
        this.tenKhoanThu = tenKhoanThu;
        this.soTien = soTien;
        this.loaiKhoanThu = loaiKhoanThu;
        this.hanNop=hanNop;
    }

    public KhoanThuModel(int maKhoanThu, String tenKhoanThu, double soTien, String loaiKhoanThu, Date hanNop) {
        this.maKhoanThu = maKhoanThu;
        this.tenKhoanThu = tenKhoanThu;
        this.soTien = soTien;
        this.loaiKhoanThu = loaiKhoanThu;
        this.hanNop = hanNop;
    }

    public KhoanThuModel(int maKhoanThu, String tenKhoanThu, double soTien, String loaiKhoanThu) {
        this.maKhoanThu = maKhoanThu;
        this.tenKhoanThu = tenKhoanThu;
        this.soTien = soTien;
        this.loaiKhoanThu = loaiKhoanThu;
    }

    public int getMaKhoanThu() {
        return maKhoanThu;
    }

    public void setMaKhoanThu(int maKhoanThu) {
        this.maKhoanThu = maKhoanThu;
    }

    public String getTenKhoanThu() {
        return tenKhoanThu;
    }

    public void setTenKhoanThu(String tenKhoanThu) {
        this.tenKhoanThu = tenKhoanThu;
    }

    public double getSoTien() {
        return soTien;
    }

    public void setSoTien(double soTien) {
        this.soTien = soTien;
    }

    public String getLoaiKhoanThu() {
        return loaiKhoanThu;
    }

    public void setLoaiKhoanThu(String loaiKhoanThu) {
        this.loaiKhoanThu = loaiKhoanThu;
    }

    public Date getHanNop() {
        return hanNop;
    }

    public void setHanNop(Date hanNop) {
        this.hanNop = hanNop;
    }

}