package todo.db;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import todo.core.Task;
import todo.core.Todo;

import java.util.List;


public class TodoDao extends BasicDAO<Todo, ObjectId> {

    public TodoDao(Datastore ds) {
        super(ds);
    }

    public Todo update(ObjectId objectId, Todo todo) {
        Todo existing = get(objectId);
        if (existing == null) {
            save(todo);
        } else {
            Todo updateTodo = copyUpdatedValues(existing, todo);
            save(updateTodo);
        }
        return get(objectId);
    }

    private Todo copyUpdatedValues(Todo existing, Todo todoToUpd) {
        if (todoToUpd.getName() != null &&
                !todoToUpd.getName().equals(existing.getName())) {
            existing.setName(todoToUpd.getName());
        }
        if (todoToUpd.getDescription() != null &&
                !todoToUpd.getDescription().equals(existing.getDescription())) {
            existing.setDescription(todoToUpd.getDescription());
        }
        if (todoToUpd.getTasks() != null) {
            existing.setTasks(updateTasks(existing, todoToUpd));
        }
        return existing;
    }

    private List<Task> updateTasks(Todo existing, Todo todoToUpd) {
        List<Task> existingTasks = existing.getTasks();
        for(Task updTask : todoToUpd.getTasks()) {
            Task matchedTask = existingTasks.stream()
                    .filter(t -> t.getId().equals(updTask.getId()))
                    .findAny().orElse(null);
            if (matchedTask == null) {
                existingTasks.add(updTask);
            } else {
                if (updTask.getName() != null) {
                    matchedTask.setName(updTask.getName());
                }
                if (updTask.getDescription() != null) {
                    matchedTask.setDescription(updTask.getDescription());
                }
            }
        }
        return existingTasks;
    }
}
