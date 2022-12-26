package com.example.collatz.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.example.collatz.LengthInfo;
import com.example.collatz.config.ParallelProperties;

import lombok.RequiredArgsConstructor;

@Service("parallel")
@RequiredArgsConstructor
public class ParallelCollatzService extends AbstractCollatzService implements CollatzService {

    private final ParallelProperties parallel;

    private Set<Long> chain(long n) {
        Set<Long> chain = new LinkedHashSet<>();
        while (chain.add(n)) {
            n = next(n);
        }
        return chain;
    }

    @Override
    public LengthInfo findMaxLength(int max) {
        return IntStream.range(0, parallel.level())
                .parallel()
                .mapToObj(iter -> {
                    Map<Integer, List<Integer>> clen = new HashMap<>();
                    for (int i = 1 + iter; i <= max; i = i + parallel.level()) {
                        clen.computeIfAbsent(chain(i).size(), k -> new ArrayList<>()).add(i);
                    }
                    int ml = clen.keySet().stream().mapToInt(i -> i).max().orElseThrow();
                    return new LengthInfo(ml, clen.get(ml));
                })
                .reduce((info1, info2) -> {
                    int comparison = Integer.compare(info1.length(), info2.length());
                    if (comparison > 0) {
                        return info1;
                    } else if (comparison < 0) {
                        return info2;
                    } else {
                        List<Integer> starts = Stream.concat(info1.starts().stream(), info2.starts().stream()).toList();
                        return new LengthInfo(info1.length(), starts);
                    }
                })
                .orElseThrow();
    }

}
