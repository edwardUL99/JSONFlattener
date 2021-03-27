# JSONFlattener
JSONFlattener is a Java application that helps flatten a JSON hierarchy
into a single level JSON object.

## Requirements
You need minimum Java 1.7 installed to run the application

## Installation
Use the maven wrapper provided (On Windows, use the mvnw.cmd file and on Linux, use ./mvnw).

To install to a JAR, run the following command on Linux:
```bash
./mvnw clean install
```

or on Windows:
```cmd
mvnw clean install
```

## Test Cases
There are some test cases defined that can be run with the following on Linux:
```bash
 ./mvnw test
```

or on Windows:
```cmd
 mvnw test
```

## Usage
The tool can be used by using the pipe operator to pass in JSON input via stdin

Piping:
```bash
 cat test.json | java -jar target/FlattenJson-1.0.jar
```

To output the result to a file, you can direct the output to a file, for example:
```bash
 cat test.json | java -jar target/FlattenJson-1.0.jar > test-flattened.json
```

## Examples
The following file is our test.json:

```json
{
    "a": 1,
    "b": true,
    "c": {
        "d": 3,
        "e": "test"
    }
}
```

Run with the following command:
```bash
cat test.json | java -jar target/FlattenJson-1.0.jar 
```

It produces:

```json
{
    "a": 1,
    "b": true,
    "c.d": 3,
    "c.e": "test"
}
```