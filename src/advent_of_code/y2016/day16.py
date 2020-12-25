def solve(data, n):
    while len(data) < n:
        data = data + "0" + data[::-1].translate(str.maketrans("01", "10"))
    data = data[:n]
    while len(data) % 2 == 0:
        data = [str(int(data[i] == data[i + 1])) for i in range(0, len(data), 2)]
    return "".join(data)

seed = open("resources/inputs/2016/day16.txt").read().strip()
print(solve(seed, 272))
print(solve(seed, 35651584))
