from collections import Counter
from functools import lru_cache

IN = open("resources/inputs/2021/day14.txt").read().splitlines()
T = IN[0]
D = dict(line.split(" -> ") for line in IN[2:])


@lru_cache(1000)
def size_after(t, n):
    if n == 0:
        return Counter(t)
    c = Counter()
    for a, b in zip(t, t[1:]):
        c.update(size_after(a + D[a + b] + b, n - 1))
    c.subtract(t[1:-1])
    return c


def solve(t, n):
    (_, hi), *_, (_, lo) = size_after(t, n).most_common()
    return hi - lo


print(solve(T, 10))
print(solve(T, 40))
