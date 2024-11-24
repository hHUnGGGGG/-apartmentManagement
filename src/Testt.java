import Service.KhoanThuService;
import Models.KhoanThuModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Testt {
    public static void main(String[] args){
        KhoanThuService test=new KhoanThuService();
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Nhập tên khoản thu: ");
            String tenKhoanThu = scanner.nextLine();
            System.out.print("Nhập số tiền: ");
            double soTien = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Nhập loại khoản thu: ");
            String loaiKhoanThu = scanner.nextLine();
            System.out.print("Nhập hạn nộp (dd-MM-yyyy): ");
            String hanNopStr = scanner.nextLine();

            // Chuyển đổi chuỗi hạn nộp sang đối tượng Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date hanNop = dateFormat.parse(hanNopStr);

            KhoanThuModel khoanThu = new KhoanThuModel(tenKhoanThu, soTien, loaiKhoanThu, hanNop);
            test.themKhoanThu(khoanThu);
        } catch (ParseException e) {
            System.out.println("Định dạng ngày không hợp lệ!");
        }
    }
}
