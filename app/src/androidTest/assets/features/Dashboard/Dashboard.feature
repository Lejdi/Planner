Feature: Ability to display tasks dashboard

  Background:
    Given current date is "13.07.2025"

  Scenario: The one where user can see an empty dashboard
    Given user has no saved tasks
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

  Scenario: The one where user can see tasks on the dashboard
    Given the user has saved tasks
      | taskID | taskName            | description         | startDate  | endDate    | asap | daysInterval | hour  |
      | 1      | ASAP                |                     | 03.07.2025 |            | yes  |              |       |
      | 2      | one time task       |                     | 15.07.2025 |            | no   |              |       |
      | 3      | other one time task | with specified hour | 16.07.2025 |            | no   |              | 10:00 |
      | 4      | Periodic task 1     | with no end date    | 14.07.2025 |            | no   | 2            |       |
      | 5      | Periodic task 2     | with end date       | 14.07.2025 | 17.07.2025 | no   | 1            |       |
      | 6      | Periodic task 3     | with specified hour | 13.07.2025 |            | no   | 1            | 16:18 |
    When user launches the application
    Then add task button is visible
    And user sees tasks on day "13.07.2025"
      | taskName        | taskDescription     | hour  | buttonsVisible |
      | Periodic task 3 | with specified hour | 16:18 | no             |
      | ASAP            |                     |       | no             |
    And user sees tasks on day "14.07.2025"
      | taskName        | taskDescription     | hour  | buttonsVisible |
      | Periodic task 3 | with specified hour | 16:18 | no             |
      | Periodic task 1 | with no end date    |       | no             |
      | Periodic task 2 | with end date       |       | no             |
    And user sees tasks on day "15.07.2025"
      | taskName        | taskDescription     | hour  | buttonsVisible |
      | Periodic task 3 | with specified hour | 16:18 | no             |
      | one time task   |                     |       | no             |
      | Periodic task 2 | with end date       |       | no             |
    And user sees tasks on day "16.07.2025"
      | taskName            | taskDescription     | hour  | buttonsVisible |
      | other one time task | with specified hour | 10:00 | no             |
      | Periodic task 3     | with specified hour | 16:18 | no             |
      | Periodic task 1     | with no end date    |       | no             |
      | Periodic task 2     | with end date       |       | no             |
    And user sees tasks on day "17.07.2025"
      | taskName        | taskDescription     | hour  | buttonsVisible |
      | Periodic task 3 | with specified hour | 16:18 | no             |
      | Periodic task 2 | with end date       |       | no             |
    And user sees tasks on day "18.07.2025"
      | taskName        | taskDescription     | hour  | buttonsVisible |
      | Periodic task 3 | with specified hour | 16:18 | no             |
      | Periodic task 1 | with no end date    |       | no             |
    And user sees tasks on day "19.07.2025"
      | taskName        | taskDescription     | hour  | buttonsVisible |
      | Periodic task 3 | with specified hour | 16:18 | no             |
    And user sees tasks on day "20.07.2025"
      | taskName        | taskDescription     | hour  | buttonsVisible |
      | Periodic task 3 | with specified hour | 16:18 | no             |
      | Periodic task 1 | with no end date    |       | no             |


  Scenario: The one where user completes ASAP task on dashboard
    Given the user has saved tasks
      | taskID | taskName | description | startDate  | endDate | asap | daysInterval | hour |
      | 1      | ASAP     |             | 03.07.2025 |         | yes  |              |      |
    When user launches the application
    Then user sees tasks on day "13.07.2025"
      | taskName | taskDescription | hour | buttonsVisible |
      | ASAP     |                 |      | no             |
    When user clicks on card with task name "ASAP" on day "13.07.2025"
    Then user sees tasks on day "13.07.2025"
      | taskName | taskDescription | hour | buttonsVisible |
      | ASAP     |                 |      | yes            |
    When user completes task with name "ASAP" on day "13.07.2025"
    And user sees no tasks on day "13.07.2025"

  Scenario: The one where user completes one time task on dashboard
    Given the user has saved tasks
      | taskID | taskName      | description | startDate  | endDate | asap | daysInterval | hour |
      | 2      | one time task |             | 15.07.2025 |         | no   |              |      |
    When user launches the application
    Then user sees no tasks on day "13.07.2025"
    And user sees no tasks on day "14.07.2025"
    And user sees tasks on day "15.07.2025"
      | taskName      | taskDescription | hour | buttonsVisible |
      | one time task |                 |      | no             |
    When user clicks on card with task name "one time task" on day "15.07.2025"
    Then user sees tasks on day "15.07.2025"
      | taskName      | taskDescription | hour | buttonsVisible |
      | one time task |                 |      | yes            |
    When user completes task with name "one time task" on day "15.07.2025"
    And user sees no tasks on day "15.07.2025"
    And user sees no tasks on day "16.07.2025"
    And user sees no tasks on day "17.07.2025"
    And user sees no tasks on day "18.07.2025"
    And user sees no tasks on day "19.07.2025"
    And user sees no tasks on day "20.07.2025"

  Scenario: The one where user completes periodic task on dashboard
    Given the user has saved tasks
      | taskID | taskName      | description      | startDate  | endDate | asap | daysInterval | hour |
      | 4      | Periodic task | with no end date | 14.07.2025 |         | no   | 2            |      |
    When user launches the application
    Then user sees no tasks on day "13.07.2025"
    And user sees tasks on day "14.07.2025"
      | taskName      | taskDescription  | hour | buttonsVisible |
      | Periodic task | with no end date |      | no             |
    When user clicks on card with task name "Periodic task" on day "14.07.2025"
    Then user sees tasks on day "14.07.2025"
      | taskName      | taskDescription  | hour | buttonsVisible |
      | Periodic task | with no end date |      | yes            |
    When user completes task with name "Periodic task" on day "14.07.2025"
    And user sees no tasks on day "14.07.2025"
    And user sees no tasks on day "15.07.2025"
    And user sees tasks on day "16.07.2025"
      | taskName      | taskDescription  | hour | buttonsVisible |
      | Periodic task | with no end date |      | no             |
    And user sees no tasks on day "17.07.2025"
    And user sees tasks on day "18.07.2025"
      | taskName      | taskDescription  | hour | buttonsVisible |
      | Periodic task | with no end date |      | no             |
    And user sees no tasks on day "19.07.2025"
    And user sees tasks on day "20.07.2025"
      | taskName      | taskDescription  | hour | buttonsVisible |
      | Periodic task | with no end date |      | no             |