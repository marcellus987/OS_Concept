// Author: Marcellus Von Sacramento
// Last modified: 02/10/2025

// Description: This program uses _execvp() for Windows.


#include <stdio.h>
#include <windows.h>
#include <string.h>

#define BUFFER_SIZE 100

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


// Note: The code inside execute() function is similar from 
//       the book: Chapter 3, page 120, since I used it for
//       reference.
int execute(char** argList) {
    
}


int main() {    
    char input[BUFFER_SIZE];
    char* argList[50];
    


    while(1) {
        printf("Tish>> "); // Print prompt.
        fgets(input, BUFFER_SIZE, stdin); // Get input from user.
        parse(input, argList); // Tokenize input.

        if(isspace(*input)) {
            printf("No argument entered! Exiting...");
            exit(-1);
        }

        // For debug.
        printf("argList[0]: ");
        char** argPtr = argList;
        printf(*argPtr);

       


        // Check if exit command.
        if(strcmp(argList[0], "exit") == 0) {
            exit(0);
        }

        
        execute(argList);
    }
} 
