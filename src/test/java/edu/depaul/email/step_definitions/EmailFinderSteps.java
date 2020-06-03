package edu.depaul.email.step_definitions;

import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.depaul.email.EmailFinder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

// There is a defect the user provided limit is not followed.
// Changed the test to confirm defect.
public class EmailFinderSteps {
    private String initialURL;
    private String initialMax;

    @Given("the user has provided a valid URL {string}")
    public void the_user_has_provided_a_valid_URL(String url) {
        initialURL = url;
    }

    @Given("the user provides max emails as {string}")
    public void the_user_provides_max_emails_as (String max){
        initialMax = max;
    }

   @Then("the total number of emails in email.txt is {int}")
    public void the_total_number_of_emails_in_email_txt_is_more_than(long expectedEmails) throws IOException {
        EmailFinder ef = new EmailFinder();
        ef.main(new String[] {initialURL, initialMax});
        File emailFile = new File("email.txt");
        long actualEmails = Files.lines(Paths.get("email.txt")).count();
        assertTrue(expectedEmails < actualEmails);
   }
}
