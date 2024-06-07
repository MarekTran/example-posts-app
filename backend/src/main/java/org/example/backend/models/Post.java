package org.example.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;
    private int upvote;
    private int downvote;
    @Column(nullable = false, updatable = true, columnDefinition = "boolean default false")
    private boolean approved;
}
