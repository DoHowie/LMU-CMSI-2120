import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Collections;

interface Sets {
    static <T> Set<Set<T>> powerSet(Set<T> set) {
        if (set.size() > 16) {
            throw new IllegalArgumentException();
        }

        HashSet<Set<T>> powerSet = new HashSet<Set<T>>();
        powerSet.add(new HashSet<>());

        for (var elementOfSet : set) {
            var copyOfCurrentSet = Set.copyOf(powerSet);

            for (var element : copyOfCurrentSet) {
                var expandedSet = new HashSet<>(element);
                expandedSet.add(elementOfSet);
                powerSet.add(expandedSet);
            }
        }

        return powerSet;
    }

    static List multiples(Set<Integer> set, int factor) {
        var listOfElements = new ArrayList<Integer>();

        for (var element : set) {
            if (element % factor == 0) {
                listOfElements.add(element);
            }
        }
        Collections.sort(listOfElements);

        return List.copyOf(listOfElements);
    }
}