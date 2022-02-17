# Selenium Cucumber Framework

---

## Project Purpose
This project aims to give a sample of automation test framework that uses Selenium, Cucumber and TestNG with Java as the programming language.

##Automated Problem
1. Navigate to https://shop.canadadrives.ca/cars/bc
2. Select Ontario Province
3. Filter RAM 1500 vehicles using Make/Model filter
4. Sort by Price Low to High
5. Favorite 3 RAM 1500 vehicles
6. Pick an available RAM 1500 vehicle
7. Click on Get Started
8. In Calculate delivery, Enter Toronto Address
9. Select 48 Months warranty


## Tools and Libraries
This project using 3 main tools, Selenium, Cucumber,TestNG.
On the other hand, I am using some tools that support this great framework such as Maven.
The complete list of tools, you can see in the `pom.xml` file.

## Requirements
* Java Development Kit
* Maven
* WebDriver, using ChromeDriver

## Running Tests
* Clone the repository from your fork to this directory
* Open the project using any Java IDE
* Run the tests with the script below
```shell
$ mvn clean install
```
* If you want to run the specific test, use the cucumber tags like this
```shell
$ mvn test -Dcucumber.options="--tags @CarTest"
```

## Test Results
* Test report automatically generated on `target` folder after finished the test execution
* See test report from `target/cucumber-reports/advanced-reports/cucumber-html-reports/overview-features.html
* You can also share your Cucumber Report with another person at https://reports.cucumber.io, just go to `src/test/resources/cucumber.properties` then change the value to be `true`
```properties
cucumber.publish.enabled=true
```
* For more information about reports cucumber you can go to https://reports.cucumber.io/docs/cucumber-jvm

### References
* https://cucumber.io/docs/installation/java/
* https://www.selenium.dev/documentation/en/
* https://www.toolsqa.com/cucumber-automation-framework/
* https://www.w3schools.com/java/
* https://www.oracle.com/java/technologies/javase/codeconventions-introduction.html
