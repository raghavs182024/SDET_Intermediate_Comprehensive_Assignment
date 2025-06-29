Feature: Round Trip Booking on MakeMyTrip

  Scenario: Book a round trip from Hyderabad to Chennai
    Given I open the MakeMyTrip website
    When I skip the login pop-up
    And I select round trip option
    And I enter "Hyderabad" as the source city
    And I enter "Chennai" as the destination city
    And I select departure date "Thu Jun 26 2025"
    And I select return date "Sun Jun 29 2025"
    And I click on search flights
    Then I should see "Hyderabad → Chennai" in the search results