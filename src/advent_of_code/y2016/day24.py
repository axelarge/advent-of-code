from heapq import heappush, heappop
from itertools import permutations


def explore(start):
    q = [(0, start, 1)]
    seen = set()
    dist_to = {}
    while q:
        d, pos, _ = heappop(q)
        if pos in seen:
            continue
        seen.add(pos)
        x, y = pos
        at = maze[y][x]
        if pos != start and at.isdigit():
            dist_to[at] = d
        for pos1 in [(x, y + 1), (x, y - 1), (x + 1, y), (x - 1, y)]:
            if maze[pos1[1]][pos1[0]] == "#" or pos1 in seen:
                continue
            heappush(q, ((d + 1), pos1, 1))
    return dist_to


maze = open("resources/inputs/2016/day24.txt").read().splitlines()
nums = {c: (x, y) for y, row in enumerate(maze) for x, c in enumerate(row) if c.isdigit()}

dists = {start: explore(nums[start]) for start in nums}

print(min(sum(dists[a][b] for a, b in zip(("0",) + perm, perm))
          for perm in permutations(k for k in nums if k != "0")))

print(min(sum(dists[a][b] for a, b in zip(("0",) + perm, perm + ("0",)))
          for perm in permutations(k for k in nums if k != "0")))
