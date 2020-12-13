--LOCATION OF JAVADOC--
Its present in the javadoc folder which is there inside Code folder. 

--INSTRUCTIONS TO RUN THE CODE ON ECLIPSE------
Import the project folder "Code"
Remove javax.mail.jar and activation.jar in "configure build path"
Then add them again from the "jar" folder
Now the program can be started from Main.java

--INSTRUCTIONS TO RUN THE CODE ON COMMAND LINE------
Type out the following commands in the command line window to run the code on it:

1. （change directory to \Code\src)

2. javac -d ../bin -cp ../jar/javax.mail.jar;../jar/activation.jar STARSapp/*.java

3. cd..

4. cd .\bin

5. java -cp ../jar/javax.mail.jar;../jar/activation.jar; STARSapp.Main

--RECEIVER'S SAMPLE GMAIL ACCOUNT FOR RECEIVING EMAILS--
email id: cz2002testreceive@gmail.com

password: cz2002testreceivez

--SENDER'S SAMPLE GMAIL ACCOUNT FOR SENDING EMAILS--
email id: cz2002test@gmail.com

password: cz2002testz

--DUMMY DATA USED IN DEMONSTRATION--
15 students:  
Username(Matric Num): 001 ~ 015
Password: password1 ~ password15

Admin :
Username: Admin1
Password: password

[Courses: Indexes] (the vacacncy for every index is 10 except for index 1043)
CZ2002:  1011
	1012  registered student: 001
	1013  registered student: 002

CZ2005:  1021
	1022 time clash with 1012
	1023

CZ2001:  1031
	1032
	1033

CZ2006：1041
	1042
	1043  vacancy: 0  student on waitlist: 004 005 006

--THANK YOU--