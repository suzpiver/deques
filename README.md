# Deques

A **deque** (pronounced "deck") is an abstract data type representing a **d**ouble-**e**nded **que**ue. Deques are linear collections (like lists, stacks, and queues) optimized for accessing, adding, and removing items from both the front and the back. Deques differ from lists in that they do not allow items to be added or removed from anywhere except for the front or the back. This restriction might make it seem like deques are much less useful than lists. Indeed, any problem you can solve using a deque you can also solve using a list!

Computer scientists invented abstract data types like deques to allow data structure implementations to be more efficient. We'll soon see that deques provide a foundation for applications like browser history where we might want to add to the back (visit a web page), remove from the back (clear pages from the last hour), and remove from the front (clear pages from over 3 months ago).

## Deque interface

Interfaces are a useful way to indicate common methods provided by all implementations (Java classes). For example, `List` is an interface with implementations such as `ArrayList` and `LinkedList`. Deques are like lists but without the capability to add or remove items from anywhere but the front or the back. For testing purposes, however, there is also a method that allows access to any element in the deque. Specifically, any `Deque` implementation must have the following methods:

- `void addFirst(T item)`. Adds an item of type `T` to the front of the deque.
- `void addLast(T item)`. Adds an item of type `T` to the back of the deque.
- `T get(int index)`. Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
- `boolean isEmpty()`. Returns true if deque is empty, false otherwise.
- `T removeFirst()`. Removes and returns the item at the front of the deque.
- `T removeLast()`. Removes and returns the item at the back of the deque.
- `int size()`. Returns the number of items in the deque.

## Design and implement

Unlike your prior programming courses, the focus of this course is not only to build programs that work according to specifications but also to compare different approaches and evaluate the consequences of our designs. In this project, we'll compare three different ways to implement the `Deque` interface: `ArrayDeque`, `LinkedDeque`, and `ArrayListDeque`.

### ArrayDeque

> ✨ An [**ArrayDeque**](https://docs.google.com/presentation/d/1c9RdR7fz-CyTH9bHzJ5bhlfmlUHgpC-EK9d3a8PMiuo/edit?usp=sharing)[^1] is like the array-based list data structures that you've learned before, but different in that elements aren't necessarily stored starting at index 0. Instead, their start and end positions are determined by two fields called `front` and `back`.

[^1]: Josh Hug. 2019. cs61b sp19 proj1 slides. In CS 61B: Data Structures, Spring 2019. <https://docs.google.com/presentation/d/1XBJOht0xWz1tEvLuvOL4lOIaY0NSfArXAvqgkrx0zpc/edit>

We've provided a *nearly*-working `ArrayDeque` class that is intentionally buggy, and a failing test case that causes the bug to emerge. Your task is to fix the bug in the `ArrayDeque` class. (This should only involve changing **at most 5 lines of code**.)

Follow the debugging cycle to address the bug.

1. Skim the `ArrayDeque` code to see how its methods and fields work together to implement the `Deque` interface.
1. Run the `ArrayDequeTests` class inside the `test/deques` folder.
1. Read the test result description and review the stack trace (the chain of calls that caused the exception).
1. Read through `ArrayDeque` again, this time focusing on methods most relevant to the failing tests. You can open the `DequeTests` file and [drag the tab for a side-by-side view](https://www.jetbrains.com/idea/guide/tips/drag-and-dock/) with your `ArrayDeque` code.
1. Based on what you now know about the bug, develop a hypothesis for what could have caused the problem.

> For example, we might *hypothesize* that the way that the `newIndex` variable inside the `resize` method is going outside the bounds of the `newData` array. Knowing the answer to this question might help us zero-in on the problem, leading us to generate another hypothesis that provides a more direct line of reasoning about the line of code that caused `newIndex` to grow too large.

To verify a hypothesis, we can add **debugging print statements** to show the current value of a field or a variable in the program. Using your debugging print statements, can you piece together what's causing the problem? It may or may not be easy because the `confusingTest` has so much packed into it. We can write a simpler **unit test** that can make things easier to debug by adding another test method to the `DequeTests` class.

A unit test is a method designed to verify that a single piece of functionality (like a single method in the `ArrayDeque` class) works as expected. In this project, we're using the JUnit framework, which allows you to define unit tests using an `@Test` annotation as seen in the `DequeTests` class. Try following the examples in the `DequeTests` class to define a unit test of your own that can reproduce the problem in `confusingTest` in as few lines as possible. Writing smaller tests can help us keep track of all the important details needed to debug a program.

> ✨ Identifying the root cause is one of the most important parts of debugging. The fix to a bug often leads directly from an explanation about the problem. It's easy to lose track of time and get stuck in a deep hole when debugging. Come to office hours, chat with other students, and return after taking a break!

Once you've identified the root cause, make a change that will address it. Check your work by running the test again. If there are more issues, you can go back and generate another hypothesis. This process can take a while: even professional programmers spend a lot of time debugging. The advantage of experience is that you're able to more rapidly generate new hypotheses and eliminate hypotheses that wouldn't work without having to test them by editing the code.

After you've implemented a fix, make sure that it also works with this alternative sequence of tricky removes.

```java
// Test a tricky sequence of removes
assertEquals(5, deque.removeLast());
assertEquals(4, deque.removeLast());
assertEquals(3, deque.removeLast());
assertEquals(2, deque.removeLast());
assertEquals(1, deque.removeLast());

// TODO ArrayDeque fails here; write better tests to help you find and fix the bug
int actual = deque.removeLast();
assertEquals(0, actual);
```

### LinkedDeque

Implement the `LinkedDeque` class with the following additional requirements:

1. The methods `addFirst`, `addLast`, `removeFirst`, and `removeLast` must run in constant time with respect to the size of the deque. To achieve this, don't use any iteration or recursion.
1. The amount of memory used by the deque must always be proportional to its size. If a client adds 10,000 items and then removes 9,999 items, the resulting deque should use about the same amount of memory as a deque where we only ever added 1 item. To achieve this, remove references to items that are no longer in the deque.
1. The class is implemented with the help of **sentinel nodes** according to the following **invariants**, or implementation requirements that must be true before and after any of the data structure's operations. Use the doubly-linked `Node` class defined at the bottom of the `LinkedDeque.java` file.

> ✨ A [**sentinel node**](https://docs.google.com/presentation/d/1qNaYV6fq-ARyhMGnY5-HJXHG1srNf3R5uofhYiJ80Y0/edit?usp=sharing)[^2] is a special node in a linked data structure that doesn't contain any meaningful data and is always present in the data structure, even when it's empty. Because we no longer need to check if the current node is null before accessing it, we can simplify the number of conditions that are needed to implement `LinkedDeque` methods.

[^2]: Josh Hug. 2019. cs61b lec5 2019 lists3, dllists and arrays. In CS 61B: Data Structures, Spring 2019. <https://docs.google.com/presentation/d/1nRGXdApMS7yVqs04MRGZ62dZ9SoZLzrxqvX462G2UbA/edit>

A `LinkedDeque` should always maintain the following invariants before and after each method call:

- The `front` field always references the front sentinel node, and the `back` field always references the back sentinel node.
- The sentinel nodes `front.prev` and `back.next` always reference null. If `size` is at least 1, `front.next` and `back.prev` reference the first and last regular nodes.
- The nodes in your deque have consistent `next` and `prev` fields. If a node `curr` has a `curr.next`, we expect `curr.next.prev == curr`.

> ✨ Write down what your `LinkedDeque` will look like on paper before writing code! Drawing more pictures often leads to more successful implementations. Better yet, if you can find a willing partner, have them give some instructions while you attempt to draw everything out. Be sure to think carefully about what happens if the data structure starts empty, some items are added, all the items are removed, and then some items are added again.

To assist in debugging, we've provided a `checkInvariants` method that returns null if and only if the above invariants are maintained (at the time the method is called), or a string describing the problem. One way to use this method is to add debugging print statements where you *hypothesize* a bug might be present. But it can be tedious editing code, moving the line around, and then running it again just to call `checkInvariants` at a different place. A better way is by [Using **Evaluate Expression** and **Watches** with IntelliJ](https://youtu.be/u5NSgMCkqOg). This allows you to pause the program at any point in time and call `checkInvariants()` or even visualize the `LinkedDeque` using the [jGRASP](https://plugins.jetbrains.com/plugin/12769-jgrasp) or [Java Visualizer](https://plugins.jetbrains.com/plugin/11512-java-visualizer) plugins.

Lastly, if your first try goes badly, don't be afraid to scrap your code and start over. My solution adds between 4 to 6 lines of code per method.

### ArrayListDeque

Finally, define a new class called `ArrayListDeque` that implements the `Deque` interface. This class should maintain a single field:

```java
private final ArrayList<T> list;
```

All `Deque` methods are implemented by *delegating* the work to the underlying `ArrayList`. Unlike `ArrayDeque`, which required lots of code to move data around the array, the methods in `ArrayListDeque` only need to call `ArrayList` methods using **at most 4 lines of code** per method.

As you implement `ArrayListDeque`, write a test class `ArrayListDequeTests` following the same pattern as `ArrayDequeTests` and `LinkedDequeTests`, and check that your `ArrayListDeque` also works!

## Analyze and compare

### Asymptotic analysis

> For the following asymptotic analysis questions, we'll ignore the `get` method because we introduced it primarily as a convenience method for testing purposes.

*Most of the time*, the order of growth of the runtime for `ArrayDeque` methods is constant with respect to the size of the deque. Perform a **case analysis** to explain the case(s) when the runtime is constant and the case(s) when the runtime is not constant.

`ArrayListDeque` required much less code to implement than `ArrayDeque`. In computer science, simpler solutions are typically preferred over more complicated solutions because they're less likely to contain subtle bugs. Give an argument based on asymptotic analysis about why we might prefer the more complicated `ArrayDeque` class over the simpler `ArrayListDeque` class.

### Experimental analysis

At the bottom of the `DequeTests` class, you'll find a nested class called `RuntimeExperiments` that is annotated `@Disabled`. Remove the `@Disabled` annotation and re-run all the deque tests to record the time it takes to repeatedly call `addLast` and `get` (on the middle element) for each implementation. Once the runtimes have been generated, copy and paste each result into its own [Desmos graphing calculator](https://www.desmos.com/calculator) to plot all the points. For each plot, [calculate a line of best fit using Desmos](https://youtu.be/ADaNyIf6NhY) for the time it takes to call `addLast` and for the time it takes to `get` the middle element.

Finally, let's use experimental analysis to support your earlier argument for `ArrayDeque` over `ArrayListDeque`. Modify the `RuntimeExperiments` class so that it presents the differences you hypothesized would exist in asymptotic analysis, and re-run the tests to generate the plots to confirm that `ArrayDeque` is more efficient than `ArrayListDeque` on certain operations.
