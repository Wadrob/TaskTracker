package manager.utils;

import domain.Task;

import java.util.List;

public class TaskListUtil {

    public static boolean isTaskRemoved(List<Task> tasksList, int id) {
        return tasksList.removeIf(task -> task.getId() == id);
    }

    public static int getNextId(List<Task> tasksList) {

        if (tasksList.isEmpty()) {
            return 1;
        }

        Task lastTask = tasksList.get(tasksList.size() - 1);
        return lastTask.getId() + 1;
    }
}
