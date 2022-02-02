# Purpose
This is a general purpose application used to support the needs of the robotics scouting team. This application interacts with a 
general database that can be used to create any type of survey needed for keeping track of important scouting data. This 
scouting data tracks the performance of different teams at competitions.

# Installation
This project creates a self contained jar that has everything needed to run. 
It is assuming that the database is installed on the local machine and is already set up with a scouting schema 
and a user, scout, that has access to that schema.

If you do not have MySQL installed, you will need to install it locally.  Here is the [MySQL Installer](https://dev.mysql.com/downloads/installer/)
You will want to install the server and the workbench. Once installed you can import the Dump.sql file that is 
located in the "database" directory of the project. You can do this with MySQL Workbench. Use the import/export commands.
This dump file will create the database and insert all the data from the previous
competitions into the database. Then run the `createUser.sql` script in MySQL Workbench. This will create the scout user
and set the password and permissions for the user.

Once the jar is built it can be run by executing this at the command line: `java -jar ./scouting.jar`
See https://docs.spring.io/spring-boot/docs/current/reference/html/deployment-install.html for deploying in unix.

An amazon account AWS account has been created and the jar is deployed to an EC2 instance. 
The database is deployed as a separate database instance using MySQL.


# Building
First ensure that you have the Oracle Java JDK installed. If not you will need to download it from Oracle. Get the 64 bit JDK.
Here is a link you can go to: [Oracle JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

Simply check out this complete project to your local system. This is a gradle wrapper project so everything is contained in the project. 
Once checked out you can build and run this on the command line simply by typing this one
command in the top directory of where you checked out the project locally:  

`gradlew.bat clean build bootrun`

This command cleans the build directory, builds the application, and then runs it. Once the application is running 
you can type CTRL+C to terminate it.

# Helpful Application Links

- The main page is: http://localhost:8082
- The main survey page is: http://localhost:8082/scouting.html
- The main results page is: http://localhost:8082/results.html

The link to the Swagger docs is: http://localhost:8082/swagger-ui.html

The other links are:
- GET: http://localhost:8080/scouting/events
- GET: http://localhost:8080/scouting/students
- POST: http://localhost:8080/scouting/responses
    
The GET events link will return all the data needed for the current event and the survey.  
The other GET students link will return all the active students.

Going to the swagger document will show you the entire api and allow you to interact with it.

The plan is that the main data entry screen will be at http://localhost:8080/scouting.
I have now added a menu to make it easier to navigate to the different screens

This project uses bootstrap-4.0.0-alpha.6-dist

# Work left to Do
See the "Issues" section in GitHub.

#Setting up an event
Go to https://www.thebluealliance.com/apidocs/v3 and look up the new events. 
The request URL is: https://www.thebluealliance.com/api/v3/events/2020/simple. 
Use the auth key found in BlueAllianceClient.groovy which is `alXSYHeSPE3hFXTQMzYYYo4vkqra4S5RuvWXgEuPbVqFpCtxkc4paUvJr4OyHOcy`
In the response locate the events you want to set up. You will need to get the 'key'
and the start and end dates. Add one day to the end day. This data needs to be inserted
into the event table. Then create the survey and link the event to the survey.
Once the event is setup in the blue alliance the system will download the data and
set up all the teams and matches. This could be automated by knowing the event name and year.

For creating a new survey first add the data in the survey table. Generally you will create
two new surveys, one for pit scouting and one for match scouting. Then associate the new 
surveys to an event. I always associate them to event_id = 1 which is the test survey.
Then create new entries in the survey_section table for the different sections of the new
survey. Then create the questions in the question table. Link the question to the section
it should belong to. Look at the question_type table and response_value table when creating
the questions to know what question type to use. The sequence number is only valid within 
the given section. You can start over at 1 for the different sections.

SQL used to create a survey:

`select * from event order by start_date;`

`select * from survey;`

`select * from event_survey order by event_id, survey_id;`

`select * from survey_section order by survey_id, sequence;`

`select * from question order by survey_section_id, sequence;`

`select * from question_type;`

`select * from response_value order by question_type_id;`

In MySQL you can edit the result grids like a spreadsheet and enter new data. When
done insert statements will be generated that you can execute. Once the data is inserted
the stuff is created and should be immediately viewable on the website.

#Thoughts on survey
page that displays choice groups and choices that can be reused on questions. allow editing of choices that have not been used yet.
