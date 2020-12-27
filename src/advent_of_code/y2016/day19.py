from math import log

N = int(open("resources/inputs/2016/day19.txt").read())

print(int(bin(N)[3:] + "1", 2))

z = N - 3 ** int(log(N, 3))
print(z + max(0, (z * 2) - N) if z else N)
