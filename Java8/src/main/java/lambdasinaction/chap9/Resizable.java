package lambdasinaction.chap9;

public interface Resizable extends Drawable {
    int getWidth();

    void setWidth(int width);

    int getHeight();

    void setHeight(int height);

    void setAbsoluteSize(int width, int height);
    //TODO: uncomment, read the README for instructions
    //public void setRelativeSize(int widthFactor, int heightFactor);
}

