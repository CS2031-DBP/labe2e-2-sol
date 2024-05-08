package org.e2e.labe2e02.review.dto;


public interface ReviewsByUser {
    Long getId();
    String getComment();
    Integer getRating();
    ReviewsByUser defaultReview = new ReviewsByUser() {
        @Override
        public Long getId() {
            return 0L; // Valor predeterminado
        }

        @Override
        public String getComment() {
            return ""; // Valor predeterminado
        }

        @Override
        public Integer getRating() {
            return 0; // Valor predeterminado
        }
    };
}

