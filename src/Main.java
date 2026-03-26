import java.util.*;
import java.util.function.Predicate;
import java.util.Random;
public class Main {
    public static void main(String[] args) {
        System.out.println("creating students\n");
        Random rand = new Random();
        Student[] allStudents = new Student[10];

        for (int i = 0; i < 10; i++) {
            allStudents[i] = new Student();
            allStudents[i].surname = Surnames.values()[rand.nextInt(Surnames.values().length)];
            allStudents[i].isMale = rand.nextBoolean();

            if (allStudents[i].isMale) {
                allStudents[i].name = MaleNames.values()[rand.nextInt(MaleNames.values().length)].name();
            } else {
                allStudents[i].name = FemaleNames.values()[rand.nextInt(FemaleNames.values().length)].name();
            }

            allStudents[i].group = rand.nextInt(7) + 1;

            allStudents[i].abcOrder = GetAbcOrder(allStudents[i].name, allStudents[i].surname.name());
        }

        System.out.println("random unsorted students");
        printStudents(allStudents);

        System.out.println("\n\nusing Selection Sort");
        SelectionSort(allStudents);

        System.out.println("\n\nsorted students");
        printStudents(allStudents);
    }
    public static void SelectionSort(Student[] stud) {
        int size = stud.length;

        for (int i = 0; i < size - 1; i++) {
            Student min = stud[i];
            int minIndex = i;

            for (int j = i + 1; j < size; j++) {
                if (stud[j].abcOrder < min.abcOrder) {
                    min = stud[j];
                    minIndex = j;
                }
            }

            System.out.println("\niteration " + (i + 1));
            System.out.printf("looking for smallest from %d \n", i);

            if (minIndex != i) {
                System.out.printf("Found: [%s %s]. Switch with [%s %s].\n",
                        min.surname.name(), min.name,
                        stud[i].surname.name(), stud[i].name);

                // Сам процес обміну (Swap)
                stud[minIndex] = stud[i];
                stud[i] = min;
            } else {
                System.out.printf("[%s %s] is already the smallest so skip.\n",
                        stud[i].surname.name(), stud[i].name);
            }

            System.out.print("current list: [ ");
            for (int k = 0; k < size; k++) {
                if (k <= i) {
                    System.out.print("*" +stud[k].name+ " "+stud[k].surname.name() + "* ");
                } else {
                    System.out.print(stud[k].name+ " "+stud[k].surname.name() + " ");
                }
            }
            System.out.println("]");
        }
    }
public static double GetAbcOrder(String name, String surname) {
        double result = 0.0;

        String fullName = (name + surname).toUpperCase();

        double divisor = 27.0;

        for (int i = 0; i < fullName.length(); i++) {
            char c = fullName.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                double value = (c - 'A' + 1); // A=1, B=2, ..., Z=26
                result += value / divisor;
                divisor *= 27.0;              // every other means less
            }
        }
        return result;
    }


    public static void printStudents(Student[] students) {
        for (Student s : students) {
            System.out.printf("%-10s %-10s | is male: %-5b | group: %d | abc order: %.8f\n",
                    s.name,s.surname.name(), s.isMale, s.group, s.abcOrder);
        }
    }
}
class Student// implements Comparable<Student> {
    {
    Surnames surname;
    String name;
    //int created;
    int group;
    boolean isMale;
        double abcOrder;
}
enum Surnames
{
    Johnson,
    Thomson,
    Alison,
    Jackson,
    Allen,
    Bateman,
    Holland,
    Barns,
    Rogers,
    Peterson,
    Wilson,
    Wong,

};
enum MaleNames
{
    John,
    Tom,
    Jack,
    Paul,
    Roger,
    Peter,
    Joe,
    William,
    Patrick,
    Rick,
    Jimmy
};
enum FemaleNames
{
    Ana,
    Hannah,
    Jeannie,
    Louis,
    Paula,
    Ezilabeth,
    Marrie,
    Martha,
    Margareth,
    Sophie,
    Skyler
};
