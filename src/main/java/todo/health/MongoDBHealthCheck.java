package todo.health;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import todo.api.TodoResource;

public class MongoDBHealthCheck extends HealthCheck {

    static final Logger LOG = LoggerFactory.getLogger(MongoDBHealthCheck.class);
        /** A client of MongoDB.*/
        private MongoClient mongoClient;

        /**
         * Constructor.
         *
         * @param mongoClient the mongo client.
         */
        public MongoDBHealthCheck(final MongoClient mongoClient) {
            this.mongoClient = mongoClient;
        }
        @Override
        protected Result check() {
            try {
                final MongoDatabase db = mongoClient.getDatabase("todo");
                MongoIterable<String> allCollections = db.listCollectionNames();
                LOG.debug( "Mongo health check, first collection"  + allCollections.first());
            } catch (final Exception e) {
                return Result.unhealthy("Can not get the information from database.");
            }
            return Result.healthy();
        }
}
