from math import prod

F = open("resources/inputs/2022/day08.txt").read().splitlines()
H = len(F)
W = len(F[0])


def lines(x, y):
    return [(F[y][x] for x in reversed(range(0, x))),
            (F[y][x] for y in reversed(range(0, y))),
            (F[y][x] for x in range(x + 1, W)),
            (F[y][x] for y in range(y + 1, H))]


def visible(l, limit):
    res = 0
    for t in l:
        res += 1
        if t >= limit:
            break
    return res


part1 = 0
part2 = 0
for y, line in enumerate(F):
    for x, tree in enumerate(line):
        part1 += any(all(t < tree for t in l) for l in lines(x, y))
        part2 = max(part2, prod(visible(l, tree) for l in lines(x, y)))

print(part1)
assert part1 == 1717
print(part2)
assert part2 == 321975
