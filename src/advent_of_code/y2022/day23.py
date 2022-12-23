import itertools
from collections import Counter

F = open("resources/inputs/2022/day23.txt").read().splitlines()

MOVES = [[(0, -1), (-1, -1), (0, -1), (+1, -1)],
         [(0, +1), (-1, +1), (0, +1), (+1, +1)],
         [(-1, 0), (-1, -1), (-1, 0), (-1, +1)],
         [(+1, 0), (+1, -1), (+1, 0), (+1, +1)]]
N8 = [(-1, -1), (0, -1), (1, -1), (1, 0), (1, 1), (0, 1), (-1, 1), (-1, 0)]


def find_move(pos, g, offset):
    x, y = pos

    def all_free(deltas):
        # For some reason this manual loop is much faster than the comprehension
        # return all((x + dx, y + dy) not in g for dx, dy in deltas)
        for dx, dy in deltas:
            if (x + dx, y + dy) in g:
                return False
        return True

    if all_free(N8):
        return x, y

    for i in range(4):
        (dx, dy), *deltas = MOVES[(i + offset) % 4]
        if all_free(deltas):
            return x + dx, y + dy

    return pos


def empties(g):
    minx = min(x for x, y in g)
    maxx = max(x for x, y in g)
    miny = min(y for x, y in g)
    maxy = max(y for x, y in g)
    return (maxx - minx + 1) * (maxy - miny + 1) - len(g)


G = {(x, y) for y, row in enumerate(F) for x, c in enumerate(row) if c == "#"}
for i in itertools.count():
    G1 = set()
    moves_count = Counter()
    moves = {}
    for pos in G:
        move = find_move(pos, G, i)
        moves_count[move] += 1
        moves[pos] = move
    for pos in G:
        move = moves[pos]
        if moves_count[move] == 1:
            G1.add(move)
        else:
            G1.add(pos)
    if i == 9:
        part1 = empties(G1)
        print(part1)
        assert part1 == 3990
    if G1 == G:
        part2 = i + 1
        print(part2)
        assert part2 == 1057
        break
    G = G1
