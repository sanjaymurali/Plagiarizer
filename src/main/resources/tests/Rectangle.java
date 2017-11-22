package core.algorithm.lcs;

/*****
 * This is a class to represent a rectangle shape.
 * Used in testing Normalization
 */
class Rectangle {

    //constructor
    public Rectangle(int height, int width) {
        this.height = height;
        this.width = width;
    }


    //gets area of rectangle
    public int area() {
        return height * width;
    }

    private void someFunction() {
        int j;
        for (int i = 0; i < 100; i++) {
            //inside of some loop
            j = i;
        }
    }

    static int someOtherFunction() {
        // height and width should not be removed
        return 100;
    }

    public void rectangle(int i) {
        i = 5000;
    }


    private int height; /* the rectangle's height */
    private int width; /* the rectangle's width */
}