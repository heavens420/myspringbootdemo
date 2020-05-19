package com.zlx.crud.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

import java.util.UUID;

/**
 *
 * 密码加密
 */

public class PasswdUtils {
    public static void main(String[] args) {
        String passwd1  = "123";
        //无盐值加密 加密后的密码固定 容易破解
        Md5Hash md5Hash = new Md5Hash(passwd1);
        System.out.println(md5Hash);

        //加盐值 使用不同的盐值 使每次加密结果都不一样 无法破解
        Md5Hash md5Hash1 = new Md5Hash(passwd1, UUID.randomUUID().toString(),2);//加密两次
        System.out.println(md5Hash1);
    }
}
