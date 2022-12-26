package com.example.collatz.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.collatz.LengthInfo;

@Service("simple")
public class SimpleCollatzService extends AbstractCollatzService implements CollatzService {

    private Set<Long> chain(long n) {
        Set<Long> chain = new LinkedHashSet<>();
        while (chain.add(n)) {
            n = next(n);
        }
        return chain;
    }

    @Override
    public LengthInfo findMaxLength(int max) {
        Map<Integer, List<Integer>> clen = new HashMap<>();
        for (int i = 1; i <= max; i++) {
            clen.computeIfAbsent(chain(i).size(), k -> new ArrayList<>()).add(i);
        }
        int ml = clen.keySet().stream().mapToInt(i -> i).max().orElseThrow();
        return new LengthInfo(ml, clen.get(ml));
    }

}
