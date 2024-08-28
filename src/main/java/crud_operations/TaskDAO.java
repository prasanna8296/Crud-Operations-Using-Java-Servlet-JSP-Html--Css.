package crud_operations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskDAO {
    private List<Task> tasks;
    private AtomicInteger idCounter;

    public TaskDAO() {
        tasks = new ArrayList<>();
        idCounter = new AtomicInteger(1); // Start ID counter from 1
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public Task getTaskById(int id) {
        return tasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void addTask(Task task) {
        int id = idCounter.getAndIncrement(); // Get new ID and increment counter
        task.setId(id); // Assign the new ID to the task
        tasks.add(task);
        System.out.println("Task added: " + task); // Debug statement
    }

    public void updateTask(Task task) {
        boolean updated = false;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == task.getId()) {
                tasks.set(i, task);
                updated = true;
                System.out.println("Task updated: " + task); // Debug statement
                break;
            }
        }
        if (!updated) {
            System.out.println("Task with ID " + task.getId() + " not found for update."); // Debug statement
        }
    }

    public void deleteTask(int id) {
        boolean removed = tasks.removeIf(task -> task.getId() == id);
        if (removed) {
            System.out.println("Task with ID " + id + " deleted."); // Debug statement
        } else {
            System.out.println("Task with ID " + id + " not found for deletion."); // Debug statement
        }
    }
}
