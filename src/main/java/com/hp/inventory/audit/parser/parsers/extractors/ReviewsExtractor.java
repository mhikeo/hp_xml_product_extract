/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.parsers.extractors;

import com.hp.inventory.audit.parser.model.Product;
import com.hp.inventory.audit.parser.model.ProductReview;
import com.hp.inventory.audit.parser.parsers.DocumentParser;
import com.hp.inventory.audit.parser.parsers.rules.QueriesSpec;
import org.apache.commons.lang3.time.DateUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

/**
 * Extractor class for extracting product reviews out of a page.
 *
 * @author TCDEVELOPER
 * @version 1.0.0
 */
public class ReviewsExtractor {

    private final DocumentParser parser;

    public ReviewsExtractor(DocumentParser parser) {
        this.parser = parser;
    }

    /**
     * Extract product reviews
     */
    public Set<ProductReview> extract() {
        Product definition = parser.getDefinition();
        Document doc = parser.getDoc();

        Set<ProductReview> reviews = new HashSet<>();
        // Reviews
        doc.select(QueriesSpec.reviewsExtractQuery).forEach(review -> {
            ProductReview prodReview = new ProductReview();
            prodReview.setSiteId(1);
            prodReview.setProductNumber(definition.getProductNumber());

            prodReview.setId(reviewId(review));
            prodReview.setReviewDate(reviewPostDate(review));
            prodReview.setRating(intVal(review.select(QueriesSpec.reviewsExtractRatingQuery)));
            prodReview.setScale(intVal(review.select(QueriesSpec.reviewsExtractScaleQuery)));
            prodReview.setTitle(textVal(review.select(QueriesSpec.reviewsExtractTitleQuery)));
            prodReview.setComments(textVal(review.select(QueriesSpec.reviewsExtractComentsQuery)));
            prodReview.setUsername(textVal(review.select(QueriesSpec.reviewsExtractUsernameQuery)));
            prodReview.setLocation(textVal(review.select(QueriesSpec.reviewsExtractLocationQuery)));
            prodReview.setResponse(textVal(review.select(QueriesSpec.reviewsExtractHpResponseQuery)));
            prodReview.setResponseDate(reviewHpResponseDate(review));
            prodReview.setResponseUser(textVal(review.select(QueriesSpec.reviewsExtractHpResponseUserQuery)));

            Elements yesVote = review.select(QueriesSpec.reviewsExtractYesQuery);
            if (yesVote != null && yesVote.size() > 0) {
                yesVote = yesVote.parents();
                if (yesVote != null && yesVote.get(0) != null) {
                    yesVote = yesVote.get(0).select(QueriesSpec.reviewsExtractYesPostQuery);
                    prodReview.setReviewHelpfulYesCount(intVal(yesVote));
                }
            }
            Elements noVote = review.select(QueriesSpec.reviewsExtractNoQuery);
            if (noVote != null && noVote.size() > 0) {
                noVote = noVote.parents();
                if (noVote != null && noVote.get(0) != null) {
                    noVote = noVote.get(0).select(QueriesSpec.reviewsExtractNoPostQuery);
                    prodReview.setReviewHelpfulNoCount(intVal(noVote));
                }

            }

            reviews.add(prodReview);
        });
        return reviews;
    }

    private Integer reviewId(Element review) {
        String id = review.attr("id");
        if (id == null || id.isEmpty())
            return null;

        Matcher matcher;

        if ((matcher = QueriesSpec.reviewIdPattern.matcher(id)).find()) {
            return Integer.parseInt(matcher.group(1).toLowerCase());
        }
        return null;
    }

    private Date reviewPostDate(Element review) {
        String dt = review.select(QueriesSpec.reviewsExtractPostDateQuery).attr("content");

        if (dt != null) {
            try {
                return DateUtils.parseDateStrictly(dt, new String[]{QueriesSpec.reviewsExtractDateFormat});
            } catch (ParseException ignored) {
            }
        }
        return null;
    }

    private Date reviewHpResponseDate(Element review) {
        String dt = textVal(review.select(QueriesSpec.reviewsExtractHpResponseDateQuery));

        if (dt != null) {
            try {
                return DateUtils.parseDateStrictly(dt, new String[]{QueriesSpec.reviewsExtractDateFormat});
            } catch (ParseException ignored) {
            }
        }
        return null;
    }

    /**
     * Convert a numeric string to Integer
     */
    protected Integer intVal(Elements elements) {
        if (elements == null)
            return null;
        String text = elements.text();

        if (text == null || text.trim().isEmpty())
            return null;

        Integer i;
        try {
            i = Integer.parseInt(text);
        } catch (NumberFormatException ex) {
            return null;
        }
        return i;
    }

    /**
     * Convert elements content to String
     */
    protected String textVal(Elements elements) {
        if (elements == null)
            return null;
        String text = elements.text();

        if (text != null && text.isEmpty()) {
            return null;
        }
        return text;
    }

}
