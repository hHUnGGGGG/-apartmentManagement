package Models;

import java.util.Date;

public class ThanhToanModel extends KhoanThuModel{
    private String trangThai;
    private Date thoiGianThanhToan;
    private String phuongThucThanhToan;
    private String tenChuHo;

    public ThanhToanModel(int maKhoanThu, String tenKhoanThu, double soTien, String loaiKhoanThu, Date hanNop,int maHoKhau, int thangNop, String trangThai, Date thoiGianThanhToan, String phuongThucThanhToan) {
        super(maKhoanThu, tenKhoanThu, soTien, loaiKhoanThu, hanNop, maHoKhau, thangNop);
        this.trangThai = trangThai;
        this.thoiGianThanhToan = thoiGianThanhToan;
        this.phuongThucThanhToan = phuongThucThanhToan;

    }


    public ThanhToanModel(int maKhoanThu, String tenKhoanThu, double soTien, String loaiKhoanThu, Date hanNop, int maHoKhau, int thangNop, String trangThai, Date thoiGianThanhToan, String phuongThucThanhToan, String tenChuHo) {
        super(maKhoanThu, tenKhoanThu, soTien, loaiKhoanThu, hanNop, maHoKhau, thangNop);
        this.trangThai = trangThai;
        this.thoiGianThanhToan = thoiGianThanhToan;
        this.phuongThucThanhToan = phuongThucThanhToan;
        this.tenChuHo= tenChuHo;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public Date getThoiGianThanhToan() {
        return thoiGianThanhToan;
    }

    public void setThoiGianThanhToan(Date thoiGianThanhToan) {
        this.thoiGianThanhToan = thoiGianThanhToan;
    }

    public String getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    public String getTenChuHo() {
        return tenChuHo;
    }

    public void setTenChuHo(String tenChuHo) {
        this.tenChuHo = tenChuHo;
    }
}
