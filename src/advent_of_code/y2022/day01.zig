const std = @import("std");
const root = @import("../main.zig");
const Result = root.Result;

pub const Solution = root.Solution{ .year = 2022, .day = 1, .run = run };

fn run(input: []const u8) !Result {
    var max = [_]i32{0} ** 3;
    var chunks = std.mem.split(u8, input, "\n\n");
    while (chunks.next()) |chunk| {
        var sum: i32 = 0;
        var lines = std.mem.split(u8, chunk, "\n");
        while (lines.next()) |line| {
            if (line.len > 0) {
                sum += try std.fmt.parseInt(i32, line, 10);
            }
        }

        var i: usize = 0;
        while (i < max.len) : (i += 1) {
            if (sum < max[i]) continue;
            const tmp = max[i];
            max[i] = sum;
            sum = tmp;
        }
    }

    return Result.of(max[0], max[0] + max[1] + max[2]);
}

fn compareFn(context: void, a: i32, b: i32) std.math.Order {
    _ = context;
    return std.math.order(b, a);
}

test "2022.01 sample" {
    const data =
        \\1000
        \\2000
        \\3000
        \\
        \\4000
        \\
        \\5000
        \\6000
        \\
        \\7000
        \\8000
        \\9000
        \\
        \\10000
    ;

    var res = try run(data);
    try std.testing.expectEqual(@as(i32, 24000), res.part1.int);
    try std.testing.expectEqual(@as(i32, 45000), res.part2.int);
}
