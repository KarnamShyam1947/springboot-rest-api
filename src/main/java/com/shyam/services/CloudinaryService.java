package com.shyam.services;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CloudinaryService {
    
    private final Cloudinary cloudinary;

    @SuppressWarnings("rawtypes")
    public Map uploadToCloud(MultipartFile file, String folderName) {
        try {
            Map upload = cloudinary.uploader().upload(file.getBytes(), Map.of(
                "overwrite", true,
                "folder" , folderName
            ));

            return upload;
        } 
        catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public void deleteFromCloud(String id) {
        try {
            ApiResponse delete = cloudinary.api().deleteResources(Arrays.asList(id), null);
            System.out.println(delete);
        } 
        catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
