def solve(data, n):
    while len(data) < n:
        data = data + "0" + data[::-1].translate(str.maketrans("01", "10"))
    chunk_size = n & -n
    return "".join(str(1 - data[i:i + chunk_size].count("1") % 2) for i in range(0, n, chunk_size))

seed = open("resources/inputs/2016/day16.txt").read().strip()
print(solve(seed, 272))
print(solve(seed, 35651584))
