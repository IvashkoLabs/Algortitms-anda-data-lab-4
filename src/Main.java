import java.util.*;
import java.util.function.Predicate;
import java.util.Random;
public class Main {
    public static void main(String[] args)
    {
        System.out.println("creating students\n");
        Random rand = new Random();
        //Student[] allStudents = new Student[10];
        List<Student> allStudents= new ArrayList<>();
        for (int i = 0; i < 10; i++)
        {
            Student s = new Student();

            s.surname = Surnames.values()[rand.nextInt(Surnames.values().length)];
            s.isMale = rand.nextBoolean();

            if (s.isMale)
                s.name = MaleNames.values()[rand.nextInt(MaleNames.values().length)].name();
            else
                s.name = FemaleNames.values()[rand.nextInt(FemaleNames.values().length)].name();

            s.group = rand.nextInt(7) + 1;

            s.abcOrder = GetAbcOrder(s.name, s.surname.name());

            allStudents.add(s);
        }
        List<Student> allStudents2 = new ArrayList<>(allStudents);
        System.out.println("random unsorted students");
        printStudents(allStudents);

        System.out.println("\n\nusing Selection Sort");
        SelectionSort(allStudents);
        System.out.println("\n\nsorted students");
        printStudents(allStudents);

        System.out.println("\n\nusing Quick Sort");
        Counter counter = new Counter();
        QuickSort(allStudents2, 0, allStudents2.size() - 1,counter);

        System.out.println("\nTOTAL QuickSort iterations: " + counter.value);
        System.out.println("\n\nsorted students");
        printStudents(allStudents2);
    }
    public static void SelectionSort(List<Student> stud)
    {
        int size = stud.size();
        int iterations = 0;

        for (int i = 0; i < size - 1; i++)
        {
            Student min = stud.get(i);
            int minIndex = i;

            for (int j = i + 1; j < size; j++)
            {
                iterations++;

                if (stud.get(j).abcOrder < min.abcOrder)
                {
                    min = stud.get(j);
                    minIndex = j;
                }
            }

            System.out.println("\niteration " + (i + 1));
            System.out.println("total comparisons so far: " + iterations);
            System.out.printf("looking for smallest from index %d\n", i);

            if (minIndex != i)
            {
                System.out.printf("Found: [%s %s] → swap with [%s %s]\n",
                        min.name, min.surname.name(),
                        stud.get(i).name, stud.get(i).surname.name());

                Student temp = stud.get(i);
                stud.set(i, min);
                stud.set(minIndex, temp);
            }
            else
            {
                System.out.printf("[%s %s] already smallest so skip\n",
                        stud.get(i).name, stud.get(i).surname.name());
            }

            printCurrentState(stud, i);
        }

        System.out.println("\nTOTAL iterations: " + iterations);
    }
    public static void printCurrentState(List<Student> stud, int sortedBorder)
    {
        System.out.print("current list: [ ");

        for (int k = 0; k < stud.size(); k++)
        {
            Student s = stud.get(k);

            if (k <= sortedBorder)
            {
                System.out.print("*" + s.name + " " + s.surname.name() + "* ");
            }
            else
            {
                System.out.print(s.name + " " + s.surname.name() + " ");
            }
        }

        System.out.println("]");
    }
    public static void QuickSort(List<Student> stud, int low, int high, Counter counter)
    {
        if (low >= high) return;

        int i = low;
        int j = high;
        double anchor = stud.get((low + high) / 2).abcOrder;

        System.out.println("\nNew iteration:"+ counter.value+" [" + low + ", " + high + "] anchor =" + anchor );

        while (i <= j)
        {
            while (stud.get(i).abcOrder < anchor ) i++;
            while (stud.get(j).abcOrder > anchor ) j--;

            if (i <= j)
            {
                System.out.printf("swapped %s %s with %s %s\n",
                        stud.get(i).name,stud.get(i).surname, stud.get(j).name,stud.get(j).surname );

                Student temp = stud.get(i);
                stud.set(i, stud.get(j));
                stud.set(j, temp);

                i++;
                j--;
            }
        }

        printCurrentState(stud, -1);
        counter.value++;
        QuickSort(stud, low, j,counter);
        QuickSort(stud, i, high, counter);
    }
    public static class Counter {
        int value = 0;
    }
    public static double GetAbcOrder(String name, String surname)
    {
        double result = 0.0;

        String fullName = (name + surname).toUpperCase();

        double divisor = 27.0;

        for (int i = 0; i < fullName.length(); i++)
        {
            char c = fullName.charAt(i);
            if (c >= 'A' && c <= 'Z')
            {
                double value = (c - 'A' + 1); // A=1, B=2, ..., Z=26
                result += value / divisor;
                divisor *= 27.0;              // every other means less
            }
        }
        return result;
    }


    public static void printStudents(List<Student> students)
    {
        for (Student s : students)
        {
            System.out.printf("%-10s %-10s | is male: %-5b | group: %d | abc order: %.8f\n",
                    s.name,s.surname.name(), s.isMale, s.group, s.abcOrder);
        }
    }
}
class Student// implements Comparable<Student>
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
