package com.pulley.captrivia;

import com.pulley.captrivia.model.questions.Question;
import com.pulley.captrivia.model.questions.QuestionsLoader;
import com.pulley.captrivia.resources.LeaderboardResource;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

import java.util.List;

public class CaptriviaApp extends Application<CaptriviaAppConfiguration> {
    public static void main(String[] args) throws Exception {
        new CaptriviaApp().run(args);
    }

    @Override
    public String getName() {
        return "captrivia";
    }

    @Override
    public void initialize(Bootstrap<CaptriviaAppConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(CaptriviaAppConfiguration configuration, Environment environment) throws Exception {
        // Load the questions
        QuestionsLoader questionsLoader = new QuestionsLoader();
        List<Question> questions = questionsLoader.loadFromFile(configuration.getQuestionsFile());

        // Set up the resources
        LeaderboardResource leaderboardResource = new LeaderboardResource();
        environment.jersey().register(leaderboardResource);
    }
}
