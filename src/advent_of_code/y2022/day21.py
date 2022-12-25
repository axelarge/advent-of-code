from operator import add, sub, mul, floordiv

F = open("resources/inputs/2022/day21.txt").read().splitlines()
NUMS = {}
DEPS = {}
OPS = {"+": add, "-": sub, "*": mul, "/": floordiv}
for line in F:
    name, *words = line.split()
    name = name[:-1]
    if len(words) == 1:
        NUMS[name] = int(words[0])
    else:
        DEPS[name] = words


def simplify(name, unknown=None):
    if name == unknown:
        return "X"
    if name in NUMS:
        return NUMS[name]
    else:
        a, op, b = DEPS[name]
        a = simplify(a, unknown)
        b = simplify(b, unknown)
        if type(a) == int and type(b) == int:
            return OPS[op](a, b)
        else:
            return a, op, b


def must_eq(expr, target):
    if type(expr) == int and type(target) != int:
        return must_eq(target, expr)
    if expr == "X":
        return target
    a, op, b = expr
    match op, type(a) == int:
        case "+", 1: return must_eq(b, target - a)
        case "+", 0: return must_eq(a, target - b)
        case "-", 1: return must_eq(b, a - target)
        case "-", 0: return must_eq(a, target + b)
        case "*", 1: return must_eq(b, target // a)
        case "*", 0: return must_eq(a, target // b)
        case "/", 1: return must_eq(b, a / target)
        case "/", 0: return must_eq(a, target * b)


part1 = simplify("root")
print(part1)
assert part1 == 54703080378102

left, _, right = DEPS["root"]
part2 = must_eq(simplify(left, "humn"), simplify(right, "humn"))
print(part2)
assert part2 == 3952673930912
