package com.project.carro.Utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Slf4j
public class FileUploadUtil {
    public static void saveFile(String dir, String fileName, MultipartFile file) throws IOException {
        Path uploadPath= Paths.get(dir);
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        try(InputStream inputStream = file.getInputStream()){
            Path filepath=uploadPath.resolve(fileName);
            Files.copy(inputStream, filepath, StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException io){
            log.error("Could not save image file: " + fileName, io);
            throw new IOException("Could not save image file: " + fileName, io);
        }
    }

    public static File convert(MultipartFile file){
        File newFile=new File(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(newFile);
            fileOutputStream.write(file.getBytes());
        } catch (IOException e) {
            log.error("Could not convert image file: " +newFile);
            throw new RuntimeException(e);
        }
        return newFile;
    }
}
