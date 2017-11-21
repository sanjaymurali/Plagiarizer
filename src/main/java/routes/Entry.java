package routes;

import core.configuration.ApplicationConfig;
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

    /**
     * This is the BASE_URL of the back-end.
     * @return a String which tells the user where the front-end of the application is
     */
    @RequestMapping("/")
    public String Entry() {
        return "Hey! Thanks for visiting, Please visit: http://plagiarizer.herokuapp.com/";
    }
}
