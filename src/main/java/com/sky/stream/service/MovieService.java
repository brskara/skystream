package com.sky.stream.service;

import com.sky.stream.exception.TechnicalFailureException;
import com.sky.stream.exception.TitleNotFoundException;

public interface MovieService {

    String getParentalControlLevel(final long movieId) throws TitleNotFoundException, TechnicalFailureException;

}
