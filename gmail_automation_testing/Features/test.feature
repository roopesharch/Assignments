@login
Feature: Google mail testing

  Scenario: Login with google account details
    Given Opened browser and Navigate to google sign in page
    And login with google account 
    When Navigated to gmail screen and compose button is invoked
    And Enter to_mail address
    And Enter subject for mail
    And Enter Body of the mail
    Then send mail and verify the status
		And Close webdriver
 
