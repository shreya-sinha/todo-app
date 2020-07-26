package todo.api;

import com.codahale.metrics.annotation.Timed;
import com.mongodb.WriteResult;
import io.dropwizard.jersey.params.NonEmptyStringParam;
import io.dropwizard.validation.Validated;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.QueryResults;
import todo.core.Todo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import todo.db.TodoDao;

@Path("/todos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TodoResource {

    static final Logger LOG = LoggerFactory.getLogger(TodoResource.class);

    private final TodoDao todoDao;

    public TodoResource(TodoDao todoDao) {
        this.todoDao = todoDao;
    }

    @GET
    @Timed
    public List<Todo> getAllToDos() {
        LOG.debug("getAll");
        QueryResults<Todo> all = todoDao.find();
        return all.asList();
    }

    @POST
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    public Todo createTodo(Todo todo) {
        LOG.debug("create " + todo);
        Key<Todo> key = todoDao.save(todo);
        Todo savedTodo = todoDao.get(new ObjectId(key.getId().toString()));
        return savedTodo;
    }

    @GET
    @Timed
    @Path("/{id}")
    public Todo getToDo(@PathParam("id") @Validated NonEmptyStringParam id) {
        LOG.debug("get " + id);
        Todo todo = todoDao.get(new ObjectId(id.get().get()));
        return todo;
    }

    @PUT
    @Timed
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Todo updateToDo(@PathParam("id")  @Validated NonEmptyStringParam id, Todo todo) {
        LOG.debug("update " + id);
        return todoDao.update(new ObjectId(id.get().get()), todo);
    }

    @DELETE
    @Timed
    @Path("/{id}")
    public String deleteToDo(@PathParam("id")  @Validated NonEmptyStringParam id) {
        LOG.debug("delete " + id);
        WriteResult response = todoDao.deleteById(new ObjectId(id.get().get()));
        if (response.wasAcknowledged()) {
           return "Deleted Todo " + id.get().get();
        }
        return "Missing Todo " + id.get().get();
    }
}
