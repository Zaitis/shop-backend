package pl.zaitis.shop.review.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.web.bind.annotation.*;
import pl.zaitis.shop.review.model.Review;
import pl.zaitis.shop.review.service.ReviewService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public Review addReview(@RequestBody @Valid ReviewDto reviewDto){
        return reviewService.addReview(Review.builder()
                .authorName(cleanContent(reviewDto.authorName()))
                .content(cleanContent(reviewDto.content()))
                .productId(reviewDto.productId())
                .build());
    }


    private String cleanContent(String text) {
        return Jsoup.clean(text, Safelist.none());
    }
}
