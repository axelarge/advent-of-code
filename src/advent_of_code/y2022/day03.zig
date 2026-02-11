const std = @import("std");
const root = @import("../main.zig");
const Result = root.Result;
const BitSet = root.support.BitSet(u64);

pub const Solution = root.Solution{ .year = 2022, .day = 3, .run = run };

fn run(input: []const u8) !Result {
    return Result.of(part1(input), try part2(input));
}

fn part1(input: []const u8) u32 {
    var res: u32 = 0;
    var lines = std.mem.splitSequence(u8, input, "\n");
    while (lines.next()) |line| {
        if (line.len == 0) break;
        const len = line.len / 2;
        const idx = std.mem.indexOfAny(u8, line[0..len], line[len..]);
        res += value(line[idx.?]);
    }
    return res;
}

fn part2(input: []const u8) !u32 {
    var lines = std.mem.splitSequence(u8, input, "\n");
    var res: u32 = 0;
    while (lines.rest().len > 1) {
        var seen = BitSet.full();
        var i: u8 = 0;
        while (i < 3) : (i += 1) {
            var seenNow = BitSet.empty();
            for (lines.next().?) |c| {
                seenNow.add(value(c));
            }
            seen.intersect(seenNow);
        }
        res += @ctz(seen.bits);
    }
    return res;
}

fn value(c: u8) u6 {
    return switch (c) {
        'a'...'z' => @intCast(c - 'a' + 1),
        'A'...'Z' => @intCast(c - 'A' + 1 + 26),
        else => unreachable,
    };
}

test "2022.03 sample" {
    const sample =
        \\vJrwpWtwJgWrhcsFMMfFFhFp
        \\jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
        \\PmmdzqPrVvPwwTWBwg
        \\wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
        \\ttgJtRGJQctTZtZT
        \\CrZsJsPPZsGzwwsLwLmpwMDw
    ;

    const res = try run(sample);
    try std.testing.expectEqual(@as(u64, 157), res.part1.int);
    try std.testing.expectEqual(@as(u64, 70), res.part2.int);
}
