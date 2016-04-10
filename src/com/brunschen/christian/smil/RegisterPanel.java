/**
 * 
 */
package com.brunschen.christian.smil;

import javax.swing.JPanel;

public class RegisterPanel<RegisterT extends Register> extends JPanel {
  public static final long serialVersionUID = 0L;
  public static final int padding = 5;
  public RegisterUI<RegisterT> ui;

  public RegisterPanel(RegisterUI<RegisterT> ui) {
    super();
    this.ui = ui;
  }
}