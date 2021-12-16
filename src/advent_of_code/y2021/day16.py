from math import prod

OPS = {
    0: sum,
    1: prod,
    2: min,
    3: max,
    5: lambda args: args[0] > args[1],
    6: lambda args: args[0] < args[1],
    7: lambda args: args[0] == args[1],
}

def solve(s, i=0, vsum=0):
    def read(n):
        nonlocal i
        i += n
        return s[i - n:i]

    def read_int(n):
        return int(read(n), 2)

    vsum += read_int(3)
    type_id = read_int(3)
    if type_id == 4:
        parts = []
        done = False
        while not done:
            done = read(1) == "0"
            parts.append(read(4))
        res = int("".join(parts), 2)
    else:
        args = []
        if read_int(1):
            for _ in range(read_int(11)):
                res1, vsum, i = solve(s, i, vsum)
                args.append(res1)
        else:
            end = read_int(15) + i
            while i < end:
                res1, vsum, i = solve(s, i, vsum)
                args.append(res1)
        res = int(OPS[type_id](args))

    return res, vsum, i


IN = open("resources/inputs/2021/day16.txt").read().strip()
res, vsum, _ = solve("".join(f"{int(h, 16):04b}" for h in IN))
print(res)
print(vsum)
