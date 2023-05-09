
# Demo shop

Demo shop is demo e-commerce shop. You can check and test this project in swagger at 
http://zaitis.alwaysdata.net/swagger-ui/index.html
## Tech Stack




## API Reference


#### Get all categories

```http
  GET /admin/categories
```


#### Get category with id

```http
  GET /admin/categories${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of item to fetch |

#### Insert new category 

```http
  POST /admin/categories
```

#### Request body


| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `name`    | `string` | **Required**. Category name       |
| `desc`    | `string` | Category description              | 
| `slug`    | `string` | **Required**. Category slug       |

#### Update category with id

```http
  PUT /admin/categories${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of item to fetch |

#### Delete category with id

```http
  DELETE /admin/categories${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of item to fetch |


#### Get all products

```http
  GET /admin/products
```


#### Get product with id

```http
  GET /admin/products${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of item to fetch |

#### Insert new product 

```http
  POST /admin/products
```

#### Request body


| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `name`    | `string` | **Required**. Product name       |
| `categoryId`    | `Long` | **Required**. Category Id              | 
| `description`    | `string` | **Required**. Product description       |
| `fullDescription`    | `string` |  Longer version product description     |
| `price`    | `string` | **Required**. Product price       |
| `currency`    | `ENUM` |  One of currency      |
| `image`    | `string` | image name for product     |
| `slug`    | `string` | **Required**. Product slug       |

#### Update product with id

```http
  PUT /admin/products${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of item to fetch |

#### Delete product with id

```http
  DELETE /admin/products${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of item to fetch |


#### Upload image to server

```http
  POST /admin/products/upload
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `file`      | `MultipartFile` | **Required**. Upload image on server and back slug name of this image. |


#### Get image from server with name

```http
  GET /data/productImage/{filename}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `filename`      | `string` | **Required**. Slug image name |

#### Get category with id

```http
  GET /admin/reviews
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of item to fetch |


#### Update review with id

```http
  PUT /admin/reviews/{id}/moderate
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Change review on visible with id. |

#### Delete review with id

```http
  DELETE /admin/reviews/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of item to fetch |





## Feedback

If you have any feedback, please reach me at krzysztof@painm.pl


## Installation

Not implemented yet...

```bash
  Not implemented yet...
```
    
## Lessons Learned

What did you learn while building this project? What challenges did you face and how did you overcome them?


## Roadmap

- Not implemented yet...

- Not implemented yet...


## Run Locally

# Not implemented yet...

```bash
  git clone https://github.com/Zaitis/shop-backend
```

Go to the project directory

```bash
  cd shop-backend
```

Install dependencies

```bash
  mvn clean install
```

Go to the target directory

```bash
  cd target
```

Start the server

```bash
  npm run start
```


## Screenshots

![App Screenshot](https://via.placeholder.com/468x300?text=App+Screenshot+Here)

