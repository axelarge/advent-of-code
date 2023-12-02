import re
from collections import Counter
from functools import reduce
from math import prod

F = open("resources/inputs/2023/day02.txt").read().splitlines()
VALID = Counter({"red": 12, "green": 13, "blue": 14})

res1 = 0
res2 = 0
for line in F:
    game_id, draws = re.match(r"Game (\d+): (.+)", line).groups()
    game_id = int(game_id)
    draws = [Counter({m[1]: int(m[0]) for m in re.findall(r"(\d+) ([^,]+)", d)}) for d in draws.split("; ")]
    res1 += game_id if all(d <= VALID for d in draws) else 0
    res2 += prod(reduce(Counter.__or__, draws).values())

print(res1)
print(res2)
assert res1 == 2679
assert res2 == 77607
