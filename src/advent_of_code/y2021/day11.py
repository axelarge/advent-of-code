from itertools import count


def neighbors(pos):
    x, y = pos
    return [p for p in ((x + dx, y + dy) for dx in (-1, 0, 1) for dy in (-1, 0, 1) if dx or dy) if p in G]


IN = open("resources/inputs/2021/day11.txt").read().splitlines()
G = {(x, y): int(l) for y, row in enumerate(IN) for x, l in enumerate(row)}

tot = 0
for i in count(1):
    G = {pos: l + 1 for pos, l in G.items()}
    Q = set(G)
    flashed = set()
    while Q:
        pos = Q.pop()
        if G[pos] > 9 and pos not in flashed:
            flashed.add(pos)
            for pos1 in neighbors(pos):
                G[pos1] += 1
                Q.add(pos1)
    for pos in flashed:
        G[pos] = 0
    tot += len(flashed)
    if i == 100:
        print(tot)
    if len(flashed) == len(G):
        print(i)
        break
