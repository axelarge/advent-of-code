import re
from collections import defaultdict
from itertools import combinations


def move(floors, from_floor, to_floor, things):
    floors = list(floors)
    floors[from_floor] = floors[from_floor] - things
    floors[to_floor] = floors[to_floor] | things
    return tuple(floors)


def valid(floor):
    gs = set(thing[0] for thing in floor if thing[1] == "G")
    if gs:
        for x, gm in floor:
            if gm == "M" and x not in gs:
                return False
    return True


def debug(floors):
    return [",".join(f"{x}{mg}" for x, mg in sorted(f)) for f in floors]


def fingerprint(e, floors):
    s = defaultdict(lambda: [0, 0])
    for i, f in enumerate(floors):
        for x, gm in f:
            s[x]["MG".index(gm)] = i
    return tuple([e, *map(tuple, sorted(s.values()))])


def solve(floors):
    q = [(0, tuple(frozenset(floor) for floor in floors))]
    n = sum(map(len, floors))
    seen = set()
    t = 0
    while q:
        q1 = []
        for e, floors in q:
            fp = fingerprint(e, floors)
            if fp not in seen:
                seen.add(fp)
                if len(floors[3]) == n:
                    return t
                floor = floors[e]
                e1s = []
                if e < 3:
                    e1s.append(e + 1)
                if e > 0 and any(floors[:e]):
                    e1s.append(e - 1)
                for e1 in e1s:
                    did_bring_two = False
                    for thing1, thing2 in combinations(floor, 2):
                        f1 = move(floors, e, e1, {thing1, thing2})
                        if valid(f1[e]) and valid(f1[e1]):
                            did_bring_two = True
                            q1.append((e1, f1))
                    if not (e1 > e and did_bring_two):
                        for thing in floor:
                            f1 = move(floors, e, e1, {thing})
                            if valid(f1[e]) and valid(f1[e1]):
                                q1.append((e1, f1))
        q = q1
        t += 1


F = open("resources/inputs/2016/day11.txt").read()
FLOORS = []
for line in F.splitlines():
    floor = [*((x[:2], "G") for x in re.findall(r"(\w+) generator", line)),
             *((x[:2], "M") for x in re.findall(r"(\w+)-compatible microchip", line))]
    FLOORS.append(floor)

part1 = solve(FLOORS)
print(part1)
assert part1 == 47

part2 = solve([FLOORS[0] + [("el", "G"), ("el", "M"), ("di", "G"), ("di", "M")], *FLOORS[1:]])
print(part2)
assert part2 == 71
