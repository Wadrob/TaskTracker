package manager.parser;

import java.util.List;

public class TaskCommandParser {
    public static String getCommand(List<String> listCommands) {
        return listCommands.get(1).trim();
    }

    public static int getIdForUpdate(String line) {
        String updateId = List.of(line.split(" ", 3))
                .get(1);

        return Integer.parseInt(updateId);
    }

    public static String getDescriptionForAdd(String line) {
        return List.of(line.split(" ", 2))
                .get(1)
                .trim();
    }

    public static String getDescriptionForUpdate(String line) {
        return List.of(line.split(" ", 3))
                .get(2)
                .trim();
    }

    public static int getIdForDelete(String line) {
        String deleteId = List.of(line.split(" ", 2))
                .get(1);

        return Integer.parseInt(deleteId);
    }

    public static int getIdForMark(String line) {
        String markId = List.of(line.split(" ", 3))
                .get(2);

        return Integer.parseInt(markId);
    }

    public static List<String> getCommandsForList(String line) {
        return List.of(line.split(" ", 2));
    }
}
