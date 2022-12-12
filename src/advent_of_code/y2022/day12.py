from collections import deque

F = open("resources/inputs/2022/day12.txt").read().splitlines()

G = {}
S = None
E = None
for y, row in enumerate(F):
    for x, c in enumerate(row):
        if c == "S":
            S = (x, y)
            c = "a"
        elif c == "E":
            E = (x, y)
            c = "z"
        G[(x, y)] = c


def neighbors(pos, reachable):
    v1 = G[pos]
    for dx, dy in ((-1, 0), (+1, 0), (0, -1), (0, +1)):
        p = (pos[0] + dx, pos[1] + dy)
        v2 = G.get(p)
        if v2 and reachable(ord(v1), ord(v2)):
            yield p


def solve(start, is_end, reachable):
    q = deque([(start, 0)])
    seen = set()
    while q:
        pos, n = q.popleft()
        if pos in seen:
            continue
        seen.add(pos)
        if is_end(pos):
            return n
        for pos1 in neighbors(pos, reachable):
            if pos1 not in seen:
                q.append((pos1, n + 1))


part1 = solve(S, lambda pos: pos == E, lambda v1, v2: v1 + 1 >= v2)
print(part1)
assert part1 == 520

part2 = solve(E, lambda pos: G[pos] == "a", lambda v2, v1: v1 + 1 >= v2)
print(part2)
assert part2 == 508
