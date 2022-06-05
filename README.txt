IDE used
-------------------------------
Eclipse 4.22.0

GitHub repository
-------------------------------
https://github.com/Kaniuday4/Assignment2_PartB.git

Versions
-------------------------------
Java - SE 17.0.1
JavaFX - 18.0.1

Steps to run
-------------------------------
1. Install java FX SDK version 18.0.1
2. Add java SDK to eclipse
3. Preferences -> Java -> Build Path -> User Libraries -> JavaFX (Path to Java FX jar files in lib folder)
4. Import java project
5. Project -> Configure Build path -> Libraries -> add java FX and SDK to class path
6. Project -> Run configurations -> Arguments
	- Program arguments - input.txt output.txt
	- VM arguments - --module-path <Path to JAVA FX SDK lib> --add-modules=javafx.controls