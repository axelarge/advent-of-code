import re

F = open("resources/inputs/2022/day15.txt").read().splitlines()
D = [tuple(map(int, re.findall(r"-?\d+", line))) for line in F]

Y = 2000000
r = []
for sx, sy, bx, by in D:
    m = abs(sx - bx) + abs(sy - by) - abs(Y - sy)
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
    for sx, sy, bx, by in D:
        m = abs(sx - bx) + abs(sy - by) + 1
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
minx = max(0, min(sx for sx, sy, bx, by in D))
maxx = min(R, max(sx for sx, sy, bx, by in D))
miny = max(0, min(sy for sx, sy, bx, by in D))
maxy = min(R, max(sy for sx, sy, bx, by in D))
for x, y in shells():
    if minx <= x <= maxx and miny <= y <= maxy:
        if all(abs(sx - x) + abs(sy - y) > abs(sx - bx) + abs(sy - by) for sx, sy, bx, by in D):
            part2 = x * 4000000 + y
            break
print(part2)
assert part2 == 11558423398893
