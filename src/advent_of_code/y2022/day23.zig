const std = @import("std");
const root = @import("../main.zig");
const Result = root.Result;

const XY = root.support.XY;
const Set = std.AutoHashMap(XY, void);

pub const Solution = root.Solution{ .year = 2022, .day = 23, .run = run };

fn run(input: []const u8) !Result {
    var arena = std.heap.ArenaAllocator.init(std.heap.page_allocator);
    defer arena.deinit();
    const allocator = arena.allocator();

    var points = try parse(input, allocator);
    var moves = std.AutoHashMap(XY, XY).init(allocator);
    var counter = std.AutoHashMap(XY, u8).init(allocator);
    var nextGen = Set.init(allocator);
    var part1: u64 = 0;
    var i: u32 = 0;
    var didMove = true;
    while (didMove or i < 10) : (i += 1) {
        didMove = false;
        counter.clearRetainingCapacity();
        nextGen.clearRetainingCapacity();
        moves.clearRetainingCapacity();
        {
            var iter = points.keyIterator();
            while (iter.next()) |pos| {
                if (findMove(pos.*, points, i)) |move| {
                    try counter.put(move, (counter.get(move) orelse 0) + 1);
                    try moves.put(pos.*, move);
                }
            }
        }
        {
            var iter = points.keyIterator();
            while (iter.next()) |pos| {
                if (moves.get(pos.*)) |move| {
                    if (counter.get(move).? == 1) {
                        try nextGen.put(move, {});
                        didMove = true;
                        continue;
                    }
                }
                try nextGen.put(pos.*, {});
            }
        }

        const tmp = points;
        points = nextGen;
        nextGen = tmp;

        if (i == 9) {
            part1 = @intCast(countEmpties(points));
        }
    }

    return Result.of(part1, i);
}

fn countEmpties(points: Set) i32 {
    var minx: i32 = std.math.maxInt(i32);
    var maxx: i32 = std.math.minInt(i32);
    var miny: i32 = std.math.maxInt(i32);
    var maxy: i32 = std.math.minInt(i32);
    var iter = points.keyIterator();
    while (iter.next()) |pos| {
        minx = @min(minx, pos.*.x);
        maxx = @max(maxx, pos.*.x);
        miny = @min(miny, pos.*.y);
        maxy = @max(maxy, pos.*.y);
    }
    return (maxx - minx + 1) * (maxy - miny + 1) - @as(i32, @intCast(points.count()));
}

fn parse(input: []const u8, allocator: std.mem.Allocator) !Set {
    var points = Set.init(allocator);
    var lines = std.mem.splitSequence(u8, input, "\n");
    var row: i32 = 0;
    while (lines.next()) |line| : (row += 1) {
        for (line, 0..) |ch, col| {
            if (ch == '#') {
                try points.put(XY.of(@intCast(col), row), {});
            }
        }
    }
    return points;
}

fn findMove(pos: XY, points: Set, offset: usize) ?XY {
    const N8 = [8]XY{ XY.of(-1, -1), XY.of(-1, 0), XY.of(-1, 1), XY.of(0, -1), XY.of(0, 1), XY.of(1, -1), XY.of(1, 0), XY.of(1, 1) };
    const moves = [4][4]XY{
        .{ XY.of(0, -1), XY.of(-1, -1), XY.of(0, -1), XY.of(1, -1) },
        .{ XY.of(0, 1), XY.of(-1, 1), XY.of(0, 1), XY.of(1, 1) },
        .{ XY.of(-1, 0), XY.of(-1, -1), XY.of(-1, 0), XY.of(-1, 1) },
        .{ XY.of(1, 0), XY.of(1, -1), XY.of(1, 0), XY.of(1, 1) },
    };

    if (allFree(pos, points, &N8)) {
        return null;
    }

    for (0..moves.len) |i| {
        const move = moves[(i + offset) % moves.len];
        if (allFree(pos, points, move[1..])) {
            return pos.addXY(move[0]);
        }
    }
    return pos;
}

fn allFree(pos: XY, points: Set, deltas: []const XY) bool {
    for (deltas) |delta| {
        if (points.contains(pos.addXY(delta))) return false;
    }
    return true;
}

test {
    const sample =
        \\..............
        \\..............
        \\.......#......
        \\.....###.#....
        \\...#...#.#....
        \\....#...##....
        \\...#.###......
        \\...##.#.##....
        \\....#..#......
        \\..............
        \\..............
        \\..............
    ;
    const eql = std.testing.expectEqual;
    try eql(Result.of(110, 20), try run(sample));
}
