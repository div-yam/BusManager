# BusManager
This code and case study aims to implement a bus booking system that would allow users to book seats in a bus, that the user would choose from the multiple routes between a source and a destination. 
This application will allow the admin to enter the bus routes between a source and a destination, and the users can book seats accordingly. 

**Below are the user roles :**
1. Admin:

	- Responsibilities: Admins are responsible for managing bus details, including adding, updating, and deleting bus information. They also handle the creation and management of bus routes.
	- Functionalities:
	  - Manage Bus Details: Add, update, and delete bus information such as bus name, seat capacity, and operational days.
	  - Create Bus Routes: Define and manage bus routes, including origin, destination, and departure times.
1. User:

	- Responsibilities: Users are individuals looking to book bus tickets through the platform.
	- Functionalities:
		- Browse Available Buses: Search for buses based on origin, destination, and travel dates. View details like distance and estimated time of arrival (ETA).
		- Check Seat Availability: View real-time seat availability for selected buses.
		- Seat Booking: Book available seats on desired buses.
		- Cancel Bookings: Cancel previously booked tickets with ease.

Entity Relationship Diagram

![ER Diagram](https://github.com/div-yam/BusManager/assets/65958420/88a01314-f485-4e13-a800-e3e6a77496e9)

Flow chart of the workflow

![Blank diagram - Page 1](https://github.com/div-yam/BusManager/assets/65958420/29f15174-32f2-46af-bd46-fcc28e467312)

**Tech stack used** - Java, Spring Boot, PostgreSQL, JWT, SLF4J

Important Links - 

- [https://drive.google.com/drive/u/0/folders/1dTQA0VpKemL4DJu3f89NPfk_yT7wvV6C](https://drive.google.com/file/d/1Zk_bodh5XN8c7yiQe4FXfsM-szawtUj2/view?usp=sharing) (Bus Manager Postman Collection)
[https://drive.google.com/drive/u/0/folders/1dTQA0VpKemL4DJu3f89NPfk_yT7wvV6C](https://drive.google.com/file/d/1ug-OiQjEFiJBX2hZITWiSVvU5AO-USyK/view?usp=sharing) (Contains the video demonstration of this application)
- https://github.com/div-yam/BusManagerAdmin/tree/master (Bus Manager Admin)

Implementation Details:
1. Authentication:
   - Implement robust user authentication protocols to ensure secure access.
   	- The sign in and sign up calls are made using JWT tokens, so that API calls are authorized. 
2. Cost Estimation - Time and Space:
   - Conduct a thorough analysis of time and space complexity in the system and utilize efficient algorithms and data structures to optimize both time and space requirements.
  	 - It is tried to reduce the time and space complexity of the entire system using SQL queries to fetch the data. 
3. Object-Oriented Programming Language (OOPS):
   - Choose a robust OOPS language for structured and modular code.
  	 - Java and Spring Boot are used for implementing this application. 
   - Leverage OOPS principles such as encapsulation, inheritance, and polymorphism for maintainability and extensibility.
  	 - All of these are implemented using intefaces and subclasses. 

4. Trade-offs in the System:
   - Clearly define and document trade-offs made during system design.
     	- Following trade-offs were made during designing this application.
  		1. It is assumed that user will book only 1 seat at a time. 
  		1. Bus data wil be available for only 1 month
6. System Monitoring:
   - Implement comprehensive monitoring tools to track system performance.
   	  - Spring Boot Admin UI is used for monitoring. 
   - Utilize real-time dashboards and logging mechanisms to promptly identify and address issues.
     	- SLF4J logs are used to debug the exceptions. 
7. Caching:
   - Integrate caching mechanisms to enhance system response times.
  	 - PostgreSQL provides the query cache feature to speed up data retrieval by caching query results. This feature improves the performance of database queries in scenarios that require more reads than writes, especially those in which identical queries are frequently repeated.
   - Utilize caching for frequently accessed data to reduce database load.
   - Implement cache eviction policies for optimal resource utilization.
8. Error and Exception Handling:
   - Develop a robust error and exception handling framework.
   	  - Custom exceptions are used to detect the exact error during runtime. 
   - Provide meaningful error messages for effective debugging.
      -  Meaningful error messages are provided when any API call fails. 
   - Regularly review and update error-handling strategies based on system usage patterns.
