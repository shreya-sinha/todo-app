package todo;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.mongodb.morphia.Datastore;
import todo.core.Todo;
import todo.db.TodoDao;
import todo.api.TodoResource;
import todo.health.MongoDBHealthCheck;
import xyz.randomcode.dropwizard_morphia.MongoConfiguration;
import xyz.randomcode.dropwizard_morphia.MorphiaBundle;

public class TodoApplication extends Application<TodoConfiguration> {

    public static void main(final String[] args) throws Exception {
        new TodoApplication().run(args);
    }

    @Override
    public String getName() {
        return "todo";
    }

    @Override
    public void initialize(final Bootstrap<TodoConfiguration> bootstrap) {
        bootstrap.addBundle(morphiaBundle);
    }

    @Override
    public void run(final TodoConfiguration configuration,
                    final Environment environment) {
        Datastore datastore = morphiaBundle.getDatastore();
        TodoDao dao = new TodoDao(datastore);
        environment.jersey().register(new TodoResource(dao));

        final MongoDBHealthCheck healthCheck = new MongoDBHealthCheck(datastore.getMongo());
        environment.healthChecks().register("mongodb", healthCheck);
    }

    private MorphiaBundle<TodoConfiguration> morphiaBundle =
            new MorphiaBundle<TodoConfiguration>(Todo.class) {
                @Override
                protected MongoConfiguration getMongo(TodoConfiguration configuration) {
                    return configuration.getMongo();
                }
            };

}
