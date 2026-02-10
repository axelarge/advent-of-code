import re

F = open("resources/inputs/2023/day12.txt").read().splitlines()
lines = []
for line in F:
    row, nums = line.split()
    # lines.append(["?".join([row]*5), list(map(int, nums.split(",")))*5])  # part 2 (too slow)
    lines.append([row, list(map(int, nums.split(",")))])


def solve(row, nums, need=None):
    if not row:
        return not nums and (need is None or need == 0)
    c = row[0]
    if c == ".":
        if need is None or need == 0:
            return solve(row[1:], nums, None)
        else:
            return 0
    if c == "#":
        if need is None:
            if nums:
                return solve(row, nums[1:], nums[0])
            else:
                return 0
        elif need > 0:
            return solve(row[1:], nums, need - 1)
        else:
            return 0
    if c == "?":
        res = 0
        if need is None or need > 0:
            res += solve(["#", *row[1:]], nums, need)
        if need is None or need == 0:
            res += solve([".", *row[1:]], nums, need)
        return res
    raise f"Reached end with row |{row}| nums {nums}"


res1 = sum(solve(row, nums) for row, nums in lines)
print(res1)
#  75433 high
# 121026
