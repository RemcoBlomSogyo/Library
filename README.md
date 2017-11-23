# Sogyo Library

### Digital book management system for Soygo with REST API.

#### Used techniques
- Language: Java
- REST framework: Jersey
- ORM: Hibernate
- Database: Microsoft SQL Server
- Google SignIn API: https://git.sogyo.nl/academy-projecten/Java-Google-SignIn

#### REST API
| Function                        | Method | URI                                                            |
| ------------------------------- | ------ | -------------------------------------------------------------- |
| Get list of books with criteria | GET    | books?title={titleInput}&author={authorInput}&isbn={isbnInput} |
| Get book by id                  | GET    | books/{id}                                                     |
| Add book                        | POST   | books                                                          |
| Update book                     | PUT    | books                                                          |                                    
| Delete book and copies          | DELETE | books                                                          |
| Add one copy of book            | POST   | books/{id}/copies                                              |
| Delete one copy of book         | DELETE | books/{id}/copies                                              |
| Get list of all authors         | GET    | authors                                                        |
| Get list of all users           | GET    | users                                                          |
| Register user                   | POST   | users                                                          |
| Update list of all users        | PUT    | users                                                          |
| Borrow copy of book             | POST   | users/{userId}/books/{bookId}                                  |

#### Important information if you start working on this project
The frontend is built in Polymer and can be found at: https://git.sogyo.nl/academy-projecten/SogyoLibrary-Polymer.
It was on a Polymer server at localhost:8081. If you use another port, you have to change the port in the
CORSResponseFilter.

The project doesn't provide a webcontainer. You have to download it yourself. During working on this project I used Tomcat 9.

The application needs a database connection. Configurations are in hibernate.cfg.xml. I used Microsoft SQL Server. If you don't use SQL Server 2012 or higher, you have to change this. You probably have to change the IP address in the file. Be sure that you create the database in the dbms before you make the first connection and that it has the same name as in the xml file. If you make the first connection, the tables are automatically created. Username and password have to be the same as in the dbms and don't forget to give this user the correct rights to make changes in the database.

To make signing in with Google possible, the project has a dependency on a jar that is in the lib folder of WEB-INF. This jar is in this folder, because it is self-made and the Sogyo build master can't find it if it is only on your own machine. So be sure that your project always can find the jar when building. The project can be found at https://git.sogyo.nl/academy-projecten/Java-Google-SignIn.

#### Important information for running tests
The integration tests in the test folder are dependent on data of the database. Without this data a lot of tests will fail. During working on this project I made a test user and test admin. Make these also or import the data of the CSV files in the database folder.

Some tests request specific book information with a specific id. These data is also in the CSV files. If you don't want to use this data, you have to change these variables in the tests so they are equal with the data in your database.

There are also logic classes in the test folder. Many of them are duplicates of the code in the main folder. This is because of the TokenParser class. This class is different in the main folder. In the test folder the TokenParser doesn't make a connection with Google. Otherwise the tests will fail. The duplicate code is quite ugly, so feel free to change this to a better solution.

What I would like to improve in this project is to use Docker. Now The build server uses the database that's on my own machine. Because of this distance, random tests are failing sometimes. What I also would like to improve is to use test sogyo users, which can login at Google, so I can use that token on the real TokenParser. The duplicate code in the test folder is a bit clumsy.

#### How to get the code working again
1. Create a project in an IDE.
2. Clone the repo into this project.
3. If you don't work in Eclipse, you have to add the IDE directories and files to .gitignore.
4. Download a webcontainer, like Tomcat, and add it to your IDE.
5. Install a database server and dbms, like MS SQL Server and SMSS.
6. Create a database object in the SMSS, don't create tables.
7. Create a user that is able to perform all actions on the database.
8. Configure the database so that an application can access it, like opening port 1433 for SQL Server.
9. Open hibernate.cfg.xml and change to correct ip address, port, library name, username and password.
10. If you don't use SQL Server 2012 or higher, you have to change the jdbc driver to the driver that your server needs. Change also the dialect.
11. Run one of the integration tests, to force connection with the database. Now it gives an error message, that's fine. Check if the tables are created.
12. Import the CSV files with data of the database folder into the tables.
13. Run the tests again, they will probably succeed.
14. Go to the developer console of Google and make your own Client ID. Add this Client ID to the TokenParser class (so remove the old one).
15. Download Polymer and clone the SogyoLibrary-Polymer project.
16. Add your Client ID that you just created to the google-signin element of the my-app element (so remove the old one).
17. Change the port in token-ajax and token-body-ajax to the port of your webcontainer. If the backend project isn't called SogyoLibrary, change that part of the URL also.
18. In the backend project, open CORSResponseFilter.java and change the port to the port that the polymer server is on. This is probably 8081. You can start the polymer server with 'polymer serve' in the command line.
19. If you run both servers the application is working.

#### About
Developer: Remco Blom

Product Owner: Robbert van Vugt

If you have questions you can send an email.
