
import com.javarush.kalenyuk.quest.entity.User;
import com.javarush.kalenyuk.quest.repository.QuestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Теста для QuestRepository.
 * Проверяем, что узел q1 возвращается с правильными данными.
 * Проверяем, что для несуществующего ID возвращается null.
 * Проверяем добавление и получение пользователя.
 * Проверяем, что для несуществующего пользователя возвращается null.
 * Проверяем обновление данных пользователя.
 */
public class QuestRepositoryTest {

    private QuestRepository questRepository;

    @BeforeEach
    void setUp() {
        questRepository = new QuestRepository();
    }

    @Test
    void testGetNode_existingNode_returnsNode() {
        String nodeId = "q1";
        QuestRepository.Node node = questRepository.getNode(nodeId);
        assertNotNull(node, "Node should not be null");
        assertEquals("ТЫ АГЕНТ DELTA GREEN. ЧТО ДЕЛАТЬ ДАЛЬШЕ?", node.getQuestion(), "Node question should match");
        assertEquals(3, node.getOptions().length, "Node should have 3 options");
        assertEquals(3, node.getNextNodes().length, "Node should have 3 next nodes");
        assertFalse(node.isVictory(), "Node should not be a victory node");
    }

    @Test
    void testGetNode_nonExistingNode_returnsNull() {
        String nodeId = "invalid_id";
        QuestRepository.Node node = questRepository.getNode(nodeId);
        assertNull(node, "Node should be null for invalid ID");
    }

    @Test
    void testAddUser_andGetUser() {
        User user = new User("q1", 0, "Александр");
        questRepository.addUser(user);
        User retrievedUser = questRepository.getUser("Александр");
        assertNotNull(retrievedUser, "Retrieved user should not be null");
        assertEquals("Александр", retrievedUser.getName(), "User name should match");
        assertEquals("q1", retrievedUser.getId(), "User node ID should match");
        assertEquals(0, retrievedUser.getCount(), "User count should match");
    }

    @Test
    void testGetUser_nonExistingUser_returnsNull() {
        User retrievedUser = questRepository.getUser("Неизвестный");
        assertNull(retrievedUser, "Retrieved user should be null for non-existing name");
    }

    @Test
    void testUpdateUser() {
        User user = new User("q1", 0, "Александр");
        questRepository.addUser(user);

        user.setId("q1.1");
        user.setCount(1);
        questRepository.updateUser(user);
        User retrievedUser = questRepository.getUser("Александр");

        assertNotNull(retrievedUser, "Retrieved user should not be null");
        assertEquals("q1.1", retrievedUser.getId(), "Updated node ID should match");
        assertEquals(1, retrievedUser.getCount(), "Updated count should match");
    }
}