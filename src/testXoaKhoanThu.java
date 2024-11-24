import Service.KhoanThuService;
import Models.KhoanThuModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class testXoaKhoanThu {
    public static void main(String[] args){
        KhoanThuService test=new KhoanThuService();
        Scanner sc=new Scanner(System.in);
        test.xoaKhoanThu(1);
    }
}
