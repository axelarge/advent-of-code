from collections import Counter

nums = sorted(map(int, open("resources/inputs/2020/day10.txt")))
nums.append(nums[-1] + 3)

c = Counter(y - x for x, y in zip([0] + nums, nums))
print(c[1] * c[3])

res = {0: 1}
for x in nums:
    res[x] = sum(res.get(x + d, 0) for d in range(-3, 0))
print(res[nums[-1]])

# nums.insert(0, 0)
# res = [1]
# for i, x in enumerate(nums[1:], 1):
#     res.append(sum(res[i + d] for d in range(-3, 0) if i + d >= 0 and x - nums[i + d] <= 3))
# print(res[-1])
