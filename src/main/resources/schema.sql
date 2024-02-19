CREATE TABLE
    IF NOT EXISTS ingredient (
        id VARCHAR(4) PRIMARY KEY,
        name VARCHAR(25) NOT NULL,
        type VARCHAR(10) NOT NULL
    );

CREATE TABLE
    IF NOT EXISTS taco (
        id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(50) NOT NULL,
        createdAt TIMESTAMP NOT NULL
    );

CREATE TABLE
    IF NOT EXISTS taco_ingredients (
        taco_id INT NOT NULL,
        ingredient_id VARCHAR(4) NOT NULL,
        FOREIGN KEY (taco_id) REFERENCES taco (id),
        FOREIGN KEY (ingredient_id) REFERENCES ingredient (id)
    );

CREATE TABLE
    IF NOT EXISTS taco_order (
        id INT AUTO_INCREMENT PRIMARY KEY,
        deliveryName VARCHAR(50) NOT NULL,
        deliveryStreet VARCHAR(50) NOT NULL,
        deliveryCity VARCHAR(50) NOT NULL,
        deliveryState VARCHAR(2) NOT NULL,
        deliveryZip VARCHAR(10) NOT NULL,
        ccNumber VARCHAR(16) NOT NULL,  
        ccCVV VARCHAR(3) NOT NULL,
        placedAt TIMESTAMP NOT NULL
    );

CREATE TABLE
    IF NOT EXISTS taco_order_tacos (
        taco_order_id INT NOT NULL,
        taco_id INT NOT NULL,
        FOREIGN KEY (taco_order_id) REFERENCES taco_order (id),
        FOREIGN KEY (taco_id) REFERENCES taco (id)
    );