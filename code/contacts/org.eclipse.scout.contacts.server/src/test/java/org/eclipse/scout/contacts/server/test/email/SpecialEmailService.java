package org.eclipse.scout.contacts.server.test.email;

import org.eclipse.scout.rt.platform.Replace;

@Replace
public class SpecialEmailService extends EmailService {

  @Override
  public String getName() {
    return "SpecialEmailService";
  }

}
