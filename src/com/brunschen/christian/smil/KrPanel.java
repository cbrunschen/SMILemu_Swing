/**
 * 
 */
package com.brunschen.christian.smil;

import javax.swing.Box;
import javax.swing.BoxLayout;

public class KrPanel extends RegisterPanel<ProgramCounter> {
  public static final long serialVersionUID = 0L;

  public KrPanel(KrUI ui) {
    super(ui);
    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    add(Box.createHorizontalStrut(4 * padding));
    add(ui.addressField);
    add(ui.leftRightField);
    add(Box.createHorizontalGlue());
    ui.update();
  }
}