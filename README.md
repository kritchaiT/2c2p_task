# 2c2p_task

Thank you for your time and consideration. I would like to submit the assessment task received from your email.

--------------------------------------------------------------------------------
### Proclaimation

Start by running the "App.java" as an entry point

Using CHATGPT for `generating` the test cases - to comprehend the unit test
Using CHATGPT for assisting `CLI generating` and `project initiallization`
Using Maven to create the project and import the JACKSON lib => to parse json
Using Gemini for `coding assistance` and `debug refinement`
I design the main logic and also refine the final code myself
I use `github codespace` to develop this project
###
--------------------------------------------------------------------------------

## How to run my work?
You can run my work in 2 ways: via yourself and via CLI command

* Manually Run
1. `cd /workspaces/2c2p_task/transaction-analysis/src/main/java/com/example/App.java`
2. Run the App.java to interact with the code
3. Using `help` keyword to see all the eligible commands; the command in `help` are

load               - Load transaction data
show all           - Display all transactions
show valid         - Display valid transactions
show invalid       - Display invalid transactions
show dup           - Display duplicate transactions
report             - Generate report.json
clear              - Clear the console
help               - Show this help
end                - Exit the program

4. I would recommend using `load` before performing any action as it will reimport the dataset
--------------------------------------------------------------------------------
* CLI Run
1. enter to the correct path
```cd /workspaces/2c2p_task/transaction-analysis```
2. run the CLI for automatic process

```
java -cp "target/classes:$HOME/.m2/repository/com/fasterxml/jackson/core/jackson-core/2.15.2/jackson-core-2.15.2.jar:$HOME/.m2/repository/com/fasterxml/jackson/core/jackson-databind/2.15.2/jackson-databind-2.15.2.jar:$HOME/.m2/repository/com/fasterxml/jackson/core/jackson-annotations/2.15.2/jackson-annotations-2.15.2.jar" \
com.example.App --input transaction.json --output report.json
```
--------------------------------------------------------------------------------

## For your easy insert testcase
- Replace the transaction.json inside the `/workspaces/2c2p_task/transaction-analysis` path so my program will be able to see the dataset
- report.json is where the result is
- pom.xml was to config the mvn environment

--------------------------------------------------------------------------------

In case you have any question, plese feel free to contact me via email and mobilephone (+66 854980608),
Best regards
Kritchai.