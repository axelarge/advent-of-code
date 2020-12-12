data = [list(map(set, g.splitlines())) for g in open("resources/inputs/2020/day06.txt").read().split("\n\n")]

print(sum(len(set.union(*g)) for g in data))
print(sum(len(set.intersection(*g)) for g in data))
