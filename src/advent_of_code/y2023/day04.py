import re
from collections import OrderedDict, defaultdict, deque, Counter
from itertools import count, permutations, combinations
from heapq import heappush, heappop
from functools import lru_cache

F = open("resources/inputs/2023/day04.txt").read().splitlines()

F2 = """Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11""".splitlines()
# F = F2
print(F)

res1 = 0
res2 = 0
multi = deque()
extras = Counter()
for i, line in enumerate(F, 1):
    print()
    print(f"Card {i}")
    winning, have = [set(map(int, chunk.split())) for chunk in re.split(r"[:|]", line)[1:]]
    print(winning, have)
    score = len(winning & have)
    print(f"score: {score}")
    if score:
        res1 += 2 ** (score - 1)

    copies = extras[i] + 1
    print(f"copies: {copies}")
    res2 += 1+score * copies
    for j in range(i + 1, i + 1 + score):
        print(f"Adding {copies} to card {j}")
        extras[j] += copies

print(res1)
print(res2)
