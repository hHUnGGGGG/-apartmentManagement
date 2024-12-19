package Models;

public class XeModel {
    private String bienSo;
    private String loaiXe;
    private int maHoKhau;

    public XeModel(String bienSo, String loaiXe, int maHoKhau) {
        this.bienSo = bienSo;
        this.loaiXe = loaiXe;
        this.maHoKhau = maHoKhau;
    }

    public String getBienSo() {
        return bienSo;
    }

    public void setBienSo(String bienSo) {
        this.bienSo = bienSo;
    }

    public String getLoaiXe() {
        return loaiXe;
    }

    public void setLoaiXe(String loaiXe) {
        this.loaiXe = loaiXe;
    }

    public int getMaHoKhau() {
        return maHoKhau;
    }

    public void setMaHoKhau(int maHoKhau) {
        this.maHoKhau = maHoKhau;
    }
}
