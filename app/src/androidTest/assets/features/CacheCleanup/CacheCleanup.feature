Feature: Application performing periodic outdated tasks cleanup

  Background:
    Given current date is "13.07.2025"

  Scenario: The one where more than 7 days passed and the application performs cleanup
    Given the last cleanup date is "01.07.2025"
    And the user has saved tasks
      | taskID | taskName            | startDate  | endDate    | asap | daysInterval |
      | 1      | periodic task       | 03.07.2025 | 12.07.2025 | no   | 2            |
      | 2      | one time task       | 03.07.2025 |            | no   |              |
      | 3      | other periodic task | 03.07.2025 | 19.07.2025 | no   | 2            |
      | 4      | asap task           | 03.07.2025 |            | yes  |              |
    When user launches the application
    Then tasks saved in cache are
      | savedTaskId |
      | 3           |
      | 4           |

  Scenario: The one where less than 7 days passed and the cleanup is skipped
    Given the last cleanup date is "10.07.2025"
    And the user has saved tasks
      | taskID | taskName            | startDate  | endDate    | asap | daysInterval |
      | 1      | periodic task       | 03.07.2025 | 12.07.2025 | no   | 2            |
      | 2      | one time task       | 03.07.2025 |            | no   |              |
      | 3      | other periodic task | 03.07.2025 | 19.07.2025 | no   | 2            |
      | 4      | asap task           | 03.07.2025 |            | yes  |              |
    When user launches the application
    Then tasks saved in cache are
      | savedTaskId |
      | 1           |
      | 2           |
      | 3           |
      | 4           |