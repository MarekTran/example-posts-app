package org.example.backend;

import org.example.backend.models.Image;
import org.example.backend.models.ImagePost;
import org.example.backend.models.ImagePostId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagePostRepo extends JpaRepository<ImagePost, ImagePostId> {
    // TODO Perhaps refactor to split this from return an Image, only ImageIds ???
    @Query("SELECT i FROM Image i, ImagePost ip WHERE ip.id.postId = :postId AND ip.id.imageId = i.id")
    List<Image> findImagesByPostId(Long postId);
}
