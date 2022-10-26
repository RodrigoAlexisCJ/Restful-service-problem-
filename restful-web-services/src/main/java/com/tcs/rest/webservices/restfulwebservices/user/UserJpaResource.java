package com.tcs.rest.webservices.restfulwebservices.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tcs.rest.webservices.restfulwebservices.jpa.PostRepository;
import com.tcs.rest.webservices.restfulwebservices.jpa.UserRepository;

@RestController
public class UserJpaResource {
//@Autowired
private PostRepository postRepository;
private UserRepository userRepository;

public UserJpaResource(UserRepository userRepository,PostRepository postRepository) {
	this.userRepository=userRepository;
	this.postRepository=postRepository;
}

@RequestMapping(value="/jpa/users",method=RequestMethod.GET)
public List<User> retrieveAllUsers(){
	return userRepository.findAll();
}

@RequestMapping(value="/jpa/users/{id}",method=RequestMethod.GET)
public EntityModel<User> retrieveUser(@PathVariable int id){
	Optional<User> user=userRepository.findById(id);
	if(user.isEmpty()) {
		throw new UserNotFoundException("id "+id);
	}
	
	EntityModel<User> entityModel = EntityModel.of(user.get());
	
	WebMvcLinkBuilder link =  linkTo(methodOn(this.getClass()).retrieveAllUsers());
	
	entityModel.add(link.withRel("all-users"));
	
	return entityModel;
}

/*@RequestMapping(value="/users/{id}",method=RequestMethod.GET)
public User retrieveUser(@PathVariable int id){
	User user=service.findOneUser(id);
	if(user==null) {
		throw new UserNotFoundException("id "+id);
	}
	return user;
}*/

@RequestMapping(value="/jpa/users/{id}",method=RequestMethod.DELETE)
public void deletUser(@PathVariable int id){
	userRepository.deleteById(id);
}

@RequestMapping(value="/jpa/users/{id}/posts",method=RequestMethod.GET)
public List<Post> retrievePostsForAllUser(@PathVariable int id){
	Optional<User> user=userRepository.findById(id);
	if(user.isEmpty()) {
		throw new UserNotFoundException("id "+id);
	}
	
	return user.get().getPosts();
}

@RequestMapping(value="/jpa/users/{id}/posts",method=RequestMethod.POST)
public ResponseEntity<Object> createPostsForAllUser(@PathVariable int id, @Valid @RequestBody Post post){
	Optional<User> user=userRepository.findById(id);
	if(user.isEmpty()) {
		throw new UserNotFoundException("id "+id);
	}
	post.setUser(user.get());
	
	Post savedPost=postRepository.save(post);
	
	URI location = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedPost.getId())
			.toUri();
	return ResponseEntity.created(location).build();
}

@RequestMapping(value="/jpa/users",method=RequestMethod.POST)
public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
	User savedUser = userRepository.save(user);
	URI location = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedUser.getId())
			.toUri();
	return ResponseEntity.created(location).build();
	}
}
