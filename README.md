# Delta Green Quest

Добро пожаловать в текстовый квест "Delta Green Quest"! В этом квесте ты играешь за агента Delta Green, которому предстоит принимать сложные решения, чтобы выжить и победить.

## Диаграмма квеста

Ниже представлена диаграмма квеста, показывающая все возможные пути и исходы:

```mermaid
flowchart TD
    q1["ТЫ АГЕНТ DELTA GREEN.<br>ЧТО ДЕЛАТЬ ДАЛЬШЕ?"] -->|1. ВЫЗВАТЬ ПОДКРЕПЛЕНИЕ| q1_1
    q1 -->|2. ИССЛЕДОВАТЬ МЕСТО| q1_2
    q1 -->|3. ДОЛОЖИТЬ В ШТАБ| q1_3

    %% Подветка: ВЫЗВАТЬ ПОДКРЕПЛЕНИЕ (q1.1)
    q1_1["ТЫ ВЫЗВАЛ ПОДКРЕПЛЕНИЕ.<br>СКОЛЬКО АГЕНТОВ ПРИБЫЛО?"] -->|1. ПРИБЫЛО МАЛО АГЕНТОВ<br>(ТЫ В МЕНЬШИНСТВЕ)| q1_1_1
    q1_1 -->|2. ПРИБЫЛО ДОСТАТОЧНО АГЕНТОВ<br>(ТЫ В БОЛЬШИНСТВЕ)| q1_1_2

    q1_1_1["ТЫ В МЕНЬШИНСТВЕ.<br>КТО ЭТО?"] -->|1. ЭТО КУЛЬТИСТЫ| q1_1_1_1["ТЕБЕ НУЖНО БЕЖАТЬ<br>(Поражение)"]
    q1_1_1 -->|2. ЭТО НЕЧТО ИНОЕ| q1_1_1_2["ТЫ ПЕРЕД РИТУАЛОМ<br>(Поражение)"]

    q1_1_2["ТЫ В БОЛЬШИНСТВЕ.<br>ЕСТЬ ЛИ У ВАС ПЛАН?"] -->|1. ДА, ПЛАН ЕСТЬ| q1_1_2_1
    q1_1_2 -->|2. НЕТ, ПЛАНА НЕТ| q1_1_2_2["ТЫ ПОГИБАЕШЬ<br>(Поражение)"]

    q1_1_2_1["СРАЖАЕТЕСЬ ВМЕСТЕ И ПОБЕЖДАЕТЕ<br>(Победа)"]

    %% Подветка: ИССЛЕДОВАТЬ МЕСТО (q1.2)
    q1_2["ТЫ ИССЛЕДУЕШЬ МЕСТО.<br>ЧТО ВЫБРАЛ?"] -->|1. СТАРЫЙ ГРАНАТОМЁТ| q1_2_1
    q1_2 -->|2. СРЕДНЕ КУСТЫ| q1_2_2

    q1_2_1["ТЫ ВЫБРАЛ СТАРЫЙ ГРАНАТОМЁТ.<br>ОН НЕ РАБОТАЕТ"] -->|1. ТЫ ПОГИБАЕШЬ| q1_2_1_1["ТЫ ПОГИБАЕШЬ<br>(Поражение)"]

    q1_2_2["ТЫ СПРЯТАЛСЯ В КУСТАХ.<br>ЧТО ДЕЛАТЬ ДАЛЬШЕ?"] -->|1. ДА, Я УСЛЫШУ| q1_2_2_1["ТЫ ПОГИБАЕШЬ<br>(Поражение)"]
    q1_2_2 -->|2. НЕТ, Я НЕ УСЛЫШУ| q1_2_2_2["ТЫ ЖИВ, НО СКРЫЛСЯ<br>(Поражение)"]

    %% Подветка: ДОЛОЖИТЬ В ШТАБ (q1.3)
    q1_3["ТЫ СВЯЗАЛСЯ СО ШТАБОМ.<br>ЧТО ГОВОРЯТ?"] -->|1. БИТЬ ПОСЛЕДНИЙ РАЗ| q1_3_1
    q1_3 -->|2. КУСТЫ НА ВЫБОР| q1_3_2

    q1_3_1["БИТЬ ПОСЛЕДНИЙ РАЗ.<br>ВРЕМЯ ОСТАЛОСЬ?"] -->|1. БОЛЬШЕ ЧАСА| q1_3_1_1
    q1_3_1 -->|2. МЕНЬШЕ ЧАСА| q1_3_1_2

    q1_3_1_1["ДА, У ТЕБЯ ЕСТЬ ВРЕМЯ"] -->|1. ПОДКРЕПЛЕНИЕ УСЛЫШАЛО ТЕБЯ| q1_3_1_1_1["ТЫ ПОБЕДИЛ<br>(Победа)"]

    q1_3_1_2["НЕТ, У ТЕБЯ НЕТ ВРЕМЕНИ"] -->|1. ТЫ ПОГИБАЕШЬ| q1_3_1_2_1["ТЫ ПОГИБАЕШЬ<br>(Поражение)"]

    q1_3_2["КУСТЫ НА ВЫБОР.<br>ВРЕМЯ ОСТАЛОСЬ?"] -->|1. БОЛЬШЕ ЧАСА| q1_3_2_1["ТЫ ЖИВ, НО СКРЫЛСЯ<br>(Поражение)"]
    q1_3_2 -->|2. МЕНЬШЕ ЧАСА| q1_3_2_2["ТЫ ПОГИБАЕШЬ<br>(Поражение)"]
