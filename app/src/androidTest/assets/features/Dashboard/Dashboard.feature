Feature: Ability to display tasks dashboard

  Scenario: The one where user can see an empty dashboard
    Given user has no saved tasks
    When user launches the application
    Then user sees no tasks on dashboard