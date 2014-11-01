package com.anue7.ewastecollector.utilities;

import java.util.Comparator;

public class IdComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer o, Integer o2) {
        return (o.compareTo(o2));
    }
}
