# library-management-system
A simple web application for managing a library, allowing users to issue, return, search, and view books.

## APIs

| HTTP Method | Endpoint                 | Description                          | Request Params / Body |
|------------|-------------------------|--------------------------------------|---------------------|
| POST       | /api/user/register       | Register a new user                  | JSON: { "name", "email", "password" } |
| GET        | /api/book/all            | Get all books (paginated optional)  | Query: page, size (optional) |
| GET        | /api/book/search         | Search books by title                | Query: title |
| POST       | /api/book/add            | Add a new book                        | JSON: { "title", "author" } |
| POST       | /api/book/issue          | Issue a book to a user               | Params: key, userId |
| POST       | /api/book/return         | Return a book                        | Params: key, userId |



## Scope of Enhancement

1. Logging: Implement
2. Spring Security
3. Integration Testing:

## Postman collection link
https://.postman.co/workspace/My-Workspace~ae26a476-9759-4c09-8f07-7f3bf5ecd7d8/request/31509190-8e75679b-3338-4792-a6e0-55e55109279b?action=share&creator=31509190&ctx=documentation
