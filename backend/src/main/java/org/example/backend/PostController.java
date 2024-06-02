package org.example.backend;

import org.example.backend.models.Image;
import org.example.backend.models.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) { this.postService = postService; }

    @CrossOrigin(origins = "*")
    @GetMapping("/post")
    public ResponseEntity<Post> getPostById(@RequestParam Long id) {
        logger.info("GET /post", id);
        try {
            return ResponseEntity.ok(postService.getPostById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/post/add")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        logger.info("POST /post/add", post.getTitle(), post.getContent());
        try {
            return ResponseEntity.ok(postService.createPost(post));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/posts/get")
    public ResponseEntity<List<Post>> getPostsSorted(@RequestParam boolean desc, @RequestParam int numberOfPosts) {
        logger.info("GET /posts/get", desc, numberOfPosts);
        return ResponseEntity.of(Optional.ofNullable(postService.getNSortedPosts(numberOfPosts, desc)));
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/post/{id}/upvote")
    public ResponseEntity<Post> incrementUpvote(@PathVariable Long id) {
        logger.info("POST /post/{id}/upvote", id);
        try {
            Post post = postService.incrementUpvote(id);
            return ResponseEntity.ok(post);
        } catch (PostNotFoundException ex) {
            return ResponseEntity.status(404).body(null);
        }
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/post/{id}/downvote")
    public ResponseEntity<Post> incrementDownvote(@PathVariable Long id) {
        logger.info("POST /post/{id}/downvote", id);
        try {
            Post post = postService.incrementDownvote(id);
            return ResponseEntity.ok(post);
        } catch (PostNotFoundException ex) {
            return ResponseEntity.status(404).body(null);
        }
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/post/{postId}/images")
    public ResponseEntity<Image> addPostImage(@PathVariable Long postId, @RequestParam("file") MultipartFile file) {
        try {
            // TODO Return image id instead???
            logger.info("POST /post/{id}/images", postId);
            return ResponseEntity.ok(postService.uploadImage(postId, file));
        }
        catch (Exception e) {
            logger.error("POST ERROR 500: "+ e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @CrossOrigin(origins="*")
    @GetMapping("/post/{postId}/images")
    public ResponseEntity<List<Image>> getPostImages(@PathVariable Long postId) {
        logger.info("GET /post/{postId}/images", postId);
        return ResponseEntity.ok(postService.getAllImagesOfPostById(postId));
    }

    @CrossOrigin(origins="*")
    @GetMapping("/post/{postId}/images/urls")
    public ResponseEntity<List<String>> getPostImagesUrls(@PathVariable Long postId) {
        logger.info("GET /post/{postId}/images/urls", postId);
        try {
            List<String> preSignedUrls = postService.getPreSignedImagesOfPostById(postId);
            return ResponseEntity.ok(preSignedUrls);
        } catch (Exception e){
            logger.error("GET ERROR 404: "+ e.getMessage());
            return ResponseEntity.status(404).body(null);
        }
    }
}
