IN = sorted(map(int, open("resources/inputs/2021/day07.txt").read().split(",")))
R = range(IN[0], IN[-1] + 1)
print(min(sum(abs(crab - pos) for crab in IN) for pos in R))
print(min(sum(abs(crab - pos) * (abs(crab - pos) + 1) // 2 for crab in IN) for pos in R))
