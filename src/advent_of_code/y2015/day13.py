#!/usr/local/bin/python3
import re
from itertools import permutations

TABLE = {}
PEOPLE = set()
with open("resources/inputs/2015/day13.txt") as f:
    for line in f:
        me, act, d, other = re.match(r"(\w).*(gain|lose) (\d+).* (\w)\w+.", line).groups()
        TABLE[(me, other)] = int(d) * (-1 if act == "lose" else 1)
        PEOPLE.add(me)


def pair_score(a, b):
    return TABLE.get((a, b), 0) + TABLE.get((b, a), 0)


def score(seating):
    return sum(pair_score(a, b) for a, b in zip(seating, seating[1:] + seating[:1]))


def solve(people):
    return max(map(score, permutations(people, len(people))))


print(solve(PEOPLE))
print(solve(PEOPLE | {"_"}))
