package com.bruno.api.config;

import com.bruno.api.dto.AuthorDTO;
import com.bruno.api.dto.CommentDTO;
import com.bruno.api.entities.Post;
import com.bruno.api.entities.User;
import com.bruno.api.repositories.PostRepository;
import com.bruno.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        userRepository.deleteAll();
        postRepository.deleteAll();


        User maria = new User(null, "Maria Brown", "maria@gmail.com");
        User alex = new User(null, "Alex Green", "alex@gmail.com");
        User bob = new User(null, "Bob Grey", "bob@gmail.com");

        userRepository.saveAll(Arrays.asList(maria, alex, bob));

        Post post1 = new Post(null, sdf.parse("21/03/2023"), "My Trip", "Trip to Australia", new AuthorDTO(maria));
        Post post2 = new Post(null, sdf.parse("23/03/2023"), "Hello world", "Good day", new AuthorDTO(maria));

        CommentDTO c1 = new CommentDTO("Good travel", sdf.parse("05/11/2020"), new AuthorDTO(alex));
        CommentDTO c2 = new CommentDTO("Enjoy", sdf.parse("05/11/2020"), new AuthorDTO(bob));
        CommentDTO c3 = new CommentDTO("Have a nice day", sdf.parse("05/11/2020"), new AuthorDTO(alex));

        post1.getComments().addAll(Arrays.asList(c1, c2));
        post2.getComments().addAll(Arrays.asList(c3));

        postRepository.saveAll(Arrays.asList(post1, post2));

        maria.getPosts().addAll(Arrays.asList(post1, post2));
        userRepository.save(maria);
    }
}
