package com.project.carro.Utils;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
@Component
@Slf4j
public class AWSUtil {
    @Autowired
    private  AmazonS3 s3Client;
    @Value("${app.bucket.name}")
    public  String bucketName ;

    public String uploadFile(MultipartFile file,String fileName) {
        File fileObj = FileUploadUtil.convert(file);
        System.out.println(bucketName+"   "+ fileObj);
        try {
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
            fileObj.delete();
            log.info(" File : [ " + fileName+" ] uploaded in S3");
        } catch (SdkClientException e) {
            log.error("Error uploading in S3  ");
            throw new RuntimeException(e);
        }
        return String.valueOf(s3Client.getUrl(bucketName, fileName));
    }
}
