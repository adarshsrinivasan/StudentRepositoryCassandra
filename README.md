# StudentRepositoryCassandra

## Overview
 
This is a Java program of a student data repository. It stores and catalogs the data persisted in Cassandra DB and the data can be sorted by any column with pagination.

## Student table metadata
**Field Name**        **Type**        **Type Of Key**       **Description**
1. department           text          Partition Key         Department to which the student is enrolled to. Since the
                                                            distribution of students is equal among the department, 
                                                            it is chosen as the partition key as there will be uniform 
                                                            distribution among each partition.

2. roll                 int           Clustering Key        Roll number of the student. Under each partition (department), 
                                                            students’ records will be sorted (ASC) based on roll number.
                                                            
3. year                 int           Clustering Key        Joining year of the student. This column consists of a constant value
                                                            across all records. When creating a Materialized view, 
                                                            this key can be used as partition key so that all the records 
                                                            will be present in a single partition and can be sorted accordingly
 
4. firstName            text                -               First name of the student.

5. lastName             text                -               Last name of the student.

6. club                 text                -               Club to which the student belongs to.


API Collection (Postman collection link) : https://www.getpostman.com/collections/8c752e05b4f139885116

## Setting up and starting the program
1. Adding host Ip in docker-compose.yml
```
services.student-repository.extra_hosts : -"cassandra:<YOUR SYSTEM IP>"
```
2. Starting cassandra
```
docker-compose up cassandra
```
3. Building docker image of "student-repository". The created image's name will be  "student-repository:latest"
```
mvn clean install docker:build
```
4. Starting student-repository
```
docker-compose up student-repository
```

## APIs

1. Insert single student record
**Method:** POST
**URL:** http://localhost:8080/api/v1/student
**Request Body:** 
```
{
	"roll": 19001,
	"department": "CSE",
	"firstName": "Adarsh",
	"lastName": "Srinivasan",
	"club": "ASCII"
}
```
**Response code:** 200 OK

2. Insert multiple students record
**Method:** POST
**URL:** http://localhost:8080/api/v1/student
**Request Body:** 
```
[{
	"roll": 19002,
	"department": "CSE",
	"firstName": "Kumar",
	"lastName": "Krishnan",
	"club": "SPORTS"
}
...
]
```
**Response code:** 200 OK

3. Get a student record
**Method:** GET
**URL:** http://localhost:8080/api/v1/student/{department}/{roll number}
**Response Body:** 
```
{
	"roll": 19002,
	"department": "CSE",
	"firstName": "Kumar",
	"lastName": "Krishnan",
	"club": "SPORTS"
}
```
**Response code:** 200 OK

4. List students record
**Method:** GET
**URL:** http://localhost:8080/api/v1/student?pageNum=<Current Page Number>&sortedBy=<To sort the results by a column>&sortOrder=<Sorting order>&recordsPerPage=<Recores per page>  
  
**Note**: Next and previous can the navigated by changing the page number
**Response Body:** 
```
[{
	"roll": 19002,
	"department": "CSE",
	"firstName": "Kumar",
	"lastName": "Krishnan",
	"club": "SPORTS"
}
...
]
```
**Response code:** 200 OK

4. Update a student's record
**Method:** PUT
**URL:** http://localhost:8080/api/v1/student 
**Request Body:** 
```
[{
	"roll": 19002,
	"department": "CSE",
	"firstName": "Kumar",
	"lastName": "Krishnan",
	"club": "SPORTS"
}
...
]
```
**Response code:** 200 OK

5. Delete a student's record
**Method:** DELETE
**URL:** http://localhost:8080/api/v1/student/{department}/{roll number}
**Response code:** 200 OK

5. Delete all students record
**Method:** DELETE
**URL:** http://localhost:8080/api/v1/student
**Response code:** 200 OK

## Important Code
- Cassandra configuration: StudentRepositoryCassandra/StudentRepository/src/main/java/com/repo/student/StudentRepository/Configuration/*

- KetSpace, Table and Materialized view creation: StudentRepositoryCassandra/StudentRepository/src/main/java/com/repo/student/StudentRepository/Utils/CassandraUtils.java

- Cassandra configuration: StudentRepositoryCassandra/StudentRepository/src/main/resources/application.properties


