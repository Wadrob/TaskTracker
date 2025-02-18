package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static manager.helper.TaskFileHelper.getObjectMapper;
import static manager.helper.TaskFileHelper.getTasksFromFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskManagerServiceTest {

    private TaskManagerService taskManagerService;
    private ObjectMapper objectMapper;
    private File testFile;

    @BeforeEach
    void setUp() throws IOException {
        objectMapper = getObjectMapper();
        testFile = File.createTempFile("tasks", ".json");
        taskManagerService = new TaskManagerService(testFile);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(testFile.toPath());
    }

    @Test
    void shouldAddTaskSuccessfully() throws IOException {
        String taskDescription = "add task";

        String result = taskManagerService.addToTasks(taskDescription);
        List<Task> tasks = getTasksFromFile(testFile, objectMapper);

        assertTrue(result.contains("Task added successfully"));
        assertEquals(1, tasks.size());
    }

    @Test
    void shouldUpdateTaskSuccessfully() throws IOException {
        String originalTask = "add task";
        String updateLine = "update 1 newTask";

        taskManagerService.addToTasks(originalTask);
        String result = taskManagerService.updateTask(updateLine);
        List<Task> updatedTasks = getTasksFromFile(testFile, objectMapper);

        assertEquals(1, updatedTasks.size());
        assertEquals("Task updated successfully (ID: 1)", result);
        assertEquals("newTask", updatedTasks.get(0).getDescription());
    }

    @Test
    void shouldDeleteTaskSuccessfully() throws IOException {
        String firstTask = "add first";
        String secondLine = "add second";

        taskManagerService.addToTasks(firstTask);
        taskManagerService.addToTasks(secondLine);

        List<Task> originalTasks = getTasksFromFile(testFile, objectMapper);
        assertEquals(2, originalTasks.size());

        String result = taskManagerService.deleteTask("delete 1");
        List<Task> updatedTasks = getTasksFromFile(testFile, objectMapper);

        assertEquals(1, updatedTasks.size());
        assertEquals("Task deleted successfully (ID: 1)", result);
    }

    @Test
    void shouldMarkAllStatuses() throws IOException {
        String task = "add task";

        taskManagerService.addToTasks(task);
        List<Task> tasks = getTasksFromFile(testFile, objectMapper);
        assertEquals("todo", tasks.get(0).getStatus());

        String inProgress = "mark in-progress 1";
        taskManagerService.markTask(inProgress);
        List<Task> tasksInProgress = getTasksFromFile(testFile, objectMapper);
        assertEquals("in-progress", tasksInProgress.get(0).getStatus());

        String done = "mark done 1";
        taskManagerService.markTask(done);
        List<Task> tasksDone = getTasksFromFile(testFile, objectMapper);
        assertEquals("done", tasksDone.get(0).getStatus());

        String toDo = "mark todo 1";
        taskManagerService.markTask(toDo);
        List<Task> tasksToDo = getTasksFromFile(testFile, objectMapper);
        assertEquals("todo", tasksToDo.get(0).getStatus());
    }

    @Test
    void shouldList() throws IOException {
        String taskToDo = "add task";
        String taskInProgress = "add task2";
        String taskDone = "add task3";

        taskManagerService.addToTasks(taskToDo);
        taskManagerService.addToTasks(taskInProgress);
        taskManagerService.markTask("mark in-progress 2");
        taskManagerService.addToTasks(taskDone);
        taskManagerService.markTask("mark done 3");

        String toDoTasks = taskManagerService.listTasks("list todo");
        String inProgressTasks = taskManagerService.listTasks("list in-progress");
        String doneTasks = taskManagerService.listTasks("list done");

        assertTrue(toDoTasks.contains("id=1"));
        assertTrue(inProgressTasks.contains("id=2"));
        assertTrue(doneTasks.contains("id=3"));
    }
}
