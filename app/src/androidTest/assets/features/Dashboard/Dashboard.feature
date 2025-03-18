Feature: Ability to display tasks dashboard

  Scenario: The one where user can see an empty dashboard
    Given user has no saved tasks
    And current date is "13.07.2025"
    When user launches the application
    Then user sees no tasks on dashboard