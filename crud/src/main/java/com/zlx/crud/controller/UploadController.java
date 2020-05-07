package com.zlx.crud.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.UUID;

@RestController
public class UploadController {

    public String upload(MultipartFile file, HttpServletRequest request) throws Exception {
        if (!file.isEmpty()) {
            String realPath = request.getServletContext().getRealPath("/upload")+new Date();
            File folder = new File(realPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // 获取文件原始名称
            String originalFilename = file.getOriginalFilename();
            String newName = UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));

            try {
                //保存图片到硬盘
                file.transferTo(new File(originalFilename,newName));
            } catch (Exception e) {
                throw new Exception("文件上传失败");
            } finally {

            }
        }
        return "文件上传成功";
    }
}
