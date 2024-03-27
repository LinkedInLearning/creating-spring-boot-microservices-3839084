package com.example.explorecaliimages.web;

import com.example.explorecaliimages.business.ImageService;
import com.example.explorecaliimages.model.IdName;
import com.example.explorecaliimages.model.Image;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@Tag(name = "Hosting of Images", description = "Upload and fetch Images for the Explore California website")
@RequestMapping("/api/images")
public class ImageController {

    private ImageService service;

    public ImageController(ImageService service) {
        this.service = service;
    }

    @PostMapping(
            path = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Upload a file to add to the online brochure")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {

        log.info("POST /api/images {}", file.getOriginalFilename());
        Image image = new Image();
        image.setFileName(file.getOriginalFilename());
        image.setData(file.getBytes());
        Image savedImage = service.saveImage(image);
        return savedImage.getId();
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Fetch the file by mongo identifier")
    public ResponseEntity<byte[]> handleFileDownload(@PathVariable String id) {
        log.info("GET /api/images/{}", id);
        return service.getImage(id).map(i ->  ResponseEntity.ok().header("Content-Disposition", "attachment; filename=\"" + i.getFileName() + "\"")
                    .body(i.getData())).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "List all filenames with their mongodb identifier")
    public List<IdName> findAll() {
        log.info("GET /api/images");
        return service.findIdNames();
    }
}
