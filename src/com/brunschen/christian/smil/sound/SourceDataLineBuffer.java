package com.brunschen.christian.smil.sound;

import javax.sound.sampled.SourceDataLine;

public class SourceDataLineBuffer extends CircularByteBuffer {
  
  public static SourceDataLineBuffer extendingBuffer(int capacity) {
    return new SourceDataLineBuffer(capacity, OnOverrun.EXTEND);
  }
    
  public static SourceDataLineBuffer overwritingBuffer(int capacity) {
    return new SourceDataLineBuffer(capacity, OnOverrun.OVERWRITE);
  }
    
  public static SourceDataLineBuffer throwingBuffer(int capacity) {
    return new SourceDataLineBuffer(capacity, OnOverrun.THROW);
  }
  
  public SourceDataLineBuffer(int capacity, OnOverrun defaultOnOverrun) {
    super(capacity, defaultOnOverrun);
  }
  
  public SourceDataLineBuffer(int capacity) {
    this(capacity, OnOverrun.EXTEND);
  }

  public synchronized int read(SourceDataLine sourceDataLine) {
    int len = sourceDataLine.available();
    
    if (nItems == 0) {
      return 0;
    }
    
    if (next > first) {
      int nAvailable = next - first;
      int n = nAvailable < len ? nAvailable : len;
      sourceDataLine.write(items, first, n);
      first += n;
      nItems -= n;
      return n;
    } else {
      // first batch - from 'first' to end of buffer
      int nAvailable = capacity - first;
      int n = nAvailable < len ? nAvailable : len;
      sourceDataLine.write(items, first, n);
      first = (first + n) % capacity;
      nItems -= n;

      // if necessary, second batch - from 0 to next - using a recursive call
      if (len > n) {
        return n + read(sourceDataLine);
      } else {
        return n;
      }
    }
  }
}
