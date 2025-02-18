package manager.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskFileHelperTest {

    private ObjectMapper objectMapper;
    private File file;

    @BeforeEach
    void setUp() throws IOException {
        objectMapper = TaskFileHelper.getObjectMapper();
        file = new File("src/test/resources/testJson.json");
        file.createNewFile();
    }

    @AfterEach
    void cleanUp(){
        file.delete();
    }

    @Test
    void testGetObjectMapperNotNull() {
        assertNotNull(objectMapper);
    }

    @Test
    void testGetFileCreatesFileIfNotExists() {
        assertTrue(file.exists());
    }

    @Test
    void testGetTasksFromFileReturnsEmptyListForEmptyFile() throws IOException {
        List<Task> tasks = TaskFileHelper.getTasksFromFile(file, objectMapper);
        assertTrue(tasks.isEmpty());
    }

    @Test
    void testGetTasksFromFileReadsTasksCorrectly() throws IOException {
        List<Task> expectedTasks = List.of(new Task(1, "Test task", "TODO", LocalDateTime.now(), LocalDateTime.now()));
        objectMapper.writeValue(file, expectedTasks);

        List<Task> tasks = TaskFileHelper.getTasksFromFile(file, objectMapper);

        assertEquals(1, tasks.size());
        assertEquals("Test task", tasks.get(0).getDescription());
    }
}