package org.eclipse.scout.contacts.jobs.helper;

import java.security.PrivilegedAction;

import javax.security.auth.Subject;

import org.eclipse.scout.rt.platform.security.SimplePrincipal;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Use this {@link TestRule} to run a test on behalf of a {@link Subject}.
 */
public class SubjectRule implements TestRule {

  private final Subject m_subject;

  public SubjectRule(final String principal) {
    m_subject = new Subject();
    m_subject.getPrincipals().add(new SimplePrincipal(principal));
    m_subject.setReadOnly();
  }

  @Override
  public Statement apply(final Statement base, final Description description) {
    return new Statement() {

      @Override
      public void evaluate() throws Throwable {
        Subject.doAs(m_subject, new PrivilegedAction<Void>() {

          @Override
          public Void run() {
            try {
              base.evaluate();
              return null;
            }
            catch (final RuntimeException | Error e) {
              throw e;
            }
            catch (final Throwable t) {
              throw new Error(t);
            }
          }
        });
      }
    };
  }
}
