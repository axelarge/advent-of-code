import functools
import re
from math import prod


def max_geos(blueprint, time):
    _, ore_cost_ore, clay_cost_ore, obs_cost_ore, obs_cost_clay, geo_cost_ore, geo_cost_obs = blueprint
    max_ore_cost = max(ore_cost_ore, clay_cost_ore, obs_cost_ore, geo_cost_ore) + 1
    best_ever = 0

    @functools.cache
    def inner(t, ore, clay, obs, geo, ore_bots, clay_bots, obs_bots, geo_bots):
        nonlocal best_ever

        # can we even theoretically beat the best so far?
        if geo + geo_bots * t + (t * (t - 1)) // 2 <= best_ever:
            return 0

        if t == 1:
            best = geo + geo_bots
            best_ever = max(best, best_ever)
            return best

        # geo bot is best bot
        if ore >= geo_cost_ore and obs >= geo_cost_obs:
            return inner(t - 1,
                         ore + ore_bots - geo_cost_ore,
                         clay + clay_bots,
                         obs + obs_bots - geo_cost_obs,
                         geo + geo_bots,
                         ore_bots,
                         clay_bots,
                         obs_bots,
                         geo_bots + 1)

        if t == 2:
            best = geo + geo_bots + geo_bots
            best_ever = max(best, best_ever)
            return best

        best = 0
        # build ore bot
        if ore >= ore_cost_ore and ore_bots < max_ore_cost and t > 2:
            best = max(best, inner(t - 1,
                                   ore + ore_bots - ore_cost_ore,
                                   clay + clay_bots,
                                   obs + obs_bots,
                                   geo + geo_bots,
                                   ore_bots + 1,
                                   clay_bots,
                                   obs_bots,
                                   geo_bots))
        # build clay bot
        if ore >= clay_cost_ore and clay_bots < obs_cost_clay and t > 3:
            best = max(best, inner(t - 1,
                                   ore + ore_bots - clay_cost_ore,
                                   clay + clay_bots,
                                   obs + obs_bots,
                                   geo + geo_bots,
                                   ore_bots,
                                   clay_bots + 1,
                                   obs_bots,
                                   geo_bots))
        # build obsidian bot
        if ore >= obs_cost_ore and clay >= obs_cost_clay and obs_bots < geo_cost_obs and t > 2:
            best = max(best, inner(t - 1,
                                   ore + ore_bots - obs_cost_ore,
                                   clay + clay_bots - obs_cost_clay,
                                   obs + obs_bots,
                                   geo + geo_bots,
                                   ore_bots,
                                   clay_bots,
                                   obs_bots + 1,
                                   geo_bots))
        # wasn't able to check every type of bot yet, keep digging
        if ore < max_ore_cost:
            best = max(best, inner(t - 1,
                                   ore + ore_bots,
                                   clay + clay_bots,
                                   obs + obs_bots,
                                   geo + geo_bots,
                                   ore_bots,
                                   clay_bots,
                                   obs_bots,
                                   geo_bots))
        return best

    return inner(time, 0, 0, 0, 0, 1, 0, 0, 0)


F = open("resources/inputs/2022/day19.txt").read().splitlines()
blueprints = [list(map(int, re.findall(r"\d+", line))) for line in F]

part1 = sum(bp[0] * max_geos(bp, 24) for bp in blueprints)
print(part1)
assert part1 == 2301

part2 = prod(max_geos(bp, 32) for bp in blueprints[:3])
print(part2)
assert part2 == 10336
