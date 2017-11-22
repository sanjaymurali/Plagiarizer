package PlagiarismDetector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring created this file, to bootstrap and start the app
 */
@SpringBootApplication(scanBasePackages = {"routes"})
// tells spring to look for routes/urls in the package called "routes"
public class PlagiarismDetectorApplication {

    /**
     * @param args is args used by Spring to BootStrap the application
     */
    public static void main(String[] args) {
        SpringApplication.run(PlagiarismDetectorApplication.class, args);
    }

}
