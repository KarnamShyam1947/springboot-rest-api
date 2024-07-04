package com.shyam.services;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.shyam.dto.response.PredictResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PredictionService {

    private final RestTemplate restTemplate;

    public PredictResult getPrediction(String url, MultipartFile multipartFile) {
        HttpEntity<MultiValueMap<String, Object>> requestEntity = getRequestEntity(multipartFile, "");

        try {
            PredictResult postForObject = restTemplate.postForObject(url, requestEntity, PredictResult.class);
            System.out.println(postForObject);
            return postForObject;
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public PredictResult getPrediction(String url, MultipartFile multipartFile, String type) {
        HttpEntity<MultiValueMap<String, Object>> requestEntity = getRequestEntity(multipartFile, type);

        try {
            PredictResult postForObject = restTemplate.postForObject(url, requestEntity, PredictResult.class);

            return postForObject;
            
        } catch (Exception e) {
            log.error(e.toString());
        }

        return null;
    }

    public HttpEntity<MultiValueMap<String, Object>> getRequestEntity(MultipartFile multipartFile, String type) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        ByteArrayResource fileAsResource;
        try {
            fileAsResource = new ByteArrayResource(multipartFile.getBytes()) {
                @Override
                public String getFilename() {
                    return multipartFile.getOriginalFilename();
                }
            };
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileAsResource);
        body.add("type", type);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        return requestEntity;
    }


}
