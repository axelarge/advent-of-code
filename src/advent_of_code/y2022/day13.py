from functools import cmp_to_key
from math import prod


def compare(a, b):
    al = type(a) == list
    bl = type(b) == list
    if not al and not bl:
        return a - b
    elif al and not bl:
        return compare(a, [b])
    elif not al and bl:
        return compare([a], b)
    else:
        for ax, bx in zip(a, b):
            if c := compare(ax, bx):
                return c
        return len(a) - len(b)


F = open("resources/inputs/2022/day13.txt").read()
pairs = [list(map(eval, pair.splitlines())) for pair in F.split("\n\n")]

part1 = sum(i for i, (a, b) in enumerate(pairs, 1) if compare(a, b) < 0)
print(part1)
assert part1 == 6428

dividers = [[[2]], [[6]]]
msg = dividers + [x for pair in pairs for x in pair]
part2 = prod(i for i, x in enumerate(sorted(msg, key=cmp_to_key(compare)), 1) if x in dividers)
print(part2)
assert part2 == 22464
