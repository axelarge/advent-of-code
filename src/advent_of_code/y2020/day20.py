from collections import defaultdict
from math import prod


def parse(lines):
    return [[int(c == "#") for c in row] for row in lines]


def normalize_edge(edge):
    edge = "".join(map(str, edge))
    return min(edge, edge[::-1])


def get_edge(tile, e):
    if e == "T": return normalize_edge(tile[0])
    if e == "B": return normalize_edge(tile[-1])
    if e == "L": return normalize_edge(row[0] for row in tile)
    if e == "R": return normalize_edge(row[-1] for row in tile)


def key(tid1, tid2):
    return tuple(sorted((tid1, tid2)))


tiles = {}
by_edge = defaultdict(set)
connected = defaultdict(set)
connected_by = {}

for chunk in open("resources/inputs/2020/day20.txt").read().split("\n\n"):
    lines = chunk.splitlines()
    tid = int(lines[0][5:-1])
    tile = parse(lines[1:])
    tiles[tid] = tile
    for e in "TBLR":
        edge = get_edge(tile, e)
        for tid2 in by_edge[edge]:
            connected[tid].add(tid2)
            connected[tid2].add(tid)
            connected_by[key(tid, tid2)] = edge
        by_edge[edge].add(tid)

corners = [tid for tid, near in connected.items() if len(near) == 2]
size = int(len(tiles) ** 0.5)
print(prod(corners))


def arrange(used):
    if len(used) == size * size:
        return used
    y, x = divmod(len(used), size)
    candidates = []
    if y > 0:
        candidates.append(connected[used[-size]])
    if x > 0:
        candidates.append(connected[used[-1]])
    for tid in set.intersection(*candidates):
        if tid not in used:
            if res := arrange(used + [tid]):
                return res


def rotate(tile):
    return list(map(list, zip(*tile[::-1])))


def flip(tile):
    return [row[::-1] for row in tile]


def options(tile):
    for _ in range(4):
        yield tile
        yield flip(tile)
        tile = rotate(tile)


grid = arrange([corners[0]])
tile_size = len(tiles[corners[0]]) - 2
img = [[0] * size * tile_size for _ in range(size * tile_size)]
for i, tid in enumerate(grid):
    y, x = divmod(i, size)
    edges = dict(T=connected_by[key(tid, grid[i - size])] if y > 0 else None,
                 B=connected_by[key(tid, grid[i + size])] if y < size - 1 else None,
                 L=connected_by[key(tid, grid[i - 1])] if x > 0 else None,
                 R=connected_by[key(tid, grid[i + 1])] if x < size - 1 else None)
    for tile in options(tiles[tid]):
        if all(not edge or get_edge(tile, e) == edge for e, edge in edges.items()):
            for dy, row in enumerate(tile[1:-1]):
                for dx, c in enumerate(row[1:-1]):
                    img[y * tile_size + dy][x * tile_size + dx] = c

monster = parse("""\
                  # 
#    ##    ##    ###
 #  #  #  #  #  #   \
""".splitlines()[::-1])  # reverse to fail faster

for img1 in options(img):
    if tot := sum(all(not c or img1[y0 + dy][x0 + dx]
                      for dy, mr in enumerate(monster)
                      for dx, c in enumerate(mr))
                  for y0 in range(0, len(img1) - len(monster) + 1)
                  for x0 in range(0, len(img1[0]) - len(monster[0]) + 1)):
        print(sum(c for row in img1 for c in row) - tot * 15)
        break
