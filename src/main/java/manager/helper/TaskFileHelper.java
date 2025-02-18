package manager.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import domain.Task;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TaskFileHelper {

    private final static String TASKS_FILE_JSON = "tasksFile.json";

    public static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));

        objectMapper.registerModule(module);
        return objectMapper;
    }

    public static File getFile() throws IOException {
        File file = new File(TASKS_FILE_JSON);

        if (!file.exists()) {
            createFileIfNotExist(file);
        }

        return file;
    }

    private static void createFileIfNotExist(File file) throws IOException {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new IOException("Error when creating a file: " + e.getMessage());
        }
    }

    public static List<Task> getTasksFromFile(File file, ObjectMapper objectMapper) throws IOException {
        if (file.length() == 0) {
            return List.of();
        }

        try {
            return objectMapper.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new IOException("Error reading file " + file.getAbsolutePath() + ": " + e.getMessage());
        }
    }
}
