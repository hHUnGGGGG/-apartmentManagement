package Models;

public class HoKhauModel {
    private int maHoKhau;

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
}