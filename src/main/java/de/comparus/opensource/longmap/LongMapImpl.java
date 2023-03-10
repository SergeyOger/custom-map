package de.comparus.opensource.longmap;

import java.util.Arrays;
import java.util.Objects;

public class LongMapImpl<V> implements LongMap<V> {
    private static int DEFAULT_CAPACITY = 15;
    private static float LOAD_FACTOR = 0.8f;
    private int currentCapacity;
    private int currentSize;
    private CustomMapEntry[] table;

    public LongMapImpl() {
        this(DEFAULT_CAPACITY);
    }

    public LongMapImpl(int initialCapacity) {
        this.table = new CustomMapEntry[initialCapacity];
        this.currentCapacity = initialCapacity;
    }

    public V put(long key, V value) {
        if ((float) currentSize / currentCapacity > LOAD_FACTOR) {
            currentCapacity = currentCapacity + (int) (currentCapacity * LOAD_FACTOR) + 1;
            resize(currentCapacity);
        }

        final int hash = hash(key);
        CustomMapEntry<V> collisionNode = table[indexOf(hash, currentCapacity)];
        if (Objects.nonNull(collisionNode)) {
            CustomMapEntry<V> latestChainNode = collisionNode;
            while (Objects.nonNull(latestChainNode.nextElement)) {
                latestChainNode = latestChainNode.nextElement;
            }
            latestChainNode.nextElement = new CustomMapEntry<>(key, value, null);
        } else {
            table[indexOf(hash, currentCapacity)] = new CustomMapEntry<>(key, value, null);
        }

        this.currentSize++;
        return value;
    }

    public V get(long key) {
        final int hash = hash(key);
        final int entryIndex = indexOf(hash, currentCapacity);
        if (entryIndex > table.length || Objects.isNull(table[entryIndex])) {
            return null;
        }
        return getEntry(entryIndex, key);
    }

    private V getEntry(int entryIndex, long key) {
        CustomMapEntry<V> firstEntry = table[entryIndex];
        if (Objects.isNull(firstEntry.nextElement)) {
            return firstEntry.value;
        }

        CustomMapEntry<V> latestChainNode = firstEntry;

        while (Objects.nonNull(latestChainNode.nextElement)) {
            latestChainNode = latestChainNode.nextElement;
            if (latestChainNode.key == key) {
                return latestChainNode.value;
            }
        }

        return null;
    }

    public V remove(long key) {
        final int hash = hash(key);
        final int entryIndex = indexOf(hash, currentCapacity);
        if (entryIndex > table.length) {
            return null;
        }
        final CustomMapEntry<V> entry = table[entryIndex];
        table[entryIndex] = null;
        currentSize--;
        return Objects.isNull(entry) ? null : entry.value;
    }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    public boolean containsKey(long key) {
        final int hash = hash(key);
        final int entryIndex = indexOf(hash, currentCapacity);
        return entryIndex > table.length || Objects.nonNull(table[entryIndex]);
    }

    public boolean containsValue(V value) {
        return Arrays.stream(table)
                .anyMatch(entry -> Objects.nonNull(entry) && entry.value == value);
    }

    public long[] keys() {
        return Arrays.stream(table)
                .filter(Objects::nonNull)
                .mapToLong(entry -> entry.key).toArray();
    }

    public V[] values() {
        Object[] values = Arrays.stream(table)
                .filter(Objects::nonNull)
                .map(entry -> entry.value).toArray();
        return (V[]) Arrays.copyOf(values, table.length, Object[].class);
    }

    public long size() {
        return this.currentSize;
    }

    public void clear() {
        this.table = new CustomMapEntry[currentCapacity];
        this.currentSize = 0;
    }

    private void resize(int newCapacity) {
        table = Arrays.copyOf(table, newCapacity);
    }

    private int indexOf(int hash, int currentCapacity) {
        return hash & (currentCapacity - 1);
    }

    private static int hash(Long key) {
        if (Objects.isNull(key)) {
            return 0;
        }
        int hash = key.hashCode();
        return hash ^ (hash >>> 16);
    }


    static class CustomMapEntry<V> {
        Long key;
        V value;
        CustomMapEntry<V> nextElement;

        CustomMapEntry(Long key, V value, CustomMapEntry<V> nextElement) {
            this.key = key;
            this.value = value;
            this.nextElement = nextElement;
        }
    }
}
