The coding part is in Code folder.


Please follow the steps below to compile and run the project using terminal:

1/ Run the following command to compile:
        javac -cp ".:libs/hsqldb.jar:" hung/Ozlympic.java

2/ Run the following command to run the app:
        java -cp libs/hsqldb.jar:./ hung.Ozlympic



To run the embedded unit tests, please follow the steps below:

1/ Run the following command to compile:
        javac -cp libs/hsqldb.jar:libs/junit-4.12.jar:. tests/<test_class_name>.java

2/ Run the following command to run the test:
        java -cp libs/hsqldb.jar:libs/hamcrest-core-1.3.jar:libs/junit-4.12.jar:. org.junit.runner.JUnitCore tests.<test_class_name>



Game play instructions:

