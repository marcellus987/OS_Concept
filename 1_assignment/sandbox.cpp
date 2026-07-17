#include <iostream>

using namespace std;


void showAddress(const char* arg) {
     printf("%p", arg);
}

void newline() {
     cout << "\n";
}

int main() {
     const char* text = "Hello";

     printf("Address of &text: ");
     printf("%p", &text);
     newline();

     printf("Address of text using showAddress(): ");
     showAddress(text);
     newline();


     printf("Address pointed by text: ");
     printf("%p", text);
     newline();

     printf("Text[0]: ");
     printf("%c", *(text + 0));
     newline();

     cout << "Text[0]: " << *(text + 0) << "\n";
     
    
}