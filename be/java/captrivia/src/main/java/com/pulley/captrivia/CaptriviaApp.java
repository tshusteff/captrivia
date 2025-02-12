package com.pulley.captrivia;

import be.tomcools.dropwizard.websocket.WebsocketBundle;
import com.pulley.captrivia.model.questions.Question;
import com.pulley.captrivia.model.questions.QuestionsLoader;
import com.pulley.captrivia.resources.GamesResource;
import com.pulley.captrivia.resources.LeaderboardResource;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterRegistration;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import java.util.EnumSet;
import java.util.List;

public class CaptriviaApp extends Application<CaptriviaAppConfiguration> {
    private WebsocketBundle websocket = new WebsocketBundle();
    public static void main(String[] args) throws Exception {
        new CaptriviaApp().run(args);
    }

    @Override
    public String getName() {
        return "captrivia";
    }

    @Override
    public void initialize(Bootstrap<CaptriviaAppConfiguration> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addBundle(websocket);
    }

    @Override
    public void run(CaptriviaAppConfiguration configuration, Environment environment) throws Exception {
        // Load the questions
        QuestionsLoader questionsLoader = new QuestionsLoader();
        List<Question> questions = questionsLoader.loadFromFile(configuration.getQuestionsFile());

        // Set up CORS headers
        allowAllCORS(environment);

        // Set up the resources
        LeaderboardResource leaderboardResource = new LeaderboardResource();
        environment.jersey().register(leaderboardResource);
        GamesResource gamesResource = new GamesResource(questions);
        environment.jersey().register(gamesResource);

        websocket.addEndpoint(PlayerConnectServerEndpoint.class);
    }

    private void allowAllCORS(Environment environment) {
        // Enable CORS headers
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add to all paths
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }
}
