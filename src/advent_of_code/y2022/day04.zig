const std = @import("std");
const root = @import("../main.zig");
const Result = root.Result;

pub const Solution = root.Solution{ .year = 2022, .day = 4, .run = run };

fn run(input: []const u8) !Result {
    var part1: u32 = 0;
    var part2: u32 = 0;
    var lines = std.mem.split(u8, input, "\n");
    while (lines.next()) |line| {
        if (line.len == 0) continue;

        var tokens = std.mem.tokenize(u8, line, "-,");
        const a = try std.fmt.parseInt(u32, tokens.next().?, 10);
        const b = try std.fmt.parseInt(u32, tokens.next().?, 10);
        const c = try std.fmt.parseInt(u32, tokens.next().?, 10);
        const d = try std.fmt.parseInt(u32, tokens.next().?, 10);
        const r1 = Range{ .start = a, .end = b };
        const r2 = Range{ .start = c, .end = d };

        if (r1.includes(r2) or r2.includes(r1))
            part1 += 1;

        if (r1.overlaps(r2))
            part2 += 1;
    }

    return Result.of(part1, part2);
}

const Range = struct {
    start: u32,
    end: u32,

    inline fn includes(self: Range, other: Range) bool {
        return self.start <= other.start and other.end <= self.end;
    }

    inline fn overlaps(self: Range, other: Range) bool {
        return self.start <= other.end and other.start <= self.end;
    }
};

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
    try std.testing.expectEqual(@as(u64, 2), res.part1.int);
    try std.testing.expectEqual(@as(u64, 4), res.part2.int);
}
