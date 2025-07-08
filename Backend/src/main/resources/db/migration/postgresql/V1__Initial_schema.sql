CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  email VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  highest_score INT DEFAULT 0
);


CREATE TABLE statements (
  id BIGSERIAL PRIMARY KEY,
  statement TEXT NOT NULL,
  is_fact BOOLEAN NOT NULL,
  explanation TEXT NOT NULL,
  difficulty INT NOT NULL, -- 1: Easy, 2: Medium, 3: Hard, 4: Expert
  category VARCHAR(50) NOT NULL
);


CREATE TABLE game_history (
  id BIGSERIAL PRIMARY KEY,
  user_id BIGINT NOT NULL,
  score INT NOT NULL,
  played_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id)
);
