from collections import Counter
from itertools import cycle, product
from functools import lru_cache

ROLL_COUNTS = Counter(map(sum, product(*[[1, 2, 3]] * 3)))
IN = open("resources/inputs/2021/day21.txt").read()
P1, P2 = [int(line.split()[-1]) - 1 for line in IN.splitlines()]

state = [P1, 0, P2, 0]
for i, rolled in enumerate(map(sum, zip(*[cycle(range(1, 101))] * 3)), 1):
    p1pos, p1score, p2pos, p2score = state
    p1pos = (p1pos + rolled) % 10
    p1score += p1pos + 1
    if p1score >= 1000:
        print(p2score * i * 3)
        break
    state = [p2pos, p2score, p1pos, p1score]


@lru_cache(None)
def wins_when(p1pos, p1score, p2pos, p2score):
    p1wins = 0
    p2wins = 0
    for rolled, n in ROLL_COUNTS.items():
        p1pos1 = (p1pos + rolled) % 10
        p1score1 = p1score + p1pos1 + 1
        if p1score1 >= 21:
            p1wins += n
        else:
            p2w1, p1w1 = wins_when(p2pos, p2score, p1pos1, p1score1)
            p1wins += n * p1w1
            p2wins += n * p2w1
    return p1wins, p2wins


print(max(wins_when(P1, 0, P2, 0)))
