/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.jswidgets.rest;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.api.uinotification.UiNotificationResource;

@Replace
public class SampleUiNotificationResource extends UiNotificationResource {

  @Path("put")
  @Consumes(MediaType.APPLICATION_JSON)
  @POST
  public void put(SampleUiNotificationPutRequest request) {
    if (request.getMessage() == null) {
      throw new BadRequestException("message must not be null");
    }
    if (request.getTopic() == null) {
      throw new BadRequestException("topic must not be null");
    }
    getRegistry().put(request.getMessage(), request.getTopic());
  }
}
