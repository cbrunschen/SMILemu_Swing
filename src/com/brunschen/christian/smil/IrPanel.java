/**
 * 
 */
package com.brunschen.christian.smil;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class IrPanel extends RegisterPanel<ValueRegister> {
  public static final long serialVersionUID = 0L;

  public IrPanel(IrUI ui) {
    super(ui);
    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    JPanel column;

    column = new JPanel();
    column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
    column.add(ui.leftLabel);
    column.add(Box.createVerticalStrut(padding));
    column.add(ui.rightLabel);
    add(column);
    add(Box.createHorizontalStrut(padding));

    column = new JPanel();
    column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
    column.add(ui.leftHexField);
    column.add(Box.createVerticalStrut(padding));
    column.add(ui.rightHexField);
    add(column);
    add(Box.createHorizontalStrut(padding));

    column = new JPanel();
    column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
    column.add(ui.leftInstructionField);
    column.add(Box.createVerticalStrut(padding));
    column.add(ui.rightInstructionField);
    add(column);

    ui.update();
  }
}