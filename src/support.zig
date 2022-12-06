const std = @import("std");


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