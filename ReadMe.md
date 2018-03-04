# Purpose
This is a general purpose application used to support the needs of the robotics scouting team. This application interacts with a 
general database that can be used to create any type of survey needed for keeping track of important scouting data. This 
scouting data tracks the performance of different teams at competitions.

# Installation
This project creates a self contained jar that has everything needed to run. 
It is assuming that the database is installed on the local machine and is already set up with a scouting schema 
and a user, scout, that has access to that schema.

If you do not have MySQL installed, you will need to install it locally.  Here is the [MySQL Installer](https://dev.mysql.com/downloads/installer/)
You will want to install the server and the workbench. Once installed you can run the creation_ddl.sql and the inserts.sql scripts
that are located in the database directory of the project. These scripts will create the database and insert some 
sample. Then create a user with the name of 'scout' and a password of 'password' that has privileges to the scouting 
scheme. This can be done in the workbench. Someday I will export that SQL into a script.

Once the jar is built it can be run by executing this at the command line: `java -jar ./scouting.jar`

The idea is to install the database and this application on a dedicated laptop that is brought to the competitions.
This laptop is attached to a dedicated router, thus creating its own private network. 
People are then able to connect to this network and interact with the system. Currently this network is:
Millerbots and the passcode is millerbots2549.
This dedicated computer would be configured to have the application and database all start when the system starts.

An alternative plan is to deploy this to AWS on an EC2 instance. 
This would provide a nice stable environment. We could even use AWS databases, 
which would even be more stable. The disadvantage of this approach is that we might not 
have very good internet access at the competitions.

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

The link to the Swagger docs is: http://localhost/scouting/swagger-ui.html

The other links are:
- GET: http://localhost/scouting/events
- GET: http://localhost/scouting/students
- POST: http://localhost/scouting/responses
    
The GET events link will return all the data needed for the current event and the survey.  
The other GET students link will return all the active students.

Going to the swagger document will show you the entire api and allow you to interact with it.

The plan is that the main data entry screen will be at http://localhost/scouting

This project uses bootstrap-4.0.0-alpha.6-dist

There are currently no plans for a menu
