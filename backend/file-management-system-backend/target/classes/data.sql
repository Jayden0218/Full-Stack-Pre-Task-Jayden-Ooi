-- Ensure the 'note' table exists
CREATE TABLE IF NOT EXISTS note
(
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content VARCHAR(255) NOT NULL,
    color VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    identification VARCHAR(255) NOT NULL
    );

-- Insert First Note only if it doesn't exist
INSERT INTO note (id, title, content, color, created_at, updated_at,identification)
SELECT '123e4567-e89b-12d3-a456-426614174001',
       'First Note',
       'First Content',
       '#ef4444',
       '1985-06-15 23:43:24',
       '1985-06-15 23:43:24',
       'hello1@qq.com'
    WHERE NOT EXISTS (
    SELECT 1 FROM note WHERE id = '123e4567-e89b-12d3-a456-426614174001'
);

-- Insert Second Note only if it doesn't exist
INSERT INTO note (id, title, content, color, created_at, updated_at,identification)
SELECT '123e4567-e89b-12d3-a456-426614174002',
       'Second Note',
       'Second Content',
       '#ef4444',
       '1985-06-15 23:43:24',
       '1985-06-15 23:43:24',
       'hello2@qq.com'
    WHERE NOT EXISTS (
    SELECT 1 FROM note WHERE id = '123e4567-e89b-12d3-a456-426614174002'
);

-- Insert Third Note only if it doesn't exist
INSERT INTO note (id, title, content, color, created_at, updated_at,identification)
SELECT '123e4567-e89b-12d3-a456-426614174003',
       'Third Note',
       'Third Content',
       '#ef4444',
       '1985-06-15 23:43:24',
       '1985-06-15 23:43:24',
       'hello3@qq.com'
    WHERE NOT EXISTS (
    SELECT 1 FROM note WHERE id = '123e4567-e89b-12d3-a456-426614174003'
);
