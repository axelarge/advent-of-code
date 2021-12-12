from collections import defaultdict

E = defaultdict(set)
for line in open("resources/inputs/2021/day12.txt").read().splitlines():
    a, b = line.split("-")
    E[a].add(b)
    E[b].add(a)


def solve(part2=False):
    n = 0
    q = [("start", {"start"}, part2)]
    while q:
        at, visited, can_go_again = q.pop()
        if at == "end":
            n += 1
        else:
            for to in E[at]:
                if to.isupper() or to not in visited:
                    q.append((to, visited | {to}, can_go_again))
                elif can_go_again and to != "start":
                    q.append((to, visited, False))
    return n


print(solve(False))
print(solve(True))
