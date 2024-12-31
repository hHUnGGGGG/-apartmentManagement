package Models;

import java.util.Date;

public class KhoanThuModel {
    private int maKhoanThu;

    private HoKhauModel hoKhauModel;
    private LoaiPhiModel loaiPhiModel;
    private int soTien;
    private Date hanNop;
    private Date thoiGianThanhToan;
    private String trangThai;
    private String phuongThucThanhToan;



    public KhoanThuModel(int maKhoanThu, HoKhauModel hoKhauModel, LoaiPhiModel loaiPhiModel, int soTien, Date thoiGianThanhToan, String trangThai, String phuongThucThanhToan) {
        this.maKhoanThu = maKhoanThu;
        this.hoKhauModel = hoKhauModel;
        this.loaiPhiModel = loaiPhiModel;
        this.soTien = soTien;
        this.thoiGianThanhToan = thoiGianThanhToan;
        this.trangThai = trangThai;
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    public int getMaKhoanThu() {
        return maKhoanThu;
    }

    public void setMaKhoanThu(int maKhoanThu) {
        this.maKhoanThu = maKhoanThu;
    }

    public HoKhauModel getHoKhauModel() {
        return hoKhauModel;
    }

    public void setHoKhauModel(HoKhauModel hoKhauModel) {
        this.hoKhauModel = hoKhauModel;
    }

    public LoaiPhiModel getLoaiPhiModel() {
        return loaiPhiModel;
    }

    public void setLoaiPhiModel(LoaiPhiModel loaiPhiModel) {
        this.loaiPhiModel = loaiPhiModel;
    }

    public int getSoTien() {
        return soTien;
    }

    public void setSoTien(int soTien) {
        this.soTien = soTien;
    }

    public Date getHanNop() {
        return hanNop;
    }

    public void setHanNop(Date hanNop) {
        this.hanNop = hanNop;
    }

    public Date getThoiGianThanhToan() {
        return thoiGianThanhToan;
    }

    public void setThoiGianThanhToan(Date thoiGianThanhToan) {
        this.thoiGianThanhToan = thoiGianThanhToan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }
}
