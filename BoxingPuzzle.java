/**
 * Что здесь не так? Объясните почему? Как можно поправить?
 *
 * Ссылка передавалась по значению, а когда делаем декремент создается новый обьект и меняется ссылка
 * Поэтому для изменения ссылки снаружи надо вернуть новую ссылку
 */

public class BoxingPuzzle {

    public void puzzleMe() {
        Integer n = 6;
        Integer half = n / 2;
        Integer None = null;

        if (n == 6)
            System.out.println("Six little nigger boys playing with a hive;\n" +
                    "A bumble-bee stung one, and then there were five.");
        n = decrement(n);

        if (n == 5)
            System.out.println("Five little nigger boys going in for law;\n" +
                    "One got in chancery, and then there were four.");
        n = decrement(n);

        if (n == 4)
            System.out.println("Four little nigger boys going out to sea;\n" +
                    "A red herring swallowed one, and then there were three.");
        n = decrement(n);

        if (n == half)
            System.out.println("debug: Only half is left");

        if (n == 3)
            System.out.println("Three little nigger boys walking in the Zoo;\n" +
                    "A big bear hugged one, and then there were two.\n");
        n = decrement(n);

        if (n == 2)
            System.out.println("Two little nigger boys sitting in the sun; \n" +
                    "One got frizzled up, and then there was one.");
        n = decrement(n);

        if (n == 1)
            System.out.println("One little nigger boy left all alone;\n" +
                    "He went out and hanged himself and then there were None.");
        n = decrement(n);

        if (n == 0)
            n = None;

        if (n == null)
            System.out.println("debug: Nothing left");
    }

    private Integer decrement(Integer i) {
        return --i;
    }
}
