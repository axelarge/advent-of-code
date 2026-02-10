import re

IN = open("resources/inputs/2021/day24.txt").read()

IN1 = """inp x
mul x -1"""

# IN = IN1

print(IN)


def parse_arg(arg):
    if re.match(r"-?\d+", arg):
        return int(arg)
    return arg


parsed = []
for line in IN.splitlines():
    ins, *args = line.split()
    # print(f" ins: {ins} args: {args}")
    parsed.append([ins] + list(map(parse_arg, args)))
print(parsed)

def run(raw_input):
    R = dict(w=0, x=0, y=0, z=0)
    inp = iter(str(raw_input))

    # print(f" running with |{raw_input}|")
    # print(f" >{next(inp)}")
    # print(f" >{next(inp)}")

    def arg(n):
        a = args[n]
        if isinstance(a, int):
            return a
        else:
            return R[a]

    for ins, *args in parsed:
        # print(f" ins: {ins} {args}")
        if ins == "inp":
            R[args[0]] = int(next(inp))
        elif ins == "add":
            R[args[0]] += arg(1)
        elif ins == "mul":
            R[args[0]] *= arg(1)
        elif ins == "div":
            R[args[0]] //= arg(1)
        elif ins == "mod":
            R[args[0]] %= arg(1)
        elif ins == "eql":
            R[args[0]] = int(R[args[0]] == arg(1))
        else:
            assert False, f"Unknown instr {ins}"
    return R



def compile():
    code = ["w, x, y, z = 0, 0, 0, 0"]

    def arg(n):
        a = args[n]
        if isinstance(a, int):
            return a
        else:
            return a
            return f"'{a}'"
            return f"R['{a}']"

    for ins, *args in parsed:
        if ins == "inp":
            code.append(f"{args[0]} = int(next(inp))")
            # code.append(f"R[{args[0]}] = int(next(inp))")
        elif ins == "add":
            code.append(f"{args[0]} += {arg(1)}")
            # code.append(f"R[{args[0]}] += {arg(1)}")
            # R[args[0]] += arg(1)
        elif ins == "mul":
            code.append(f"{args[0]} *= {arg(1)}")
            # code.append(f"R[{args[0]}] *= {arg(1)}")
            # R[args[0]] *= arg(1)
        elif ins == "div":
            code.append(f"{args[0]} //= {arg(1)}")
            # code.append(f"R[{args[0]}] //= {arg(1)}")
            # R[args[0]] //= arg(1)
        elif ins == "mod":
            code.append(f"{args[0]} %= {arg(1)}")
            # code.append(f"R[{args[0]}] %= {arg(1)}")
            # R[args[0]] %= arg(1)
        elif ins == "eql":
            code.append(f"{args[0]} = int({arg(0)} == {arg(1)})")
            # code.append(f"R[{args[0]}] = int({arg(0)} == {arg(1)})")
        else:
            assert False, f"Unknown instr {ins}"

    code.append("return w, x, y, z")
    return "\n".join(code)

print(compile())


R = run(13579246899999)
valid = R["z"] == 0
print(R)
print(valid)
#  READ THE PROBLEM!

# #
# best = 0
# best_r = None
# for i in range(int("9"*13)+1, int("9"*14)+1):
#     if i % 1000 == 0:
#         print(f"...{i}")
#     ret = run(i)
#     if ret["z"] == 0:
#         if i > best:
#             best = i
#             best_r = ret
#
# print(best_r)
# print(best)
