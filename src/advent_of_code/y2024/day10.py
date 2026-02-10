import functools
from collections import Counter

F = open("resources/inputs/2024/day10.txt").read().splitlines()
G = {(x, y): int(c) for y, row in enumerate(F) for x, c in enumerate(row) if c != "."}


@functools.lru_cache()
def score_at(x, y):
    v = G.get((x, y), None)
    if v is None:
        return Counter()
    if v == 9:
        return Counter({(x, y): 1})
    res = Counter()
    for dx, dy in [(1, 0), (-1, 0), (0, 1), (0, -1)]:
        if G.get((x + dx, y + dy), 0) == v + 1:
            res += score_at(x + dx, y + dy)
    return res


tot1 = 0
tot2 = 0
for xy in G:
    if G[xy] == 0:
        score = score_at(*xy)
        tot1 += len(score)
        tot2 += score.total()
print(tot1)
print(tot2)
