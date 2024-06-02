package org.example.backend.models;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
public class ImagePostId implements Serializable {
    private Long postId;
    private Long imageId;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        ImagePostId that = (ImagePostId) o;
//        return Objects.equals(postId, that.postId) && Objects.equals(imageId, that.imageId);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(postId, imageId);
//    }
}
