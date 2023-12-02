import re

DIGITS = [r"\d", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"]
DR = rf"(?=({'|'.join(DIGITS)}))"
F = open("resources/inputs/2023/day01.txt").read().splitlines()


def get_digit(s: str):
    return int(s) if s.isdigit() else DIGITS.index(s)


res1 = 0
res2 = 0
for line in F:
    digits = re.findall(r"\d", line)
    res1 += int(digits[0] + digits[-1])

    str_digits = re.findall(DR, line)
    a = get_digit(str_digits[0])
    b = get_digit(str_digits[-1])
    res2 += a * 10 + b

print(res1)
print(res2)
assert res1 == 54304
assert res2 == 54418
