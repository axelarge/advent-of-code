from math import prod

TYPES = {
    0: "SUM",
    1: "PRODUCT",
    2: "MINIMUM",
    3: "MAXIMUM",
    4: "LITERAL",
    5: "GREATER",
    6: "LESS",
    7: "EQUAL",
}

OPS = {
    0: sum,
    1: prod,
    2: min,
    3: max,
    5: lambda args: args[0] > args[1],
    6: lambda args: args[0] < args[1],
    7: lambda args: args[0] == args[1],
}


def solve(s, i=0, level=0):
    def debug(msg):
        print(f"{'    ' * level}L{level} {msg}")

    def read(n):
        nonlocal i
        chunk = s[i:i + n]
        debug(f"  {i:3}: read({n}) = {chunk}, rem: {s[i + n:i + n + 20]}{'...' if len(s) > i + n + 20 else ''}")
        i += n
        return chunk

    def read_int(n):
        num = int(read(n), 2)
        debug(f"  = {num}")
        return num

    def read_literal():
        parts = []
        done = False
        while not done:
            done = read(1) == "0"
            parts.append(read(4))
        num = int("".join(parts), 2)
        debug(f"= {num} {parts}")
        return num

    debug(f"PACKET {s[:20]}{'...' if len(s) > 20 else ''} ({len(s)})")

    version_ids = [read_int(3)]
    debug(f"VID {version_ids[-1]}")

    type_id = read_int(3)
    debug(f"TID {type_id} ({TYPES.get(type_id)})")
    if type_id == 4:
        res = read_literal()
    else:
        len_type_id = read_int(1)
        debug(f"len type id = {len_type_id}")
        args = []
        if len_type_id == 0:
            end = read_int(15) + i
            while i < end:
                res1, vnums1, i = solve(s, i, level + 1)
                debug(f"---")
                args.append(res1)
                version_ids.extend(vnums1)
        else:
            for _ in range(read_int(11)):
                res1, vnums1, i = solve(s, i, level + 1)
                debug(f"---")
                args.append(res1)
                version_ids.extend(vnums1)
        res = int(OPS[type_id](args))
        debug(f"{TYPES[type_id]}{args}")
        debug(f"= {res}")
    return res, version_ids, i


IN = open("resources/inputs/2021/day16.txt").read().strip()
# IN = "D2FE28"
# IN = "38006F45291200"
# IN = "EE00D40C823060"
# IN = "8A004A801A8002F478"
# IN = "C200B40A82"
print(IN)
res, version_ids, _ = solve("".join(f"{int(h, 16):04b}" for h in IN))
print(res)
print(sum(version_ids))
