package Models;

public class HoKhauModel {
    private int maHoKhau;
    private int soPhong;
    private NhanKhauModel nhanKhauModel;
    public HoKhauModel() {
        // Default constructor
    }

    public HoKhauModel(int maHoKhau) {
        this.maHoKhau = maHoKhau;
    }

    // Getter và Setter
    public int getMaHoKhau() {
        return maHoKhau;
    }

    public void setMaHoKhau(int maHoKhau) {
        this.maHoKhau = maHoKhau;
    }

    public int getSoPhong() {
        return soPhong;
    }

    public void setSoPhong(int soPhong) {
        this.soPhong = soPhong;
    }

    public NhanKhauModel getNhanKhauModel() {
        return nhanKhauModel;
    }

    public void setNhanKhauModel(NhanKhauModel nhanKhauModel) {
        this.nhanKhauModel = nhanKhauModel;
    }
}