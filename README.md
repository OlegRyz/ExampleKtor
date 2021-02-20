# ExampleKtor

Simple RestAPI example on Kotlin using Ktor

## Run

Checkout the project and run ./gradlew run

After you see EXECUTING open http://127.0.0.1:8080. If you see "Hello from Ktor" then server is up and running.

## Query API

API implements simple StateMachine with the foollowing possible states:

- Starting
- Started
- Waiting
- Executing
- Stopping
- Stopped
  
With this allowed transitions:
 
- Stopped -> Started
- Started -> Waiting
- Waiting -> Executing
- Executing -> Waiting
- Waiting -> Stopping

API has a PUT query http://127.0.0.1:8080/state that takes a JSON as a parameter. 
JSON hasonly one property "state". 
This query updates an internal value according to allowed transitions.

Body example:
{"state": "Started"}

You could get a current state by calling GET on http://127.0.0.1:8080/state

