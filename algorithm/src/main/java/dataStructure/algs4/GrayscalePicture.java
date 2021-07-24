package dataStructure.algs4;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public final class GrayscalePicture implements ActionListener {
    private final int width, height;
    private BufferedImage image;
    private JFrame frame;
    private String filename;
    private boolean isOriginUpperLeft = true;


    public GrayscalePicture(int width, int height) {
        if (width < 0) throw new IllegalArgumentException("width must be non-negative");
        if (height < 0) throw new IllegalArgumentException("height must be non-negative");
        this.width = width;
        this.height = height;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }


    public GrayscalePicture(GrayscalePicture picture) {
        if (picture == null) throw new IllegalArgumentException("constructor argument is null");

        width = picture.width();
        height = picture.height();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        filename = picture.filename;
        isOriginUpperLeft = picture.isOriginUpperLeft;
        for (int col = 0; col < width(); col++)
            for (int row = 0; row < height(); row++)
                image.setRGB(col, row, picture.image.getRGB(col, row));
    }


    public GrayscalePicture(String filename) {
        if (filename == null) throw new IllegalArgumentException("constructor argument is null");
        this.filename = filename;
        try {

            File file = new File(filename);
            if (file.isFile()) {
                image = ImageIO.read(file);
            } else {
                URL url = getClass().getResource(filename);
                if (url == null) {
                    url = new URL(filename);
                }
                image = ImageIO.read(url);
            }

            if (image == null) {
                throw new IllegalArgumentException("could not read image file: " + filename);
            }

            width = image.getWidth(null);
            height = image.getHeight(null);


            for (int col = 0; col < width; col++) {
                for (int row = 0; row < height; row++) {
                    Color color = new Color(image.getRGB(col, row));
                    Color gray = toGray(color);
                    image.setRGB(col, row, gray.getRGB());
                }
            }
        } catch (IOException ioe) {
            throw new IllegalArgumentException("could not open image file: " + filename, ioe);
        }
    }


    private static Color toGray(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int y = (int) (Math.round(0.299 * r + 0.587 * g + 0.114 * b));
        return new Color(y, y, y);
    }

    public static void main(String[] args) {
        GrayscalePicture picture = new GrayscalePicture(args[0]);
        StdOut.printf("%d-by-%d\n", picture.width(), picture.height());
        GrayscalePicture copy = new GrayscalePicture(picture);
        picture.show();
        copy.show();
        while (!StdIn.isEmpty()) {
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            int gray = StdIn.readInt();
            picture.setGrayscale(row, col, gray);
            StdOut.println(picture.get(row, col));
            StdOut.println(picture.getGrayscale(row, col));
        }
    }

    public JLabel getJLabel() {
        if (image == null) return null;
        ImageIcon icon = new ImageIcon(image);
        return new JLabel(icon);
    }

    public void setOriginUpperLeft() {
        isOriginUpperLeft = true;
    }

    public void setOriginLowerLeft() {
        isOriginUpperLeft = false;
    }

    public void show() {


        if (frame == null) {
            frame = new JFrame();

            JMenuBar menuBar = new JMenuBar();
            JMenu menu = new JMenu("File");
            menuBar.add(menu);
            JMenuItem menuItem1 = new JMenuItem(" Save...   ");
            menuItem1.addActionListener(this);

            menuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                    Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            menu.add(menuItem1);
            frame.setJMenuBar(menuBar);


            frame.setContentPane(getJLabel());

            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            if (filename == null) frame.setTitle(width + "-by-" + height);
            else frame.setTitle(filename);
            frame.setResizable(false);
            frame.pack();
            frame.setVisible(true);
        }


        frame.repaint();
    }

    public int height() {
        return height;
    }

    public int width() {
        return width;
    }

    private void validateRowIndex(int row) {
        if (row < 0 || row >= height())
            throw new IllegalArgumentException("row index must be between 0 and " + (height() - 1) + ": " + row);
    }

    private void validateColumnIndex(int col) {
        if (col < 0 || col >= width())
            throw new IllegalArgumentException("column index must be between 0 and " + (width() - 1) + ": " + col);
    }

    private void validateGrayscaleValue(int gray) {
        if (gray < 0 || gray >= 256)
            throw new IllegalArgumentException("grayscale value must be between 0 and 255");
    }

    public Color get(int col, int row) {
        validateColumnIndex(col);
        validateRowIndex(row);
        Color color = new Color(image.getRGB(col, row));
        return toGray(color);
    }

    public int getGrayscale(int col, int row) {
        validateColumnIndex(col);
        validateRowIndex(row);
        if (isOriginUpperLeft) return image.getRGB(col, row) & 0xFF;
        else return image.getRGB(col, height - row - 1) & 0xFF;
    }

    public void set(int col, int row, Color color) {
        validateColumnIndex(col);
        validateRowIndex(row);
        if (color == null) throw new IllegalArgumentException("color argument is null");
        Color gray = toGray(color);
        image.setRGB(col, row, gray.getRGB());
    }

    public void setGrayscale(int col, int row, int gray) {
        validateColumnIndex(col);
        validateRowIndex(row);
        validateGrayscaleValue(gray);
        int rgb = gray | (gray << 8) | (gray << 16);
        if (isOriginUpperLeft) image.setRGB(col, row, rgb);
        else image.setRGB(col, height - row - 1, rgb);
    }

    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        GrayscalePicture that = (GrayscalePicture) other;
        if (this.width() != that.width()) return false;
        if (this.height() != that.height()) return false;
        for (int col = 0; col < width(); col++)
            for (int row = 0; row < height(); row++)
                if (this.getGrayscale(col, row) != that.getGrayscale(col, row)) return false;
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(width + "-by-" + height + " grayscale picture (grayscale values given in hex)\n");
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int gray = 0;
                if (isOriginUpperLeft) gray = 0xFF & image.getRGB(col, row);
                else gray = 0xFF & image.getRGB(col, height - row - 1);
                sb.append(String.format("%3d ", gray));
            }
            sb.append("\n");
        }
        return sb.toString().trim();
    }

    public int hashCode() {
        throw new UnsupportedOperationException("hashCode() is not supported because pictures are mutable");
    }

    public void save(String name) {
        if (name == null) throw new IllegalArgumentException("argument to save() is null");
        save(new File(name));
        filename = name;
    }

    public void save(File file) {
        if (file == null) throw new IllegalArgumentException("argument to save() is null");
        filename = file.getName();
        if (frame != null) frame.setTitle(filename);
        String suffix = filename.substring(filename.lastIndexOf('.') + 1);
        if ("jpg".equalsIgnoreCase(suffix) || "png".equalsIgnoreCase(suffix)) {
            try {
                ImageIO.write(image, suffix, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Error: filename must end in .jpg or .png");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FileDialog chooser = new FileDialog(frame,
                "Use a .png or .jpg extension", FileDialog.SAVE);
        chooser.setVisible(true);
        if (chooser.getFile() != null) {
            save(chooser.getDirectory() + File.separator + chooser.getFile());
        }
    }

}



