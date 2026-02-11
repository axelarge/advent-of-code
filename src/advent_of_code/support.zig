const std = @import("std");

pub const Error = error{NoImplError};
pub const ResultType = enum { int, str };
pub const ResultVal = union(ResultType) {
    int: u64,
    str: []const u8,
};

pub const Result = struct {
    part1: ResultVal,
    part2: ResultVal,

    pub fn of(part1: u64, part2: u64) Result {
        return .{ .part1 = .{ .int = part1 }, .part2 = .{ .int = part2 } };
    }

    pub fn ofStrings(part1: []const u8, part2: []const u8) Result {
        return .{ .part1 = .{ .str = part1 }, .part2 = .{ .str = part2 } };
    }
};

pub const Solution = struct {
    year: u32,
    day: u32,
    run: *const fn ([]const u8) anyerror!Result,
};

pub fn BitSet(comptime T: type) type {
    return struct {
        bits: T = 0,

        const Self = @This();
        const V = std.math.Log2Int(T);

        pub inline fn empty() Self {
            return .{};
        }

        pub inline fn full() Self {
            return .{ .bits = ~@as(T, 0) };
        }

        pub inline fn add(self: *Self, val: V) void {
            self.bits |= @as(T, 1) << val;
        }

        pub inline fn contains(self: Self, val: V) bool {
            return self.bits & @as(T, 1) << val != 0;
        }

        pub inline fn intersect(self: *Self, other: Self) void {
            self.bits &= other.bits;
        }
    };
}

pub const XY = struct {
    x: i32,
    y: i32,

    pub inline fn of(x: i32, y: i32) XY {
        return .{ .x = x, .y = y };
    }

    pub inline fn add(self: XY, x: i32, y: i32) XY {
        return .{ .x = self.x + x, .y = self.y + y };
    }

    pub fn addXY(self: XY, other: XY) XY {
        return add(self, other.x, other.y);
    }
};

pub fn resizeZFill(comptime T: type, list: *std.ArrayList(T), gpa: std.mem.Allocator, n: usize) !void {
    if (list.items.len >= n) return;
    try list.appendNTimes(gpa, 0, n - list.items.len + 1);
}
