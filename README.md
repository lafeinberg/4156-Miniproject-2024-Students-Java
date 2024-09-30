# Welcome Students of 4156

# COMS-4156-Project
This repo is for COMS 4156: Advanced Software Engineering at Columbia University Spring 2024.

## Building and Running a Local Instance
These are the specifications for running on your local machine, using an ARM 64 MAC.

1. Maven 3.9.5: Use the instructions on Maven site: https://maven.apache.org/download.cgi 
2. JDK 17: This can be downloaded on oracle website for your specific machine. 
3. Editing: You can use intelliJ or VSCode. I used VSCode. 
4. Clone this repo to your machine! git clone ...
5. Then to set up the project, navigate to ~/IndividualProject directory and run the following command <mvn spring-boot:run -Dspring-boot.run.arguments="setup">
6. Now, you can run the stylechecker by running <mvn checkstyle:check>
7. You can run the tests using <mvn clean test>
8. You can clean install the project using <mvn clean install>
9. Generate the jacoco report by running <mvn jacoco:report>


## Running a Cloud based Instance
The followin is using Google Cloud App Enginer[https://cloud.google.com/appengine/docs/an-overview-of-app-engine](url)
1. Follow the link to download gcloud CLI (link: [https://cloud.google.com/sdk/docs/install](url))
2. Run the following on your terminal for initalizing and setting up your SDK:
```
gcloud init
```
3. Create a Google Cloud account and create a new project. Navigate to the console, and add an app.yaml file for confifuration, with the following contents: 
```
entrypoint: java -jar target/IndividualProject-0.0.1-SNAPSHOT.jar
runtime: java17
instance_class: F1
```
4. In your terminal run:
```
gcloud config set project PROJECT_ID
```
replacing `PROJECT_ID` with your GCP Project ID.
5. Deploy app:
```
gcloud app deploy
```
6. Retrieve URL for the app, and open in browser using this command:
```
gcloud app browse
```
7. Now, you can finally test your app using [Postman](https://www.postman.com/). Please reference postman documentation to test endpoints and PATCH/GET requests.

## Running Tests
The unit tests are within the path 'src/test'. Remember to first build your project.
To see the test results, run:
```
mvn clean test
```
To generate Jacoco report: 
```
mvn jacoco:report
```

# Report Documentation
## Style Check Report
To generate the stylecheck, run
```
mvn checkstyle:check
```
This yields zero checkstyle violations or warnings: 
<img src="https://github.com/lafeinberg/4156-Miniproject-2024-Students-Java/blob/main/stylecheck.png">

## Jacoco Report
To generate the jacoco report, run
```
mvn jacoco:report
```
This yields the following report:
<img src="https://github.com/lafeinberg/4156-Miniproject-2024-Students-Java/blob/main/jacoco_report.png>

We can see the branch coverage > %55

# Development Process

## Static bug checking using PMD:
The following commands can be used for the corresponding files:
1. Course.java:
```
pmd check -d ~/4156-Miniproject-2024-Students-Java/IndividualProject/src/main/java/dev/coms4156/project/individualproject/course.java -R rulesets/java/quickstart.xml -f text
```
2. Department.java:
```
pmd check -d ~/4156-Miniproject-2024-Students-Java/IndividualProject/src/main/java/dev/coms4156/project/individualproject/department.java -R rulesets/java/quickstart.xml -f text
```
3. IndividualProjectApplication.java:
```
pmd check -d ~/4156-Miniproject-2024-Students-Java/IndividualProject/src/main/java/dev/coms4156/project/individualproject/IndividualProjectApplication.java -R rulesets/java/quickstart.xml -f text
```
4. MyFileDatabase.java:
```
pmd check -d ~/4156-Miniproject-2024-Students-Java/IndividualProject/src/main/java/dev/coms4156/project/individual project/MyFileDatabase.java -R rulesets/java/quickstart.xml -f text
```
5. RouteController.java:
```
pmd check -d ~/4156-Miniproject-2024-Students-Java/IndividualProject/src/main/java/dev/coms4156/project/individual project/RouteController.java -R rulesets/java/quickstart.xml -f text
```
