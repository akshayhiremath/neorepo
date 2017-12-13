================================================
-- QUICK USER GUIDE ----------------------------
================================================

--Setup
================================================
Extract the neorepo.zip anywhere on your PC. 
This directory named neorepo contains 3 items,

1. src/ -> directory with source code of NeoUtility
2. pom.xml -> POM file of the Neo maven project of Java nature
3. README.txt -> This quick user guide, the file you are currently reading :)

--Build Process
================================================

To build this project. You should have maven installed 
with right settings to connect to internet and download the dependencies.
If you are behind firewall make sure you add proxy in your .m2/settings.xml.
.m2/settigs.xml is generally located in the USER home directory. 
Which is for Windows:
C:\User\<USERNAME>\

Once you have maven in place.

1. Go to commandline by running cmd command.
2. Go to the <Your_PC_PATH>/neorepo directory,
3. Run following command:
	mvn clean install

--Executing the NeoUtility
=================================================
Once you have got SUCCESS in the build process above, you will find the executables in target directory.
To run NeoUtility. 
Go to target and you will find two NeoUtility files 
	one with extension .bat and other with extension .sh
Use appropriate version based on your OS.

You can also execute the jar by using:
java -Djava.net.useSystemProxies=true -jar neo-1.1.0-SNAPSHOT-jar-with-dependencies.jar START_DATE END_DATE

NeoUtility has 3 simple options to fetch the data of Near Earth Objects(NEO) 
from REST service exposed by NASA.

You can get NEO information for,
1. today,
2. Any date range of span of 7 days max,
3. 7 days starting from particular date.

Information has 3 points,
	1. No. of NEOs,
	2. Largest NEO details,
	3. Closest NEO details

For each option, you have to following commands respectively:
1. Go to neorepo/ directory on command line.
2. Run:
	a. For Data for today):
		NeoUtility 
	b. For Data between 2 dates:
		NeoUtility <START_DATE> <END_DATE>
	c. For Data for 7 days from start date of your choice:
		NeoUtility <START_DATE>

After execution, the utility will wait for you to see the response. 
You can press any key to exit the window.

--Clean Up
=================================================
1. After you are done using the utility. All other directories files in neorepo 
except the above mentioned 2 could
be deleted.

Thanks!!
Akshay
