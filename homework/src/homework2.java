import java.util.*;

class Student {
    String name;
    String city;
    int age;

    Student(String name, String city, int age) {
        this.name = name;
        this.city = city;
        this.age = age;
    }

    @Override
    public String toString() {
        return name + " (" + age + ", " + city + ")";
    }
}

public class homework2 {
    public static void main(String[] args) {
        task1();
        task2();
        task3();
        task4();
        task5();
        task6();
        task7();
        task8();
    }

    // Задание 1. Работа со списком городов
    public static void task1() {
        System.out.println("\nЗАДАНИЕ 1");

        ArrayList<String> cities = new ArrayList<>();

        cities.add("Костанай");
        cities.add("Атырау");
        cities.add("Семей");
        cities.add("Талдыкорган");
        cities.add("Уральск");
        cities.add("Туркестан");

        System.out.println("Начальный список:");

        for (int i = 0; i < cities.size(); i++) {
            System.out.println(i + ": " + cities.get(i));
        }

        cities.remove(3);
        cities.add("Актау");
        cities.add("Жезказган");

        System.out.println("\nОбновленный список:");

        for (int i = 0; i < cities.size(); i++) {
            System.out.println(i + ": " + cities.get(i));
        }
    }

    // Задание 2. Удаление повторяющихся чисел
    public static void task2() {
        System.out.println("\nЗАДАНИЕ 2");

        List<Integer> numbers =
                Arrays.asList(7, 2, 7, 9, 4, 2, 6, 9, 1);

        System.out.println("Список чисел:");
        System.out.println(numbers);

        LinkedHashSet<Integer> uniqueNumbers =
                new LinkedHashSet<>(numbers);

        List<Integer> result =
                new ArrayList<>(uniqueNumbers);

        System.out.println("Уникальные числа:");
        System.out.println(result);
    }

    // Задание 3. Сравнение участников двух курсов
    public static void task3() {
        System.out.println("\nЗАДАНИЕ 3");

        HashSet<String> courseA = new HashSet<>();

        courseA.add("Ерлан");
        courseA.add("Настя");
        courseA.add("Диана");
        courseA.add("Тимур");

        HashSet<String> courseB = new HashSet<>();

        courseB.add("Настя");
        courseB.add("Тимур");
        courseB.add("Арман");
        courseB.add("София");

        System.out.println("Первый курс: " + courseA);
        System.out.println("Второй курс: " + courseB);

        HashSet<String> commonStudents =
                new HashSet<>(courseA);

        commonStudents.retainAll(courseB);

        System.out.println(
                "Участники обоих курсов: " + commonStudents
        );

        HashSet<String> allStudents =
                new HashSet<>(courseA);

        allStudents.addAll(courseB);

        System.out.println(
                "Все участники: " + allStudents
        );

        HashSet<String> onlyA =
                new HashSet<>(courseA);

        onlyA.removeAll(courseB);

        System.out.println(
                "Только первый курс: " + onlyA
        );
    }

    // Задание 4. Поиск элементов в TreeSet
    public static void task4() {
        System.out.println("\nЗАДАНИЕ 4");

        TreeSet<Integer> numbers = new TreeSet<>();
        Random random = new Random();

        while (numbers.size() < 10) {
            numbers.add(random.nextInt(90) + 10);
        }

        System.out.println("Упорядоченный набор:");
        System.out.println(numbers);

        System.out.println(
                "Наименьшее значение: " + numbers.first()
        );

        System.out.println(
                "Наибольшее значение: " + numbers.last()
        );

        System.out.println(
                "Значения меньше 45: " + numbers.headSet(45)
        );

        System.out.println(
                "Значения от 25 до 75: "
                        + numbers.subSet(25, 75)
        );
    }

    // Задание 5. Подсчет повторений слов
    public static void task5() {
        System.out.println("\nЗАДАНИЕ 5");

        String text =
                "html java kotlin java sql html java kotlin";

        String[] words = text.split(" ");

        HashMap<String, Integer> frequency =
                new HashMap<>();

        for (String word : words) {
            frequency.put(
                    word,
                    frequency.getOrDefault(word, 0) + 1
            );
        }

        List<Map.Entry<String, Integer>> sortedWords =
                new ArrayList<>(frequency.entrySet());

        sortedWords.sort(
                Map.Entry.<String, Integer>comparingByValue()
                        .reversed()
        );

        System.out.println("Количество повторений:");

        for (Map.Entry<String, Integer> entry : sortedWords) {
            System.out.println(
                    entry.getKey() + " — " + entry.getValue()
            );
        }
    }

    // Задание 6. Распределение студентов по городам
    public static void task6() {
        System.out.println("\nЗАДАНИЕ 6");

        List<Student> students = new ArrayList<>();

        students.add(new Student("Настя", "Костанай", 19));
        students.add(new Student("Ерлан", "Актау", 21));
        students.add(new Student("Диана", "Костанай", 18));
        students.add(new Student("Тимур", "Семей", 20));
        students.add(new Student("София", "Актау", 19));

        Map<String, List<Student>> groupedStudents =
                new HashMap<>();

        for (Student student : students) {
            groupedStudents
                    .computeIfAbsent(
                            student.city,
                            city -> new ArrayList<>()
                    )
                    .add(student);
        }

        System.out.println("Студенты по городам:");

        for (Map.Entry<String, List<Student>> entry
                : groupedStudents.entrySet()) {
            System.out.println(
                    entry.getKey() + ": " + entry.getValue()
            );
        }
    }

    // Задание 7. Упорядочивание студентов по возрасту и имени
    public static void task7() {
        System.out.println("\nЗАДАНИЕ 7");

        List<Student> students = new ArrayList<>();

        students.add(new Student("Настя", "Костанай", 19));
        students.add(new Student("Ерлан", "Актау", 21));
        students.add(new Student("Диана", "Семей", 18));
        students.add(new Student("Арман", "Уральск", 20));
        students.add(new Student("София", "Туркестан", 18));

        System.out.println("Первоначальный порядок:");

        for (Student student : students) {
            System.out.println(student);
        }

        students.sort(
                Comparator.comparingInt(
                                (Student student) -> student.age
                        )
                        .thenComparing(student -> student.name)
        );

        System.out.println("\nПорядок после сортировки:");

        for (Student student : students) {
            System.out.println(student);
        }
    }

    // Задание 8. Сортировка чисел и вычисление результатов
    public static void task8() {
        System.out.println("\nЗАДАНИЕ 8");

        Integer[] array = new Integer[15];
        Random random = new Random();

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(90) + 10;
        }

        System.out.println("Массив до сортировки:");
        System.out.println(Arrays.toString(array));

        List<Integer> numbers =
                new ArrayList<>(Arrays.asList(array));

        Collections.sort(numbers);

        System.out.println("Числа по возрастанию:");
        System.out.println(numbers);

        int index = Collections.binarySearch(numbers, 55);

        if (index >= 0) {
            System.out.println(
                    "Число 55 присутствует. Индекс: " + index
            );
        } else {
            System.out.println("Число 55 не найдено");
        }

        int min = numbers.get(0);
        int max = numbers.get(numbers.size() - 1);
        int sum = 0;

        for (int number : numbers) {
            sum += number;
        }

        System.out.println("Минимальное число: " + min);
        System.out.println("Максимальное число: " + max);
        System.out.println("Общая сумма: " + sum);
    }
}