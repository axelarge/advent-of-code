data = [(l[:3], int(l[4:])) for l in open("resources/inputs/2020/day08.txt")]

def run(code):
    i = acc = 0
    seen = set()
    while i not in seen and i < len(code):
        seen.add(i)
        op, x = code[i]
        if op == "acc":
            acc += x
        elif op == "jmp":
            i += x - 1
        i += 1
    return i, acc

print(run(data)[1])

for i, (op, x) in enumerate(data):
    if op in ["nop", "jmp"]:
        code = data[:]
        code[i] = ("nop", x) if op == "jmp" else ("jmp", x)
        stop_i, acc = run(code)
        if stop_i == len(code):
            print(acc)
            break
