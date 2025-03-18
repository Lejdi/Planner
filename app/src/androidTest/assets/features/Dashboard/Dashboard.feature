Feature: Ability to display tasks dashboard

  Scenario: The one where user can see an empty dashboard
    Given user has no saved tasks
    And current date is "13.07.2025"
    When user launches the application
    Then add task button is visible
    And user sees no tasks on day "13.07.2025"
    And user sees no tasks on day "14.07.2025"
    And user sees no tasks on day "15.07.2025"
    And user sees no tasks on day "16.07.2025"
    And user sees no tasks on day "17.07.2025"
    And user sees no tasks on day "18.07.2025"
    And user sees no tasks on day "19.07.2025"
    And user sees no tasks on day "20.07.2025"