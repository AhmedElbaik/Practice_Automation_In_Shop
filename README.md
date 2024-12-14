# QPROS Web Assignment

This project is a Java-based test automation framework for testing the "https://practice.automationtesting.in/shop/" website. The framework supports testing on desktop browsers, mobile browsers, and Selenium Grid for distributed testing.

## Project Structure

The project structure includes the following components:

- `src/main/java/com/practice/automationtesting/`: Core classes of the framework, including `BaseTest`, `DriverFactory`, `AppiumDriverFactory`, and utility classes.
- `src/test/java/com/practice/automationtesting/tests/`: Contains the test classes.
- `resources/`: Holds configuration files such as `appSettings.json`, which defines the settings for the test run.
- `pom.xml`: The Maven Project Object Model (POM) file, which manages the project's dependencies and build process.

## Dependencies

The project uses Maven to manage its dependencies, which include:

- **Selenium WebDriver**: For browser automation on desktop platforms.
- **Appium**: For mobile browser automation on Android devices.
- **TestNG**: The testing framework used for writing and running the tests.
- **Allure**: For generating rich test reports.
- **Java Faker**: For generating test data.
- **Apache Commons IO**: For file management utilities.

## Test Run Types

The project supports three different test run types, controlled by the `testRunType` setting in the `appSettings.json` file:

1. **LOCAL**: Runs tests using Selenium WebDriver on a local desktop browser.
2. **ANDROID**: Runs tests using Appium on an emulated Android device.
3. **GRID**: Runs tests on Selenium Grid for distributed testing across multiple browsers and platforms.

### **GRID Mode**

In **GRID mode**, the tests run on a Selenium Grid, which allows for parallel and distributed execution. The grid setup includes a hub and nodes for different browsers. This mode is particularly useful for cross-browser testing.

#### How to Trigger GRID Mode:
1. Update the `testRunType` in `appSettings.json` to `GRID`:
   ```json
   "testRunType": "GRID"
   ```
2. Ensure the `gridUri` in `appSettings.json` points to your Selenium Grid hub:
   ```json
   "gridUri": "http://localhost:4444"
   ```
3. Start the Selenium Grid using the provided script:
   ```bash
   ./grid-setup.sh start
   ```
4. To stop the Selenium Grid, run:
   ```bash
   ./grid-setup.sh stop
   ```
5. To scale nodes, use:
   ```bash
   ./grid-setup.sh scale <node-type> <count>
   ```
   Example:
   ```bash
   ./grid-setup.sh scale chrome-node 3
   ```

### **ANDROID Mode Prerequisites**

When running in the **ANDROID** mode, ensure the following:

1. **Appium Server**: The Appium server must be running and accessible.
2. **Android Studio**: Install Android Studio and ensure an Android emulator is running.

The `AppiumDriverFactory` class handles the initialization of the Appium driver.

## Prerequisites for Running the Tests

1. **Java Development Kit (JDK)**: Ensure JDK 11 or above is installed.
2. **Maven**: Install Maven to manage dependencies and build the project.
3. **ChromeDriver**: Place the `chromedriver.exe` file in `src/test/resources` for browser testing.
4. **Selenium Grid (optional)**: For GRID mode, ensure the grid is set up and accessible using the `grid-setup.sh` script.
5. **Appium Server (optional)**: For ANDROID mode, ensure the Appium server is running.

## Running the Tests

### **Running All Tests**
Use the following Maven command to execute all tests:
```bash
mvn clean test
```

### **Viewing Test Reports**
Test reports are generated in the `target/allure-results` directory. To view the reports:
1. Install Allure CLI.
2. Serve the reports using the command:
   ```bash
   allure serve target/allure-results
   ```

## Example Test Execution

### **Running in LOCAL Mode**
Ensure the `testRunType` in `appSettings.json` is set to `LOCAL`:
```json
"testRunType": "LOCAL"
```
Run the tests with:
```bash
mvn clean test
```

### **Running in GRID Mode**
1. Start the Selenium Grid:
   ```bash
   ./grid-setup.sh start
   ```
2. Update the `testRunType` in `appSettings.json` to `GRID`.
3. Run the tests:
   ```bash
   mvn clean test
   ```

### **Running in ANDROID Mode**
1. Start the Appium server.
2. Launch the Android emulator.
3. Update the `testRunType` in `appSettings.json` to `ANDROID`.
4. Run the tests:
   ```bash
   mvn clean test
   ```

