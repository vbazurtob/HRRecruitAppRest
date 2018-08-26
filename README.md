# HRRecruitAppRest
REST endpoints implementation to interact with the 
[HRRecruitApp DB](https://github.com/vbazurtob/HRRecruitApp).
This project makes use of the Spring boot 2 framework and Java 8 features.

### Live Demo
You can access and interact with the API with a live demo located 
[here](https://hrrecruitapprest.herokuapp.com/)

### Download the code

Create a folder for containing the project source code, access the created folder and use git clone with the project url.

````
mkdir hrrecruitapprest
cd hrrecruitapprest
git clone https://github.com/vbazurtob/HRRecruitAppRest.git .
````


### Setting the database

Check the instructions available in the
[HRRecruitApp project page](https://github.com/vbazurtob/HRRecruitApp) 
for setting the database.

### Building the source code

This project uses gradle for building and testing the application. Three 
profile application configuration files are provided: default, dev and release.

For local testing purposes you can set the current profile to be used 
at the beginning of the build.gradle file by modifying the ``profiles`` variable.

````
//Three profiles are available: default, dev and release
def profiles = 'dev'
````

After setting your selected profile to ``dev``, you must run from the root folder of the project :

````
./gradlew build
./gradlew bootRun
````


Application will be ready to access in http://localhost:8080/


### Credits
Voltaire Bazurto Blacio. All rights reserved 2018.