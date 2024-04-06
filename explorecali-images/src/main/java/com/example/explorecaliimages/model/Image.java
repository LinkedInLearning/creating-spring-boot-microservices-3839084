package com.example.explorecaliimages.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Domain Document Class for an Image.
 */
@Document(collection = "images")
@Data
public class Image {

    @Id
    private String id;
    
    private String fileName;
    private byte[] data;
}
