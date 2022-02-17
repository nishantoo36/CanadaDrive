Feature: Order page tests

  Background:
    Given Load test Data from "TestData/CanadaDriveCsv.csv" file with reset is "false"
    Given user should be on car page

  @CarTest
  Scenario Outline: Test Car Selection flow
    When user select province from "Province" header and row <row>
    Then user should see selected province
    When user filer car list with "FilterName" "BrandName" and "ModalName" from row <row>
    Then user should see selected filter with car and modal name
    And user should able to see the car list with selected brand and modal
    When user do sorting by "Price: Low to High"
    Then Verify that car list sorted by "asc" order by price
    When user add 3 item as favorite randomly
    Then user should able to see 3 favorite item in favorite list
    When user select any available car from list
    Then user should navigate to the selected car details page
    When user click on start purchase button
    Then user should navigate to price calculation page
    When user set the address for delivery from "Address" header and row <row>
    Then user should able to see selected address
    When user select warranty from "Warranty" header and row <row>
    Then user should able to see price for selected warranty
    Examples:
      |row |
       | 1  |






