data = list(map(int, open("resources/inputs/2020/day15.txt").read().split(",")))
spoken = {n: i for i, n in enumerate(data)}
n = data[-1]
prev = len(data) - 1
for i in range(prev + 1, 30_000_000):
    if i == 2020:
        print(n)
    n = i - 1 - prev
    prev = spoken.get(n, i)
    spoken[n] = i
print(n)
