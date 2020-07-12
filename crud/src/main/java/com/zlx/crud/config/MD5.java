package com.zlx.crud.config;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MD5 {
    private static  String type = "md5"; //加密方式
    private static Integer times = 2;  //加密两次

    public static void main(String[] args) throws ParseException {
        String userId = "12345";
        String passwd = "999";
        String md5Passwd = md5(userId, passwd);
        System.out.println(md5Passwd);

        String s = "2020-04-23";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = simpleDateFormat.parse(s);
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy年MM月dd日");
        String format = simpleDateFormat1.format(parse);
        System.out.println(format);
    }

    public static String md5(String userId,String passwd){
        return new  SimpleHash(type,passwd, ByteSource.Util.bytes(userId),times).toHex();
    }

}
