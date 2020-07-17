package com.fr.sonarfilm.movie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fr.sonarfilm.movie.dto.MovieDTO;
import com.fr.sonarfilm.movie.models.Movie;
import com.fr.sonarfilm.movie.repositories.MovieRepository;

@WebMvcTest
@TestInstance(Lifecycle.PER_CLASS)
class MovieApplicationTests {
		
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MovieRepository movieRepo;
	
	
	
	
	
/*	private MovieDTO convertToDto(Movie movie) {
		MovieDTO movieDto = modelMapper.map(movie, MovieDTO.class);
		return movieDto;
	}
*/
	
	private MovieDTO convertToDto(Movie movie) {
		MovieDTO movieDto = new MovieDTO();
		BeanUtils.copyProperties(movie, movieDto);
		return movieDto;
	}

	@Test
	public void testGetMovie() throws Exception {

		 Movie jumanji = new Movie("jumanji", "jumanji", "robin williams", "112", "action", "comedy", "1998", "adventure", "drama", "dangerous game", "tt12345", "no", "williames", "na", "na", 7, 9874L);
		 List<Movie> movie = Arrays.asList(jumanji);
		 
		given(movieRepo.findByTitle(jumanji.getTitle())).willReturn(movie);


		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/apimovies/title/{title}", "jumanji")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().json("[{'title':'jumanji','originalTitle':jumanji,'actors':'robin williams','duration':'112', 'firstGenre':'action'}]"));

	} 

	
	
	
	@Test
	public void WrongPathShouldReturnERRORCLIENT() throws Exception {

		 Movie jumanji = new Movie("jumanji", "jumanji", "robin williams", "112", "action", "comedy", "1998", "adventure", "drama", "dangerous game", "tt12345", "no", "williams", "na", "na", 7, 9874L);

		List<Movie> movie = Arrays.asList(jumanji);

		given(movieRepo.findByTitle(jumanji.getTitle())).willReturn(movie);


		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/wrongpath/")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());;

	}
	
	
	 
	 @Test
	    public void whenConvertMovieEntityToMovieDto_thenCorrect() {
			MovieDTO movieDto = new MovieDTO();

		 Movie jumanji = new Movie("jumanji", "jumanji", "robin williams", "112", "action", "comedy", "1998", "adventure", "drama", "dangerous game", "tt12345", "no", "williames", "na", "na", 7, 9874L);
		 	movieDto = convertToDto(jumanji);
	        assertEquals(jumanji.getTitle(), movieDto.getTitle());

	    }
	

}
