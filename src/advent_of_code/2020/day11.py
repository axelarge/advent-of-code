data = open("resources/inputs/2020/day11.txt").read().splitlines()
H = len(data)
W = len(data[0])
G = {(x, y): 0 for y, row in enumerate(data) for x, c in enumerate(row) if c == 'L'}
D = [(dx, dy) for dx in [-1, 0, 1] for dy in [-1, 0, 1] if dx or dy]


def adjacent(g, x, y):
    return [(x + dx, y + dy) for dx, dy in D if (x + dx, y + dy) in g]


def visible(g, x0, y0):
    for dx, dy in D:
        x = x0 + dx
        y = y0 + dy
        while 0 <= x < W and 0 <= y < H and (x, y) not in g:
            x += dx
            y += dy
        if (x, y) in g:
            yield x, y


def step(c, limit, n):
    return n < limit if c else n == 0


def run(g, near, limit):
    lookup = {(x, y): list(near(g, x, y)) for x, y in g}
    prev = None
    while prev != g:
        prev, g = g, {xy: step(g[xy], limit, sum(g[xy1] for xy1 in lookup[xy])) for xy in g}
    return sum(g.values())


print(run(G, adjacent, 4))
print(run(G, visible, 5))


# # Faster but ugly 1D list version
#
#
# def valid(x, y):
#     return 0 <= x < W and 0 <= y < H
#
#
# def adjacent2(g, xy):
#     y0, x0 = divmod(xy, W)
#     return [xy + dy * W + dx for dx, dy in D if valid(x0 + dx, y0 + dy) and xy + dy * W + dx in g]
#
#
# def visible2(g, xy0):
#     y0, x0 = divmod(xy0, W)
#     for dx, dy in D:
#         x = x0 + dx
#         y = y0 + dy
#         while valid(x, y) and y * W + x not in g:
#             x += dx
#             y += dy
#         if valid(x, y) and y * W + x in g:
#             yield y * W + x
#
#
# def run2(g, near, limit):
#     lookup = {xy: list(near(g, xy)) for xy in g}
#     this = [0] * (W * H)
#     that = [0] * (W * H)
#     while True:
#         for xy in lookup:
#             that[xy] = step(this[xy], limit, sum(this[xy1] for xy1 in lookup[xy]))
#         if that == this:
#             return sum(this)
#         this, that = that, this
#
#
# G2 = {y * W + x: 0 for x, y in G}
# print(run2(G2, adjacent2, 4))
# print(run2(G2, visible2, 5))
