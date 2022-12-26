#!/bin/env python3

import sys
from collections import defaultdict, namedtuple
from typing import Set

def next(n: int) -> int:
    if n % 2 == 0:
        return n // 2
    else:
        return 3 * n + 1

def chain(n: int) -> Set[int]:
    chain = {n}
    while (n := next(n)) not in chain:
        chain.add(n)
    return chain

LengthInfo = namedtuple("LengthInfo", "length starts")

def find_max_length(m: int) -> LengthInfo:
    clen = defaultdict(list)
    for i in range(1, m + 1):
        clen[len(chain(i))].append(i)
    ml = max(clen.keys())
    return LengthInfo(ml, clen[ml])

if __name__ == "__main__":
    if (len(sys.argv)) < 2:
        print("pass the starting number you dolt!")
        exit(1)
    ml, starts = find_max_length(int(sys.argv[1]))
    print(f'Max lenght {ml} for starting value(s): {starts}')
