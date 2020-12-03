from functools import reduce
from operator import mul

rows = open("resources/inputs/2020/day03.txt").read().splitlines()
trees = lambda dx, dy: sum(row[dx * y % len(rows[0])] == "#" for y, row in enumerate(rows[::dy]))
print(trees(3, 1))
print(reduce(mul, (trees(x, y) for x, y in [(1, 1), (3, 1), (5, 1), (7, 1), (1, 2)])))

# print((lambda r:[reduce(mul,(sum(l[a*y%len(r[0])]=="#"for y,l in enumerate(r[::b]))for a,b in c))for c in[[(3,1)],[(1,1),(3,1),(5,1),(7,1),(1,2)]]])(open("resources/inputs/2020/day03.txt").read().splitlines()))
