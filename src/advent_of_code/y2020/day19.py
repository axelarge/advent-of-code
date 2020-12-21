import re
from functools import lru_cache as memoize

A, B = open("resources/inputs/2020/day19.txt").read().split("\n\n")
messages = B.splitlines()
rules = {}
for line in A.splitlines():
    k, rule = line.split(": ")
    if '"' in line:
        rules[k] = line[-2]
    else:
        rules[k] = rule.split(" ")

@memoize(None)
def pattern(k, part2=False):
    rule = rules[k]
    if type(rule) == str:
        pat = rule
    elif part2 and k == "8":
        # 8: 42 | 42 8
        pat = "(?:" + pattern("42", part2) + ")+"
    elif part2 and k == "11":
        # 11: 42 31 | 42 11 31
        a = pattern("42", part2)
        b = pattern("31", part2)
        # haxx
        pat = "(?:" + "|".join(f"({a}){{{n}}}({b}){{{n}}}" for n in range(1, 10)) + ")"
    else:
        pat = "(?:" + "".join(x if x == "|" else pattern(x, part2) for x in rule) + ")"
    return pat

print(sum(1 for m in messages if re.fullmatch(pattern("0"), m)))
print(sum(1 for m in messages if re.fullmatch(pattern("0", part2=True), m)))
