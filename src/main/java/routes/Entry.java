package routes;

import configuration.ApplicationConfig;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Entry class has mapping to "/" URL for our backend, to make sure that when somebody visits this, they know where the
 * front-end of the Plagiarizer is.
 */
@RestController
@CrossOrigin(origins = {
        ApplicationConfig.local,
        ApplicationConfig.herokuNoHttps,
        ApplicationConfig.heroku})
public class Entry {

    // Entry to backend of the app
    @RequestMapping("/")
    public String Entry() {
        return "Hey! Thanks for visiting, Please visit: http://plagiarizer.herokuapp.com/";
    }
}
