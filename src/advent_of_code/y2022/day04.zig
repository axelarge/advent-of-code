const std = @import("std");
const root = @import("../../main.zig");
const Result = root.Result;

pub fn run(input: []const u8) !Result {
    var part1: i32 = 0;
    var part2: i32 = 0;
    var lines = std.mem.split(u8, input, "\n");
    while (lines.next()) |line| {
        if (line.len == 0) continue;

        var x: [4]i32 = undefined;
        var tokens = std.mem.tokenize(u8, line, "-,");
        var i: usize = 0;
        while (i < 4) : (i += 1) {
            x[i] = try std.fmt.parseInt(i32, tokens.next().?, 10);
        }

        if (x[0] <= x[2] and x[3] <= x[1] or
            x[2] <= x[0] and x[1] <= x[3])
            part1 += 1;

        if ((x[0] <= x[2] and x[2] <= x[1]) or
            (x[0] <= x[3] and x[3] <= x[1]) or
            (x[2] <= x[0] and x[0] <= x[3]) or
            (x[2] <= x[1] and x[1] <= x[3]))
            part2 += 1;
    }

    return .{ .part1 = part1, .part2 = part2 };
}

test {
    const sample =
        \\2-4,6-8
        \\2-3,4-5
        \\5-7,7-9
        \\2-8,3-7
        \\6-6,4-6
        \\2-6,4-8
    ;
    const res = try run(sample);
    try std.testing.expectEqual(@as(i32, 2), res.part1);
    try std.testing.expectEqual(@as(i32, 4), res.part2);
}
