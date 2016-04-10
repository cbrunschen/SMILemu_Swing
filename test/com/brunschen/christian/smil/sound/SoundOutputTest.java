/**
 * 
 */
package com.brunschen.christian.smil.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.SourceDataLine;

import junit.framework.TestCase;


/**
 * @author Christian Brunschen
 *
 */
public class SoundOutputTest extends TestCase {
  public void testSampleRate() throws Exception {
    AudioFormat format = SourceDataLineSoundGenerator.findFormat();
    SourceDataLine sourceDataLine = AudioSystem.getSourceDataLine(format);
    int bytesPerSample = format.getSampleSizeInBits()>>3 * format.getChannels();
    int bytesPerSecond = (int) Math.floor(bytesPerSample * format.getSampleRate());
    int seconds = 120;
    int bufferSizeSamples = 1500;
    int bufferSize = (int) (bytesPerSample * bufferSizeSamples);
    int writeSize = bufferSize / 4;
    byte[] buf = new byte[seconds * bytesPerSecond];
    for (int i = 0; i < buf.length; i++) {
      buf[i] = 0;
    }
    
    int offset = 0;
    int len = buf.length;
    sourceDataLine.open(format, bufferSize);
    sourceDataLine.start();
    LineListener listener = new LineListener() {
      public void update(LineEvent e) {
        if (e.getType() == LineEvent.Type.STOP) {
          System.err.format("Data Line underun!\n");
        }
      }
    };
    sourceDataLine.addLineListener(listener);
    
    while (sourceDataLine.available() > writeSize) {
      int nRead = sourceDataLine.write(buf, offset, writeSize);
      offset += nRead;
      len -= nRead;
    }

    long startNanos = System.nanoTime();
    long nextReportNanos = startNanos + 1000000000L;
    
    while (len > 0) {
      int nWrite = writeSize < len ? writeSize : len;
      int nRead = sourceDataLine.write(buf, offset, nWrite);
      offset += nRead;
      len -= nRead;
      
      long now = System.nanoTime();
      if (now > nextReportNanos) {
        double elapsedSeconds = (now - startNanos) / 1000000000.0f;
        System.out.format("average sample rate over %f seconds is %f\n",
            elapsedSeconds, (buf.length - len) / (bytesPerSample * elapsedSeconds));
        while (nextReportNanos <= now) {
          nextReportNanos += 1000000000L;
        }
      }
    }
    sourceDataLine.removeLineListener(listener);
    System.out.format("draining\n");
    sourceDataLine.drain();
    long endNanos = System.nanoTime();
    System.out.format("done.\n");
    
    double actualSampleRate = (seconds * format.getSampleRate()) / ((endNanos - startNanos) / 1000000000.0);
    System.err.format("expected sample rate %f, actual sample rate %f\n",
        format.getSampleRate(), actualSampleRate);
  }
}
