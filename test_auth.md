# Manual Authentication Testing for shop-backend.zaitis.dev

## Test 1: Admin Login (Should Work)

**Request:**
```bash
curl -X POST https://shop-backend.zaitis.dev/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "zaitis123"}'
```

**Expected Success Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTczNDM3NTYwMH0.abcdef123456...",
  "adminAccess": true
}
```

## Test 2: Admin Endpoint Access

**Request (use token from Test 1):**
```bash
curl -X GET https://shop-backend.zaitis.dev/admin/products \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json"
```

**Expected Success Response:**
```json
{
  "content": [...],
  "pageable": {...},
  "totalElements": 0,
  "totalPages": 0
}
```

## Test 3: Regular User Login (Your New User)

**Request:**
```bash
curl -X POST https://shop-backend.zaitis.dev/login \
  -H "Content-Type: application/json" \
  -d '{"username": "YOUR_EMAIL@example.com", "password": "YOUR_PASSWORD"}'
```

**Expected Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "adminAccess": false  // ← This is why you get 403!
}
```

## Test 4: Regular User Tries Admin Access

**Request (use token from Test 3):**
```bash
curl -X GET https://shop-backend.zaitis.dev/admin/products \
  -H "Authorization: Bearer REGULAR_USER_TOKEN_HERE" \
  -H "Content-Type: application/json"
```

**Expected Response (403 Forbidden):**
```json
{
  "timestamp": "2024-01-16T20:30:00.000+00:00",
  "status": 403,
  "error": "Forbidden",
  "path": "/admin/products"
}
```

## Test 5: User Registration

**Request:**
```bash
curl -X POST https://shop-backend.zaitis.dev/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "newuser@example.com",
    "password": "password123",
    "repeatPassword": "password123"
  }'
```

**Expected Success Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "adminAccess": false  // New users get ROLE_CUSTOMER by default
}
```

## Diagnosis Results

- ✅ **If Test 1 & 2 work**: Admin authentication is working correctly
- ✅ **If Test 3 & 4 show 403**: This is expected - regular users can't access admin endpoints
- ✅ **If Test 5 works**: User registration is working correctly

## Solutions

1. **Use admin credentials**: `admin` / `zaitis123`
2. **Grant admin role to your user**: Run the SQL script in `grant_admin_access.sql`
3. **Check adminAccess field**: Only users with `adminAccess: true` can access dashboard

## Common Issues

- **401 Unauthorized**: Wrong credentials
- **403 Forbidden**: Correct credentials but insufficient permissions
- **Missing Authorization header**: JWT token not included in request
- **Invalid JWT token**: Token expired or malformed 