package com.shyam.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shyam.dto.response.PredictResult;
import com.shyam.enums.Diseases;
import com.shyam.services.PredictionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/predict")
@Tag(
    name = "Disease Prediction Controller",
    description = "Here any user can upload the disease images and get prediction"
)
public class PredictController {
    
    private final PredictionService predictService;

    @PostMapping(
        path = "/brain-tumor",
        consumes = {"multipart/form-data"},
        produces = { "application/json" }
    )
    public ResponseEntity<?> brainTumor(
        @RequestPart("file") MultipartFile file
    ) {
        PredictResult prediction = predictService.getPrediction("/disease/brain-tumor", file);

        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(prediction);
    }
    
    @PostMapping(
        path = "/skin-cancer",
        consumes = {"multipart/form-data"},
        produces = { "application/json" }
    )
    public ResponseEntity<?> skinCancer(
        @RequestPart("file") MultipartFile file
    ) {
        PredictResult prediction = predictService.getPrediction("/disease/skin-cancer", file);

        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(prediction);
    }
    @PostMapping(
        path = "/lungs-cancer",
        consumes = {"multipart/form-data"},
        produces = { "application/json" }
    )
    public ResponseEntity<?> lungCancer(
        @RequestPart("file") MultipartFile file
    ) {
        PredictResult prediction = predictService.getPrediction("/disease/lungs-cancer", file);

        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(prediction);
    }

    @PostMapping(
        path = "/eye-disease",
        consumes = {"multipart/form-data"},
        produces = { "application/json" }
    )
    public ResponseEntity<?> eyeDisease(
        @RequestPart("file") MultipartFile file
    ) {

        PredictResult prediction = predictService.getPrediction("/disease/eye-disease", file);

        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(prediction);
    }
   
    @PostMapping(
        path = "/general",
        consumes = {"multipart/form-data"},
        produces = { "application/json" }
    )
    @Operation(
        parameters = {
            @Parameter(
                in = ParameterIn.QUERY,
                name = "type",
                schema = @Schema(
                    implementation = Diseases.class
                ),
                description ="select the target organ"
            )
        }
    )
    public ResponseEntity<?> general(
        @RequestParam("type") String type,
        @RequestPart("file") MultipartFile file
    ) {
        System.out.println(type);

        PredictResult prediction = predictService.getPrediction("/disease/general", file, type);

        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(prediction);
    }

}
