package org.predictor.jpaorm.hashtag.domain;

import jakarta.persistence.*;
import lombok.*;
import org.predictor.jpaorm.board.domain.Post;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HashtagType hashtag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post; // 게시글과 매칭

    @Builder
    public Hashtag(HashtagType hashtag, Post post) {
        this.hashtag = hashtag;
        this.post =  post;
    }

}
