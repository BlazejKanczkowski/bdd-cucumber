CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  username VARCHAR(50) NOT NULL,
  full_name VARCHAR(100),
  email VARCHAR(100)
);

CREATE TABLE orders (
  id SERIAL PRIMARY KEY,
  user_id INT REFERENCES users(id),
  product_name VARCHAR(100),
  price NUMERIC(6,2),
  order_date TIMESTAMP DEFAULT NOW()
);

INSERT INTO users (username, full_name, email) VALUES
('standard_user', 'Standard User', 'standard@example.com'),
('locked_out_user', 'Locked Out User', 'locked@example.com'),
('problem_user', 'Problem User', 'problem@example.com'),
('performance_glitch_user', 'Performance Glitch User', 'glitch@example.com');

INSERT INTO orders (user_id, product_name, price) VALUES
(1, 'Sauce Labs Backpack', 29.99),
(1, 'Sauce Labs Bike Light', 9.99),
(1, 'Sauce Labs Bolt T-Shirt', 15.99),
(2, 'Sauce Labs Onesie', 7.99),
(3, 'Test.allTheThings() T-Shirt', 15.99),
(3, 'Sauce Labs Backpack', 29.99),
(4, 'Sauce Labs Fleece Jacket', 49.99);
