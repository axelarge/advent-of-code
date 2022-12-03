IN = open("resources/inputs/2021/day25.txt").read().splitlines()
H = len(IN)
W = len(IN[0])
G = {(x, y): c for y, line in enumerate(IN) for x, c in enumerate(line) if c in ">v"}

for i in range(100000):
    G1 = {}
    moved = False

    for (x, y), c in G.items():
        if c == ">":
            near = ((x + 1) % W, y)
            if near not in G:
                moved = True
                G1[near] = ">"
            else:
                G1[x, y] = c

    for (x, y), c in G.items():
        if c == "v":
            near = (x, (y + 1) % H)
            if G.get(near) != "v" and G1.get(near) != ">":
                moved = True
                G1[near] = c
            else:
                G1[x, y] = c

    G = G1
    if not moved:
        print(i + 1)
        break
