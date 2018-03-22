package com.udacity.gradle.builditbigger.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.ji.javajokeslibrary.Jokes;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "jokeApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.gradle.ji.com",
                ownerName = "backend.builditbigger.gradle.ji.com",
                packagePath = ""
        )
)
public class JokeApiEndPoint {

    @ApiMethod(name = "getJoke")
    public Joke getJoke() {
        Joke result = new Joke();
        Jokes jokesLoader = new Jokes();
        result.setJokeText(jokesLoader.getJoke());
        return result;
    }

}
