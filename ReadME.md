Welcome!

This is a Spring MVC application. Its the backend of the Plagiarizer that we are building.

JAVA_HOME should be set for this to work. To set: https://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t/

Make sure that no other application is running on port 8080. Usually skype runs on port 8080. Quit these applications 
before running the "java -jar target/PlagiarismDetector-0.0.1-SNAPSHOT.jar" command.

To run the application:

1. Clone the repo to your local and open a Terminal/Command prompt and navigate to the path where this file exists
2. Once you are in the root of directory "PlagiarismDetector". 

For MAC OS users:

1. run the following command : "./mvnw clean install" - This would run all the tests and generate a jar file for execution
2. run the following command : "java -jar target/PlagiarismDetector-0.0.1-SNAPSHOT.jar"
3. The above should run the server in port 8080. The url is "http://localhost:8080"

For Windows users:

1. run the following command : "mvnw.cmd clean install" - This would run all the tests and generate a jar file for execution
2. run the following command : "java -jar target/PlagiarismDetector-0.0.1-SNAPSHOT.jar"
3. The above should run the server in port 8080. The url is "http://localhost:8080"