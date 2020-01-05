package dataStructure.algs4;

import javax.sound.sampled.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


public final class StdAudio {


    public static final int SAMPLE_RATE = 44100;

    private static final int BYTES_PER_SAMPLE = 2;
    private static final int BITS_PER_SAMPLE = 16;
    private static final double MAX_16_BIT = Short.MAX_VALUE;
    private static final int SAMPLE_BUFFER_SIZE = 4096;


    private static SourceDataLine line;
    private static byte[] buffer;
    private static int bufferSize = 0;

    static {
        init();
    }


    private StdAudio() {

    }

    private static void init() {
        try {

            AudioFormat format = new AudioFormat((float) SAMPLE_RATE, BITS_PER_SAMPLE, 1, true, false);
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format, SAMPLE_BUFFER_SIZE * BYTES_PER_SAMPLE);


            buffer = new byte[SAMPLE_BUFFER_SIZE * BYTES_PER_SAMPLE / 3];
        } catch (LineUnavailableException e) {
            System.out.println(e.getMessage());
        }


        line.start();
    }


    public static void close() {
        line.drain();
        line.stop();
    }


    public static void play(double sample) {


        if (Double.isNaN(sample)) throw new IllegalArgumentException("sample is NaN");
        if (sample < -1.0) sample = -1.0;
        if (sample > +1.0) sample = +1.0;


        short s = (short) (MAX_16_BIT * sample);
        buffer[bufferSize++] = (byte) s;
        buffer[bufferSize++] = (byte) (s >> 8);


        if (bufferSize >= buffer.length) {
            line.write(buffer, 0, buffer.length);
            bufferSize = 0;
        }
    }


    public static void play(double[] samples) {
        if (samples == null) throw new IllegalArgumentException("argument to play() is null");
        for (int i = 0; i < samples.length; i++) {
            play(samples[i]);
        }
    }


    public static double[] read(String filename) {
        byte[] data = readByte(filename);
        int n = data.length;
        double[] d = new double[n / 2];
        for (int i = 0; i < n / 2; i++) {
            d[i] = ((short) (((data[2 * i + 1] & 0xFF) << 8) + (data[2 * i] & 0xFF))) / ((double) MAX_16_BIT);
        }
        return d;
    }


    private static byte[] readByte(String filename) {
        byte[] data = null;
        AudioInputStream ais = null;
        try {


            File file = new File(filename);
            if (file.exists()) {
                ais = AudioSystem.getAudioInputStream(file);
                int bytesToRead = ais.available();
                data = new byte[bytesToRead];
                int bytesRead = ais.read(data);
                if (bytesToRead != bytesRead)
                    throw new IllegalStateException("read only " + bytesRead + " of " + bytesToRead + " bytes");
            } else {
                URL url = StdAudio.class.getResource(filename);
                ais = AudioSystem.getAudioInputStream(url);
                int bytesToRead = ais.available();
                data = new byte[bytesToRead];
                int bytesRead = ais.read(data);
                if (bytesToRead != bytesRead)
                    throw new IllegalStateException("read only " + bytesRead + " of " + bytesToRead + " bytes");
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("could not read '" + filename + "'", e);
        } catch (UnsupportedAudioFileException e) {
            throw new IllegalArgumentException("unsupported audio format: '" + filename + "'", e);
        }

        return data;
    }


    public static void save(String filename, double[] samples) {
        if (samples == null) {
            throw new IllegalArgumentException("samples[] is null");
        }


        AudioFormat format = new AudioFormat(SAMPLE_RATE, 16, 1, true, false);
        byte[] data = new byte[2 * samples.length];
        for (int i = 0; i < samples.length; i++) {
            int temp = (short) (samples[i] * MAX_16_BIT);
            data[2 * i + 0] = (byte) temp;
            data[2 * i + 1] = (byte) (temp >> 8);
        }


        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            AudioInputStream ais = new AudioInputStream(bais, format, samples.length);
            if (filename.endsWith(".wav") || filename.endsWith(".WAV")) {
                AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File(filename));
            } else if (filename.endsWith(".au") || filename.endsWith(".AU")) {
                AudioSystem.write(ais, AudioFileFormat.Type.AU, new File(filename));
            } else {
                throw new IllegalArgumentException("unsupported audio format: '" + filename + "'");
            }
        } catch (IOException ioe) {
            throw new IllegalArgumentException("unable to save file '" + filename + "'", ioe);
        }
    }


    public static synchronized void play(final String filename) {
        if (filename == null) throw new IllegalArgumentException();

        InputStream is = StdAudio.class.getResourceAsStream(filename);
        if (is == null) {
            throw new IllegalArgumentException("could not read '" + filename + "'");
        }


        try {


            AudioSystem.getAudioInputStream(is);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    stream(filename);
                }
            }).start();
        } catch (UnsupportedAudioFileException e) {
            playApplet(filename);
            return;
        } catch (IOException ioe) {
            throw new IllegalArgumentException("could not play '" + filename + "'", ioe);
        }

    }


    private static void playApplet(String filename) {
        URL url = null;
        try {
            File file = new File(filename);
            if (file.canRead()) url = file.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("could not play '" + filename + "'", e);
        }


        if (url == null) {
            throw new IllegalArgumentException("could not play '" + filename + "'");
        }

        AudioClip clip = Applet.newAudioClip(url);
        clip.play();
    }


    private static void stream(String filename) {
        SourceDataLine line = null;
        int BUFFER_SIZE = 4096;

        try {
            InputStream is = StdAudio.class.getResourceAsStream(filename);
            AudioInputStream ais = AudioSystem.getAudioInputStream(is);
            AudioFormat audioFormat = ais.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(audioFormat);
            line.start();
            byte[] samples = new byte[BUFFER_SIZE];
            int count = 0;
            while ((count = ais.read(samples, 0, BUFFER_SIZE)) != -1) {
                line.write(samples, 0, count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } finally {
            if (line != null) {
                line.drain();
                line.close();
            }
        }
    }


    public static synchronized void loop(String filename) {
        if (filename == null) throw new IllegalArgumentException();


        try {
            Clip clip = AudioSystem.getClip();
            InputStream is = StdAudio.class.getResourceAsStream(filename);
            AudioInputStream ais = AudioSystem.getAudioInputStream(is);
            clip.open(ais);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException e) {
            throw new IllegalArgumentException("unsupported audio format: '" + filename + "'", e);
        } catch (LineUnavailableException e) {
            throw new IllegalArgumentException("could not play '" + filename + "'", e);
        } catch (IOException e) {
            throw new IllegalArgumentException("could not play '" + filename + "'", e);
        }
    }


    private static double[] note(double hz, double duration, double amplitude) {
        int n = (int) (StdAudio.SAMPLE_RATE * duration);
        double[] a = new double[n + 1];
        for (int i = 0; i <= n; i++)
            a[i] = amplitude * Math.sin(2 * Math.PI * i * hz / StdAudio.SAMPLE_RATE);
        return a;
    }


    public static void main(String[] args) {


        double freq = 440.0;
        for (int i = 0; i <= StdAudio.SAMPLE_RATE; i++) {
            StdAudio.play(0.5 * Math.sin(2 * Math.PI * freq * i / StdAudio.SAMPLE_RATE));
        }


        int[] steps = {0, 2, 4, 5, 7, 9, 11, 12};
        for (int i = 0; i < steps.length; i++) {
            double hz = 440.0 * Math.pow(2, steps[i] / 12.0);
            StdAudio.play(note(hz, 1.0, 0.5));
        }


        StdAudio.close();
    }
}


