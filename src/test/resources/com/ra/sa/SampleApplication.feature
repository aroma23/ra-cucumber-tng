@SampleApplication
Feature: DeviceInventory testing
  Description: The purpose of this feature is to test ReqRes sample application endpoints

  @GetUsers
  Scenario Outline: Validate SA get users api
  Description: Validate SA get users api
    When Get users api called with page: '<pageNo>' and size: '<pageSize>'
    Then 'Get users' api should respond with expectedStatusCode: <expectedStatusCode>
    Then Get users api response should match the schema
    Then Get users api should respond with total pages more than 1
    Then Get users api should respond user id 1

    Examples:
      | pageNo | pageSize | expectedStatusCode |
      | 0      | 2        | 200                |

  @GetUser
  Scenario Outline: Validate SA get user api
  Description: Validate SA get user api
    When Get user api called with userId: '<userId>'
    Then 'Get user' api should respond with expectedStatusCode: <expectedStatusCode>
    Then Get user api response should match the schema

    Examples:
      | userId | expectedStatusCode |
      | 2      | 200                |

  @CreateUser
  Scenario Outline: Validate SA create user api
  Description: Validate SA create user api
    When Post user api called with name: '<name>' and job: '<job>'
    Then 'Post user' api should respond with expectedStatusCode: <expectedStatusCode>
    Then Post user api respond match the schema with name: '<name>' and job: '<job>'

    Examples:
      | name      | job | expectedStatusCode |
      | stringify | dev | 201                |

  @UpdateUser
  Scenario Outline: Validate SA update user api
  Description: Validate SA update user api
    When Put user api called to update user: '<userId>' with name: '<name>' and job: '<job>'
    Then 'Put user' api should respond with expectedStatusCode: <expectedStatusCode>
    Then Put user api respond match the schema with name: '<name>' and job: '<job>'

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