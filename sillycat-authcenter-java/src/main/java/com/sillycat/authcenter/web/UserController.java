package com.sillycat.authcenter.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sillycat.authcenter.model.User;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
	
	Counter getUserByIDCounter;
	
	public UserController(MeterRegistry registry) {
		getUserByIDCounter = Counter.builder("users_getuserbyid")
            .description("Users getUserByID called count")
            .register(registry);
    }
	
	@Operation(summary = "Get a User by its id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the book", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
			@ApiResponse(responseCode = "404", description = "Book not found", content = @Content) })
	@GetMapping("/{id}")
	public User findByID(@RequestHeader("Authentication") String authToken, @PathVariable Long id) {
		log.info("findbyId get path variable id = " + id);
		log.info("authToken = " + authToken);
		getUserByIDCounter.increment();
		User item = new User();
		item.setId(Long.valueOf(1));
		item.setLoginName("luohuazju");
		item.setEmail("luohuazju@gmail.com");
		return item;
	}

	@Timed(value = "users.lists", description = "Time taken to list users")
	@GetMapping("/")
	public List<User> findUsers() {
		User item1 = new User();
		item1.setId(Long.valueOf(1));
		item1.setLoginName("luohuazju");
		item1.setEmail("luohuazju@gmail.com");

		User item2 = new User();
		item2.setId(Long.valueOf(2));
		item2.setLoginName("yiyikangrachel");
		item2.setEmail("yiyikangrachel@gmail.com");

		List<User> list = new ArrayList<>();
		list.add(item1);
		list.add(item2);
		return list;
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public User updateUser(@PathVariable("id") String id, @RequestBody final User user) {
		log.info("updateUser get user info = " + user);
		return user;
	}

	@PostMapping("/")
	public User saveUser(@RequestBody User user) {
		log.info("saveUser get user info = " + user);
		return user;
	}

}
