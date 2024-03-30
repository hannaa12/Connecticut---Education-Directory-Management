# app-project-backend

## Project Information

- Language Used: Java (v11)
- Server Interactions though: API
- Framework used for API Server: Spring (Boot)

## About the API (Dataset) used

- Source: Public data provided by Connecticut State Agencies
- Dataset Link: https://data.ct.gov/Education/Education-Directory/9k2y-kqxn
- API Link: https://data.ct.gov/resource/9k2y-kqxn.json
- Information: This dataset contains the official listing of all public educational organizations in Connecticut. Data
  elements include district name, school name, organization type, organization code, address, open date, interdistrict
  magnet status and grades offered.

### Why we used this API:

- It is available to the public for usage.
- The data is for a real-world scenario.
- It can be broken down into multiple logical tables.
- Practical parameterized queries can be used for this data

---

## Setting up the database

- Install and run postgres locally.
- Update Postgres the following connection properties in ```src/main/resources/application.properties```
    - ```database.school.url```
    - ```database.school.username```
    - ```database.school.password```
- Run the sql commands in the file ```database.sql``` to create the schema in the postgres.

## Running the Project Server

- Install Java 11. Verify by running the command: ```java --version```
- Install Maven. Verify by running the command: ```mvn --version```
- Run the server using the command: ```mvn spring-boot:run```

## Running tests

- Run the command: ```mvn test```
- Update Postgres test database connection properties in ```src/test/resources/application.properties```
- Run the tests using the command ```mvn test```

## Package the Project Server

- The following file can be used to create a JAR file of the server: ```mvn package```
- The JAR file will be produced under the ```target``` folder. Example file: ```target/project-0.0.1-SNAPSHOT.jar```