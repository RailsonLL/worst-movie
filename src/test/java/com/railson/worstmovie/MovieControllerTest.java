package com.railson.worstmovie;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.railson.worstmovie.dto.ProducerWinIntervalMinMaxDto;
import com.railson.worstmovie.dto.SimpleMovieDto;
import com.railson.worstmovie.dto.WinnerStudioDto;
import com.railson.worstmovie.dto.YearsWithMultipleWinnersDto;
import com.railson.worstmovie.entity.Movie;
import com.railson.worstmovie.entity.Producer;
import com.railson.worstmovie.entity.Studio;
import com.railson.worstmovie.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class MovieControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Order(1)
    @DisplayName("Return list of movies when successful")
    public void getAllTest(){
        ResponseEntity<List<SimpleMovieDto>> movies = testRestTemplate.exchange("/api/movies",
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        Assertions.assertThat(movies.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(movies.getBody())
                .isNotNull()
                .isNotEmpty()
                .hasSize(206);
    }

    @Test
    @Order(2)
    @DisplayName("Return producer with longest and shortest interval between wins")
    public void getProducerWinIntervalMinMaxTest(){
        ProducerWinIntervalMinMaxDto intervalMinMax = testRestTemplate.exchange("/api/movies/max-min-win-interval-for-producers",
                HttpMethod.GET, null, new ParameterizedTypeReference<ProducerWinIntervalMinMaxDto>() {
                }).getBody();

        Assertions.assertThat(intervalMinMax.getMax().get(0).getProducer()).isEqualTo("Matthew Vaughn");
        Assertions.assertThat(intervalMinMax.getMax().get(0).getInterval()).isEqualTo(13);
        Assertions.assertThat(intervalMinMax.getMax().get(0).getPreviousWin()).isEqualTo(2002);
        Assertions.assertThat(intervalMinMax.getMax().get(0).getFollowingWin()).isEqualTo(2015);

        Assertions.assertThat(intervalMinMax.getMin().get(0).getProducer()).isEqualTo("Joel Silver");
        Assertions.assertThat(intervalMinMax.getMin().get(0).getInterval()).isEqualTo(1);
        Assertions.assertThat(intervalMinMax.getMin().get(0).getPreviousWin()).isEqualTo(1990);
        Assertions.assertThat(intervalMinMax.getMin().get(0).getFollowingWin()).isEqualTo(1991);
    }

    @Test
    @Order(3)
    @DisplayName("Return list years with multiple winners")
    public void yearsWithMultipleWinnersTest(){
        List<YearsWithMultipleWinnersDto> yearsWithMultipleWinners = testRestTemplate.exchange("/api/movies/years-with-multiple-winners",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<YearsWithMultipleWinnersDto>>() {
                }).getBody();

        Assertions.assertThat(yearsWithMultipleWinners.get(0).getYear()).isEqualTo(1986 );
        Assertions.assertThat(yearsWithMultipleWinners.get(0).getWinnerCount()).isEqualTo(2 );

        Assertions.assertThat(yearsWithMultipleWinners.get(1).getYear()).isEqualTo(1990 );
        Assertions.assertThat(yearsWithMultipleWinners.get(1).getWinnerCount()).isEqualTo(2 );

        Assertions.assertThat(yearsWithMultipleWinners.get(2).getYear()).isEqualTo(2015 );
        Assertions.assertThat(yearsWithMultipleWinners.get(2).getWinnerCount()).isEqualTo(2 );
    }

    @Test
    @Order(4)
    @DisplayName("Return first page with 15 movies when successful")
    public void getMoviesPageByWinnerAndYearTest(){
        String url = "http://localhost:8080/api/movies/movies-page-by-winner-year";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("winner", "null")
                .queryParam("year", "null")
                .queryParam("page", 0)
                .queryParam("size", 15);

        ResponseEntity<PageableResponse<SimpleMovieDto>> movies = testRestTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        Assertions.assertThat(movies.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(movies.getBody())
                .isNotNull()
                .isNotEmpty()
                .hasSize(15);
    }



    @Test
    @Order(5)
    @DisplayName("Return one movie filtered by winner and year when successful")
    public void getMoviesByWinnerAndYearTest(){
        String url = "http://localhost:8080/api/movies/movies-by-winner-and-year";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("winner", true)
                .queryParam("year", 1980);

        ResponseEntity<List<SimpleMovieDto>> movies = testRestTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        Assertions.assertThat(movies.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        Assertions.assertThat(movies.getBody())
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    @Order(6)
    @DisplayName("Return top 3 studios with winners")
    public void getTop3WinnerStudiosTest(){
        List<WinnerStudioDto> winnersStudios = testRestTemplate.exchange("/api/studios/studios-with-win-count",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<WinnerStudioDto>>() {
                }).getBody();

        Assertions.assertThat(winnersStudios.get(0).getName()).isEqualTo("Columbia Pictures" );
        Assertions.assertThat(winnersStudios.get(0).getWinCount()).isEqualTo(7 );

        Assertions.assertThat(winnersStudios.get(1).getName()).isEqualTo("Paramount Pictures" );
        Assertions.assertThat(winnersStudios.get(1).getWinCount()).isEqualTo(6 );

        Assertions.assertThat(winnersStudios.get(2).getName()).isEqualTo("Warner Bros." );
        Assertions.assertThat(winnersStudios.get(2).getWinCount()).isEqualTo(5 );
    }

    @Test
    @Order(7)
    @DisplayName("Return status Created when saving one movie")
    public void movieCreateTest() throws Exception {
        Movie movie = Movie.builder()
                .year(1980)
                .studios(Set.of(new Studio(1,"Warner Bros")))
                .producers(Set.of(new Producer(1,"Lucas Films")))
                .winner(true)
                .build();

        mockMvc.perform(post("/api/movies")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(movie)))
                .andExpect(status().isCreated());
    }

}
