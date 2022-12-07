const std = @import("std");
const root = @import("../../main.zig");
const Result = root.Result;
const BitSet = @import("../../support.zig").BitSet(u32);

pub const Solution = root.Solution{ .year = 2022, .day = 6, .run = run };

fn run(input: []const u8) !Result {
    const part1: i32 = find(input[0 .. input.len - 1], 4);
    const part2: i32 = find(input[0 .. input.len - 1], 14);
    return Result.of(part1, part2);
}

fn find(input: []const u8, n: u8) i32 {
    var i: usize = 0;
    while (i < input.len - n) : (i += 1) {
        var bits = BitSet.empty();
        for (input[i .. i + n]) |ch| {
            bits.add(@truncate(u5, ch - 'a'));
        }
        if (@popCount(bits.bits) == n)
            return @intCast(i32, i + n);
    }
    return -1;
}

test {
    const eql = std.testing.expectEqual;
    try eql(Result.of(7, 19), try run("mjqjpqmgbljsphdztnvjfqwrcgsmlb"));
    try eql(Result.of(7, 19), try run("mjqjpqmgbljsphdztnvjfqwrcgsmlb"));
    try eql(Result.of(5, 23), try run("bvwbjplbgvbhsrlpgdmjqwftvncz"));
    try eql(Result.of(6, 23), try run("nppdvjthqldpwncqszvftbrmjlhg"));
    try eql(Result.of(10, 29), try run("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"));
    try eql(Result.of(11, 26), try run("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"));
}
