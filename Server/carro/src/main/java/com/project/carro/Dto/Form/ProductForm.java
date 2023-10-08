package com.project.carro.Dto.Form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductForm {
    private String name;
    private Long price;
    private Long quantity;
    private Long discount;
    private Collection<String> category;
    private MultipartFile images;
    private String description;
}
