from functools import cmp_to_key
from json import loads
from math import prod


def compare(a, b):
    match a, b:
        case int(), int():
            return a - b
        case int(), list():
            return compare([a], b)
        case list(), int():
            return compare(a, [b])
        case list(), list():
            for ax, bx in zip(a, b):
                if c := compare(ax, bx):
                    return c
            return len(a) - len(b)


F = open("resources/inputs/2022/day13.txt").read()
pairs = [list(map(loads, pair.splitlines())) for pair in F.split("\n\n")]

part1 = sum(i for i, (a, b) in enumerate(pairs, 1) if compare(a, b) < 0)
print(part1)
assert part1 == 6428

dividers = [[[2]], [[6]]]
msg = dividers + [x for pair in pairs for x in pair]
part2 = prod(i for i, x in enumerate(sorted(msg, key=cmp_to_key(compare)), 1) if x in dividers)
print(part2)
assert part2 == 22464
