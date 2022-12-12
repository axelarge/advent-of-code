from collections import deque

F = open("resources/inputs/2022/day12.txt").read().splitlines()

G = {}
start = None
end = None
for y, row in enumerate(F):
    for x, c in enumerate(row):
        if c == "S":
            start = (x, y)
            c = "a"
        elif c == "E":
            end = (x, y)
            c = "z"
        G[(x, y)] = c


def neighbors(pos):
    v1 = G[pos]
    for dx, dy in ((-1, 0), (+1, 0), (0, -1), (0, +1)):
        p = (pos[0] + dx, pos[1] + dy)
        v2 = G.get(p)
        if v2 and ord(v1) + 1 >= ord(v2):
            yield p


def solve(start):
    q = deque([(start, 0)])
    seen = set()
    while q:
        pos, n = q.popleft()
        if pos in seen:
            continue
        seen.add(pos)
        if pos == end:
            return n
        for pos1 in neighbors(pos):
            if pos1 not in seen:
                q.append((pos1, n + 1))


part1 = solve(start)
print(part1)
assert part1 == 520

# TODO - Reuse data from part 1
part2 = min(filter(None, (solve(pos) for pos, c in G.items() if c == "a")))
print(part2)
assert part2 == 508
