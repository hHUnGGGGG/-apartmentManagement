package Models;

import java.util.Date;

public class LoaiPhiModel {
    private int maLoaiPhi;
    private String tenLoaiPhi;
    private int donGia;
    private String loaiThu;
    private Date hanNop;

    public LoaiPhiModel(int maLoaiPhi, String tenLoaiPhi, int donGia, String loaiThu, Date hanNop) {
        this.maLoaiPhi = maLoaiPhi;
        this.tenLoaiPhi = tenLoaiPhi;
        this.donGia = donGia;
        this.loaiThu = loaiThu;
        this.hanNop = hanNop;
    }

    public LoaiPhiModel() {

    }

    public int getMaLoaiPhi() {
        return maLoaiPhi;
    }

    public void setMaLoaiPhi(int maLoaiPhi) {
        this.maLoaiPhi = maLoaiPhi;
    }

    public String getTenLoaiPhi() {
        return tenLoaiPhi;
    }

    public void setTenLoaiPhi(String tenLoaiPhi) {
        this.tenLoaiPhi = tenLoaiPhi;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public String getLoaiThu() {
        return loaiThu;
    }

    public void setLoaiThu(String loaiThu) {
        this.loaiThu = loaiThu;
    }

    public Date getHanNop() {
        return hanNop;
    }

    public void setHanNop(Date hanNop) {
        this.hanNop = hanNop;
    }
}
