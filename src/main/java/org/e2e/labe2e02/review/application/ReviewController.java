package org.e2e.labe2e02.review.application;

import org.e2e.labe2e02.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.e2e.labe2e02.review.domain.ReviewService;
import org.e2e.labe2e02.review.dto.NewReviewDto;
import org.e2e.labe2e02.review.dto.ReviewsByUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;


    @GetMapping("/{driverId}")
    public ResponseEntity<Page<ReviewsByUser>> getReviewsByDriverId (@PathVariable Long driverId, @RequestParam int page , @RequestParam int size){
        return ResponseEntity.ok(reviewService.getReviewsByDriverId(driverId, page, size));
    }


    @PostMapping()
    public ResponseEntity<Void> createNewReviewDTO (@RequestBody NewReviewDto newReviewDto){
        String uri = reviewService.createNewReviewDTO(newReviewDto);
        return ResponseEntity.created(URI.create(uri)).build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview (@PathVariable Long id){
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

}