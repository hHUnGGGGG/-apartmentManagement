package Models;

public class HoKhauChuHoModel {
    private int maHoKhau;
    private int maChuHo;
    private String tenChuHo;
    private String cccdChuHo;
    private String sdtChuHo;

    public HoKhauChuHoModel(int maHoKhau, int maChuHo, String tenChuHo, String cccdChuHo, String sdtChuHo) {
        this.maHoKhau = maHoKhau;
        this.maChuHo = maChuHo;
        this.tenChuHo = tenChuHo;
        this.cccdChuHo = cccdChuHo;
        this.sdtChuHo = sdtChuHo;
    }

    // Getter và Setter
    public int getMaHoKhau() {
        return maHoKhau;
    }

    public void setMaHoKhau(int maHoKhau) {
        this.maHoKhau = maHoKhau;
    }

    public int getMaChuHo() {
        return maChuHo;
    }

    public void setMaChuHo(int maChuHo) {
        this.maChuHo = maChuHo;
    }

    public String getTenChuHo() {
        return tenChuHo;
    }

    public void setTenChuHo(String tenChuHo) {
        this.tenChuHo = tenChuHo;
    }

    public String getCccdChuHo() {
        return cccdChuHo;
    }

    public void setCccdChuHo(String cccdChuHo) {
        this.cccdChuHo = cccdChuHo;
    }

    public String getSdtChuHo() {
        return sdtChuHo;
    }

    public void setSdtChuHo(String sdtChuHo) {
        this.sdtChuHo = sdtChuHo;
    }
}
