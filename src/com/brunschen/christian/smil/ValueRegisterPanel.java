/**
 * 
 */
package com.brunschen.christian.smil;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

public class ValueRegisterPanel extends RegisterPanel<ValueRegister> {
  public static final long serialVersionUID = 0L;

  public ValueRegisterPanel(ValueRegisterUI<ValueRegister> ui, int leftSpace, int rightSpace) {
    super(ui);
    setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
    add(Box.createHorizontalStrut(leftSpace));
    add(ui.hexField);
    add(Box.createHorizontalStrut(rightSpace + padding));
    JLabel equalsLabel = new JLabel("=");
    equalsLabel.setFont(SMILApp.memoryFont().awtFont());
    add(equalsLabel);
    add(Box.createHorizontalStrut(padding));
    add(ui.doubleField);
    add(Box.createHorizontalGlue());
    ui.update();
  }
}