package com.javarush.kalenyuk.quest.repository;
import com.javarush.kalenyuk.quest.entity.User;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Quest repository.
 */
public class QuestRepository {
    /**
     * The type Node.
     */
// Внутренний класс для узлов квеста
    public static class Node {
        private String question;
        private String[] options;
        private String[] nextNodes;
        private boolean isVictory;

        /**
         * Instantiates a new Node.
         *
         * @param question  the question
         * @param options   the options
         * @param nextNodes the next nodes
         */
        public Node(String question, String[] options, String[] nextNodes, Boolean isVictory) {
            this.question = question;
            this.options = options;
            this.nextNodes = nextNodes;
            this.isVictory = isVictory;
        }

        /**
         * Gets question.
         *
         * @return the question
         */
        public String getQuestion() {
            return question;
        }

        /**
         * Get options string [ ].
         *
         * @return the string [ ]
         */
        public String[] getOptions() {
            return options;
        }

        /**
         * Get next nodes string [ ].
         *
         * @return the string [ ]
         */
        public String[] getNextNodes() {
            return nextNodes;
        }

        public boolean isVictory() {
            return isVictory;
        }
    }

    private static HashMap<String, Node> questMap;
    private List<User> users;


    public QuestRepository() {
        questMap = new HashMap<>();
        users = new ArrayList<>();
        initializeQuestMap();
    }

    // Инициализация квеста (как в предыдущем примере)
    private void initializeQuestMap() {
        // Корневой узел
        questMap.put("q1", new Node(
                "ТЫ АГЕНТ DELTA GREEN. ЧТО ДЕЛАТЬ ДАЛЬШЕ?",
                new String[]{"1. Вызвать подкрепление", "2. Исследовать место", "3. Доложить в штаб"},
                new String[]{"q1.1", "q1.2", "q1.3"},
                false
        ));

        // Ветка 1: Вызвать подкрепление
        questMap.put("q1.1", new Node(
                "ТЫ ВЫЗВАЛ ПОДКРЕПЛЕНИЕ. СКОЛЬКО АГЕНТОВ ПРИБЫЛО?",
                new String[]{"1. Прибыло мало агентов", "2. Прибыло достаточно агентов"},
                new String[]{"q1.1.1", "q1.1.2"},
                false
        ));

        questMap.put("q1.1.1", new Node(
                "ТЫ В МЕНЬШИНСТВЕ. КТО ЭТО?",
                new String[]{"1. Это культисты", "2. Это нечто иное"},
                new String[]{"q1.1.1.1", "q1.1.1.2"},
                false
        ));

        questMap.put("q1.1.1.1", new Node(
                "Тебе нужно бежать.",
                new String[]{},
                new String[]{},
                true // Победа
        ));

        questMap.put("q1.1.1.2", new Node(
                "Твой разум разрушен.",
                new String[]{},
                new String[]{},
                false // Поражение
        ));

        questMap.put("q1.1.2", new Node(
                "ЕСТЬ ЛИ У ВАС ПЛАН?",
                new String[]{"1. Да, план есть", "2. Нет, плана нет"},
                new String[]{"q1.1.2.1", "q1.1.2.2"},
                false
        ));

        questMap.put("q1.1.2.1", new Node(
                "Сражайтесь вместе.",
                new String[]{},
                new String[]{},
                true // Победа
        ));

        questMap.put("q1.1.2.2", new Node(
                "Культисты вас окружили.",
                new String[]{},
                new String[]{},
                false // Поражение
        ));

        // Ветка 2: Исследовать место
        questMap.put("q1.2", new Node(
                "ТЫ ИССЛЕДУЕШЬ МЕСТО. ЧТО ТЫ НАШЁЛ?",
                new String[]{"1. Старый гримуар", "2. Следы культа"},
                new String[]{"q1.2.1", "q1.2.2"},
                false
        ));

        questMap.put("q1.2.1", new Node(
                "ТЫ ЧИТАЕШЬ ЗАКЛИНАНИЕ. ПОНИМАЕШЬ ЛИ ТЫ ЯЗЫК?",
                new String[]{"1. Да, ты понимаешь", "2. Нет, ты не понимаешь"},
                new String[]{"q1.2.1.1", "q1.2.1.2"},
                false
        ));

        questMap.put("q1.2.1.1", new Node(
                "Заклинание сработало. Ты вызвал союзника.",
                new String[]{},
                new String[]{},
                true // Победа
        ));

        questMap.put("q1.2.1.2", new Node(
                "Заклинание вызвало древнее зло.",
                new String[]{},
                new String[]{},
                false // Поражение
        ));

        questMap.put("q1.2.2", new Node(
                "ТЫ ИДЁШЬ ПО СЛЕДАМ. УСПЕЛ ЛИ ТЫ НА РИТУАЛ?",
                new String[]{"1. Да, ты успел", "2. Нет, ты не успел"},
                new String[]{"q1.2.2.1", "q1.2.2.2"},
                false
        ));

        questMap.put("q1.2.2.1", new Node(
                "ТЫ ОДИН. СМОЖЕШЬ ЛИ ОСТАНОВИТЬ РИТУАЛ?",
                new String[]{"1. Да, смогу", "2. Нет, не смогу"},
                new String[]{"q1.2.2.1.1", "q1.2.2.1.2"},
                false
        ));

        questMap.put("q1.2.2.1.1", new Node(
                "Ты прервал ритуал.",
                new String[]{},
                new String[]{},
                true // Победа
        ));

        questMap.put("q1.2.2.1.2", new Node(
                "Ритуал завершён.",
                new String[]{},
                new String[]{},
                false // Поражение
        ));

        questMap.put("q1.2.2.2", new Node(
                "Мир погрузился во тьму.",
                new String[]{},
                new String[]{},
                false // Поражение
        ));

        // Ветка 3: Доложить в штаб
        questMap.put("q1.3", new Node(
                "ТЫ СВЯЗЫВАЕШЬСЯ СО ШТАБОМ. ЧТО ОНИ СООБЩАЮТ?",
                new String[]{"1. Штаб присылает поддержку", "2. Штаб отказывает в поддержке"},
                new String[]{"q1.3.1", "q1.3.2"},
                false
        ));

        questMap.put("q1.3.1", new Node(
                "ТЫ ЖДЁШЬ. СКОЛЬКО ВРЕМЕНИ ОСТАЛОСЬ?",
                new String[]{"1. Меньше часа", "2. Больше часа"},
                new String[]{"q1.3.1.1", "q1.3.1.2"},
                false
        ));

        questMap.put("q1.3.1.1", new Node(
                "Поддержка успела.",
                new String[]{},
                new String[]{},
                true // Победа
        ));

        questMap.put("q1.3.1.2", new Node(
                "Культисты нашли тебя.",
                new String[]{},
                new String[]{},
                false // Поражение
        ));

        questMap.put("q1.3.2", new Node(
                "Ты остался один. Твой разум на пределе.",
                new String[]{},
                new String[]{},
                false // Поражение
        ));
    }

    /**
     * Методы для работы с квестом
     *
     * @param nodeId the node id
     * @return the node
     */
    public static Node getNode(String nodeId) {
        return questMap.get(nodeId);
    }

    /**
     * Методы для работы с пользователями
     *
     * @param user the user
     */
    public void addUser(User user) {
        users.add(user);
    }

    /**
     * Возвращение.
     *
     * @param name the name
     * @return the user
     */
    public User getUser(String name) {
        return users.stream()
                .filter(user -> user.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Обновляет user.
     *
     * @param user the user
     */
    public void updateUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getName().equals(user.getName())) {
                users.set(i, user);
                break;
            }
        }
    }
}