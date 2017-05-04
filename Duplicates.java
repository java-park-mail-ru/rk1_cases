import java.util.ArrayList;
import java.util.List;

/**
 * Реализовать clearDuplicates и clearSame. Первая убирает одинаковые объекты из списка, вторая убирает одни и те же объекты из списка.
 */
public class Duplicates {
    
    private final List<Object> bestListEver = new ArrayList();
    
    public void cleareDuplicates() {
        for(Integer i = 0; i < bestListEver.size(); i++)
            for(int j = i + 1; j < bestListEver.size(); j++)
                if(bestListEver.get(i) == bestListEver.get(j)) {
                    bestListEver.remove(j);
                    j--;
                }
    }
    
    public void clearSame() {
        List<Integer> list = bestListEver.stream().distinct().collect(Collectors.toList());
        Iterator<Integer> i = bestListEver.iterator();

        while (i.hasNext()) {
            Integer val = i.next();
            if (list.contains(val))
                list.remove(val);
            else
                i.remove();
        }
    }

    public List<Object> getBestListEver() {
        return bestListEver;
    }
}
