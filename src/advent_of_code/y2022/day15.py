import re

F = open("resources/inputs/2022/day15.txt").read().splitlines()
D = []
for line in F:
    sx, sy, bx, by = map(int, re.findall(r"-?\d+", line))
    D.append((sx, sy, abs(sx - bx) + abs(sy - by)))

Y = 2000000
r = []
for sx, sy, d in D:
    m = d - abs(Y - sy)
    if m > 0:
        r.append([sx - m, sx + m])
r.sort()

part1 = 0
hii = r[0][0]
for lo, hi in r:
    part1 += max(0, hi - max(lo, hii))
    hii = max(hii, hi)
print(part1)
assert part1 == 5564017


def shells():
    for sx, sy, d in D:
        m = d + 1
        # this is 3x faster than 4 yields in one loop
        for i in range(m):
            yield sx - m + i, sy + i
        for i in range(m):
            yield sx + i, sy + m - i
        for i in range(m):
            yield sx + m - i, sy - i
        for i in range(m):
            yield sx - i, sy - m + i


part2 = None
R = 4000000
minx = max(0, min(sx for sx, sy, _ in D))
maxx = min(R, max(sx for sx, sy, _ in D))
miny = max(0, min(sy for sx, sy, _ in D))
maxy = min(R, max(sy for sx, sy, _ in D))
for x, y in shells():
    if minx <= x <= maxx and miny <= y <= maxy:
        if all(abs(sx - x) + abs(sy - y) > d for sx, sy, d in D):
            part2 = x * 4000000 + y
            break
print(part2)
assert part2 == 11558423398893
