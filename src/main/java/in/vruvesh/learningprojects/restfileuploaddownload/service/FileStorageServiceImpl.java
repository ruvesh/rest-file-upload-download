package in.vruvesh.learningprojects.restfileuploaddownload.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
@Log4j2
public class FileStorageServiceImpl implements FileStorageService{

    private final Path ROOT_PATH = Paths.get("uploads");

    @Override
    public void init() {
        try {
            if(!Files.exists(ROOT_PATH))
                Files.createDirectory(ROOT_PATH);
        } catch (IOException e) {
            log.error("### Error while creating root directory, details are -", e);
            throw new RuntimeException("Couldn't initialize folder for upload");
        }
    }

    @Override
    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.ROOT_PATH.resolve(file.getOriginalFilename()));
        } catch (IOException e) {

            log.error("### Error while storing file, details are -", e);
            throw new RuntimeException("Couldn't store file - " + e.getMessage());
        }

    }

    @Override
    public Resource load(String fileName) {

        try {
            Path file = ROOT_PATH.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());

            if(resource.exists() || resource.isReadable()){
                return resource;
            }
            else {

                throw new RuntimeException("Couldn't read file");
            }
        } catch (MalformedURLException e) {
            log.error("### Exception while reading file, details are - ", e);
            throw new RuntimeException("Exception while reading file - " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(ROOT_PATH.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.ROOT_PATH, 1).filter(path -> !path.equals(this.ROOT_PATH)).map(this.ROOT_PATH::relativize);
        } catch (IOException e) {
            log.error("### Exception while loading files, details are- ", e);
            throw new RuntimeException("Couldn't load files!");
        }
    }
}
