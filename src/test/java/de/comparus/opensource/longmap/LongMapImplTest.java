package de.comparus.opensource.longmap;

import org.junit.Test;

import static junit.framework.TestCase.*;

public class LongMapImplTest {

    @Test
    public void putTest() {
        // WITH
        final int EXPECTED_SIZE = 1;
        LongMap<Integer> ints = new LongMapImpl<>();

        // WHEN
        ints.put(1, 3);

        // THEN
        assertFalse(ints.isEmpty());
        assertEquals(EXPECTED_SIZE,  ints.size());
    }

    @Test
    public void get() {
        // WITH
        final int EXPECTED_KEY = 1;
        final Integer EXPECTED_VALUE = 2;
        LongMap<Integer> ints = new LongMapImpl<>();
        ints.put(EXPECTED_KEY, EXPECTED_VALUE);

        // WHEN
        Integer actualValue = ints.get(EXPECTED_KEY);

        // THEN
        assertEquals(EXPECTED_VALUE, actualValue);
    }

    @Test
    public void remove() {
        // WITH
        final int EXPECTED_KEY = 1;
        final Integer EXPECTED_VALUE = 2;
        LongMap<Integer> ints = new LongMapImpl<>();
        ints.put(EXPECTED_KEY, EXPECTED_VALUE);

        // WHEN
        ints.remove(EXPECTED_KEY);

        // THEN
        assertFalse(ints.containsKey(EXPECTED_KEY));
    }

    @Test
    public void isEmpty() {
        // WITH
        LongMap<Integer> ints = new LongMapImpl<>();

        // WHEN
        boolean empty = ints.isEmpty();

        // THEN
        assertTrue(empty);
    }

    @Test
    public void containsKey() {
        // WITH
        final int EXPECTED_KEY = 1;
        final Integer EXPECTED_VALUE = 2;
        LongMap<Integer> ints = new LongMapImpl<>();
        ints.put(EXPECTED_KEY, EXPECTED_VALUE);

        // WHEN
        boolean containsKey = ints.containsKey(EXPECTED_KEY);

        // THEN
        assertTrue(containsKey);
    }

    @Test
    public void containsValue() {
        // WITH
        final int EXPECTED_KEY = 1;
        final Integer EXPECTED_VALUE = 2;
        LongMap<Integer> ints = new LongMapImpl<>();
        ints.put(EXPECTED_KEY, EXPECTED_VALUE);

        // WHEN
        boolean containsValue = ints.containsValue(EXPECTED_VALUE);

        // THEN
        assertTrue(containsValue);
    }

    @Test
    public void keys() {
        // WITH
        final int EXPECTED_KEY = 1;
        final Integer EXPECTED_VALUE = 2;
        LongMap<Integer> ints = new LongMapImpl<>();
        ints.put(EXPECTED_KEY, EXPECTED_VALUE);

        // WHEN
        long[] keys = ints.keys();

        // THEN
        assertEquals(EXPECTED_KEY, keys[0]);
    }

    @Test
    public void values() {
        // WITH
        final int EXPECTED_KEY = 1;
        final Integer EXPECTED_VALUE = 2;
        LongMap<Integer> ints = new LongMapImpl<>();
        ints.put(EXPECTED_KEY, EXPECTED_VALUE);

        // WHEN
        Integer[] values = ints.values();

        // THEN
        assertEquals(EXPECTED_VALUE, values[0]);
    }

    @Test
    public void size() {
        // WITH
        final int expectedSize = 1;
        LongMap<Integer> ints = new LongMapImpl<>();
        ints.put(1,1);

        // WHEN
        long actualSize = ints.size();

        // THEN
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void clear() {
        // WITH
        LongMap<Integer> ints = new LongMapImpl<>();
        ints.put(1,1);

        // WHEN
        ints.clear();

        // THEN
        assertTrue(ints.isEmpty());
    }
}