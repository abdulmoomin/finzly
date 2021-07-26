# Eyalty Micro Service

Eyalty Microservices.


## Compiling
mvn install

## Running
cd userservice-web
mvn spring-boot:run

OR

java -jar ./userservice-web/target/userservice-web-0.0.1-SNAPSHOT.jar

## Accessing the API

### Swagger
http://localhost:8080/userservice-api/swagger-ui.html

### End point 
GET
http://localhost:8080/userservice-api

## DB Configuration Encryption

### DEV Profile

TODO

## Checkstyle 

mvn checkstyle check can be performed using 

```
mvn checkstyle:check
```

mvn checkstyle can be aggregated into a html file

```
mvn checkstyle:checkstyle-aggregate
```

mvn aggregage will result in basic html with line number. In order to have more sophisticated report, 
mvn site plugin can be used

```
mvn site
```

> Please note if you execute this for first time, it is going to take a lot of time to download dependencies.
> Ignore all errors in console as long as build is successful

Once above mvn command is executed, use following command to re-generate checkstyle report

```
mvn checkstyle:checkstyle-aggregate
```

And then open $PROJECT_HOME/target/site/checkstyle-aggregate.html to see generated report. 
Line numbers can be clicked to see source.
Thanks to maven's jxr plugin.

> Note: Checkstyle xml must be used IDE (Intellij/Eclipse) for formatting. 
>Plugins needs to be installed in these ideas and etc/checkstyle.xml will need to be imported

## PDF Documentation

The following command generates pdf and html documentation under $PROJECT_HOME/eyalty-docs/target/reference folder

```
cd eyalty-docs
mvn pre-site
```

## Postgresql 11 : How to connect to database (For Windows)

1) Creating user:-
```
create user eyaltyadmin;
```

2) Providing password to the user:-
```
alter user eyaltyadmin with login password 'eyaltyadmin';
```

3) Providing user (superuser role)
```
ALTER ROLE eyaltyadmin WITH SUPERUSER;
```

4) Create Database:-
```
create database userdb;
```

5)Login using user to access the created db(userdb)
```
psql -d userdb -U eyaltyadmin
```

6) Creating new schema:-
```
create schema userschema;
```

7)Providing/Assigning user to access userschema and userdb

##Altering the eyaltyadmin role to access userschema and userdb
```
alter role eyaltyadmin set search_path to userschema;
alter role eyaltyadmin set search_path to userdb;
alter user eyaltyadmin set search_path to 'userschema';
alter user eyaltyadmin set search_path to 'userschema';
```

##Grant permissions to eyaltyadmin 
```
grant connect on database userdb to eyaltyadmin;
grant usage on schema userschema to eyaltyadmin;
grant select,update,insert,delete on all tables in schema userschema to eyaltyadmin;
grant all privileges on schema userschema to eyaltyadmin;
```

##Postgresql 12 : How to install and configure postgres (For Ubuntu)

1) Install postgresql in ubuntu 20.04
```
sudo apt install postgresql postgresql-contrib
```

2) Check status of postgresql
```
service postgresql status
```

3) To enter postgres command shell
```
sudo -u postgres psql
```

Note: -inside postgres command line 
```
ALTER USER postgres PASSWORD ‘<your own password>’;
```

4) Creating a user in postgres
```
sudo -u postgres createuser –interactive
```

5) Enter inside postgrs terminal
```
psql -U postgres -h localhost
```

6) To check the list of databases
```
\l
```

7) To check the list of roles
```
\du
```

8) To check the list of schemas
```
\dn+
```

9) To check the list of relations
```
\d
```

10) Create a new database
```
create database <db name>
```

11) To connect to postgres db
```
psql -U postgres -h localhost
```

12) Accessing any db

Note:- For a particular/any user, some database must be present.
```
psql -U <username> <dbname>   eg - psql -U eyaltyadmin userdb 

or 

psql -U <dbname> -h localhost    eg – psql -U userdb -h localhost
```
13) Deleting user role (do this outside terminal)
```
sudo su - postgres -c "dropuser <user role>"
```

14) Create new schema
```
create schema <schema name>;
```

15) Providing/Assigning user to access userschema and userdb

a) Altering the eyaltyadmin role to access userschema and userdb:-
```
alter role eyaltyadmin set search_path to userschema;
alter role eyaltyadmin set search_path to userdb;
alter user eyaltyadmin set search_path to 'userschema';
```

b) Grant permissions to eyaltyadmin:-
```
grant connect on database userdb to eyaltyadmin;
grant usage on schema userschema to eyaltyadmin;
grant select,update,insert,delete on all tables in schema userschema to eyaltyadmin;
grant all privileges on schema userschema to eyaltyadmin;
```

16) Dropping schema from db.
```
drop schema <schema name> cascade;
```
Note:- Once schema is dropped, need to enter all commands present in 15a and 15b again. Otherwise DB cannot be accessed.

##How to Generate Self-Signed Certificate

1) Open cmd as administrator.

2) Open your desired destination and run the below command.

keytool -genkeypair -alias selfsigned_localhost_sslserver -keyalg RSA 
-keysize 2048 -storetype PKCS12 -keystore <your desired certificate name>.p12 -validity 3650

3) The certificate will ask first and lastname - value to be given is localhost localhost

4) The certificate will be then generated, which can be used in the IDE to access (https://).

## How to send Email through Spring Boot Application

1) You must have to log into just one gmail account (the one you are using to send email).

-Then go to (https://www.google.com/settings/security/lesssecureapps) and Turn On this feature.

-And last go to (https://accounts.google.com/DisplayUnlockCaptcha) and click Continue.

2) In the application.yml file
 spring:
   mail:
    host: smtp.gmail.com
    port: 587
    username: <provide your email login id>
    password: <provide your email login password>
    properties.mail.smtp:
                 auth: true
                 starttls.enable: true

The above properties are the sender's email information.

3) In forget service


           SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
         
		passwordResetEmail.setFrom(<senders email Id>);
		
		passwordResetEmail.setTo(<customer email Id>);
		passwordResetEmail.setSubject(<Subject to be given>);
		passwordResetEmail.setText(<Password Reset Details >);
			
			
## Running environment variables dyamically

1) The below method enables runtime execution of environment variables.

         
         private static Map<String,String> getModifiableEnvironment() throws Exception{
             Class pe = Class.forName("java.lang.ProcessEnvironment");
             Method getenv = pe.getDeclaredMethod("getenv");
             getenv.setAccessible(true);
             Object unmodifiableEnvironment = getenv.invoke(null);
             Class map = Class.forName("java.util.Collections$UnmodifiableMap");
             Field m = map.getDeclaredField("m");
             m.setAccessible(true);
             return (Map) m.get(unmodifiableEnvironment);
         }
         
2) For adding a specific environment variable, the below code is required

                 
                 getModifiableEnvironment().put("YOUR_VARIABLE",
                    "VARIABLE_VALUE");       
        			
## Prerequisite	for microservices before pushing to docker

In application.yml, include h2-console 
(
h2:
  console:
    enabled: true
    settings:
      web-allow-others: true
    path: /h2-console
)

Add Docker file in the microservice folder

(FROM openjdk:8-jre-alpine

WORKDIR /services

COPY image-service/target/example-service-0.0.1-SNAPSHOT.jar /services

ENTRYPOINT ["java","-jar","/services/example-service-0.0.1-SNAPSHOT.jar"]

EXPOSE (your own port)
)

		
## Docker Commands

To display all the images - “ sudo docker images”


To display all the containers - “ sudo docker ps -a”


To build an image - “ sudo docker build -t <example/userservice-web> . “


To run an image in docker – “sudo docker run -d --name userservice-web -p 8000:8000 <example/userservice-web> “


To remove a specific image – “ sudo docker image rm <Image-ID> -f


To kill all the containers - “ sudo docker kill $(sudo docker ps -aq) “


To remove all the containers - “ sudo docker rm $(sudo docker ps -aq) “


To kill a specific container - “ sudo docker kill <containter ID>”


To remove a specific container - “ sudo docker rm <containter ID>”


To link docker (Service A -> Service B) - “sudo docker run -d --name user-srv --link=reference-service 
-p 8443:8443 faz/userservice-web”


To link docker (Service A -> Service B and C) - “sudo docker run -d --name user-srv 
--link=reference-service --link=image-service -p 8443:8443 faz/userservice-web”			

##How to load text data to CSV File.

create new table in DB(needed feilds only)

create insert statement and compile the script

rightclick on table and go to export option

