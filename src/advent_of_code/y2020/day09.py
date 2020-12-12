from itertools import combinations

data = list(map(int, open("resources/inputs/2020/day09.txt")))

N = next(x for n, x in enumerate(data[25:]) if not any(a + b == x for a, b in combinations(data[n:n + 25], 2)))
print(N)

lo = hi = total = 0
while total != N:
    if total < N:
        total += data[hi]
        hi += 1
    else:
        total -= data[lo]
        lo += 1
nums = data[lo:hi]
print(min(nums) + max(nums))
