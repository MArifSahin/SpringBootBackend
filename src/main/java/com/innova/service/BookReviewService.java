package com.innova.service;

import com.innova.dto.request.EditorReviewForm;
import com.innova.dto.request.UserReviewForm;
import com.innova.dto.response.DashboardBookResponse;
import com.innova.model.BookReview;
import com.innova.model.User;

import java.util.Map;

public interface BookReviewService {
    BookReview saveReview(BookReview bookReview);

    Map<String, DashboardBookResponse> getLatestReviews();

    BookReview saveUserReview(UserReviewForm userReviewForm, User user);

    BookReview saveEditorReview(EditorReviewForm editorReviewForm, User user);
}
