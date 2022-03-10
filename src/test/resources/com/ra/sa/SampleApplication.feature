@SampleApplication
Feature: DeviceInventory testing
  Description: The purpose of this feature is to test ReqRes sample application endpoints

  @GetUsers
  Scenario Outline: Validate SA get users api
  Description: Validate SA get users api
    When Get users api called with page: '<pageNo>' and size: '<pageSize>'
    Then 'Get users' api should respond with expectedStatusCode: <expectedStatusCode>
    * Get users api response should not be empty
    * Get users api response should match the schema
    * Get users api should respond with total pages more than 1
    * Get users api should respond with 10 users
    * Extract first user's email from response
    * Get users api should respond user id 1
    And Get users api should respond user ids '1,2,3,4,5,6,7,10'
    And Get users api should respond user ids in order '1,2,3,4,5,6,7,10'

    Examples:
      | pageNo | pageSize | expectedStatusCode |
      | 0      | 10        | 200                |

  @GetUser
  Scenario Outline: Validate SA get user api
  Description: Validate SA get user api
    When Get user api called with userId: '<userId>'
    Then 'Get user' api should respond with expectedStatusCode: <expectedStatusCode>
    * Get user api response should match the schema

    Examples:
      | userId | expectedStatusCode |
      | 2      | 200                |

  @CreateUser
  Scenario Outline: Validate SA create user api
  Description: Validate SA create user api
    When Post user api called with name: '<name>' and job: '<job>'
    Then 'Post user' api should respond with expectedStatusCode: <expectedStatusCode>
    And Post user api respond match the schema with name: '<name>' and job: '<job>'

    Examples:
      | name      | job | expectedStatusCode |
      | stringify | dev | 201                |

  @UpdateUser
  Scenario Outline: Validate SA update user api
  Description: Validate SA update user api
    When Put user api called to update user: '<userId>' with name: '<name>' and job: '<job>'
    Then 'Put user' api should respond with expectedStatusCode: <expectedStatusCode>
    * Put user api respond match the schema with name: '<name>' and job: '<job>'

    Examples:
      | userId | name      | job | expectedStatusCode |
      | 2      | stringify | dev | 200                |

  @DeleteUser
  Scenario Outline: Validate SA delete user api
  Description: Validate SA delete user api
    When Delete user api called to update user: '<userId>'
    Then Delete user api should respond with expectedStatusCode: <expectedStatusCode>

    Examples:
      | userId | expectedStatusCode |
      | 2      | 204                |