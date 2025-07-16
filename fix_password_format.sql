-- SQL script to fix password format issues
-- This script ensures passwords are in the correct format for Spring Security

-- Step 1: Check current password formats
SELECT username, 
       LEFT(password, 20) as password_prefix,
       CHAR_LENGTH(password) as password_length
FROM users;

-- Step 2: Fix admin user password to be exactly as in migration
UPDATE users 
SET password = '{bcrypt}$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'
WHERE username = 'admin';

-- Step 3: Fix any passwords that don't start with {bcrypt}
-- This adds the {bcrypt} prefix to passwords that don't have it
UPDATE users 
SET password = CONCAT('{bcrypt}', password)
WHERE password NOT LIKE '{bcrypt}%' 
  AND password LIKE '$2a$%';

-- Step 4: Remove any double prefixes (if they exist)
UPDATE users 
SET password = REPLACE(password, '{bcrypt}{bcrypt}', '{bcrypt}')
WHERE password LIKE '{bcrypt}{bcrypt}%';

-- Step 5: Verify the changes
SELECT username, 
       LEFT(password, 30) as password_prefix,
       CHAR_LENGTH(password) as password_length
FROM users;

-- Step 6: Check authorities
SELECT u.username, u.enabled, a.authority 
FROM users u 
LEFT JOIN authorities a ON u.username = a.username 
ORDER BY u.username; 