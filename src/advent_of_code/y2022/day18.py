import re
from collections import deque

F = open("resources/inputs/2022/day18.txt").read().splitlines()
G = set(tuple(map(int, re.findall(r"\d+", line))) for line in F)

minx = min(x for x, y, z in G)
maxx = max(x for x, y, z in G)
miny = min(y for x, y, z in G)
maxy = max(y for x, y, z in G)
minz = min(z for x, y, z in G)
maxz = max(z for x, y, z in G)
OUTSIDE = set()
INSIDE = set()


def neighbors(x, y, z):
    for dx, dy, dz in [(-1, 0, 0), (+1, 0, 0), (0, -1, 0), (0, +1, 0), (0, 0, -1), (0, 0, +1)]:
        yield x + dx, y + dy, z + dz


def is_outside(pos):
    if pos in OUTSIDE:
        return True
    if pos in INSIDE:
        return False
    assert pos not in G

    seen = set()
    q = deque([pos])
    while q:
        pos = q.popleft()
        if pos not in seen:
            seen.add(pos)

            x, y, z = pos
            if not (minx <= x <= maxx and miny <= y <= maxy and minz <= z <= maxz):
                OUTSIDE.update(seen)
                return True

            for pos1 in neighbors(*pos):
                if pos1 not in G:
                    q.append(pos1)

    INSIDE.update(seen)
    return False


part1 = sum(pos not in G for droplet in G for pos in neighbors(*droplet))
print(part1)
assert part1 == 4536

part2 = sum(pos not in G and is_outside(pos) for droplet in G for pos in neighbors(*droplet))
print(part2)
assert part2 == 2606


def debug():
    for z in range(minz, maxz + 1):
        for y in range(miny, maxy + 1):
            line = []
            for x in range(minx, maxx + 1):
                p = (x, y, z)
                line.append("#" if p in G else " " if is_outside(p) else ".")
            print("".join(line))
        print()
# debug()
