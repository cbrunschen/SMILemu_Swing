/**
 * 
 */
package com.brunschen.christian.smil;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFormattedTextField;

public class ArUI extends ValueRegisterUI<Accumulator> {
  private Color overflowColor = new Color(1.0f, 0.8f, 0.8f);
  public JFormattedTextField extraBitField;
  public JFormattedTextField lowBitField;

  public void update() {
    super.update();
    if (extraBitField != null) {
      extraBitField.setValue(register.isExtraBitSet() ? "1" : "0");
    }
    if (lowBitField != null) {
      lowBitField.setValue(register.isLowBitSet() ? "1" : "0");
    }
    if (register.overflow()) {
      doubleField.setBackground(overflowColor);
    } else {
      doubleField.setBackground(Color.WHITE);
    }
  }

  public ArUI(Accumulator register) {
    super(register);
    extraBitField = new JFormattedTextField(SMILApp.bitMaskFormatter());
    extraBitField.setFont(SMILApp.memoryFont().awtFont());
    extraBitField.setColumns(1);
    extraBitField.addPropertyChangeListener("value", new PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent e) {
        if (shouldUpdate(e)) {
          ArUI.this.register.setBit(ArUI.this.register.EXTRA_BIT, "1".equals(e.getNewValue()));
        }
      }
    });
    lowBitField = new JFormattedTextField(SMILApp.bitMaskFormatter());
    lowBitField.setFont(SMILApp.memoryFont().awtFont());
    lowBitField.setColumns(1);
    lowBitField.addPropertyChangeListener("value", new PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent e) {
        if (shouldUpdate(e)) {
          ArUI.this.register.setBit(ArUI.this.register.LOW_BIT, "1".equals(e.getNewValue()));
        }
      }
    });

    update();
    extraBitField.setMaximumSize(extraBitField.getPreferredSize());
    lowBitField.setMaximumSize(lowBitField.getPreferredSize());
  }

  public double extraBitFieldWidth() {
    return extraBitField.getPreferredSize().getWidth();
  }

  public double lowBitFieldWidth() {
    return lowBitField.getPreferredSize().getWidth();
  }
}