package de.comparus.opensource.longmap;

import org.junit.Assert;
import org.junit.Test;

public class LongMapImplTest {

    @Test
    public void shouldReplaceValueIfKeyTheSame(){
        //given:
        LongMap<String> map = new LongMapImpl<>();
        long[] keys = new long[] {1L, 244L};
        String valueToReplace = "valueToReplace";
        //when:
        map.put(keys[0], "original");
        map.put(keys[0], valueToReplace);
        //then:
        Assert.assertEquals(valueToReplace, map.get(keys[0]));
    }

    @Test
    public void shouldNotFindElementIfItRemoved(){
        //given:
        LongMap<String> map = new LongMapImpl<>();
        long[] keys = new long[] {1L, 244L};
        map.put(keys[0], "not important");
        map.put(keys[1], "not important");
        //when:
        map.remove(keys[0]);
        map.remove(keys[1]);
        //then:
        Assert.assertNull(map.get(keys[0]));
        Assert.assertNull(map.get(keys[1]));
    }

    @Test
    public void shouldBeEmptyIfNoElementsAdded(){
        //given:
        LongMap<String> map = new LongMapImpl<>();
        //expect:
        Assert.assertTrue(map.isEmpty());
    }

    @Test
    public void shouldBeEmptyIfElementsAddedButThenRemoved(){
        //given:
        LongMap<String> map = new LongMapImpl<>();
        long[] keys = new long[] {1L, 244L};
        map.put(keys[0], "not important");
        map.put(keys[1], "not important");
        //when:
        map.remove(keys[0]);
        map.remove(keys[1]);
        //then:
        Assert.assertTrue(map.isEmpty());
    }

    @Test
    public void shouldReturnTrueIfSuchKeyPresent(){
        //given:
        LongMap<String> map = new LongMapImpl<>();
        long[] keys = new long[] {1L, 244L};
        //when:
        map.put(keys[0], "not important");
        map.put(keys[1], "not important");
        //then:
        Assert.assertTrue(map.containsKey(keys[0]));
        Assert.assertTrue(map.containsKey(keys[1]));
    }

    @Test
    public void shouldReturnTrueIfSuchValuePresent(){
        //given:
        LongMap<String> map = new LongMapImpl<>();
        long[] keys = new long[] {1L, 244L};
        String[] values = new String[] {"value1", "value2"};
        //when:
        map.put(keys[0], values[0]);
        map.put(keys[1], values[1]);
        //then:
        Assert.assertTrue(map.containsValue(values[0]));
        Assert.assertTrue(map.containsValue(values[1]));
    }

    @Test
    public void shouldReturnRightKeys(){
        //given:
        LongMap<String> map = new LongMapImpl<>();
        long[] keys = new long[] {1L, 2L};
        //when:
        map.put(keys[0], "not important");
        map.put(keys[1], "not important");
        //then:
        Assert.assertArrayEquals(keys, map.keys());
    }

    @Test
    public void shouldReturnRightValues(){
        //given:
        LongMap<String> map = new LongMapImpl<>();
        long[] keys = new long[] {1L, 2L};
        String[] values = new String[] {"value1", "value2"};
        //when:
        map.put(keys[0], values[0]);
        map.put(keys[1], values[1]);
        //then:
        Assert.assertArrayEquals(values, map.values());
    }

    @Test
    public void shouldReturnRightSize(){
        //given:
        LongMap<String> map = new LongMapImpl<>();
        //when:
        int elementsCount = 9999;
        for (int i = 0; i < elementsCount; i++){
            map.put(i, "not important");
        }
        //then:
        Assert.assertEquals(elementsCount, map.size());
    }

    @Test
    public void shouldBeEmptyAfterClear() {
        //given:
        LongMap<String> map = new LongMapImpl<>();
        long[] keys = new long[]{1L, 2L};
        map.put(keys[0], "not important");
        map.put(keys[1], "not important");
        //when:
        map.clear();
        //then:
        Assert.assertTrue(map.isEmpty());
        Assert.assertNull(map.get(keys[0]));
        Assert.assertNull(map.get(keys[1]));
    }

}