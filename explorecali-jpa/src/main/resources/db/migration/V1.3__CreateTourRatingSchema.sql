

CREATE TABLE tour_rating (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tour_id BIGINT,
    customer_id BIGINT,
    score INT,
    comment VARCHAR(100));

