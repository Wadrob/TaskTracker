package manager.parser;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskCommandParserTest {

    @Test
    void testGetIdForUpdateParsesCorrectly() {
        String line = "update 1 New description";

        int id = TaskCommandParser.getIdForUpdate(line);

        assertEquals(1, id);
    }

    @Test
    void testGetDescriptionForAddParsesCorrectly() {
        String line = "add This is a new task";

        String description = TaskCommandParser.getDescriptionForAdd(line);

        assertEquals("This is a new task", description);
    }

    @Test
    void testGetDescriptionForUpdateParsesCorrectly() {
        String line = "update 1 Updated description";

        String description = TaskCommandParser.getDescriptionForUpdate(line);

        assertEquals("Updated description", description);
    }

    @Test
    void testGetIdForDeleteParsesCorrectly() {
        String line = "delete 1";

        int id = TaskCommandParser.getIdForDelete(line);

        assertEquals(1, id);
    }

    @Test
    void testGetIdForMarkParsesCorrectly() {
        String line = "mark done 1";

        int id = TaskCommandParser.getIdForMark(line);

        assertEquals(1, id);
    }

    @Test
    void testGetCommandsForListSplitsCorrectly() {
        String line = "list all";

        List<String> commands = TaskCommandParser.getCommandsForList(line);

        assertEquals(2, commands.size());
        assertEquals("list", commands.get(0));
        assertEquals("all", commands.get(1));
    }
}
