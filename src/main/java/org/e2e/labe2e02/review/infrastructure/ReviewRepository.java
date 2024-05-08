package org.e2e.labe2e02.review.infrastructure;

import org.e2e.labe2e02.review.domain.Review;
import org.e2e.labe2e02.review.dto.ReviewsByUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Set<Review> findByRating(Integer rating);
    Set<Review> findByAuthor_Id(Long authorId);
    Long countByAuthor_Id(Long authorId);
    Page<ReviewsByUser> findByTarget_Id(Long targetId, Pageable pageable);
    Optional<Review> findByRide_IdAndTarget_Id(Long rideId, Long targetId);
    Optional<Review> findByRide_Id(Long rideId);
}