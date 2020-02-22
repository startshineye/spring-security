package com.yxm.security.web.controller;

import com.yxm.security.dto.FileInfo;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * @author yexinming
 * @date 2020/2/21
 **/
@RestController
@RequestMapping("/file")
public class FileController {
    private String folder = "F:\\std\\spring-security\\spring-security-demo\\src\\main\\java\\com\\yxm\\security\\web\\controller";

    @PostMapping
    public FileInfo upload(MultipartFile file) throws Exception{
       System.out.println(file.getName());
       System.out.println(file.getOriginalFilename());
       System.out.println(file.getSize());

        File localFile = new File(folder, new Date().getTime() + ".txt");
        file.transferTo(localFile);

        return new FileInfo(localFile.getAbsolutePath());
    }

    @GetMapping("/{id}")
    public void download(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
         //jdk7提供的一种操作
        try (InputStream inputStream = new FileInputStream(new File(folder, id + ".txt"));
             OutputStream outputStream = response.getOutputStream();) {

            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=test.txt");

            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        }
    }
}
