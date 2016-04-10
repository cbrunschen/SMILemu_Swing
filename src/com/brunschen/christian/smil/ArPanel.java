/**
 * 
 */
package com.brunschen.christian.smil;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

public class ArPanel extends RegisterPanel<Accumulator> {
  public static final long serialVersionUID = 0L;

  public ArPanel(ArUI ui) {
    super(ui);
    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    add(ui.extraBitField);
    add(ui.hexField);
    add(ui.lowBitField);
    add(Box.createHorizontalStrut(padding));
    JLabel equalsLabel = new JLabel("=");
    equalsLabel.setFont(SMILApp.memoryFont().awtFont());
    add(equalsLabel);
    add(Box.createHorizontalStrut(padding));
    add(ui.doubleField);
    add(Box.createHorizontalGlue());
    ui.update();
  }
}