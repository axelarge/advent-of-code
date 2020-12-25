from heapq import heappush, heappop

def wall(pos):
    x, y = pos
    return x < 0 or y < 0 or bin((x + y) ** 2 + 3 * x + y + seed).count("1") % 2

def explore(stop, pos=(1, 1)):
    steps = 0
    seen = set()
    q = []
    while not stop(pos, steps):
        seen.add(pos)
        x, y = pos
        for near in (x - 1, y), (x + 1, y), (x, y - 1), (x, y + 1):
            if near not in seen and not wall(near):
                heappush(q, (steps + 1, near))
        steps, pos = heappop(q)
    return steps, len(seen)

seed = int(open("resources/inputs/2016/day13.txt").read())
print(explore(stop=lambda pos, steps: pos == (31, 39))[0])
print(explore(stop=lambda pos, steps: steps > 50)[1])
