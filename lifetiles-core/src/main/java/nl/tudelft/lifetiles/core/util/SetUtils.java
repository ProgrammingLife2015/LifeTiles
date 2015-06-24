package nl.tudelft.lifetiles.core.util;

import java.util.HashSet;
import java.util.Set;

/**
 * Contains Set utilities.
 *
 * @author Rutger van den Berg
 *
 */
public final class SetUtils {
    /**
     * Don't instantiate.
     */
    private SetUtils() {

    }

    /**
     * Counts the size of the intersection of the provided sets.
     *
     * @param left
     *            The first set.
     * @param right
     *            The second set.
     * @return Count of objects that are in both sets.
     */
    public static int intersectionSize(final Set<?> left, final Set<?> right) {
        assert left != null;
        assert right != null;
        // need to copy the set because retainsAll modifies the set.
        Set<Object> intersection;
        if (left.size() > right.size()) {
            intersection = new HashSet<>(right);
            intersection.retainAll(left);
        } else {
            intersection = new HashSet<>(left);
            intersection.retainAll(right);
        }
        return intersection.size();
    }
}
