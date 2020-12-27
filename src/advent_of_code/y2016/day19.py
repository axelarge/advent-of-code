N = int(open("resources/inputs/2016/day19.txt").read())

after = [(i + 1) % N for i in range(N)]
i = 0
while after[i] != i:
    after[i] = after[after[i]]
    i = after[i]
print(i + 1)

after = [(i + 1) % N for i in range(N)]
oppositeL = N // 2 - 1
i = 0
while N > 1:
    after[oppositeL] = after[after[oppositeL]]
    if N % 2:
        oppositeL = after[oppositeL]
    i = after[i]
    N -= 1
print(i + 1)
