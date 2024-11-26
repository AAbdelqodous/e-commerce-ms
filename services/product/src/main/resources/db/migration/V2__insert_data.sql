-- Insert categories
INSERT INTO Category (id, name, description)
VALUES
    (nextval('category_seq'), 'Electronics', 'Electronic devices and accessories'),
    (nextval('category_seq'), 'Books', 'Physical and digital books'),
    (nextval('category_seq'), 'Clothing', 'Apparel and fashion items'),
    (nextval('category_seq'), 'Home & Garden', 'Home improvement and garden supplies'),
    (nextval('category_seq'), 'Sports', 'Sports equipment and accessories');

-- Insert products
INSERT INTO Product (id, name, description, available_quantity, price, category_id)
VALUES
    -- Electronics
    (nextval('product_seq'), 'Smartphone', 'Latest model smartphone', 50, 899.99,
        (SELECT id FROM Category WHERE name = 'Electronics')),
    (nextval('product_seq'), 'Laptop', 'High-performance laptop', 30, 1299.99,
        (SELECT id FROM Category WHERE name = 'Electronics')),
    (nextval('product_seq'), 'Wireless Earbuds', 'Bluetooth earbuds', 100, 149.99,
        (SELECT id FROM Category WHERE name = 'Electronics')),

    -- Books
    (nextval('product_seq'), 'Programming Guide', 'Comprehensive programming book', 75, 49.99,
        (SELECT id FROM Category WHERE name = 'Books')),
    (nextval('product_seq'), 'Cooking Recipe Book', 'International recipes', 60, 29.99,
        (SELECT id FROM Category WHERE name = 'Books')),

    -- Clothing
    (nextval('product_seq'), 'Winter Jacket', 'Warm winter jacket', 40, 129.99,
        (SELECT id FROM Category WHERE name = 'Clothing')),
    (nextval('product_seq'), 'Running Shoes', 'Professional running shoes', 80, 89.99,
        (SELECT id FROM Category WHERE name = 'Clothing')),

    -- Home & Garden
    (nextval('product_seq'), 'Garden Tools Set', 'Complete garden toolkit', 25, 79.99,
        (SELECT id FROM Category WHERE name = 'Home & Garden')),
    (nextval('product_seq'), 'LED Light Bulbs', 'Energy-efficient bulbs pack', 150, 19.99,
        (SELECT id FROM Category WHERE name = 'Home & Garden')),

    -- Sports
    (nextval('product_seq'), 'Yoga Mat', 'Professional yoga mat', 100, 29.99,
        (SELECT id FROM Category WHERE name = 'Sports')),
    (nextval('product_seq'), 'Tennis Racket', 'Professional tennis racket', 45, 159.99,
        (SELECT id FROM Category WHERE name = 'Sports'));