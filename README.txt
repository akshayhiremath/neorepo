================================================
-- QUICK USER GUIDE ----------------------------
================================================

--Setup
================================================
Extract the neorepo.zip anywhere on your PC. 
This directory named neorepo contains 4 items,

1. src/ -> directory with source code of NeoUtility
2. pom.xml -> POM file of the Neo maven project of Java nature
3. lib/ -> directory containing external libraries that 
	   Neo Utility will need at RUNTIME
4. NeoUtility.bat -> A script file to fire the utility on windows.
5. README.txt -> A qucik user guide i.e. the file you are currently reading.

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
Once you have got SUCCESS in the build process above.
You can run NeoUtility.

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
		NeoUtility.bat 
	b. For Data between 2 dates:
		NeoUtility.bat <START_DATE> <END_DATE>
	c. For Data for 7 days from start date of your choice:
		NeoUtility.bat <START_DATE>

After execution, the utility will wait for you to see the response. 
You can press any key to exit the window.

--Clean Up
=================================================
1. After you are done using the utility. All other directories files in neorepo 
except the above mentioned 4 could
be deleted.

Thanks!!
Akshay
