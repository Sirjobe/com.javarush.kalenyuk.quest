

import com.javarush.kalenyuk.quest.entity.User;
import com.javarush.kalenyuk.quest.repository.QuestRepository;
import com.javarush.kalenyuk.quest.servlet.QuestServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuestServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private QuestRepository questRepository;

    @InjectMocks
    private QuestServlet questServlet;

    @BeforeEach
    void setUp() throws ServletException, NoSuchFieldException, IllegalAccessException {
        questServlet.init(); // Инициализируем сервлет
        when(request.getSession()).thenReturn(session);

        // Убрали when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher)

        // Используем рефлексию, чтобы заменить поле questRepository на мок
        Field questRepositoryField = QuestServlet.class.getDeclaredField("questRepository");
        questRepositoryField.setAccessible(true);
        questRepositoryField.set(questServlet, questRepository);
    }

    @Test
    void testDoGet_userNotInSession_redirectsToNameJsp() throws ServletException, IOException {
        // Arrange
        when(request.getMethod()).thenReturn("GET");
        when(session.getAttribute("user")).thenReturn(null);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher); // Перенесли сюда

        // Act
        questServlet.service(request, response);

        // Assert
        verify(request).getRequestDispatcher("/name.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoGet_userInSession_forwardsToQuestJsp() throws ServletException, IOException {
        // Arrange
        when(request.getMethod()).thenReturn("GET");
        User user = new User("q1", 0, "Александр");
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher); // Перенесли сюда

        QuestRepository.Node node = new QuestRepository.Node(
                "ТЫ АГЕНТ DELTA GREEN. ЧТО ДЕЛАТЬ ДАЛЬШЕ?",
                new String[]{"1. Вызвать подкрепление", "2. Исследовать место", "3. Доложить в штаб"},
                new String[]{"q1.1", "q1.2", "q1.3"},
                false
        );
        try (var mockedStatic = mockStatic(QuestRepository.class)) {
            mockedStatic.when(() -> QuestRepository.getNode("q1")).thenReturn(node);

            // Act
            questServlet.service(request, response);

            // Assert
            verify(request).setAttribute("node", node);
            verify(request).setAttribute("user", user);
            verify(request).setAttribute("gameEnded", false);
            verify(request).getRequestDispatcher("/quest.jsp");
            verify(requestDispatcher).forward(request, response);
        }
    }

    @Test
    void testDoPost_createNewUser_redirectsToQuest() throws ServletException, IOException {
        // Arrange
        when(request.getMethod()).thenReturn("POST");
        when(session.getAttribute("user")).thenReturn(null);
        when(request.getParameter("name")).thenReturn("Александр");
        when(questRepository.getUser("Александр")).thenReturn(null);

        // Act
        questServlet.service(request, response);

        // Assert
        verify(session).setAttribute(eq("user"), any(User.class));
        verify(questRepository).addUser(any(User.class));
        verify(response).sendRedirect("quest");
    }

    @Test
    void testDoPost_restartGame_resetsNodeId() throws ServletException, IOException {
        // Arrange
        when(request.getMethod()).thenReturn("POST");
        User user = new User("q1.1", 1, "Александр");
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("restart")).thenReturn("true");

        // Act
        questServlet.service(request, response);

        // Assert
        assertEquals("q1", user.getId(), "Node ID should be reset to q1");
        verify(questRepository).updateUser(user);
        verify(response).sendRedirect("quest");
    }

    @Test
    void testDoPost_makeChoice_updatesNodeId() throws ServletException, IOException {
        // Arrange
        when(request.getMethod()).thenReturn("POST");
        User user = new User("q1", 0, "Александр");
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("restart")).thenReturn(null);
        when(request.getParameter("choice")).thenReturn("1");

        QuestRepository.Node currentNode = new QuestRepository.Node(
                "ТЫ АГЕНТ DELTA GREEN. ЧТО ДЕЛАТЬ ДАЛЬШЕ?",
                new String[]{"1. Вызвать подкрепление", "2. Исследовать место", "3. Доложить в штаб"},
                new String[]{"q1.1", "q1.2", "q1.3"},
                false
        );
        QuestRepository.Node nextNode = new QuestRepository.Node(
                "ТЫ ВЫЗВАЛ ПОДКРЕПЛЕНИЕ. СКОЛЬКО АГЕНТОВ ПРИБЫЛО?",
                new String[]{"1. Прибыло мало агентов", "2. Прибыло достаточно агентов"},
                new String[]{"q1.1.1", "q1.1.2"},
                false
        );
        try (var mockedStatic = mockStatic(QuestRepository.class)) {
            mockedStatic.when(() -> QuestRepository.getNode("q1")).thenReturn(currentNode);
            mockedStatic.when(() -> QuestRepository.getNode("q1.1")).thenReturn(nextNode);

            // Act
            questServlet.service(request, response);

            // Assert
            assertEquals("q1.1", user.getId(), "Node ID should be updated to q1.1");
            verify(questRepository).updateUser(user);
            verify(response).sendRedirect("quest");
        }
    }

    @Test
    void testDoPost_gameEnded_incrementsCount() throws ServletException, IOException {
        // Arrange
        when(request.getMethod()).thenReturn("POST");
        User user = new User("q1.1.1", 0, "Александр");
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("restart")).thenReturn(null);
        when(request.getParameter("choice")).thenReturn("1");

        QuestRepository.Node currentNode = new QuestRepository.Node(
                "ТЫ В МЕНЬШИНСТВЕ. КТО ЭТО?",
                new String[]{"1. Это культисты", "2. Это нечто иное"},
                new String[]{"q1.1.1.1", "q1.1.1.2"},
                false
        );
        QuestRepository.Node nextNode = new QuestRepository.Node(
                "Тебе нужно бежать.",
                new String[]{},
                new String[]{},
                true
        );
        try (var mockedStatic = mockStatic(QuestRepository.class)) {
            mockedStatic.when(() -> QuestRepository.getNode("q1.1.1")).thenReturn(currentNode);
            mockedStatic.when(() -> QuestRepository.getNode("q1.1.1.1")).thenReturn(nextNode);

            // Act
            questServlet.service(request, response);

            // Assert
            assertEquals("q1.1.1.1", user.getId(), "Node ID should be updated to q1.1.1.1");
            assertEquals(1, user.getCount(), "User count should be incremented");
            verify(questRepository).updateUser(user);
            verify(response).sendRedirect("quest");
        }
    }
}