package com.example.springbootbackend.controller;

import com.example.springbootbackend.exception.ResourceNotFoundException;
import com.example.springbootbackend.model.Commentary;
import com.example.springbootbackend.model.Post;
import com.example.springbootbackend.repository.CommentaryRepository;
import com.example.springbootbackend.repository.PostRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000/", exposedHeaders = "x-total-count")
@RestController
public class PostController {

    private PostRepository postRepository;

    private CommentaryRepository commentaryRepository;

    public PostController(PostRepository postRepository, CommentaryRepository commentaryRepository) {
        this.postRepository = postRepository;
        this.commentaryRepository = commentaryRepository;
    }

    //  get all posts
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts(@RequestParam(value = "_limit", required = false)
                                                          Integer limit,
                                                  @RequestParam(value = "_page", required = false, defaultValue = "1")
                                                          Integer page){
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-total-count", String.valueOf(postRepository.findAll().size()));
        return new ResponseEntity<>(limit == null || limit == -1
                ? postRepository.findAll()
                : postRepository.findAll().stream().skip((long) (page - 1) * limit).limit(limit).collect(Collectors.toList()),
                headers,
                HttpStatus.OK);
    }

    //  get post by id
    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id){
        return ResponseEntity.ok(postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not exists with id " + id)));
    }

    //  get comments by post's id
    @GetMapping("/posts/{id}/comments")
    public ResponseEntity<List<Commentary>> getCommentariesPostById(@PathVariable Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not exists with id " + id));

        return ResponseEntity.ok(post.getCommentaries());
    }


    //  save post
    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(@RequestBody Post post){
        return new ResponseEntity<>(postRepository.save(post), HttpStatus.OK);
    }

    @PutMapping("/posts/{id}/comments")
    public ResponseEntity<Post> addCommentary(@PathVariable Long id, @RequestBody Commentary commentary){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not exists with id " + id));
        post.addComment(commentary);
        return new ResponseEntity<>(postRepository.save(post), HttpStatus.OK);
    }

    @PutMapping("/posts/{id}/comments/delete/{commId}")
    public ResponseEntity<Post> addCommentary(@PathVariable Long id, @PathVariable Long commId){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not exists with id " + id));
        commentaryRepository.deleteById(commId);
        post.deleteComment(commId);
        return new ResponseEntity<>(postRepository.save(post), HttpStatus.OK);
    }

//  delete post
    @DeleteMapping("/posts/{id}")
    public void deleteEmployee(@PathVariable Long id){
        postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not exists with id " + id));
        postRepository.deleteById(id);
    }
}
