package com.brunschen.christian.smil;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SMILApplet extends JApplet {
  public static final long serialVersionUID = 0L;

  private SMILApp smilApp;

  private JPanel contentPane = null;

  /**
   * This is the default constructor
   */
  public SMILApplet() {
    super();
  }

  /**
   * This method initializes this
   * 
   * @return void
   */
  @Override
  public void init() {
    this.setSize(300, 200);
    setContentPane(contentPane());

    smilApp = new SMILApp();
    smilApp.applet = this;
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        smilApp.init();
      }
    });
  }

  /**
   * This method initializes jContentPane
   * 
   * @return javax.swing.JPanel
   */
  private JPanel contentPane() {
    if (contentPane == null) {
      contentPane = new JPanel();
      contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
      contentPane.add(Box.createVerticalStrut(5));
      contentPane.add(Box.createVerticalGlue());
      JPanel row = new JPanel();
      row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
      row.add(Box.createHorizontalStrut(5));
      row.add(Box.createHorizontalGlue());
      JLabel label = new JLabel("<html><em>SMILemu</em> will open in a separate window.</html>");
      label.setMaximumSize(label.getPreferredSize());
      label.setAlignmentY(0.5f);
      row.add(label);
      row.add(Box.createHorizontalGlue());
      row.add(Box.createHorizontalStrut(5));
      contentPane.add(row);
      contentPane.add(Box.createVerticalGlue());
      contentPane.add(Box.createVerticalStrut(5));
    }
    return contentPane;
  }

  @Override
  public void stop() {
    smilApp.deinit();
    super.stop();
  }

}