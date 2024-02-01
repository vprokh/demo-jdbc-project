INSERT INTO address (id, display_address, post_code, city, street, created_at)
VALUES (1, '123 com.example.Main St', 'ABC123', 'New York', 'com.example.Main Street', '2023-11-30 08:00:00'),
       (2, '456 Elm St', 'DEF456', 'Los Angeles', 'Elm Street', '2023-11-29 15:30:00'),
       (3, '789 Oak St', 'GHI789', 'Chicago', 'Oak Avenue', '2023-11-28 12:45:00');

INSERT INTO users (id, first_name, last_name, email, phone_number, password, role, address_id)
VALUES
    (1, 'John', 'Doe', 'john@example.com', '123456789', 'password123', 'admin', 1),
    (2, 'Jane', 'Smith', 'jane@example.com', '987654321', 'qwerty456', 'user', 2),
    (3, 'Alice', 'Johnson', 'alice@example.com', '555123456', 'pass1234', 'user', 3);
