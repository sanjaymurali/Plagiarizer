package PlagiarismDetector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"routes"})
public class PlagiarismDetectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlagiarismDetectorApplication.class, args);
	}

}
