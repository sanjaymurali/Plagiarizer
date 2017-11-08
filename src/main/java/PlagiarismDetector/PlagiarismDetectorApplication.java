package PlagiarismDetector;

import IO.Writer;
import PlagiarizerFactory.Factory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring created this file, to bootstrap and start the app
 */
@SpringBootApplication(scanBasePackages = {"routes"}) // tells spring to look for routes/urls in the package called "routes"
public class PlagiarismDetectorApplication {

    public static void main(String[] args) {

        Factory factory = new Factory();
        Writer writer = factory.Writer();
        writer.deleteAllUploads(); // initially delete the contents of the "uploads" folder

        SpringApplication.run(PlagiarismDetectorApplication.class, args);
    }

}
