import re
from collections import defaultdict

lines = open("resources/inputs/2020/day07.txt").readlines()
children = {" ".join(l.split()[:2]): {c: int(n) for n, c in re.findall(r"(\d+) (\w+ \w+)", l)} for l in lines}
parents = defaultdict(list)
for p in children:
    for c in children[p]:
        parents[c].append(p)

def all_parents(bag, to=None):
    to = to or set()
    for p in parents[bag]:
        to.add(p)
        all_parents(p, to)
    return to

def bag_size(bag):
    return 1 + sum(n * bag_size(c) for c, n in children[bag].items())

print(len(all_parents("shiny gold")))
print(bag_size("shiny gold") - 1)
