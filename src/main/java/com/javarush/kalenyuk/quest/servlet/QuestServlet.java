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
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            request.getRequestDispatcher("/name.jsp").forward(request, response);
            return;
        }

        String currentNodeId = user.getId() != null ? user.getId() : "q1";
        QuestRepository.Node currentNode = questRepository.getNode(currentNodeId);

        request.setAttribute("node", currentNode);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/quest.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            String name = request.getParameter("name");
            user = questRepository.getUser(name);
            if (user == null) {
                user = new User("q1", 0, name);
                questRepository.addUser(user);
            }
            session.setAttribute("user", user);
            response.sendRedirect("quest");
            return;
        }

        String currentNodeId = user.getId();
        QuestRepository.Node currentNode = questRepository.getNode(currentNodeId);
        int choiceIndex = Integer.parseInt(request.getParameter("choice")) - 1;

        if (currentNode.getNextNodes().length > 0) {
            String nextNodeId = currentNode.getNextNodes()[choiceIndex];
            user.setId(nextNodeId);

            QuestRepository.Node nextNode = questRepository.getNode(nextNodeId);
            if (nextNode.getOptions().length == 0) {
                user.setCount(user.getCount() + 1);
                user.setId("q1");
            }

            questRepository.updateUser(user);
            session.setAttribute("user", user);
        }

        response.sendRedirect("quest");
    }
}