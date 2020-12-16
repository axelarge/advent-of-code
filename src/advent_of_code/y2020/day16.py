import re
from functools import reduce
from operator import mul

A, B, C = map(str.splitlines, open("resources/inputs/2020/day16.txt").read().split("\n\n"))
rules = [(l.split(":")[0], *map(int, re.findall(r"\d+", l))) for l in A]
my_ticket = list(map(int, B[1].split(",")))
tickets = [list(map(int, l.split(","))) for l in C[1:]]


def valid_field(val, rule):
    _, lo1, hi1, lo2, hi2 = rule
    return lo1 <= val <= hi1 or lo2 <= val <= hi2


invalid = []
valid_tix = []
for ticket in tickets:
    for i, val in enumerate(ticket):
        if not any(valid_field(val, rule) for rule in rules):
            invalid.append(val)
            break
    else:
        valid_tix.append(ticket)

print(sum(invalid))

all_fields = set(r[0] for r in rules)
candidates = [set(all_fields) for _ in my_ticket]

for ticket in valid_tix + [my_ticket]:
    for i, val in enumerate(ticket):
        for rule in rules:
            if not valid_field(val, rule):
                candidates[i].remove(rule[0])

while not all(len(cs) == 1 for cs in candidates):
    for i, cs in enumerate(candidates):
        if len(cs) == 1:
            for j, cs2 in enumerate(candidates):
                if i != j:
                    cs2 -= cs

fields = {cs.pop(): i for i, cs in enumerate(candidates)}
print(reduce(mul, (my_ticket[i] for k, i in fields.items() if k.startswith("departure"))))
