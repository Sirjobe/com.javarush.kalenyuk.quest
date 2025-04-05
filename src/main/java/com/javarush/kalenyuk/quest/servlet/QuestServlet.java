package com.javarush.kalenyuk.quest.servlet;
import com.javarush.kalenyuk.quest.entity.User;
import com.javarush.kalenyuk.quest.repository.QuestRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/quest")
public class QuestServlet extends HttpServlet {
    private QuestRepository questRepository;

    @Override
    public void init() throws ServletException {
        questRepository = new QuestRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            System.out.println("User is null, redirecting to name.jsp");
            request.getRequestDispatcher("/name.jsp").forward(request, response);
            return;
        }

        String currentNodeId = user.getId() != null ? user.getId() : "q1";
        System.out.println("doGet: Current node ID: " + currentNodeId);
        QuestRepository.Node currentNode = questRepository.getNode(currentNodeId);
        if (currentNode == null) {
            System.out.println("doGet: Current node is null for ID: " + currentNodeId);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Node not found for ID: " + currentNodeId);
            return;
        }
        System.out.println("doGet: Current node question: " + currentNode.getQuestion());
        System.out.println("doGet: Options length: " + currentNode.getOptions().length);

        // Если игра закончилась, передаём статус победы/поражения
        if (currentNode.getOptions().length == 0) {
            request.setAttribute("gameEnded", true);
            request.setAttribute("isVictory", currentNode.isVictory());
        } else {
            request.setAttribute("gameEnded", false);
        }

        request.setAttribute("node", currentNode);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/quest.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            String name = request.getParameter("name");
            System.out.println("doPost: Creating new user with name: " + name);
            user = questRepository.getUser(name);
            if (user == null) {
                user = new User("q1", 0, name);
                questRepository.addUser(user);
            }
            session.setAttribute("user", user);
            response.sendRedirect("quest");
            return;
        }

        // Проверяем, нажал ли пользователь "Начать заново"
        String restart = request.getParameter("restart");
        if ("true".equals(restart)) {
            System.out.println("doPost: Restarting game for user: " + user.getName());
            user.setId("q1");
            questRepository.updateUser(user);
            session.setAttribute("user", user);
            response.sendRedirect("quest");
            return;
        }

        String currentNodeId = user.getId();
        System.out.println("doPost: Current node ID: " + currentNodeId);
        QuestRepository.Node currentNode = questRepository.getNode(currentNodeId);
        if (currentNode == null) {
            System.out.println("doPost: Current node is null for ID: " + currentNodeId);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Node not found for ID: " + currentNodeId);
            return;
        }

        String choice = request.getParameter("choice");
        System.out.println("doPost: User choice: " + choice);
        int choiceIndex;
        try {
            choiceIndex = Integer.parseInt(choice) - 1;
        } catch (NumberFormatException e) {
            System.out.println("doPost: Invalid choice value: " + choice);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid choice value: " + choice);
            return;
        }

        if (currentNode.getNextNodes().length > 0) {
            if (choiceIndex < 0 || choiceIndex >= currentNode.getNextNodes().length) {
                System.out.println("doPost: Invalid choice index: " + choiceIndex + ", max: " + (currentNode.getNextNodes().length - 1));
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid choice index: " + choiceIndex);
                return;
            }

            String nextNodeId = currentNode.getNextNodes()[choiceIndex];
            System.out.println("doPost: Next node ID: " + nextNodeId);
            user.setId(nextNodeId);

            QuestRepository.Node nextNode = questRepository.getNode(nextNodeId);
            if (nextNode == null) {
                System.out.println("doPost: Next node is null for ID: " + nextNodeId);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Next node not found for ID: " + nextNodeId);
                return;
            }

            if (nextNode.getOptions().length == 0) {
                System.out.println("doPost: Game ended");
                user.setCount(user.getCount() + 1);
            }

            questRepository.updateUser(user);
            session.setAttribute("user", user);
        } else {
            System.out.println("doPost: No next nodes available for current node: " + currentNodeId);
        }

        response.sendRedirect("quest");
    }
}