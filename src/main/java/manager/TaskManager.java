package manager;

import service.TaskManagerService;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static manager.helper.TaskFileHelper.getFile;

public class TaskManager {

    public void run() throws IOException {
        Scanner scan = new Scanner(System.in);
        File file = getFile();
        TaskManagerService taskService = new TaskManagerService(file);

        while (true) {
            String line = scan.nextLine();

            if (line.contains("add")) {
                System.out.println(taskService.addToTasks(line));
            } else if (line.contains("update")) {
                System.out.println(taskService.updateTask(line));
            } else if (line.contains("delete")) {
                System.out.println(taskService.deleteTask(line));
            } else if (line.contains("mark")) {
                System.out.println(taskService.markTask(line));
            } else if (line.contains("list")) {
                System.out.println(taskService.listTasks(line));
            } else if (line.contains("quit")) {
                System.out.println("Goodbye");
                break;
            } else {
                System.out.println("Unknown command, please try again.");
            }
        }
    }
}
