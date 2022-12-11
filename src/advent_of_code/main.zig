const std = @import("std");
const assert = std.debug.assert;

pub const support = @import("support.zig");
const ResultType = support.ResultType;
const ResultVal = support.ResultVal;
pub const Result = support.Result;
pub const Solution = support.Solution;

const Timer = std.time.Timer;
const stderr = std.io.getStdErr().writer();
var bw = std.io.bufferedWriter(std.io.getStdOut().writer());
const stdout = bw.writer();

var resultsBuf: [2048]u8 = undefined;
var fba = std.heap.FixedBufferAllocator.init(&resultsBuf);
pub const resultAllocator = fba.allocator();

pub fn main() !void {
    var arena = std.heap.ArenaAllocator.init(std.heap.page_allocator);
    defer arena.deinit();
    const allocator = arena.allocator();

    defer bw.flush() catch {};

    const args = try std.process.argsAlloc(allocator);
    if (args.len == 3) {
        const year = try std.fmt.parseInt(u32, args[1], 10);
        const day = try std.fmt.parseInt(u32, args[2], 10);
        const solution = getSolution(year, day) orelse {
            try stderr.print("No implementation for year {} day {}\n", .{ year, day });
            return error.NoImplError;
        };
        try runSolution(allocator, solution);
    } else if (args.len == 2 and std.mem.eql(u8, args[1][0..], "all")) {
        var timer = try Timer.start();
        for (solutions) |solution| {
            try runSolution(allocator, solution);
            try stdout.print("\n", .{});
        }
        const elapsed = timer.read() / std.time.ns_per_us;
        try stdout.print("Total time: {}µs\n", .{elapsed});
    } else {
        try stderr.print("Usage: main [year] [day] or main all\n", .{});
    }
}

fn getSolution(year: u32, day: u32) ?Solution {
    for (solutions) |solution| {
        if (solution.year == year and solution.day == day)
            return solution;
    }
    return null;
}

fn runSolution(allocator: std.mem.Allocator, solution: Solution) !void {
    const year = solution.year;
    const day = solution.day;

    var timer = try Timer.start();
    var buf: [32]u8 = undefined;
    var fileName = try std.fmt.bufPrint(&buf, "resources/inputs/{}/day{:0>2}.txt", .{ year, day });
    const inputFile = std.fs.cwd().openFile(fileName, .{}) catch |err| {
        try stderr.print("Could not open file {s}\n", .{fileName});
        return err;
    };
    defer inputFile.close();

    const content = try inputFile.readToEndAlloc(allocator, std.math.maxInt(usize));
    var readTime = timer.lap();
    readTime -= readTime % std.time.ns_per_us;

    const res = try solution.run(content);
    var runTime = timer.read();
    runTime -= runTime % std.time.ns_per_us;

    try stdout.print("Year {} day {}\n", .{ year, day });
    try printResult(res.part1, 1);
    try printResult(res.part2, 2);
    try stdout.print("Took {} + {} = {} to read / run\n", .{
        std.fmt.fmtDuration(readTime),
        std.fmt.fmtDuration(runTime),
        std.fmt.fmtDuration(readTime + runTime),
    });
}

fn printResult(result: ResultVal, part: usize) !void {
    switch (result) {
        ResultType.int => try stdout.print("Part {}: {}\n", .{ part, result.int }),
        ResultType.str => try stdout.print("Part {}: {s}\n", .{ part, result.str }),
    }
}

const y2022 = @import("y2022/y2022.zig");
const solutions = [_]Solution{
    y2022.day01.Solution,
    y2022.day03.Solution,
    y2022.day04.Solution,
    y2022.day05.Solution,
    y2022.day06.Solution,
};

test {
    std.testing.refAllDecls(@This());
}
