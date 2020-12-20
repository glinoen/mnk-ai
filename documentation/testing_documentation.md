# Testing documentation

Testing is done using JUnit framework. Most of the tests are unit tests. The ai is tested to make the right decision in crucial situations. There was also a lot of manual testing. 

## Code coverage and static analysis

### jacoco
![jacoco][jacoco.jpg]

### checkstyle
![checkstyle][checkstyle.jpg]

All of the checkstyle errors are MethodLength errors. I wasn't able to refactor these methods in to smaller pieces

## Running the tests
After cloning the project cd to the inner mnk-ai directory and run 
```
mvn test
```

## Summary
Considering the subject I didn't find performance testing really useful. The purpose of the project was to make an ai that makes its move in reasonable time. In Gomoku(tic tac toe on 15 x 15 board) ai tournament bots are allowed to think for 30 seconds, so I used that time limit as a rear boundary in manual testing.