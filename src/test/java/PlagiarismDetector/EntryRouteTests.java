package PlagiarismDetector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import routes.Entry;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(Entry.class)
public class EntryRouteTests {

    @InjectMocks
    Entry entry;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception{
        this.mockMvc = MockMvcBuilders.standaloneSetup(entry).build();
        MockitoAnnotations.initMocks(this);
    }

    // Test the Base URL to the backend
    @Test
    public void testEntryToBackend() throws Exception {

        String responseJSON = "Hey! Thanks for visiting, Please visit: http://plagiarizer.herokuapp.com/";

        mockMvc.perform(get("/"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(responseJSON));
    }
}
