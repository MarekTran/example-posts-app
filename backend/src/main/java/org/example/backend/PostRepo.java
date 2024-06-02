package org.example.backend;


import org.example.backend.models.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p ORDER BY (p.upvote - p.downvote) DESC")
    List<Post> getNDescByScore(Pageable pageable);

    @Query("SELECT p FROM Post p ORDER BY (p.upvote - p.downvote) ASC")
    List<Post> getNAscByScore(Pageable pageable);
}
