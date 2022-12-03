use std::collections::BinaryHeap;

pub fn run(input: String) {
    let sums = BinaryHeap::from_iter(input.split("\n\n").map(|chunk| {
        chunk
            .lines()
            .map(|line| line.parse::<i32>().unwrap())
            .sum::<i32>()
    }));
    println!("{}", sums.peek().unwrap());
    println!("{}", sums.into_iter_sorted().take(3).sum::<i32>());
}

// pub fn run(input: String) {
//     let mut sums = BinaryHeap::new();
//     let mut sum = 0;
//     input.lines().for_each(|line| {
//         if line.len() > 0 {
//             sum += line.parse::<i32>().unwrap();
//         } else {
//             sums.push(sum);
//             sum = 0;
//         }
//     });
//     if sum != 0 {
//         sums.push(sum);
//     }

//     println!("{}", sums.peek().unwrap());
//     println!("{}", (0..3).map(|_| sums.pop().unwrap()).sum::<i32>());
// }
