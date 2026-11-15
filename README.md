# Selenium POM Login Example

A small Java + Selenium + TestNG example that uses the Page Object Model to test a demo login page.

## Requirements

- Java 17 or newer
- Maven
- Google Chrome

## Run the test

From the project folder, run:

```text
mvn test
```

Selenium Manager, included with Selenium, will locate or download the matching ChromeDriver when needed.

After the run, open `target/extent-report.html` to view the ExtentReports HTML report. Failed-test screenshots are saved under `target/screenshots` and attached to the matching report entry.

The demo URL and credentials are in `src/test/resources/config.properties`. Replace them with values for your test application when you are ready. Do not put real passwords in a file that is committed to source control.

## How Maven Surefire finds tests

When `mvn test` runs, Maven Surefire looks for compiled test classes in `target/test-classes`. By default, it includes classes matching patterns such as `Test*.java`, `*Test.java`, `*Tests.java`, and `*TestCase.java`. `LoginTest.java` matches the `*Test.java` pattern.

Because TestNG is a project dependency, Surefire uses its TestNG provider to run the test methods annotated with `@Test`. The class naming pattern helps Surefire find test classes; the `@Test` annotation tells TestNG which methods to execute. This project does not currently use a `testng.xml` suite file.

Surefire also writes its standard test results to `target/surefire-reports`. This is separate from the ExtentReports HTML report at `target/extent-report.html`.

## Run in Jenkins

The root `Jenkinsfile` checks out the selected branch, runs `mvn clean test` with headless Chrome, publishes Surefire test results, and archives the Extent report and any failure screenshots.

The Jenkins agent needs Java 17 or newer, Maven available on `PATH`, and Google Chrome installed. Selenium Manager resolves the matching ChromeDriver, so the agent needs network access when it must download a driver.

Create a **Multibranch Pipeline** job for this GitHub repository so Jenkins can discover and build branches that contain a `Jenkinsfile`. After configuring the repository source, scan the repository; Jenkins will create jobs for discovered branches.
