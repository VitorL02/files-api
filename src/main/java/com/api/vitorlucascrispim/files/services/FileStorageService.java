package com.api.vitorlucascrispim.files.services;

import com.api.vitorlucascrispim.files.FileStorageProperties;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileStorageService {
    private final Path fileStorageLocation;

    public FileStorageService(FileStorageProperties fileStorageProperties) {
        //O valor está sendo recuperado pela propriedade : file.upload.directory do application properties
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
    }
    public String upload(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try{
            Path targetLocation = fileStorageLocation.resolve(fileName);
            file.transferTo(targetLocation);

            return ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/download/").path(fileName).toUriString();
        }catch (Exception ex){
            throw new RuntimeException(new StringBuilder().append("Erro ao realizar upload: ").append(ex).toString());
        }

    }

    public ResponseEntity download(String fileName, HttpServletRequest request) {
        try{
            Path fileDownloadName =  fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(fileDownloadName.toUri());
            //Recupera da requisição o tipo do arquivo
            String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

            if(contentType == null){
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);

        }catch (Exception e){
            throw new RuntimeException(new StringBuilder().append("Erro ao realizar download! Arquivo pode não existir: ").append(e).toString());
        }


    }

    public List<String> archivesList() {
        List<String> allArchives = new ArrayList<>();
        try{
            allArchives =  Files.list(fileStorageLocation).map(Path::getFileName).map(Path::toString).collect(Collectors.toList());

        }catch (Exception e){
            throw new RuntimeException(new StringBuilder().append("Erro ao recuperar lista de arquivos ").append(e).toString());
        }
        return allArchives;
    }
}
