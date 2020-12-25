import re

data = []
for line in open("resources/inputs/2016/day15.txt"):
    i, n, _, x = map(int, re.findall(r"\d+", line))
    data.append((n, x + i))

t = 0
step = 1
for i, (n, x) in enumerate(sorted(data, reverse=True) + [(11, len(data) + 1)]):
    while (t + x) % n:
        t += step
    step *= n
    if len(data) - i in (0, 1):
        print(t)
