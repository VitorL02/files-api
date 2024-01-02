package com.api.vitorlucascrispim.files.controllers;


import com.api.vitorlucascrispim.files.services.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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


    private static final String ENDPOINT_NAME = "FilesAPI - Gerenciamento de arquivos";
    private static final String UPLOAD_RESUMO = "Esse endpoint permite que um arquivo seja enviado para que posteriormente seja feito o download";
    private static final String UPLOAD_DESCRICAO = "Esse endpoint permite o envio de um arquivo utilizando uma requisição do tipo multipart/form-data informando caso sucesso o link de download do arquivo ";

    private static final String DOWNLOAD_RESUMO = "Esse endpoint permite o download do arquivo caso ele esteja disponivel, utilizando o nome juntamente com tipo de arquivo";
    private static final String DOWNLOAD_DESCRICAO = "Esse endpoint fornece um arquivo caso o mesmo tenha sido enviado posteriormente e faz o download do mesmo  ";


    private static final String LIST_ARCHIVE_RESUMO = "Esse endpoint lista os arquivos previamente enviados";
    private static final String LIST_ARCHIVE_DESCRICAO = "Esse endpoint retorna uma lista com todos os arquivos juntamente com seus tipos para realizar o download  ";

    @PostMapping(path = "/upload",consumes = {"multipart/form-data"})
    @Tag(name = ENDPOINT_NAME )
    @Operation(summary = UPLOAD_RESUMO,description = UPLOAD_DESCRICAO)
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file",required = true)MultipartFile file){
        try{
            String uploadUrl = fileStorageService.upload(file);
            return ResponseEntity.ok(new StringBuilder().append("Upload Completo! Url para Download: ").append(uploadUrl).toString());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro para fazer o upload! Tente novamente mais tarde");
        }
    }

    @GetMapping("/download/{fileName:.+}")
    @Tag(name = ENDPOINT_NAME )
    @Operation(summary = DOWNLOAD_RESUMO,description = DOWNLOAD_DESCRICAO)
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request){
        ResponseEntity download = fileStorageService.download(fileName, request);
        return download;
    }

    @GetMapping("/archives")
    @Tag(name = ENDPOINT_NAME )
    @Operation(summary = LIST_ARCHIVE_RESUMO,description = LIST_ARCHIVE_DESCRICAO)
    public  ResponseEntity<List<String>> allArchives(){
            List<String> archivesList = fileStorageService.archivesList();
            return ResponseEntity.ok(archivesList);
    }
    
    
}
