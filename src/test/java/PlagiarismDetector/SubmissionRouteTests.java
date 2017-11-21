package PlagiarismDetector;

import core.IO.Reader;
import core.PlagiarismDetection.Assignment;
import core.PlagiarismDetection.Submission;
import core.PlagiarizerFactory.Factory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import routes.Submissions;

import java.util.ArrayList;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(Submissions.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SubmissionRouteTests {

    @InjectMocks
    Submissions submissions;

    @Mock
    Factory factory;

    @Spy
    Reader reader;

    private MockMvc mockMvc;

    // Initial Setup
    Factory initialFact = new Factory();
    Assignment a = initialFact.createAssignment();

    static ArrayList<Submission> al = new ArrayList<>();

    @Before
    public void setup() throws Exception{
        this.mockMvc = MockMvcBuilders.standaloneSetup(submissions).build();
        MockitoAnnotations.initMocks(this);
    }

    @BeforeClass
    public static void setupSubmission() {
        String[] notRealFiles = {"1.java", "2.java"};

        al.add(new Submission(1, "Sanjay", notRealFiles, notRealFiles));
    }

    // Initially the Submissions for an assignment is empty.
    // Test no submissions
    @Test
    public void test1Submission() throws Exception {
        // An error should occur since there are no submissions made at all in this point

        String responseJSON = "{\"success\": false, \"message\": \"An Error occured!\"}";

        mockMvc.perform(get("/submission?studentID=1&fileName=random.java"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(responseJSON));
    }

    @Test
    public void test2Submission() throws Exception {
        // Add one submission on to the assignment array and test for an submission that doesnt exists
        a.pushSubmissions(al.get(0));
        when(factory.createAssignment()).thenReturn(a);

        String responseJSON = "{\"success\": false, \"message\": \"An Error occured!\"}";

        // both studentID and file name doesnt exists
        mockMvc.perform(get("/submission?studentID=4&fileName=random.java"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(responseJSON));

        mockMvc.perform(get("/submission?studentID=badNumber&fileName=random.java"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(responseJSON));

        // Student ID 1 exists but the file name doesnt
        mockMvc.perform(get("/submission?studentID=1&fileName=random.java"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(responseJSON));

        // Student ID doesnt exists but the file name does exists
        mockMvc.perform(get("/submission?studentID=-1&fileName=1.java"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(responseJSON));
        String program = "Java Program here!";
        doReturn(program).when(reader).getFile("1.java"); // mocking getFile method

        // Student ID exists and the file name also
        mockMvc.perform(get("/submission?studentID=1&fileName=1.java"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("\"Java Program here!\""));
    }
}
