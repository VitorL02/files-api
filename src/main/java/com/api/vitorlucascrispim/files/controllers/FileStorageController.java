package com.api.vitorlucascrispim.files.controllers;


import com.api.vitorlucascrispim.files.services.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@RestController
@RequestMapping("/files")
public class FileStorageController {

    @Autowired
    private FileStorageService fileStorageService;

    
    @PostMapping(path = "/upload",consumes = {"multipart/form-data"})
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file",required = true)MultipartFile file){
        try{
            String uploadUrl = fileStorageService.upload(file);
            return ResponseEntity.ok(new StringBuilder().append("Upload Completo! Url para Download: ").append(uploadUrl).toString());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro para fazer o upload! Tente novamente mais tarde");
        }
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request){
        ResponseEntity download = fileStorageService.download(fileName, request);
        return download;
    }

    @GetMapping("/archives")
    public  ResponseEntity<List<String>> allArchives(){
            List<String> archivesList = fileStorageService.archivesList();
            return ResponseEntity.ok(archivesList);
    }
    
    
}
