# Post API Documentation

## Endpoints

### Get Post by ID
**GET /post**

- **Description**: Retrieve a post by its ID.
- **Parameters**:
  - `id` (Long): ID of the post.
- **Response**: `200 OK` with the post data, `404 Not Found` if the post does not exist.

### Create a New Post with Images
**POST /post/new**

- **Description**: Create a new post with optional images.
- **Parameters**:
  - `title` (String): Title of the post.
  - `content` (String): Content of the post.
  - `files` (List<MultipartFile>): Optional list of images.
- **Response**: `200 OK` with the created post data, `500 Internal Server Error` if an error occurs.

### Create a New Post without Images
**POST /post/add**
- **Description**: Create a new post without images.
- **Parameters**:
  - `post` (Post): Post object in the request body.
- **Response**: `200 OK` with the created post data, `500 Internal Server Error` if an error occurs.

### Get Sorted Posts
**GET /posts/get**

- **Description**: Retrieve a specified number of posts in sorted order.
- **Parameters**:
  - `desc` (boolean): Sort order, true for descending and false for ascending.
  - `numberOfPosts` (int): Number of posts to retrieve.
- **Response**: `200 OK` with the list of sorted posts.

### Upvote a Post
**POST /post/{id}/upvote**

- **Description**: Upvote a post by its ID.
- **Parameters**:
  - `id` (Long): ID of the post.
- **Response**: `200 OK` with the updated post data, `404 Not Found` if the post does not exist.

### Downvote a Post
**POST /post/{id}/downvote**

- **Description**: Downvote a post by its ID.
- **Parameters**:
  - `id` (Long): ID of the post.
- **Response**: `200 OK` with the updated post data, `404 Not Found` if the post does not exist.

### Add Image to a Post
**POST /post/{postId}/images**

- **Description**: Add an image to an existing post.
- **Parameters**:
  - `postId` (Long): ID of the post.
  - `file` (MultipartFile): Image file to add.
- **Response**: `200 OK` with the added image data, `500 Internal Server Error` if an error occurs.

### Get All Images of a Post
**GET /post/{postId}/images**

- **Description**: Retrieve all images of a post by its ID.
- **Parameters**:
  - `postId` (Long): ID of the post.
- **Response**: `200 OK` with the list of images.

### Get Pre-Signed URLs of Post Images
**GET /post/{postId}/images/urls**

- **Description**: Retrieve S3 pre-signed URLs of images for a post by its ID.
- **Parameters**:
  - `postId` (Long): ID of the post.
- **Response**: `200 OK` with the list of pre-signed URLs, `404 Not Found` if the post does not exist.
