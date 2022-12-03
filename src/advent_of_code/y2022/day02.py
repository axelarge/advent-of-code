F = open("resources/inputs/2022/day02.txt").read().splitlines()

scores = {
    "A X": 1 + 3,
    "A Y": 2 + 6,
    "A Z": 3 + 0,
    "B X": 1 + 0,
    "B Y": 2 + 3,
    "B Z": 3 + 6,
    "C X": 1 + 6,
    "C Y": 2 + 0,
    "C Z": 3 + 3,
}

move = {
    "A X": "Z",
    "A Y": "X",
    "A Z": "Y",
    "B X": "X",
    "B Y": "Y",
    "B Z": "Z",
    "C X": "Y",
    "C Y": "Z",
    "C Z": "X",
}

print(sum(map(scores.get, F)))
print(sum(scores[l[:2] + move[l]] for l in F))
