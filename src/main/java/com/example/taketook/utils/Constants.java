package com.example.taketook.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {
    public static final Path BASE_IMAGE_FOLDER = Paths.get("pictures");
    public static final Path USER_IMAGE_FOLDER = Paths.get("pictures/users");
    public static final Path LISTING_IMAGE_FOLDER = Paths.get("pictures/listings");
    public static final Path STORY_IMAGE_FOLDER = Paths.get("pictures/stories");
    public static final String SITE_URI = "https://sparkly-toffee-166129.netlify.app";
    public static final String GET_FILE_SUB_URL = "/files/";
    public static final Double DEFAULT_RATING = 0.0;
    public static final Integer COORDINATE_RADIUS = 5; // kilometres
}
