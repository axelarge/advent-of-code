data = list(map(int, open("resources/inputs/2020/day15.txt").read().split(",")))
spoken = [-1] * 2 ** 25
for i, n in enumerate(data):
    spoken[n] = i
n = data[-1]
prev = len(data) - 1
for i in range(prev + 1, 30_000_000):
    if i == 2020:
        print(n)
    n = i - 1 - prev
    while len(spoken) <= n:
        spoken.append(-1)
    prev = spoken[n]
    if prev == -1:
        prev = i
    spoken[n] = i
print(n)
