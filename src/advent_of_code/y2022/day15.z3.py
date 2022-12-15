import re
from z3 import z3

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

R = 4000000
x = z3.Int("x")
y = z3.Int("y")
s = z3.Solver()
s.add(x >= 0, x <= R, y >= 0, y <= R)
for sx, sy, d in D:
    s.add(z3.Abs(x - sx) + z3.Abs(y - sy) > d)
s.check()
res = s.model()
part2 = res[x].as_long() * 4000000 + res[y].as_long()
print(part2)
assert part2 == 11558423398893
