package com.sky.stream.service;

import com.sky.stream.exception.TitleNotFoundException;

public interface ParentalControlService {

    /**
     * checks if the movie is suitable to watch in terms of parental control level
     * @param userControlLevel parental control level defined by user ("U", "PG", "12", "15", "18)
     * @param movieId id of the movie
     * @return true if move is suitable in terms of parental control level
     *         false otherwise
     * @throws TitleNotFoundException if movie is not found
     */
    boolean isMovieSuitable(final String userControlLevel, final long movieId) throws TitleNotFoundException;

}
