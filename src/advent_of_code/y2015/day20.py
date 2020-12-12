#!/usr/local/bin/python3
from sys import argv


def solve(num, two=False):
    n = num // 10
    lim = 50 if two else n
    multi = 11 if two else 10
    table = [0] * n
    for i in range(1, n):
        for j in range(i, min(n, i * lim + 1), i):
            table[j] += i * multi
        if table[i] >= num:
            print(table)
            return i
    print(table)


print(solve(int(argv[1]) if len(argv) > 1 else 29000000,
            int(argv[2]) if len(argv) > 2 else False))
