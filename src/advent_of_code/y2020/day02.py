import re

pws = [(lambda p: (*map(int, p[:2]), *p[2:]))(re.split(r"[ :-]+", l.strip())) for l in open("resources/inputs/2020/day02.txt")]

print(sum(a <= pw.count(c) <= b for a, b, c, pw in pws))
print(sum((pw[a - 1] == c) ^ (pw[b - 1] == c) for a, b, c, pw in pws))
