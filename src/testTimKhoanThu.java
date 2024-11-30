import Models.KhoanThuModel;
import Service.KhoanThuService;
public class testTimKhoanThu {
    public static void main(String[] args) {
        KhoanThuService t=new KhoanThuService();
        KhoanThuModel test= t.timKhoanThuMa(2);
        System.out.println(test.getMaKhoanThu() +test.getTenKhoanThu() + test.getLoaiKhoanThu() +test.getSoTien() +test.getHanNop());
    }
}
