-- SQL script to grant admin access to a user
-- Replace 'your-email@example.com' with your actual username

-- Step 1: Check if user exists
SELECT id, username, enabled FROM users WHERE username = 'your-email@example.com';

-- Step 2: Check current authorities
SELECT username, authority FROM authorities WHERE username = 'your-email@example.com';

-- Step 3: Grant admin role (run this if user exists)
INSERT INTO authorities (username, authority) 
VALUES ('your-email@example.com', 'ROLE_ADMIN');

-- Step 4: Verify the change
SELECT username, authority FROM authorities WHERE username = 'your-email@example.com';

-- Alternative: If you want to check all users and their roles
SELECT u.username, u.enabled, a.authority 
FROM users u 
LEFT JOIN authorities a ON u.username = a.username 
ORDER BY u.username; 