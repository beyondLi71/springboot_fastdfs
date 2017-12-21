package com.beyondli.rest;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by beyondLi
 * Date 2017/12/1
 * Desc .
 */
@RestController
@RequestMapping(value = "/csfastdfs")
public class FastRest {
    @Autowired
    private FastFileStorageClient fastFileStorageClient;
    @Autowired
    private FastFileStorageClient storageClient;
    // 上传图片
    @RequestMapping(value = "/csupload", method = RequestMethod.GET)
    public String csUpload() throws Exception {
        URL url = new URL("http://h5-statis.fenghuangyouxuan.com/jpg/b8deaa8d522c44ae8ba2573e3aa903b6.jpg?Expires=3088850901&AccessKey=9BBC935E745FEB1053F562620A0DC3C6&Signature=EmCFZ7ruAXoKFGStNBTVHpYlixY%3D");
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //通过输入流获取图片数据
        InputStream inputStream = conn.getInputStream();
        //注！IOUtils.toByteArray(url).length的大小如果错误则会出现图片上传成功但查看图片时图片部分丢失的情况
        StorePath storePath= fastFileStorageClient.uploadFile(inputStream, IOUtils.toByteArray(url).length,"png",null);
        System.out.println(storePath.getFullPath());
        inputStream.close();
        return storePath.getFullPath();
    }

    // 上传图片流
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(MultipartFile file) throws Exception {
        StorePath storePath = storageClient.uploadFile(file.getInputStream(),file.getSize(), "png",null);
        System.out.println(storePath.getFullPath());
        return storePath.getFullPath();
    }
}
