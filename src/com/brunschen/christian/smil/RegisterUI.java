/**
 * 
 */
package com.brunschen.christian.smil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

public abstract class RegisterUI<RegisterT extends Register> {
  public RegisterT register;

  protected long value() {
    return register.value();
  }

  protected boolean pushing = false;

  protected ActionListener listener;

  public boolean shouldUpdate(PropertyChangeEvent e) {
    boolean shouldUpdate = !pushing && "value".equals(e.getPropertyName()) && e.getNewValue() != null;
    // System.err.format("Should we update '%s'? pushing = %b, propertyName = '%s', newValue =
    // '%s' => %b\n",
    // register.name(), pushing, e.getPropertyName(), e.getNewValue(), shouldUpdate);
    return shouldUpdate;
  }

  public abstract void update();

  public long valueOf(String s) {
    try {
      return Long.parseLong(s.substring(0, 5), 16) << 20 | Long.parseLong(s.substring(6), 16);
    } catch (NumberFormatException e) {
      e.printStackTrace();
      return 0L;
    }
  }

  public RegisterUI(RegisterT register) {
    this.register = register;
  }

  public void addListener() {
    if (listener == null) {
      listener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          pushing = true;
          update();
          pushing = false;
        }
      };
      register.addListener(listener);
    }
  }

  public void removeListener() {
    if (listener != null) {
      register.removeListener(listener);
    }
    listener = null;
  }
}