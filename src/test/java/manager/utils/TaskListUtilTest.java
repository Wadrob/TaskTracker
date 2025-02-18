package manager.utils;

import domain.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskListUtilTest {

    @Test
    void testIsTaskRemovedRemovesTaskById() {
        List<Task> tasks = new ArrayList<>(List.of(
                new Task(1, "Task 1", "TODO", LocalDateTime.now(), LocalDateTime.now()),
                new Task(2, "Task 2", "TODO", LocalDateTime.now(), LocalDateTime.now())
        ));

        boolean isRemoved = TaskListUtil.isTaskRemoved(tasks, 1);

        assertTrue(isRemoved);
        assertEquals(1, tasks.size());
        assertEquals(2, tasks.get(0).getId());
    }

    @Test
    void testIsTaskRemovedReturnsFalseIfIdNotFound() {
        List<Task> tasks = new ArrayList<>(List.of(
                new Task(1, "Task 1", "TODO", LocalDateTime.now(), LocalDateTime.now()),
                new Task(2, "Task 2", "TODO", LocalDateTime.now(), LocalDateTime.now())
        ));

        boolean isRemoved = TaskListUtil.isTaskRemoved(tasks, 3);

        assertFalse(isRemoved);
        assertEquals(2, tasks.size());
    }

    @Test
    void testGetNextIdReturnsOneForEmptyList() {
        List<Task> tasks = new ArrayList<>();

        int nextId = TaskListUtil.getNextId(tasks);

        assertEquals(1, nextId);
    }

    @Test
    void testGetNextIdReturnsIncrementedId() {
        List<Task> tasks = new ArrayList<>(List.of(
                new Task(1, "Task 1", "TODO", LocalDateTime.now(), LocalDateTime.now()),
                new Task(2, "Task 2", "TODO", LocalDateTime.now(), LocalDateTime.now())
        ));

        int nextId = TaskListUtil.getNextId(tasks);

        assertEquals(3, nextId);
    }
}
