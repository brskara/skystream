package com.sky.stream.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.sky.stream.service.MovieService;
import com.sky.stream.service.ParentalControlService;
import com.sky.stream.enums.ParentalControlLevel;
import com.sky.stream.exception.TechnicalFailureException;
import com.sky.stream.exception.TitleNotFoundException;

public class ParentalControlServiceImpl implements ParentalControlService {

    private static final Logger LOGGER = Logger.getLogger(ParentalControlServiceImpl.class.getName());

    //TODO FOR IMPROVEMENT: Use a dependency injection library to instantiate & inject MovieService and ParentalControlService objects
    private MovieService movieService;

    public ParentalControlServiceImpl(final MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public boolean isMovieSuitable(final String userControlLevel, final long movieId) throws TitleNotFoundException {
        final String movieControlLevel;
        try {
            movieControlLevel = movieService.getParentalControlLevel(movieId);
        } catch (TechnicalFailureException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            return false;
        }
        return ParentalControlLevel.fromName(movieControlLevel).getValue() <= ParentalControlLevel.fromName(userControlLevel).getValue();
    }

}
