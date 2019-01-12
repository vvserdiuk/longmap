package de.comparus.opensource.longmap;

import java.util.*;

@SuppressWarnings("unchecked")
public class LongMapImpl<V> implements LongMap<V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final int DEFAULT_BUCKETS_INCREASE = 2;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private int threshold;
    private long count;
    private LinkedList<Entry<V>>[] buckets;

    public LongMapImpl() {
        this(DEFAULT_CAPACITY);
    }

    public LongMapImpl(int capacity) {
        this.buckets = (LinkedList<Entry<V>>[]) new LinkedList<?>[capacity];
        this.threshold = (int) (capacity * DEFAULT_LOAD_FACTOR);
    }

    @Override
    public V put(long key, V value) {
        int index = getBucketIndex(key);
        LinkedList<Entry<V>> bucket = buckets[index];
        if (bucket != null) {
            for (Entry<V> entry : bucket) {
                if (entry.key == key) {
                    V old = entry.value;
                    entry.value = value;
                    return old;
                }
            }

            if (count > threshold) {
                resize();
                index = getBucketIndex(key);
            }

            addEntry(index, new Entry<>(key, value));
        } else {
            buckets[index] = new LinkedList<>(Arrays.asList(new Entry<>(key, value)));
        }

        count++;
        return null;
    }

    private void resize() {
        int oldCapacity = buckets.length;
        int newCapacity = oldCapacity * DEFAULT_BUCKETS_INCREASE;
        LinkedList<Entry<V>>[] oldBuckets = buckets;
        buckets = (LinkedList<Entry<V>>[]) new LinkedList<?>[newCapacity];
        for (LinkedList<Entry<V>> bucket : oldBuckets) {
            if (bucket != null) {
                bucket.forEach(e -> addEntry(getBucketIndex(e.key), e));
            }
        }
    }

    private void addEntry(int index, Entry<V> entry) {
        LinkedList<Entry<V>> bucket = buckets[index];
        if (bucket != null) {
            bucket.add(entry);
        } else {
            buckets[index] = new LinkedList<>(Arrays.asList(entry));
        }
    }

    @Override
    public V get(long key) {
        LinkedList<Entry<V>> bucket = buckets[getBucketIndex(key)];
        if (bucket == null) { return null; }
        V value = null;
        for (Entry<V> entry : bucket) {
            if (entry.key == key) {
                value = entry.value;
            }
        }
        return value;
    }


    private int getBucketIndex(long key) {
        return Math.toIntExact(key % buckets.length);
    }

    @Override
    public V remove(long key) {
        LinkedList<Entry<V>> bucket = buckets[getBucketIndex(key)];
        V removedValue = null;
        for (Iterator<Entry<V>> i = bucket.iterator(); i.hasNext(); ) {
            Entry<V> entry = i.next();
            if (entry.key == key) {
                removedValue = entry.value;
                i.remove();
                count--;
            }
        }
        return removedValue;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public boolean containsKey(long key) {
        return get(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        return Arrays.stream(buckets)
                .filter(Objects::nonNull)
                .anyMatch(b -> b.stream()
                        .anyMatch(e -> e.value == value));
    }

    @Override
    public long[] keys() {
        return Arrays.stream(buckets)
                .filter(Objects::nonNull)
                .flatMap(b -> b.stream()
                        .map(e -> e.key))
                .mapToLong(Long::longValue).toArray();
    }

    @Override
    public V[] values() {
        return (V[]) Arrays.stream(buckets)
                .filter(Objects::nonNull)
                .flatMap(b -> b.stream()
                        .map(e -> e.value))
                .toArray();
    }

    @Override
    public long size() {
        return count;
    }

    @Override
    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = null;
        }
        count = 0;
    }

    private class Entry<V> {
        long key;
        V value;

        Entry(long key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
