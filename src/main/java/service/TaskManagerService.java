package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Task;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static manager.helper.TaskFileHelper.*;
import static manager.parser.TaskCommandParser.*;
import static manager.utils.TaskListUtil.getNextId;
import static manager.utils.TaskListUtil.isTaskRemoved;

public class TaskManagerService {

    private final ObjectMapper objectMapper = getObjectMapper();
    private final static String STATUS_TO_DO = "todo";
    private final static String STATUS_IN_PROGRESS = "in-progress";
    private final static String STATUS_DONE = "done";
    private final File file;


    public String addToTasks(String line) throws IOException {
        List<Task> tasksList = getTasksFromFile(file, objectMapper);
        int id = getNextId(tasksList);
        String description = getDescriptionForAdd(line);

        if (tasksList.isEmpty()){
            tasksList = new ArrayList<>();
        }

        Task task = new Task(id, description, STATUS_TO_DO, LocalDateTime.now(), LocalDateTime.now());
        tasksList.add(task);

        try {
            objectMapper.writeValue(file, tasksList);
        } catch (IOException e) {
            throw new IOException("Error when adding task into file: " + e.getMessage());
        }

        return String.format("Task added successfully (ID: %s)", task.getId());
    }

    public String updateTask(String line) throws IOException {
        List<Task> taskList = getTasksFromFile(file, objectMapper);
        int id = getIdForUpdate(line);
        String description = getDescriptionForUpdate(line);

        for (Task task : taskList) {
            if (task.getId() == id) {
                task.setDescription(description);
                task.setUpdatedAt(LocalDateTime.now());

                taskList.set(taskList.indexOf(task), task);
            }
        }

        try {
            objectMapper.writeValue(file, taskList);
        } catch (IOException e) {
            throw new IOException("Error when updating Task: " + e.getMessage());
        }

        return String.format("Task updated successfully (ID: %s)", id);
    }

    public String deleteTask(String line) throws IOException {
        int id = getIdForDelete(line);
        List<Task> tasksList = getTasksFromFile(file, objectMapper);

        if (isTaskRemoved(tasksList, id)) {
            objectMapper.writeValue(file, tasksList);
            return String.format("Task deleted successfully (ID: %s)", id);
        } else {
            return String.format("Task failed to delete (ID: %s)", id);
        }
    }

    public String markTask(String line) throws IOException {
        int id = getIdForMark(line);
        List<Task> tasksList = getTasksFromFile(file, objectMapper);
        String status = "";

        for (Task task : tasksList) {
            if (task.getId() == id) {
                if (line.contains(STATUS_IN_PROGRESS)) {
                    task.setStatus(STATUS_IN_PROGRESS);
                    task.setUpdatedAt(LocalDateTime.now());
                    status = STATUS_IN_PROGRESS;
                } else if (line.contains(STATUS_DONE)) {
                    task.setStatus(STATUS_DONE);
                    task.setUpdatedAt(LocalDateTime.now());
                    status = STATUS_DONE;
                } else if (line.contains(STATUS_TO_DO)) {
                    task.setStatus(STATUS_TO_DO);
                    task.setUpdatedAt(LocalDateTime.now());
                    status = STATUS_TO_DO;
                } else {
                    return "Wrong mark command, please try again";
                }

                tasksList.set(tasksList.indexOf(task), task);
            }
        }

        objectMapper.writeValue(file, tasksList);

        return String.format("Task  (ID: %s) changed status: %s", id, status);
    }

    public String listTasks(String line) throws IOException {
        List<Task> tasksList = getTasksFromFile(file, objectMapper);
        List<String> listCommands = getCommandsForList(line);

        if (listCommands.size() == 1) {
            return tasksList.toString();
        }

        switch (getCommand(listCommands)) {
            case "in-progress" -> {
                return tasksList.stream()
                        .filter(task -> task.getStatus().equals(STATUS_IN_PROGRESS))
                        .toList()
                        .toString();
            }
            case "todo" -> {
                return tasksList.stream()
                        .filter(task -> task.getStatus().equals(STATUS_TO_DO))
                        .toList()
                        .toString();
            }
            case "done" -> {
                return tasksList.stream()
                        .filter(task -> task.getStatus().equals(STATUS_DONE))
                        .toList()
                        .toString();
            }
            default -> {
                return "Wrong command for list files!";
            }
        }
    }

    public TaskManagerService(File file) {
        this.file = file;
    }
}
