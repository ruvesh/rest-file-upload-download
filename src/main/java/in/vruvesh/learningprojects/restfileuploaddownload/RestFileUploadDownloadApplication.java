package in.vruvesh.learningprojects.restfileuploaddownload;

import in.vruvesh.learningprojects.restfileuploaddownload.service.FileStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class RestFileUploadDownloadApplication implements CommandLineRunner {

	@Resource
	FileStorageService fileStorageService;

	public static void main(String[] args) {
		SpringApplication.run(RestFileUploadDownloadApplication.class, args);
		System.out.println("### REST FILE UPLOAD & DOWNLOAD APPLICATION RUNNING ###");
	}


	@Override
	public void run(String... args) throws Exception {
		fileStorageService.init();
	}
}
