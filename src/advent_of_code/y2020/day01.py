nums = set(map(int, open("resources/inputs/2020/day01.txt")))

print(next(x * (2020 - x) for x in nums if 2020 - x in nums))
print(next(x * y * (2020 - x - y) for x in nums for y in nums if 2020 - x - y in nums))
