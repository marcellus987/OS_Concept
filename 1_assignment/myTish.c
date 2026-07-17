// Author: Marcellus Von Sacramento
// Last modified: 02/19/2025

#include <stdio.h>
#include <stdlib.h>     // exit().
#include <sys/types.h>
#include <unistd.h>     // fork().
#include <wait.h>       // wait().
#include <ctype.h>      // isspace().
#include <string.h>     // strtok().

#define BUFFER_SIZE 100 // User input limit.

// Description: Tokenize input from user.
// Pre: None.
// Post: argList will contain the tokens used for execute().
void parse(char* input, char** argList) {
    char* delimiters = " \t\n";
    char* token = strtok(input, delimiters);

    while(token != NULL) {
        *argList = token; // Add token to argument list.
        ++argList; 
        token = strtok(NULL, delimiters);
    }

    *argList = NULL; // Just to signify end of arguments.
} // End of Parse().


// Description: Execute command input by user.
// Pre: "argList" not empty.
// Post: Command in "argList" is executed.
int execute(char** argList) {
    pid_t pid;
    int status;

    pid = fork(); // Create child process.

    if(pid < 0) {
        printf("ERROR: forking child process failed\n");
    }
    else if(pid == 0) {
        if(execvp(*argList, argList) < 0) {
            printf("ERROR: program execution failed\n");
            exit(1);
        }
    }
    else {
        // Parent process waits for child process to finish.
        while(wait(&status) != pid) {
            ; // Do nothing.
        }
    }
} // End of execute().


int main() {    
    char input[BUFFER_SIZE] = " ";
    char* argList[50];

    while(1) {
        // Get input from user.
        do {
            printf("Tish>> "); 
            fgets(input, BUFFER_SIZE, stdin); 
            parse(input, argList); // Tokenize input.
        } while(*argList == NULL);

        // Check if commanded to exit.
        if(strcmp(argList[0], "exit") == 0) {
            exit(0);
        }

        execute(argList); // Execute given command.
    }
} // End of main().

