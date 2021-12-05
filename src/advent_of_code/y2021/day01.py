lines = list(map(int, open("resources/inputs/2021/day01.txt")))

def increases(xs):
    return sum(b > a for a, b in zip(xs, xs[1:]))

print(increases(lines))
print(increases(list(map(sum, zip(lines, lines[1:], lines[2:])))))
