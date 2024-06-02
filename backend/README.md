# Posts API Documentation

## Endpoints

### Get Post by ID
**GET /post**

- **Description**: Retrieve a post by its ID.
- **Parameters**:
  - `id` (Long): ID of the post.
- **Response**: `200 OK` with the post data, `404 Not Found` if the post does not exist.

### Create Post
**POST /post/add**

- **Description**: Create a new post with a title and content.
- **Request Body**:
  - `title` (String): Title of the post.
  - `content` (String): Content of the post.
- **Response**: `200 OK` with the created post data, `500 Internal Server Error` on failure.

### Get Sorted Posts
**GET /posts/get**

- **Description**: Retrieve a list of posts sorted by date.
- **Parameters**:
  - `desc` (boolean): Sort order (true for descending, false for ascending).
  - `numberOfPosts` (int): Number of posts to retrieve.
- **Response**: `200 OK` with the list of posts.

### Upvote Post
**POST /post/{id}/upvote**

- **Description**: Increment the upvote count of a post.
- **Path Variable**:
  - `id` (Long): ID of the post.
- **Response**: `200 OK` with the updated post data, `404 Not Found` if the post does not exist.

### Downvote Post
**POST /post/{id}/downvote**

- **Description**: Increment the downvote count of a post.
- **Path Variable**:
  - `id` (Long): ID of the post.
- **Response**: `200 OK` with the updated post data, `404 Not Found` if the post does not exist.

### Add Image to Post
**POST /post/{postId}/images**

- **Description**: Upload an image to a post.
- **Path Variable**:
  - `postId` (Long): ID of the post.
- **Request Parameter**:
  - `file` (MultipartFile): Image file to upload.
- **Response**: `200 OK` with the uploaded image data, `500 Internal Server Error` on failure.

### Get Images of a Post
**GET /post/{postId}/images**

- **Description**: Retrieve all images of a post.
- **Path Variable**:
  - `postId` (Long): ID of the post.
- **Response**: `200 OK` with the list of images.

### Get Image URLs of a Post
**GET /post/{postId}/images/urls**

- **Description**: Retrieve pre-signed URLs for all images of a post.
- **Path Variable**:
  - `postId` (Long): ID of the post.
- **Response**: `200 OK` with the list of pre-signed URLs, `404 Not Found` on failure.
