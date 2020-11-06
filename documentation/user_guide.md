# User Guide

Execute all these commands in the directory mnk-ai/mnk-ai

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
