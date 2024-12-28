package Models;

import java.util.Date;

public class TamVangModel {
    private int maNhanKhau;
    private String liDo;
    private Date thoiGianBatDau;
    private Date thoiGianKetThuc;

    public TamVangModel() {
    }

    public TamVangModel(String liDo, Date thoiGianBatDau, Date thoiGianKetThuc) {
        this.liDo = liDo;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
    }

    public int getMaNhanKhau() {
        return maNhanKhau;
    }

    public void setMaNhanKhau(int maNhanKhau) {
        this.maNhanKhau = maNhanKhau;
    }

    public String getLiDo() {
        return liDo;
    }

    public void setLiDo(String liDo) {
        this.liDo = liDo;
    }

    public Date getThoiGianBatDau() {
        return thoiGianBatDau;
    }

    public void setThoiGianBatDau(Date thoiGianBatDau) {
        this.thoiGianBatDau = thoiGianBatDau;
    }

    public Date getThoiGianKetThuc() {
        return thoiGianKetThuc;
    }

    public void setThoiGianKetThuc(Date thoiGianKetThuc) {
        this.thoiGianKetThuc = thoiGianKetThuc;
    }

}
