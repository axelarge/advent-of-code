data = list(map(int, open("resources/inputs/2020/day15.txt").read().split(",")))
spoken = [0] * 2 ** 25
for i, n in enumerate(data, 1):
    spoken[n] = i
n = data[-1]
prev = len(data)
for i in range(prev + 1, 30_000_000 + 1):
    n = i - 1 - prev
    while len(spoken) <= n:
        spoken.append(0)
    prev = spoken[n] or i
    spoken[n] = i
    if i == 2020:
        print(n)
print(n)
