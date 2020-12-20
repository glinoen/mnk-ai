# User Guide

## Running the program without cloning
Download the release, go to the directory containing the release and run 
```
 mvn compile exec:java -Dexec.mainClass=mnkgame.Main
```

## Using the program and tests
Clone / download the project and execute all these commands in the directory mnk-ai/mnk-ai

### Running the program


```
 mvn compile exec:java -Dexec.mainClass=mnkgame.Main
```

### Testing

Running tests

```
mvn test
```

Jacoco test report
```
mvn jacoco:report
```

### Checkstyle

```
mvn jxr:jxr checkstyle:checkstyle
```
