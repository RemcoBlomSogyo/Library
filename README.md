# Sogyo Library

### Digital book management system for Soygo with REST API.

#### Used techniques:
- Language: Java
- REST framework: Jersey
- ORM: Hibernate
- Database: Microsoft SQL Server
- Google SignIn API: https://git.sogyo.nl/rblom/Java-Google-SignIn

#### REST API:
| Function                        | Method | URI                                                            |
| ------------------------------- | ------ | -------------------------------------------------------------- |
| Get list of books with criteria | GET    | books?title={titleInput]&author={authorInput}&isbn={isbnInput} |
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