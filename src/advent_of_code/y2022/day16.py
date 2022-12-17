import functools
import re
from collections import deque

F = open("resources/inputs/2022/day16.txt").read().splitlines()
FLOW = {}
CONNECTED = {}
DIST = {}
for line in F:
    valve, flow, others = re.match(r"Valve ([^ ]+) has flow rate=(\d+); [^A-Z]+ ([A-Z, ]+)", line).groups()
    if flow != "0":
        FLOW[valve] = int(flow)
    CONNECTED[valve] = set(others.split(", "))

for k in CONNECTED:
    res = {}
    q = deque([(k, 1)])
    visited = set()
    while q:
        x, dist = q.popleft()
        if x not in visited:
            visited.add(x)
            for b in CONNECTED[x]:
                res[b] = min(res.get(b, dist), dist)
                q.append((b, dist + 1))
    DIST[k] = res


def solve(start_time, players=1):
    @functools.cache
    def inner(time, opened, this, players):
        best = 0
        for that in FLOW:
            if that not in opened and that != this:
                time_left = time - DIST[this][that] - 1
                if time_left > 0:
                    ds = FLOW[that] * time_left
                    best = max(best, ds + inner(time_left, opened | {that}, that, players))

        if players > 1 and len(opened) > len(FLOW) // (players + 1):
            best = max(best, inner(start_time, opened, "AA", players - 1))

        return best

    return inner(start_time, frozenset(), "AA", players)


part1 = solve(30)
print(part1)
assert part1 == 1559

part2 = solve(26, players=2)
print(part2)
assert part2 == 2191

# def solve_slow():
#     q = [("AA", "AA", 26, 26, 26, 0, set())]
#     best = 0
#     while q:
#         pos1, pos2, move_at1, move_at2, time, score, opened = q.pop()
#         best = max(best, score)
#
#         can_move1 = time <= move_at1
#         can_move2 = time <= move_at2
#
#         targets1 = {}
#         if can_move1:
#             for that in HAS_FLOW:
#                 if that not in opened:
#                     t = DIST[pos1][that] + 1
#                     if time > t:
#                         targets1[that] = t
#
#         targets2 = {}
#         if can_move2:
#             for that in HAS_FLOW:
#                 if that not in opened:
#                     t = DIST[pos2][that] + 1
#                     if time > t:
#                         targets2[that] = t
#
#         if len(targets1) == len(targets2) == 1 and targets1.keys() == targets2.keys():
#             targets1[None] = 0
#             targets2[None] = 0
#         if len(targets1) == 0:
#             targets1[None] = 0
#         if len(targets2) == 0:
#             targets2[None] = 0
#
#         for to1 in targets1:
#             for to2 in targets2:
#                 # both can't go to the same spot
#                 if to1 == to2:
#                     continue
#
#                 # if both can go to either, pick the cheapest pair
#                 if targets1.get(to2, 0) > targets2.get(to1, 0):
#                     continue
#
#                 ds = 0
#                 opened2 = set(opened)
#                 if to1:
#                     move_at1 = time - DIST[pos1][to1] - 1
#                     opened2.add(to1)
#                     ds += move_at1 * FLOW[to1]
#                 if to2:
#                     move_at2 = time - DIST[pos2][to2] - 1
#                     opened2.add(to2)
#                     ds += move_at2 * FLOW[to2]
#                 assert to1 or to2
#                 q.append((to1 or pos1, to2 or pos2, move_at1, move_at2, max(move_at1, move_at2), score + ds, opened2))
#
#     return best
#
# print(solve_slow())
