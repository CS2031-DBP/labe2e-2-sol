package org.e2e.labe2e02.review.domain;

import org.e2e.labe2e02.driver.domain.Driver;
import org.e2e.labe2e02.exceptions.ResourceNotFoundException;
import org.e2e.labe2e02.exceptions.UniqueResourceAlreadyExist;
import org.e2e.labe2e02.passenger.domain.Passenger;
import org.e2e.labe2e02.review.dto.NewReviewDto;
import org.e2e.labe2e02.review.dto.ReviewsByUser;
import org.e2e.labe2e02.review.infrastructure.ReviewRepository;
import org.e2e.labe2e02.ride.domain.Ride;
import org.e2e.labe2e02.ride.infrastructure.RideRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.e2e.labe2e02.review.dto.ReviewsByUser.defaultReview;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ReviewService(ReviewRepository reviewRepository, RideRepository rideRepository, ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.rideRepository = rideRepository;
        this.modelMapper = modelMapper;
    }


    public String createNewReviewDTO(NewReviewDto newReviewDto) {
        Long rideId = newReviewDto.getRideId();
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found"));
        Driver target = ride.getDriver();
        Passenger author = ride.getPassenger();
        if (reviewRepository.findByRide_Id(rideId).isPresent()) {
            throw new UniqueResourceAlreadyExist("Review for this ride already exists");
        }
        Review newReview = new Review();
        newReview.setComment(newReviewDto.getComment());
        newReview.setRating(newReviewDto.getRating());
        newReview.setRide(ride);
        newReview.setAuthor(author);
        newReview.setTarget(target);
        rideRepository.save(ride);
        Review savedReview = reviewRepository.save(newReview);
        return "/review/" + savedReview.getId();
    }
    public void deleteReview(Long id) {
        reviewRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        reviewRepository.deleteById(id);
    }


    public Page<ReviewsByUser> getReviewsByDriverId(Long driverId,  int page , int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (!rideRepository.existsByDriver_Id(driverId)) {
            throw new ResourceNotFoundException("Driver not found");
        }
        Page<ReviewsByUser> reviews = reviewRepository.findByTarget_Id(driverId, pageable);
        List<ReviewsByUser> reviewsContent = new ArrayList<>(reviews.getContent());
        while (reviewsContent.size() < 5) {
            reviewsContent.add(defaultReview);
        }
        return new PageImpl<>(reviewsContent, pageable, reviewsContent.size());
    }
}