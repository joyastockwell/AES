# AES
Implementation of AES encryption for Rose-Hulman's CSSE479 Cryptography course

Encrypts 128 bits at a time with a 128 bit key

The program must read from a file called aesinput.txt (1) the number of iterations, (2) the number of
rounds, (3) the 128-bit key, represented as 16 hexadecimal characters, (4) the plaintext, represented in
hexadecimal. Each of these 4 items should be separated by a newline.

To run:

The jar file is already created from the java files. To run in command prompt on input file myInput, simply type

java --add-modules java.xml.bind -jar assgn4CSSE479.jar "myInput.txt"

Please note that the add-modules java.xml.bind is essential because the datatype converter used is now depricated.

In the java code, the last argument passed to the object constructors is a debug boolean. All are set to false in the jar file.
