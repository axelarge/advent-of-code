import re

F = open("resources/inputs/2024/day03.txt").read()

print(sum(int(a) * int(b) for a, b in re.findall(r"mul\((\d+),(\d+)\)", F)))

enabled = True
total = 0
for m in re.findall(r"(do\(\)|don't\(\))|mul\((\d+),(\d+)\)", F):
    if m[0] == "do()":
        enabled = True
    elif m[0] == "don't()":
        enabled = False
    elif enabled:
        total += int(m[1]) * int(m[2])
print(total)
