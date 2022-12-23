from collections import defaultdict, deque

F = open("resources/inputs/2018/day20.txt").read()
pos = (0, 0)
edges = defaultdict(set)
stack = [pos]
for c in F:
    if d := dict(N=(0, -1), E=(1, 0), S=(0, 1), W=(-1, 0)).get(c):
        pos1 = pos[0] + d[0], pos[1] + d[1]
        edges[pos].add(pos1)
        pos = pos1
    elif c == "|":
        pos = stack[-1]
    elif c == "(":
        stack.append(pos)
    elif c == ")":
        stack.pop()

seen = set()
part1 = 0
part2 = 0
q = deque([((0, 0), 0)])
while q:
    pos, n = q.popleft()
    if pos not in seen:
        seen.add(pos)
        part1 = max(part1, n)
        part2 += n >= 1000
        for pos1 in edges[pos]:
            q.append((pos1, n + 1))

print(part1)
assert part1 == 3465
print(part2)
assert part2 == 7956
