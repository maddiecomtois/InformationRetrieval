# Assignment 1 Run Instructions
Author: Madeleine Comtois  
Student Id: 17301720  
Last updated: 18/10/2021  

## Organisation
This assignment was created and maintained using the Maven environment.  Each project folder has a corresponding `pom.xml` file, as well as a `target` folder where the jar file to run the program is stored.

The code for this assignment is all stored in the ```Assignment1``` folder.  This folder contains two separate project folders.  Although multiple classes are used to organise the code, each project has a ```Main.java``` class from where all the code can be executed.  The first folder, `CreateIndex`, holds the code for reading in the Cran dataset and creating an index.  The second folder, `QueryIndex`, contains the code for processing the query files, searching the index, and evaluating the results.

In addition to the code, additional folders are provided to run the programs.  The first is `cran`, which stores all of the Cran files necessary for indexing and querying.  Once the indexes are created, they are stored in the ```indexes``` folder.  Secondly, once the evaluation metrics are run, the generated results files will appear in the ```results``` folder.  Finally, the `trec_eval` folder  stores the code for running evaluation metrics.  The bash script ```run_eval.sh```, which automates this evaluation for the given indexes and results, is also stored in the folder.  

## Running the Code 

### Creating the Indexes
From the ```Assignment1/CreateIndex``` folder, run the following command to create the jar file:  

```mvn package```  

Once the jar has been created, it can be run with the command:  

```java -jar target/assignment1-createIndex-0.1.0.jar```  

This program will print the id of each document as it's being indexed, and the final index will appear in the ```Assignment1/indexes``` folder.  This is repeated for each index created.  

### Querying the Index 
From the ```Assignment1/QueryIndex``` folder, run the following command to create the jar file:  

```mvn package```  

Once the jar has been created, it can be run with the command:   

```java -jar target/assignment1-queryIndex-0.1.0.jar```  

This program creates the query results files stored in the ```Assignment1/results``` folder.

### Evaluating the System
From the ```Assignment1/trec_eval``` folder, execute the script:   

```./run_eval.sh```

This prints out a table with certain metrics trec_eval uses to evaluate the retrieval system.
