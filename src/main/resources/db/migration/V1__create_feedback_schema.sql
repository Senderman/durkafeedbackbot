CREATE TABLE feedback (
    id INT PRIMARY KEY,
    message VARCHAR(2000),
    type VARCHAR(255),
    user_id BIGINT,
    user_name VARCHAR(255),
    chat_id BIGINT,
    message_id INT,
    replied BOOLEAN
);