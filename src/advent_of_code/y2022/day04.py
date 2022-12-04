import re

F = open("resources/inputs/2022/day04.txt").read().splitlines()

part1 = 0
part2 = 0
for line in F:
    e1min, e1max, e2min, e2max = map(int, re.findall(r"\d+", line))
    e1 = set(range(e1min, e1max + 1))
    e2 = set(range(e2min, e2max + 1))
    if e1 <= e2 or e2 <= e1:
        part1 += 1
    if e1 & e2:
        part2 += 1

print(part1)
print(part2)
assert part1 == 509
assert part2 == 870
