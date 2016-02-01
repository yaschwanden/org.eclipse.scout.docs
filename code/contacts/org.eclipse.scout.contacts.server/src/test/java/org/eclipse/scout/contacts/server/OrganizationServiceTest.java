package org.eclipse.scout.contacts.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.scout.contacts.server.organization.OrganizationService;
import org.eclipse.scout.contacts.server.sql.DBSetupService;
import org.eclipse.scout.contacts.server.sql.DerbySqlService;
import org.eclipse.scout.contacts.shared.organization.OrganizationFormData;
import org.eclipse.scout.rt.platform.BEANS;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for
 * <h3>{@link OrganizationService}</h3>
 */
//TODO Platform 7.1 Testing: Add testing annotations
public class OrganizationServiceTest {

  @BeforeClass
  public static void setupDatabase() {
    BEANS.get(DBSetupService.class).createOrganizationTable();
  }

  @AfterClass
  public static void destroyDBConnections() {
    BEANS.get(DerbySqlService.class).destroySqlConnectionPool();
  }

  @Test
  public void testCreate() {
    OrganizationFormData data = new OrganizationFormData();
    data.getName().setValue("test");
    OrganizationService svc = new OrganizationService();
    OrganizationFormData created = svc.create(data);

    assertEquals("test", created.getName().getValue());
    assertNotNull(created.getOrganizationId());
  }

  //TODO Platform 7.2 Testing: Add Tests for other OrganizationService methods

}
