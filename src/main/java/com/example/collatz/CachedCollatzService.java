package com.example.collatz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service("cached")
public class CachedCollatzService implements CollatzService {

    private final Map<Long, Chain> cache = new HashMap<>();

    private long next(long n) {
        if (n % 2 == 0) {
            return n / 2;
        } else {
            return 3 * n + 1;
        }
    }

    private Chain chain(long start) {
        Set<Long> segment = new LinkedHashSet<>();
        long n = start;
        Chain rest = null;
        while (segment.add(n)) {
            n = next(n);
            rest = cache.get(n);
            if (rest != null) {
                break;
            }
        }
        var chain = new Chain(segment, rest);
        cache.put(start, chain);
        return chain;
    }

    public LengthInfo findMaxLength(int max) {
        Map<Integer, List<Integer>> clen = new HashMap<>();
        for (int i = 1; i <= max; i++) {
            clen.computeIfAbsent(chain(i).size(), k -> new ArrayList<>()).add(i);
        }
        int ml = clen.keySet().stream().mapToInt(i -> i).max().orElseThrow();
        return new LengthInfo(ml, clen.get(ml));
    }

}

record Chain(Set<Long> segment, Chain rest) {
    int size() {
        Set<Long> fullChain = new HashSet<>(segment);
        Chain next = rest;
        while (next != null) {
            fullChain.addAll(next.segment);
            next = next.rest;
        }
        return fullChain.size();
    }
}
