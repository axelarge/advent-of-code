from heapq import heappop, heappush


def neighbors(risks, pos):
    x, y = pos
    return [p for p in [(x - 1, y), (x + 1, y), (x, y - 1), (x, y + 1)] if p in risks]


def find_path(risks, start, target):
    q = [(0, start)]
    seen = set()
    while q:
        risk, pos = heappop(q)
        if pos == target:
            return risk
        if pos in seen:
            continue
        seen.add(pos)
        for near in neighbors(risks, pos):
            if near in seen:
                continue
            heappush(q, (risk + risks[near], near))


IN = open("resources/inputs/2021/day15.txt").read().splitlines()
H = len(IN)
W = len(IN[0])

R = {(x, y): int(r) for y, line in enumerate(IN) for x, r in enumerate(line)}
print(find_path(R, (0, 0), (W - 1, H - 1)))

wrap = lambda r: r % 10 + r // 10
R2 = {(x, y): wrap(R[(x % W, y % H)] + (y // H) + (x // W)) for y in range(H * 5) for x in range(W * 5)}
print(find_path(R2, (0, 0), (W * 5 - 1, H * 5 - 1)))
