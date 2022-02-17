package Utilities;

import com.google.common.collect.Comparators;

import java.util.Comparator;
import java.util.List;

public class CommonUtility {
    /**
     *
     * @param orderBy - asc or desc
     * @param list values
     * @return return true if it is as per the expected order by
     */
    public static boolean isListSorted(String orderBy, List list){
        if (orderBy.equalsIgnoreCase("asc")) {
            return Comparators.isInOrder(list, Comparator.<String>naturalOrder());
        }else {
            return Comparators.isInOrder(list, Comparator.<String>reverseOrder());
        }
    }

    /**
     * @param min - The minimum.
     * @param max - The maximum.
     * @return A random int between these numbers (inclusive the minimum and maximum).
     */
    public static int getRandom(int min, int max) {
        return (int) ((Math.random() * (max + 1 - min)) + min);
    }
}
