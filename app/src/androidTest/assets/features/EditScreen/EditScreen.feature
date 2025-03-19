Feature: Ability to add and edit tasks

  Background:
    Given current date is "13.07.2025"

  Scenario: The one where customer adds new ASAP task
    Given user has no saved tasks
    When user launches the application
    Then add task button is visible
    And user sees no tasks on day "13.07.2025"
    When user clicks on add task button
    Then edit task screen is visible
    And task name field has text ""
    And task description field has text ""
    And selected task type is "ASAP"
    And start date selection field is "not visible"
    And hour selection field is "not visible"
    And end date selection field is "not visible"
    And days interval field is "not visible"
    And delete button is visible
    And save button is visible
    When user enters task name "Some task"
    And user enters task description "Some description"
    And user clicks save button
    Then user sees tasks on day "13.07.2025"
      | taskName  | taskDescription  | buttonsVisible |
      | Some task | Some description | no             |

  Scenario: The one where customer adds new one time task
    Given user has no saved tasks
    When user launches the application
    Then add task button is visible
    And user sees no tasks on day "13.07.2025"
    When user clicks on add task button
    Then edit task screen is visible
    And task name field has text ""
    And task description field has text ""
    And selected task type is "ASAP"
    And start date selection field is "not visible"
    And hour selection field is "not visible"
    And end date selection field is "not visible"
    And days interval field is "not visible"
    And delete button is visible
    And save button is visible
    When user changes task type to "Specific day"
    Then start date selection field is "visible"
    And hour selection field is "visible"
    And end date selection field is "not visible"
    And days interval field is "not visible"
    When user enters task name "Some task"
    And user enters task description "Some description"
    And user clicks save button
    Then user sees tasks on day "13.07.2025"
      | taskName  | taskDescription  | buttonsVisible |
      | Some task | Some description | no             |

  Scenario: The one where customer adds new periodic task
    Given user has no saved tasks
    When user launches the application
    Then add task button is visible
    And user sees no tasks on day "13.07.2025"
    When user clicks on add task button
    Then edit task screen is visible
    And task name field has text ""
    And task description field has text ""
    And selected task type is "ASAP"
    And start date selection field is "not visible"
    And hour selection field is "not visible"
    And end date selection field is "not visible"
    And days interval field is "not visible"
    And delete button is visible
    And save button is visible
    When user changes task type to "Periodic"
    Then start date selection field is "visible"
    And hour selection field is "visible"
    And end date selection field is "visible"
    And days interval field is "visible"
    When user enters task name "Some task"
    And user enters task description "Some description"
    And user enters days interval of "1"
    And user clicks save button
    Then user sees tasks on day "13.07.2025"
      | taskName  | taskDescription  | buttonsVisible |
      | Some task | Some description | no             |
    Then user sees tasks on day "14.07.2025"
      | taskName  | taskDescription  | buttonsVisible |
      | Some task | Some description | no             |
    Then user sees tasks on day "15.07.2025"
      | taskName  | taskDescription  | buttonsVisible |
      | Some task | Some description | no             |
    Then user sees tasks on day "16.07.2025"
      | taskName  | taskDescription  | buttonsVisible |
      | Some task | Some description | no             |
    Then user sees tasks on day "17.07.2025"
      | taskName  | taskDescription  | buttonsVisible |
      | Some task | Some description | no             |
    Then user sees tasks on day "18.07.2025"
      | taskName  | taskDescription  | buttonsVisible |
      | Some task | Some description | no             |
    Then user sees tasks on day "19.07.2025"
      | taskName  | taskDescription  | buttonsVisible |
      | Some task | Some description | no             |
    Then user sees tasks on day "20.07.2025"
      | taskName  | taskDescription  | buttonsVisible |
      | Some task | Some description | no             |

  Scenario: The one where customer changes one time task to periodic
    Given the user has saved tasks
      | taskID | taskName  | description | startDate  | endDate | asap | daysInterval | hour |
      | 2      | Some task |             | 13.07.2025 |         | no   |              |      |
    When user launches the application
    Then user sees tasks on day "13.07.2025"
      | taskName  | taskDescription | hour | buttonsVisible |
      | Some task |                 |      | no             |
    When user clicks on card with task name "Some task" on day "13.07.2025"
    When user click on edit button of task with name "Some task" on day "13.07.2025"
    Then edit task screen is visible
    And task name field has text "Some task"
    And task description field has text ""
    And selected task type is "Specific day"
    And start date selection field is "visible"
    And hour selection field is "visible"
    And end date selection field is "not visible"
    And days interval field is "not visible"
    And delete button is visible
    And save button is visible
    When user changes task type to "Periodic"
    Then start date selection field is "visible"
    And hour selection field is "visible"
    And end date selection field is "visible"
    And days interval field is "visible"
    When user enters task description "Some description"
    And user enters days interval of "1"
    And user clicks save button
    Then user sees tasks on day "13.07.2025"
      | taskName  | taskDescription  | buttonsVisible |
      | Some task | Some description | no             |
    Then user sees tasks on day "14.07.2025"
      | taskName  | taskDescription  | buttonsVisible |
      | Some task | Some description | no             |
    Then user sees tasks on day "15.07.2025"
      | taskName  | taskDescription  | buttonsVisible |
      | Some task | Some description | no             |
    Then user sees tasks on day "16.07.2025"
      | taskName  | taskDescription  | buttonsVisible |
      | Some task | Some description | no             |
    Then user sees tasks on day "17.07.2025"
      | taskName  | taskDescription  | buttonsVisible |
      | Some task | Some description | no             |
    Then user sees tasks on day "18.07.2025"
      | taskName  | taskDescription  | buttonsVisible |
      | Some task | Some description | no             |
    Then user sees tasks on day "19.07.2025"
      | taskName  | taskDescription  | buttonsVisible |
      | Some task | Some description | no             |
    Then user sees tasks on day "20.07.2025"
      | taskName  | taskDescription  | buttonsVisible |
      | Some task | Some description | no             |

  Scenario: The one where user tries to add task with empty name
    Given user has no saved tasks
    When user launches the application
    Then add task button is visible
    And user sees no tasks on day "13.07.2025"
    When user clicks on add task button
    Then edit task screen is visible
    And task name field has text ""
    And task name validation text is "not visible"
    When user clicks save button
    Then task name validation text is "visible"
    When user enters task name "Some task"
    And task name validation text is "not visible"
    When user clicks save button
    Then user sees tasks on day "13.07.2025"
      | taskName  | buttonsVisible |
      | Some task | no             |

  Scenario: The one where user deletes periodic task
    Given the user has saved tasks
      | taskID | taskName      | startDate  | asap | daysInterval | hour  |
      | 6      | Periodic task | 13.07.2025 | no   | 1            | 16:18 |
    When user launches the application
    When user clicks on card with task name "Periodic task" on day "13.07.2025"
    When user click on edit button of task with name "Periodic task" on day "13.07.2025"
    Then edit task screen is visible
    And task name field has text "Periodic task"
    When user clicks delete button
    And user sees no tasks on day "13.07.2025"
    And user sees no tasks on day "14.07.2025"
    And user sees no tasks on day "15.07.2025"
    And user sees no tasks on day "16.07.2025"
    And user sees no tasks on day "17.07.2025"
    And user sees no tasks on day "18.07.2025"
    And user sees no tasks on day "19.07.2025"
    And user sees no tasks on day "20.07.2025"