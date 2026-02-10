import re
import math

F = open("resources/inputs/2023/day06.txt").read().splitlines()
times, scores = [list(map(int, re.findall(r"\d+", line))) for line in F]


def solve(t, record):
    # hold*(time-hold) > record
    # - hold**2 + hold*time - record > 0
    d = t * t - 4 * record
    x1 = math.floor((math.sqrt(d) - t) / -2 + 1)
    x2 = math.ceil((math.sqrt(d) + t) / 2 - 1)
    return max(0, x2 - x1 + 1)


res1 = math.prod(solve(time, record) for time, record in zip(times, scores))
res2 = solve(int("".join(map(str, times))), int("".join(map(str, scores))))

print(res1)
print(res2)
assert res1 == 2269432
assert res2 == 35865985
