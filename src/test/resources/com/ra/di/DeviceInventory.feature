@DeviceInventory
Feature: DeviceInventory testing
  Description: The purpose of this feature is to test DI endpoints
  US: https://ccp.sys.comcast.net/browse/PNP-49489

  @GetDecommissionedDevices
  Scenario Outline: Validate DI get decommissioned devices api
  Description: US: https://ccp.sys.comcast.net/browse/PNP-49489
    When Get Decommissioned Devices API called with page: '<pageNo>' and size: '<pageSize>'
    Then Get Decommissioned Devices API should respond with expectedStatusCode: <expectedStatusCode>
    * Get Decommissioned Devices API should respond with total pages more than 1488
    Then Get Decommissioned Devices API should respond device id 102

    Examples:
      | pageNo | pageSize | expectedStatusCode |
      | 0      | 2        | 200                |
