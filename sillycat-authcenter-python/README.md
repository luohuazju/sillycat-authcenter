# Platform Engineering Challenge

##AuthAPI Project

I did some JWT codes before in Python/Java/PHP, so I will choose Python to do it.
I will use FastAPI to host the HTTP RESTful requests for me. To make it easy and simple, here 
is what I will implement.

swagger docs --> FastAPI RESTful --> PostgresSQL

## how to run
If you have docker on your machine and make command on your machine.
Command to start a simple postgres 

here is how the running requirements

```shell
export CONSUL_USER=username
export CONSUL_PASSWORD='password'
export CONSUL_HOST=https://xxx.xxx.io
```


```shell
make run-db
```
Command to stop the database
```shell
make clean-db
```
Command to run SQL in database
```shell
make enter-db
```

Once the database is running fine
Command to prepare the python ENV
```shell
make build
```
Command to start the FastAPI
```shell
make run
```
Command to check the log
```shell
make log
```
Command to stop the FastAPI
```shell
make clean
```

If the AuthAPI and database docker containers are all running fine, visit this swagger pages for testing and demo
http://localhost:8000/docs#

JSON file is here

http://localhost:8000/openapi.json


## Description

Design and implement a RESTful web service to facilitate a user authentication system. The authentication mechanism should be *token based*. Requests and responses should be in **JSON**.

## Requirements

**Models**

The **User** model should have the following properties (at minimum):

1. name
2. email
3. password

You should determine what, *if any*, additional models you will need.

**Endpoints**

All of these endpoints should be written from a user's perspective.

1. **User** Registration
2. Login (*token based*) - should return a token, given *valid* credentials
3. Logout - logs a user out
4. Update a **User**'s Information
5. Delete a **User**

**README**

Please include:
- a readme file that explains your thinking
- how to setup and run the project
- if you chose to use a database, include instructions on how to set that up
- if you have tests, include instructions on how to run them
- a description of what enhancements you might make if you had more time.

**Additional Info**

- We expect this project to take a few hours to complete
- You can use Rails/Sinatra, Python, Go, node.js or shiny-new-framework X, as long as you tell us why you chose it and how it was a good fit for the challenge. 
- Feel free to use whichever database you'd like; we suggest Postgres. 
- Bonus points for security, specs, etc. 
- Do as little or as much as you like.

Please fork this repo and commit your code into that fork.  Show your work and process through those commits.

