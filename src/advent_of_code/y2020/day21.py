all_ingredients = []
candidates = {}
for line in open("resources/inputs/2020/day21.txt").read().splitlines():
    ingredients, allergens = line.split(" (contains ")
    ingredients = ingredients.split()
    all_ingredients.extend(ingredients)
    for a in allergens[:-1].split(", "):
        if a in candidates:
            candidates[a] &= set(ingredients)
        else:
            candidates[a] = set(ingredients)

while not all(len(cs) == 1 for cs in candidates.values()):
    for i, cs in candidates.items():
        if len(cs) == 1:
            for j, cs2 in candidates.items():
                if i != j:
                    cs2 -= cs

bad = set.union(*candidates.values())
print(sum(i not in bad for i in all_ingredients))
print(",".join(candidates[k].pop() for k in sorted(candidates)))
