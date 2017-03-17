package technopark;

import java.util.HashSet;
import java.util.Set;

/**
 * Какое число будет выведено последней строчкой метода main()? Что будет находиться в коллекции?
 * Какие вы тут видите проблемы и как их можно исправить?
 * Created by eshubin on 02/03/2017.
 */
public class HashSetPuzzle {
    private static final class SomeClass {
        private final int id;
        private final String value;

        public SomeClass(int id, String value) {
            this.id = id;
            this.value = value;
        }

        public int getId() {
            return id;
        }

        public String getValue() {
            return value;
        }
    }

    public static void main(String[] args) {
        final Set<SomeClass> set = new HashSet<>();

        for (int i = 0; i < 10; i++) {
            set.add(new SomeClass(i, "value " + i));
        }

        for (int i = 0; i < 10; i++) {
            final SomeClass newValue = new SomeClass(i, "new value " + i);
            if (!set.contains(newValue)) {
                set.add(newValue);
            }
        }

        System.out.println(set.size());
    }
}
