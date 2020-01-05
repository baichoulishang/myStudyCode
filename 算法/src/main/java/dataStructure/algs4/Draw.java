package dataStructure.algs4;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeSet;


public final class Draw implements ActionListener, MouseListener, MouseMotionListener, KeyListener {


    public static final Color BLACK = Color.BLACK;


    public static final Color BLUE = Color.BLUE;


    public static final Color CYAN = Color.CYAN;


    public static final Color DARK_GRAY = Color.DARK_GRAY;


    public static final Color GRAY = Color.GRAY;


    public static final Color GREEN = Color.GREEN;


    public static final Color LIGHT_GRAY = Color.LIGHT_GRAY;


    public static final Color MAGENTA = Color.MAGENTA;


    public static final Color ORANGE = Color.ORANGE;


    public static final Color PINK = Color.PINK;


    public static final Color RED = Color.RED;


    public static final Color WHITE = Color.WHITE;


    public static final Color YELLOW = Color.YELLOW;


    public static final Color BOOK_BLUE = new Color(9, 90, 166);


    public static final Color BOOK_LIGHT_BLUE = new Color(103, 198, 243);


    public static final Color BOOK_RED = new Color(150, 35, 31);


    public static final Color PRINCETON_ORANGE = new Color(245, 128, 37);


    private static final Color DEFAULT_PEN_COLOR = BLACK;
    private static final Color DEFAULT_CLEAR_COLOR = WHITE;


    private static final double BORDER = 0.0;
    private static final double DEFAULT_XMIN = 0.0;
    private static final double DEFAULT_XMAX = 1.0;
    private static final double DEFAULT_YMIN = 0.0;
    private static final double DEFAULT_YMAX = 1.0;


    private static final int DEFAULT_SIZE = 512;


    private static final double DEFAULT_PEN_RADIUS = 0.002;


    private static final Font DEFAULT_FONT = new Font("SansSerif", Font.PLAIN, 16);
    private final Object mouseLock = new Object();
    private final Object keyLock = new Object();
    private final LinkedList<Character> keysTyped = new LinkedList<Character>();
    private final TreeSet<Integer> keysDown = new TreeSet<Integer>();
    private final ArrayList<DrawListener> listeners = new ArrayList<DrawListener>();
    private Color penColor;
    private int width = DEFAULT_SIZE;
    private int height = DEFAULT_SIZE;
    private double penRadius;
    private boolean defer = false;
    private double xmin, ymin, xmax, ymax;
    private String name = "Draw";
    private Font font;
    private JLabel draw;
    private BufferedImage offscreenImage, onscreenImage;
    private Graphics2D offscreen, onscreen;
    private JFrame frame = new JFrame();
    private boolean isMousePressed = false;
    private double mouseX = 0;
    private double mouseY = 0;


    public Draw(String name) {
        this.name = name;
        init();
    }


    public Draw() {
        init();
    }

    private static BufferedImage getImage(String filename) {


        try {
            URL url = new URL(filename);
            return ImageIO.read(url);
        } catch (IOException e) {

        }


        try {
            URL url = StdDraw.class.getResource(filename);
            return ImageIO.read(url);
        } catch (IOException e) {

        }


        try {
            URL url = StdDraw.class.getResource("/" + filename);
            return ImageIO.read(url);
        } catch (IOException e) {

        }
        throw new IllegalArgumentException("image " + filename + " not found");
    }

    public static void main(String[] args) {


        Draw draw1 = new Draw("Test client 1");
        draw1.square(0.2, 0.8, 0.1);
        draw1.filledSquare(0.8, 0.8, 0.2);
        draw1.circle(0.8, 0.2, 0.2);
        draw1.setPenColor(Draw.MAGENTA);
        draw1.setPenRadius(0.02);
        draw1.arc(0.8, 0.2, 0.1, 200, 45);


        Draw draw2 = new Draw("Test client 2");
        draw2.setCanvasSize(900, 200);

        draw2.setPenRadius();
        draw2.setPenColor(Draw.BLUE);
        double[] x = {0.1, 0.2, 0.3, 0.2};
        double[] y = {0.2, 0.3, 0.2, 0.1};
        draw2.filledPolygon(x, y);


        draw2.setPenColor(Draw.BLACK);
        draw2.text(0.2, 0.5, "bdfdfdfdlack text");
        draw2.setPenColor(Draw.WHITE);
        draw2.text(0.8, 0.8, "white text");
    }

    private void init() {
        if (frame != null) frame.setVisible(false);
        frame = new JFrame();
        offscreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        onscreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        offscreen = offscreenImage.createGraphics();
        onscreen = onscreenImage.createGraphics();
        setXscale();
        setYscale();
        offscreen.setColor(DEFAULT_CLEAR_COLOR);
        offscreen.fillRect(0, 0, width, height);
        setPenColor();
        setPenRadius();
        setFont();
        clear();


        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        offscreen.addRenderingHints(hints);


        ImageIcon icon = new ImageIcon(onscreenImage);
        draw = new JLabel(icon);

        draw.addMouseListener(this);
        draw.addMouseMotionListener(this);

        frame.setContentPane(draw);
        frame.addKeyListener(this);
        frame.setResizable(false);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setFocusTraversalKeysEnabled(false);
        frame.setTitle(name);
        frame.setJMenuBar(createMenuBar());
        frame.pack();
        frame.requestFocusInWindow();
        frame.setVisible(true);
    }

    public void setLocationOnScreen(int x, int y) {
        if (x <= 0 || y <= 0) throw new IllegalArgumentException();
        frame.setLocation(x, y);
    }

    public void setDefaultCloseOperation(int value) {
        frame.setDefaultCloseOperation(value);
    }

    public void setCanvasSize(int canvasWidth, int canvasHeight) {
        if (canvasWidth < 1 || canvasHeight < 1) {
            throw new IllegalArgumentException("width and height must be positive");
        }
        width = canvasWidth;
        height = canvasHeight;
        init();
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menuBar.add(menu);
        JMenuItem menuItem1 = new JMenuItem(" Save...   ");
        menuItem1.addActionListener(this);
        menuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menu.add(menuItem1);
        return menuBar;
    }

    public void setXscale() {
        setXscale(DEFAULT_XMIN, DEFAULT_XMAX);
    }

    public void setYscale() {
        setYscale(DEFAULT_YMIN, DEFAULT_YMAX);
    }

    public void setXscale(double min, double max) {
        double size = max - min;
        xmin = min - BORDER * size;
        xmax = max + BORDER * size;
    }

    public void setYscale(double min, double max) {
        double size = max - min;
        ymin = min - BORDER * size;
        ymax = max + BORDER * size;
    }

    private double scaleX(double x) {
        return width * (x - xmin) / (xmax - xmin);
    }

    private double scaleY(double y) {
        return height * (ymax - y) / (ymax - ymin);
    }

    private double factorX(double w) {
        return w * width / Math.abs(xmax - xmin);
    }

    private double factorY(double h) {
        return h * height / Math.abs(ymax - ymin);
    }

    private double userX(double x) {
        return xmin + x * (xmax - xmin) / width;
    }

    private double userY(double y) {
        return ymax - y * (ymax - ymin) / height;
    }

    public void clear() {
        clear(DEFAULT_CLEAR_COLOR);
    }

    public void clear(Color color) {
        offscreen.setColor(color);
        offscreen.fillRect(0, 0, width, height);
        offscreen.setColor(penColor);
        draw();
    }

    public double getPenRadius() {
        return penRadius;
    }

    public void setPenRadius(double r) {
        if (r < 0) throw new IllegalArgumentException("pen radius must be positive");
        penRadius = r * DEFAULT_SIZE;
        BasicStroke stroke = new BasicStroke((float) penRadius, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

        offscreen.setStroke(stroke);
    }

    public void setPenRadius() {
        setPenRadius(DEFAULT_PEN_RADIUS);
    }

    public Color getPenColor() {
        return penColor;
    }

    public void setPenColor(Color color) {
        penColor = color;
        offscreen.setColor(penColor);
    }

    public void setPenColor() {
        setPenColor(DEFAULT_PEN_COLOR);
    }

    public void setPenColor(int red, int green, int blue) {
        if (red < 0 || red >= 256) throw new IllegalArgumentException("amount of red must be between 0 and 255");
        if (green < 0 || green >= 256) throw new IllegalArgumentException("amount of red must be between 0 and 255");
        if (blue < 0 || blue >= 256) throw new IllegalArgumentException("amount of red must be between 0 and 255");
        setPenColor(new Color(red, green, blue));
    }

    public void xorOn() {
        offscreen.setXORMode(DEFAULT_CLEAR_COLOR);
    }

    public void xorOff() {
        offscreen.setPaintMode();
    }

    public JLabel getJLabel() {
        return draw;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void setFont() {
        setFont(DEFAULT_FONT);
    }

    public void line(double x0, double y0, double x1, double y1) {
        offscreen.draw(new Line2D.Double(scaleX(x0), scaleY(y0), scaleX(x1), scaleY(y1)));
        draw();
    }

    private void pixel(double x, double y) {
        offscreen.fillRect((int) Math.round(scaleX(x)), (int) Math.round(scaleY(y)), 1, 1);
    }

    public void point(double x, double y) {
        double xs = scaleX(x);
        double ys = scaleY(y);
        double r = penRadius;


        if (r <= 1) pixel(x, y);
        else offscreen.fill(new Ellipse2D.Double(xs - r / 2, ys - r / 2, r, r));
        draw();
    }

    public void circle(double x, double y, double r) {
        if (r < 0) throw new IllegalArgumentException("circle radius can't be negative");
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2 * r);
        double hs = factorY(2 * r);
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else offscreen.draw(new Ellipse2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
        draw();
    }

    public void filledCircle(double x, double y, double r) {
        if (r < 0) throw new IllegalArgumentException("circle radius can't be negative");
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2 * r);
        double hs = factorY(2 * r);
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else offscreen.fill(new Ellipse2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
        draw();
    }

    public void ellipse(double x, double y, double semiMajorAxis, double semiMinorAxis) {
        if (semiMajorAxis < 0) throw new IllegalArgumentException("ellipse semimajor axis can't be negative");
        if (semiMinorAxis < 0) throw new IllegalArgumentException("ellipse semiminor axis can't be negative");
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2 * semiMajorAxis);
        double hs = factorY(2 * semiMinorAxis);
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else offscreen.draw(new Ellipse2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
        draw();
    }

    public void filledEllipse(double x, double y, double semiMajorAxis, double semiMinorAxis) {
        if (semiMajorAxis < 0) throw new IllegalArgumentException("ellipse semimajor axis can't be negative");
        if (semiMinorAxis < 0) throw new IllegalArgumentException("ellipse semiminor axis can't be negative");
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2 * semiMajorAxis);
        double hs = factorY(2 * semiMinorAxis);
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else offscreen.fill(new Ellipse2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
        draw();
    }

    public void arc(double x, double y, double r, double angle1, double angle2) {
        if (r < 0) throw new IllegalArgumentException("arc radius can't be negative");
        while (angle2 < angle1) angle2 += 360;
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2 * r);
        double hs = factorY(2 * r);
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else offscreen.draw(new Arc2D.Double(xs - ws / 2, ys - hs / 2, ws, hs, angle1, angle2 - angle1, Arc2D.OPEN));
        draw();
    }

    public void square(double x, double y, double r) {
        if (r < 0) throw new IllegalArgumentException("square side length can't be negative");
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2 * r);
        double hs = factorY(2 * r);
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else offscreen.draw(new Rectangle2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
        draw();
    }

    public void filledSquare(double x, double y, double r) {
        if (r < 0) throw new IllegalArgumentException("square side length can't be negative");
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2 * r);
        double hs = factorY(2 * r);
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else offscreen.fill(new Rectangle2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
        draw();
    }

    public void rectangle(double x, double y, double halfWidth, double halfHeight) {
        if (halfWidth < 0) throw new IllegalArgumentException("half width can't be negative");
        if (halfHeight < 0) throw new IllegalArgumentException("half height can't be negative");
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2 * halfWidth);
        double hs = factorY(2 * halfHeight);
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else offscreen.draw(new Rectangle2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
        draw();
    }

    public void filledRectangle(double x, double y, double halfWidth, double halfHeight) {
        if (halfWidth < 0) throw new IllegalArgumentException("half width can't be negative");
        if (halfHeight < 0) throw new IllegalArgumentException("half height can't be negative");
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2 * halfWidth);
        double hs = factorY(2 * halfHeight);
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else offscreen.fill(new Rectangle2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
        draw();
    }

    public void polygon(double[] x, double[] y) {
        int n = x.length;
        GeneralPath path = new GeneralPath();
        path.moveTo((float) scaleX(x[0]), (float) scaleY(y[0]));
        for (int i = 0; i < n; i++)
            path.lineTo((float) scaleX(x[i]), (float) scaleY(y[i]));
        path.closePath();
        offscreen.draw(path);
        draw();
    }

    public void filledPolygon(double[] x, double[] y) {
        int n = x.length;
        GeneralPath path = new GeneralPath();
        path.moveTo((float) scaleX(x[0]), (float) scaleY(y[0]));
        for (int i = 0; i < n; i++)
            path.lineTo((float) scaleX(x[i]), (float) scaleY(y[i]));
        path.closePath();
        offscreen.fill(path);
        draw();
    }

    public void picture(double x, double y, String filename) {
        if (filename == null) throw new IllegalArgumentException("filename argument is null");
        BufferedImage image = getImage(filename);
        double xs = scaleX(x);
        double ys = scaleY(y);
        int ws = image.getWidth();
        int hs = image.getHeight();
        if (ws < 0 || hs < 0) throw new IllegalArgumentException("image " + filename + " is corrupt");

        offscreen.drawImage(image, (int) Math.round(xs - ws / 2.0), (int) Math.round(ys - hs / 2.0), null);
        draw();
    }

    public void picture(double x, double y, String filename, double degrees) {
        if (filename == null) throw new IllegalArgumentException("filename argument is null");
        BufferedImage image = getImage(filename);
        double xs = scaleX(x);
        double ys = scaleY(y);
        int ws = image.getWidth();
        int hs = image.getHeight();
        if (ws < 0 || hs < 0) throw new IllegalArgumentException("image " + filename + " is corrupt");

        offscreen.rotate(Math.toRadians(-degrees), xs, ys);
        offscreen.drawImage(image, (int) Math.round(xs - ws / 2.0), (int) Math.round(ys - hs / 2.0), null);
        offscreen.rotate(Math.toRadians(+degrees), xs, ys);

        draw();
    }

    public void picture(double x, double y, String filename, double w, double h) {
        if (filename == null) throw new IllegalArgumentException("filename argument is null");
        Image image = getImage(filename);
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(w);
        double hs = factorY(h);
        if (ws < 0 || hs < 0) throw new IllegalArgumentException("image " + filename + " is corrupt");
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else {
            offscreen.drawImage(image, (int) Math.round(xs - ws / 2.0),
                    (int) Math.round(ys - hs / 2.0),
                    (int) Math.round(ws),
                    (int) Math.round(hs), null);
        }
        draw();
    }

    public void picture(double x, double y, String filename, double w, double h, double degrees) {
        if (filename == null) throw new IllegalArgumentException("filename argument is null");
        Image image = getImage(filename);
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(w);
        double hs = factorY(h);
        if (ws < 0 || hs < 0) throw new IllegalArgumentException("image " + filename + " is corrupt");
        if (ws <= 1 && hs <= 1) pixel(x, y);

        offscreen.rotate(Math.toRadians(-degrees), xs, ys);
        offscreen.drawImage(image, (int) Math.round(xs - ws / 2.0),
                (int) Math.round(ys - hs / 2.0),
                (int) Math.round(ws),
                (int) Math.round(hs), null);
        offscreen.rotate(Math.toRadians(+degrees), xs, ys);

        draw();
    }

    public void text(double x, double y, String s) {
        offscreen.setFont(font);
        FontMetrics metrics = offscreen.getFontMetrics();
        double xs = scaleX(x);
        double ys = scaleY(y);
        int ws = metrics.stringWidth(s);
        int hs = metrics.getDescent();
        offscreen.drawString(s, (float) (xs - ws / 2.0), (float) (ys + hs));
        draw();
    }

    public void text(double x, double y, String s, double degrees) {
        double xs = scaleX(x);
        double ys = scaleY(y);
        offscreen.rotate(Math.toRadians(-degrees), xs, ys);
        text(x, y, s);
        offscreen.rotate(Math.toRadians(+degrees), xs, ys);
    }

    public void textLeft(double x, double y, String s) {
        offscreen.setFont(font);
        FontMetrics metrics = offscreen.getFontMetrics();
        double xs = scaleX(x);
        double ys = scaleY(y);

        int hs = metrics.getDescent();
        offscreen.drawString(s, (float) xs, (float) (ys + hs));
        draw();
    }

    @Deprecated
    public void show(int t) {
        show();
        pause(t);
        enableDoubleBuffering();
    }

    public void pause(int t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            System.out.println("Error sleeping");
        }
    }

    public void show() {
        onscreen.drawImage(offscreenImage, 0, 0, null);
        frame.repaint();
    }

    private void draw() {
        if (!defer) show();
    }

    public void enableDoubleBuffering() {
        defer = true;
    }

    public void disableDoubleBuffering() {
        defer = false;
    }

    public void save(String filename) {
        File file = new File(filename);
        String suffix = filename.substring(filename.lastIndexOf('.') + 1);


        if ("png".equalsIgnoreCase(suffix)) {
            try {
                ImageIO.write(offscreenImage, suffix, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if ("jpg".equalsIgnoreCase(suffix)) {
            WritableRaster raster = offscreenImage.getRaster();
            WritableRaster newRaster;
            newRaster = raster.createWritableChild(0, 0, width, height, 0, 0, new int[]{0, 1, 2});
            DirectColorModel cm = (DirectColorModel) offscreenImage.getColorModel();
            DirectColorModel newCM = new DirectColorModel(cm.getPixelSize(),
                    cm.getRedMask(),
                    cm.getGreenMask(),
                    cm.getBlueMask());
            BufferedImage rgbBuffer = new BufferedImage(newCM, newRaster, false, null);
            try {
                ImageIO.write(rgbBuffer, suffix, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid image file type: " + suffix);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FileDialog chooser = new FileDialog(frame, "Use a .png or .jpg extension", FileDialog.SAVE);
        chooser.setVisible(true);
        String filename = chooser.getFile();
        if (filename != null) {
            save(chooser.getDirectory() + File.separator + chooser.getFile());
        }
    }

    public void addListener(DrawListener listener) {

        show();
        listeners.add(listener);
        frame.addKeyListener(this);
        frame.addMouseListener(this);
        frame.addMouseMotionListener(this);
        frame.setFocusable(true);
    }

    public boolean isMousePressed() {
        synchronized (mouseLock) {
            return isMousePressed;
        }
    }

    @Deprecated
    public boolean mousePressed() {
        synchronized (mouseLock) {
            return isMousePressed;
        }
    }

    public double mouseX() {
        synchronized (mouseLock) {
            return mouseX;
        }
    }

    public double mouseY() {
        synchronized (mouseLock) {
            return mouseY;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        synchronized (mouseLock) {
            mouseX = userX(e.getX());
            mouseY = userY(e.getY());
            isMousePressed = true;
        }
        if (e.getButton() == MouseEvent.BUTTON1) {
            for (DrawListener listener : listeners)
                listener.mousePressed(userX(e.getX()), userY(e.getY()));
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        synchronized (mouseLock) {
            isMousePressed = false;
        }
        if (e.getButton() == MouseEvent.BUTTON1) {
            for (DrawListener listener : listeners)
                listener.mouseReleased(userX(e.getX()), userY(e.getY()));
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        synchronized (mouseLock) {
            mouseX = userX(e.getX());
            mouseY = userY(e.getY());
        }

        for (DrawListener listener : listeners)
            listener.mouseDragged(userX(e.getX()), userY(e.getY()));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        synchronized (mouseLock) {
            mouseX = userX(e.getX());
            mouseY = userY(e.getY());
        }
    }

    public boolean hasNextKeyTyped() {
        synchronized (keyLock) {
            return !keysTyped.isEmpty();
        }
    }

    public char nextKeyTyped() {
        synchronized (keyLock) {
            return keysTyped.removeLast();
        }
    }

    public boolean isKeyPressed(int keycode) {
        synchronized (keyLock) {
            return keysDown.contains(keycode);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        synchronized (keyLock) {
            keysTyped.addFirst(e.getKeyChar());
        }


        for (DrawListener listener : listeners)
            listener.keyTyped(e.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        synchronized (keyLock) {
            keysDown.add(e.getKeyCode());
        }


        for (DrawListener listener : listeners)
            listener.keyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        synchronized (keyLock) {
            keysDown.remove(e.getKeyCode());
        }


        for (DrawListener listener : listeners)
            listener.keyPressed(e.getKeyCode());
    }

}


