package com.zlx.crud.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @RequestMapping("/image")
    public String upload(MultipartFile file, HttpServletRequest request) throws Exception {
        if (!file.isEmpty()) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String format = simpleDateFormat.format(new Date());
            //保存图片路径地址
//            String realPath = request.getServletContext().getRealPath("/upload")+format;
            String realPath = "C:\\Users\\420\\Desktop\\source\\课设相关";
            System.out.println("realPath:"+realPath);
            File folder = new File(realPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // 获取文件原始名称
            String originalFilename = file.getOriginalFilename();
            System.out.println("originalFilename:"+originalFilename);
            String newName = UUID.randomUUID().toString()+format+originalFilename.substring(originalFilename.lastIndexOf("."));
            System.out.println("newName:"+newName);
            try {
                //保存图片到硬盘
                file.transferTo(new File(folder,newName));
                String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/upload/"  + newName;
                System.out.println(url);
            } catch (Exception e) {
                throw new Exception("文件上传失败");
            } finally {

            }
        }
        return "文件上传成功";
    }
}
