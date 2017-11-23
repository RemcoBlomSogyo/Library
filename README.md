# Sogyo Library

### Digital book management system for Soygo with REST API.

#### Used techniques
- Language: Java
- REST framework: Jersey
- ORM: Hibernate
- Database: Microsoft SQL Server
- Google SignIn API: https://git.sogyo.nl/rblom/Java-Google-SignIn

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
The frontend is built in Polymer and can be found at: https://git.sogyo.nl/rblom/SogyoLibrary-Polymer.
It was on a Polymer server at localhost:8081. If you use another port, you have to change the port in the
CORSResponseFilter.

The project doesn't provide a webcontainer. You have to download it yourself. During working on this project I used Tomcat 9.

The application needs a database connection. Configurations are in hibernate.cfg.xml. I used Microsoft SQL Server. If you don't use SQL Server 2012 or higher, you have to change this. You probably have to change the IP address in the file. Be sure that you create the database in the dbms before you make the first connection and that it has the same name as in the xml file. If you make the first connection, the tables are automatically created. Username and password have to be the same as in the dbms and don't forget to give this user the correct rights to make changes in the database.

To make signing in with Google possible, the project has a dependency on a jar that is in the lib folder of WEB-INF. This jar is in this folder, because it is self-made and the Sogyo build master can't find it if it is only on your own machine. So be sure that your project always can find the jar when building. The project can be found at https://git.sogyo.nl/rblom/Java-Google-SignIn.

#### Important information for running tests
The integration tests in the test folder are dependent on data of the database. Without this data a lot of tests will fail. During working on this project I made a test user and test admin. Make these also or import the data of the CSV files in the database folder.

Some tests request specific book information with a specific id. These data is also in the CSV files. If you don't want to use this data, you have to change these variables in the tests so they are equal with the data in your database.

There are also logic classes in the test folder. Many of them are duplicates of the code in the main folder. This is because of the TokenParser class. This class is different in the main folder. In the test folder the TokenParser doesn't make a connection with Google. Otherwise the tests will fail. The duplicate code is quite ugly, so feel free to change this to a better solution.

#### About
Developer: Remco Blom
Product Owner: Robbert van Vugt

If you have questions you can send an email.
URL's on this page are possibly not working anymore, because of a different owner. If so, ask a Sogyo teacher for the current URL.
