def total(row, n):
    w = len(row)
    tot = 0
    for _ in range(n):
        tot += w - sum(row)
        row = [(i > 0 and row[i - 1]) ^ (i + 1 < w and row[i + 1]) for i in range(w)]
    return tot

seed = list(map(".^".index, open("resources/inputs/2016/day18.txt").read().strip()))
print(total(seed, 40))
print(total(seed, 400000))
