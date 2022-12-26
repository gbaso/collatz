package com.example.collatz.service;

public abstract class AbstractCollatzService {

    protected long next(long n) {
        if (n % 2 == 0) {
            return n / 2;
        } else {
            return 3 * n + 1;
        }
    }

}
