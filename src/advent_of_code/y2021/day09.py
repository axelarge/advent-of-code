from collections import Counter
from math import prod

IN = open("resources/inputs/2021/day09.txt").read().splitlines()
H = {(x, y): int(h) for y, row in enumerate(IN) for x, h in enumerate(row)}

def neighbors(pos):
    x, y = pos
    return [p for p in [(x - 1, y), (x + 1, y), (x, y - 1), (x, y + 1)] if p in H]

low = [pos for pos, h in H.items() if all(H.get(pos1, 9) > h for pos1 in neighbors(pos))]

print(sum(H[x] + 1 for x in low))

B = {pos: pos for pos in low}
Q = set(low)
while Q:
    pos = Q.pop()
    for pos1 in neighbors(pos):
        if pos1 not in B:
            if H[pos1] == 9:
                B[pos1] = None
            else:
                B[pos1] = B[pos]
                Q.add(pos1)

print(prod(n for _, n in Counter(filter(None, B.values())).most_common(3)))
