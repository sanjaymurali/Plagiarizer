package PlagiarismDetector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import routes.Assignments;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(Assignments.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AssignmentRouteTests {

    @InjectMocks
    Assignments assignments;

    @Mock
    Factory factory;

    @Spy
    ObjectMapper om;

    private MockMvc mockMvc;

    // Initial Setup
    Factory initialFact = new Factory();
    Assignment a = initialFact.createAssignment();

    static ArrayList<Submission> al = new ArrayList<>();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(assignments).build();
    }

    @BeforeClass
    public static void setupSubmission() {
        String[] notRealFiles = {"1.java", "2.java"};

        al.add(new Submission(1, "Sanjay", notRealFiles, notRealFiles));
        al.add(new Submission(2, "Caroline", notRealFiles, notRealFiles));
        al.add(new Submission(3, "Hanna", notRealFiles, notRealFiles));
        al.add(new Submission(4, "Jian", notRealFiles, notRealFiles));
    }

    // Initially the Submissions for an assignment is empty.
    // Test no submissions
    @Test
    public void test1InitialAssignment() throws Exception {

        mockMvc.perform(get("/assignment"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("[]"));
    }

    @Test
    public void test2OneAssignment() throws Exception {
        // create a submission and put it into an assignment

        a.pushSubmissions(al.get(0));

        when(factory.createAssignment()).thenReturn(a);

        String responseJSON = "[{\"studentID\":1,\"studentName\":\"Sanjay\",\"filePaths\":[\"1.java\",\"2.java\"]," +
                "\"fileNames\":[\"1.java\",\"2.java\"]}]";

        mockMvc.perform(get("/assignment"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(responseJSON));
    }

    @Test
    public void test3OneOrMoreSubmissions() throws Exception {
        // create a submission and put it into an assignment
        a.pushSubmissions(al.get(1));

        when(factory.createAssignment()).thenReturn(a);

        String responseJSON = "[{\"studentID\":1,\"studentName\":\"Sanjay\",\"filePaths\":[\"1.java\",\"2.java\"],\"fileNames\":[\"1.java\",\"2.java\"]}" +
                ",{\"studentID\":2,\"studentName\":\"Caroline\",\"filePaths\":[\"1.java\",\"2.java\"],\"fileNames\":[\"1.java\",\"2.java\"]}]";

        mockMvc.perform(get("/assignment"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(responseJSON));
    }

    @Test
    public void test4AllSubmissions() throws Exception{
        a.setSubmissions(al);

        when(factory.createAssignment()).thenReturn(a);

        String responseJSON = "[{\"studentID\":1,\"studentName\":\"Sanjay\",\"filePaths\":[\"1.java\",\"2.java\"],\"fileNames\":[\"1.java\",\"2.java\"]}," +
                "{\"studentID\":2,\"studentName\":\"Caroline\",\"filePaths\":[\"1.java\",\"2.java\"],\"fileNames\":[\"1.java\",\"2.java\"]}," +
                "{\"studentID\":3,\"studentName\":\"Hanna\",\"filePaths\":[\"1.java\",\"2.java\"],\"fileNames\":[\"1.java\",\"2.java\"]}," +
                "{\"studentID\":4,\"studentName\":\"Jian\",\"filePaths\":[\"1.java\",\"2.java\"],\"fileNames\":[\"1.java\",\"2.java\"]}]";

        mockMvc.perform(get("/assignment"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(responseJSON));
    }

    // Given a submission id i.e., StudentID, return the submission from the assignment
    // try with a valid ID
    @Test
    public void test5GetASubmision() throws Exception {
        when(factory.createAssignment()).thenReturn(a);

        String responseJSON = "{\"studentID\":2,\"studentName\":\"Caroline\",\"filePaths\":[\"1.java\",\"2.java\"],\"fileNames\":[\"1.java\",\"2.java\"]}";

        mockMvc.perform(get("/assignment/{id}", 2))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(responseJSON));
    }

    // Given a submission id i.e., StudentID, return the submission from the assignment
    // try with a invalid ID but int
    @Test
    public void test6GetASubmision() throws Exception {
        when(factory.createAssignment()).thenReturn(a);
        String responseJSON = "{\"success\": false, \"message\": \"An Error occured!\"}";
        mockMvc.perform(get("/assignment/{id}", -1))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(responseJSON));

        mockMvc.perform(get("/assignment/{id}",  100))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(responseJSON));
    }

    // Test with id as String
    @Test
    public void test7GetASubmision() throws Exception {
        when(factory.createAssignment()).thenReturn(a);
        String responseJSON = "{\"success\": false, \"message\": \"An Error occured!\"}";
        mockMvc.perform(get("/assignment/{id}", "Sanjay"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(responseJSON));

        mockMvc.perform(get("/assignment/{id}", "null"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(responseJSON));

    }

    // Test with integer as String
    @Test
    public void test8GetASubmision() throws Exception {
        when(factory.createAssignment()).thenReturn(a);

        String responseJSON = "{\"studentID\":2,\"studentName\":\"Caroline\",\"filePaths\":[\"1.java\",\"2.java\"],\"fileNames\":[\"1.java\",\"2.java\"]}";
        mockMvc.perform(get("/assignment/{id}", "2"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(responseJSON));

        responseJSON = "{\"studentID\":1,\"studentName\":\"Sanjay\",\"filePaths\":[\"1.java\",\"2.java\"],\"fileNames\":[\"1.java\",\"2.java\"]}";
        mockMvc.perform(get("/assignment/{id}", "1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(responseJSON));

        responseJSON = "{\"success\": false, \"message\": \"An Error occured!\"}";
        mockMvc.perform(get("/assignment/{id}", "-1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(responseJSON));

    }

    // Test cleanse route
    @Test
    public void test9CleanseAllSubmissions() throws Exception {
        when(factory.createAssignment()).thenReturn(a);

        mockMvc.perform(get("/cleanse"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("Done!"));

        // should have deleted all the submissions
        assertEquals(a.getSubmissions().size(), 0);
        assertNull(a.findSubmission(1));

    }


    @Test
    public void test10() throws Exception {
        when( om.writeValueAsString(any())).thenThrow( new JsonProcessingException("") {});

        String responseJSON = "{\"success\": false, \"message\": \"An Error occured!\"}";
        mockMvc.perform(get("/assignment"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(responseJSON));

    }

}
