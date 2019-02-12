package com.sky.stream.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import com.sky.stream.exception.TechnicalFailureException;
import com.sky.stream.exception.TitleNotFoundException;
import com.sky.stream.impl.ParentalControlServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(Enclosed.class)
public class ParentalControlLevelServiceTest {

    private static final long MOVIE_ID = 123456L;

    @RunWith(Parameterized.class)
    public static class ParentalControlLevelServiceParameterizedTest {

        @Mock
        private MovieService movieService;

        private ParentalControlService parentalControlService;

        //fields to pass the test params
        private String userControlLevel;
        private String movieControlLevel;
        private boolean expectedResult;

        public ParentalControlLevelServiceParameterizedTest(final String userControlLevel, final String movieControlLevel, final boolean expectedResult) {
            this.userControlLevel = userControlLevel;
            this.movieControlLevel = movieControlLevel;
            this.expectedResult = expectedResult;
        }

        @Parameterized.Parameters
        public static List<Object[]> parameters() {
            return Arrays.asList(new Object[][]{
                    {"U", "U", true},
                    {"U", "PG", false},
                    {"U", "12", false},
                    {"PG", "U", true},
                    {"PG", "U", true},
                    {"PG", "PG", true},
                    {"PG", "12", false},
                    {"12", "U", true},
                    {"12", "PG", true},
                    {"12", "12", true},
                    {"12", "15", false},
                    {"15", "U", true},
                    {"15", "PG", true},
                    {"15", "15", true},
                    {"15", "18", false},
                    {"18", "U", true},
                    {"18", "12", true},
                    {"18", "18", true},
            });
        }

        @Before
        public void setUp() {
            MockitoAnnotations.initMocks(this);
            parentalControlService = new ParentalControlServiceImpl(movieService);
        }

        @Test
        public void testParentalControlService() throws Exception {
            when(movieService.getParentalControlLevel(MOVIE_ID)).thenReturn(movieControlLevel);
            boolean result = parentalControlService.isMovieSuitable(userControlLevel, MOVIE_ID);
            assertThat(expectedResult, is(result));
        }
    }

    public static class ParentalControlLevelServiceExceptionTest {

        @Mock
        private MovieService movieService;

        private ParentalControlService parentalControlService;

        @Before
        public void setUp() {
            MockitoAnnotations.initMocks(this);
            parentalControlService = new ParentalControlServiceImpl(movieService);
        }

        @Test (expected = IllegalArgumentException.class)
        public void testParentalControlService_shouldThrowIllegalArgumentException_whenInvalidControlLevelProvided() throws Exception {
            final String invalidControlLevel = "XX";
            when(movieService.getParentalControlLevel(MOVIE_ID)).thenReturn("U");
            parentalControlService.isMovieSuitable(invalidControlLevel, MOVIE_ID);
        }

        @Test (expected = TitleNotFoundException.class)
        public void testParentalControlService_shouldThrowTitleNotFoundException_whenMovieServiceThrowsTitleNotFoundException() throws Exception {
            final String controlLevel = "U";
            doThrow(TitleNotFoundException.class).when(movieService).getParentalControlLevel(MOVIE_ID);
            parentalControlService.isMovieSuitable(controlLevel, MOVIE_ID);
        }

        @Test
        public void testParentalControlService_shouldReturnFalse_whenMovieServiceThrowsTechnicalFailureException() throws Exception {
            final String controlLevel = "U";
            doThrow(TechnicalFailureException.class).when(movieService).getParentalControlLevel(MOVIE_ID);
            boolean result = parentalControlService.isMovieSuitable(controlLevel, MOVIE_ID);
            assertFalse(result);
        }

    }
}
