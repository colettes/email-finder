Feature: Complete list of emails found, limit not followed.
  Scenario: valid url, several emails on page, small amount of emails requested
    Given the user has provided a valid URL "https://www.cdm.depaul.edu/Pages/default.aspx"
    And the user provides max emails as "5"
    Then the total number of emails in email.txt is more than 5
