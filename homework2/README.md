In this assignment, Nicholas and I started with expandable array stack. We first check if the stack is full when pushing an element to the stack. If it is full, a new array with double the original size is created, and all the items are copied. When popping an element from the stack, we checked if the stack is empty and the size of the array to remove the extra spaces that are not needed.

For expandable array queue, when enqueuing an element, we checked if the array size is big enough. If it is not, a new array with double the original size is created, and elements are copied to the new array in two steps. The first step is the head to the end of the array, and the second step is the rest of the elements. When dequeuing an element, the same method is used to create a smaller array, and elements are copied to the new smaller queue.