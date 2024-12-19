# Library Management System SQL Schema



```sql
CREATE DATABASE library_management_system;

CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    author VARCHAR(255),
    publisher VARCHAR(255),
    publication_year INTEGER,
    edition INTEGER,
    page INTEGER,
    stock INTEGER,
    available_book_count INTEGER,
    borrowed_book_count INTEGER DEFAULT 0,
    lost_book_count INTEGER DEFAULT 0
);


CREATE TABLE students (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    fine NUMERIC(5, 2)
);


CREATE TABLE admins (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    username VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    email VARCHAR
);


CREATE TABLE loans (
    loan_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    book_id INTEGER NOT NULL,
    borrow_date DATE,
    due_date DATE,
    return_date DATE,
    is_returned BOOLEAN DEFAULT FALSE,
    CONSTRAINT loans_user_id_fkey FOREIGN KEY (user_id) REFERENCES students(id),
    CONSTRAINT loans_book_id_fkey FOREIGN KEY (book_id) REFERENCES books(id)
);


CREATE TABLE notifications (
    id SERIAL PRIMARY KEY, 
    user_id INTEGER NOT NULL,
    book_id INTEGER NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_sent BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES books(id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES students(id)
);


