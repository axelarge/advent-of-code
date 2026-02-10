import re

F = open("resources/inputs/2023/day09.txt").read().splitlines()
vals = [list(map(int, re.findall(r"-?\d+", line))) for line in F]
res1 = 0
res2 = 0
for xs in vals:
    k = 1
    while any(xs):
        res1 += xs[-1]
        res2 += xs[0] * k
        k = -k
        xs = [b - a for a, b in zip(xs, xs[1:])]

print(res1)
print(res2)
assert res1 == 1901217887
assert res2 == 905
