package org.example.backend;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import org.example.backend.models.Image;
import org.example.backend.models.ImagePost;
import org.example.backend.models.ImagePostId;
import org.example.backend.models.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    protected final transient PostRepo postRepo;
    protected final transient ImagePostRepo imagePostRepo;
    protected final transient ImageRepo imageRepo;
    private final MinioClient minioClient;
    private final String bucketName;

    @Autowired
    public PostService(PostRepo postRepo, ImagePostRepo imagePostRepo, ImageRepo imageRepo, MinioClient minioClient, @Value("${minio.postservice.bucket.name}") String bucketName){
        this.postRepo = postRepo;
        this.imagePostRepo = imagePostRepo;
        this.imageRepo = imageRepo;
        this.minioClient = minioClient;
        this.bucketName = bucketName;
    }

    public List<Post> getAllPosts(){
        return postRepo.findAll();
    }

    public List<Post> getNSortedPosts(int n, boolean desc) {
        PageRequest pageRequest = PageRequest.of(0, n);
        if (desc) {
            return postRepo.getNDescByScore(pageRequest);
        } else {
            return postRepo.getNAscByScore(pageRequest);
        }
    }

    public Post getPostById(Long id) {
        return postRepo.findById(id).orElseThrow(() -> new PostNotFoundException("Post not found with id: " + id));
    }

    public Post createPost(Post post) {
        Post newPost = Post.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .upvote(0)
                .downvote(0)
                .createdAt(LocalDateTime.now())
                .build();
        return Optional.of(postRepo.save(newPost)).orElseThrow(() -> new PostNotSavedException("Post not saved."));
    }

    public Post incrementUpvote(Long postId) {
        Optional<Post> optionalPost = postRepo.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setUpvote(post.getUpvote() + 1);
            return postRepo.save(post);
        } else {
            throw new PostNotFoundException("Post not found with id: " + postId);
        }
    }

    public Post incrementDownvote(Long postId) {
        Optional<Post> optionalPost = postRepo.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setDownvote(post.getDownvote() + 1);
            return postRepo.save(post);
        } else {
            throw new PostNotFoundException("Post not found with id: " + postId);
        }
    }

    @Transactional
    public Image uploadImage(Long postId, MultipartFile file) throws Exception {
        String fileName = "images/" + postId + "/" + file.getOriginalFilename();

        try (InputStream inputStream = file.getInputStream()) {
            // Save image metadata to db
            Image image = Image.builder()
                    .createdAt(LocalDateTime.now())
                    .fileKey(fileName)
                    .build();
            image = imageRepo.save(image);

            logger.info("Saved metadata, creating relationship of post: "+postId+" to image: "+image.getId());
            // Create ImagePost relationship
            ImagePostId imagePostId = new ImagePostId();
            imagePostId.setPostId(postId);
            imagePostId.setImageId(image.getId());
            ImagePost imagePost = new ImagePost(imagePostId);
            imagePost = imagePostRepo.save(imagePost);
            logger.info("Service: "+image.toString());

            // Upload the file to minio
            logger.info("Uploading image to minio: " + fileName);
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucketName)
                            .object(fileName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());
            logger.info("Saving metadata to db");

            return image;
        } catch (MinioException e){
            logger.error(e.getMessage());
            throw new Exception("Error occurred while uploading the image to MinIO", e);
        }
    }



    public List<Image> getAllImagesOfPostById(Long postId) throws PostNotFoundException {
        // Check if postId exists
        if (!postRepo.existsById(postId)) {
            throw new PostNotFoundException("Post not found with id: " + postId);
        }
        // TODO Refactor to minimize db request, only for fileKeys
        List<Image> images = imagePostRepo.findImagesByPostId(postId);
        return images;
    }

    public List<String> getPreSignedImagesOfPostById(Long postId) {
        List<Image> images = null;
        try {
            images = getAllImagesOfPostById(postId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<String> preSignedImages = images.stream().map(image -> {
                    try {
                        return minioClient.getPresignedObjectUrl(
                                GetPresignedObjectUrlArgs.builder()
                                        .method(Method.GET)
                                        .bucket(bucketName)
                                        .object(image.getFileKey())
                                        .expiry(60 * 60)
                                        .build()
                        );
                    } catch (Exception e) {
                        logger.error("Error generating pre-signed URL for image: " + image.getFileKey(), e);
                        return null; // or handle differently, e.g., throw a runtime exception
                    }
                }).filter(Objects::nonNull) // To remove any null values from the list
                .collect(Collectors.toList());
        return preSignedImages;
    }
}
