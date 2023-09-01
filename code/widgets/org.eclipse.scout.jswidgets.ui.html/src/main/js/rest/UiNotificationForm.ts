/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {ajax, AjaxError, DoEntity, Form, FormModel, InitModelOf, models, UiNotificationHandler, uiNotifications} from '@eclipse-scout/core';
import RestFormModel from './UiNotificationFormModel';
import {UiNotificationFormWidgetMap} from '../index';

export class UiNotificationForm extends Form {
  declare widgetMap: UiNotificationFormWidgetMap;
  protected _notificationHandler: UiNotificationHandler;

  constructor() {
    super();
    this._notificationHandler = this._onNotification.bind(this);
  }

  protected override _jsonModel(): FormModel {
    return models.get(RestFormModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    let messageField = this.widget('MessageField');
    messageField.addValidator(value => {
      if (!value) {
        return value;
      }
      try {
        return JSON.stringify(JSON.parse(value), null, 2);
      } catch (e) {
        throw e.message;
      }
    });
    messageField.setValue(this._createSampleMessage());

    this.widget('SubscribeButton').on('click', () => {
      uiNotifications.subscribe(this.widget('TopicField').value, this._notificationHandler);
    });
    this.widget('UnsubscribeButton').on('click', () => {
      uiNotifications.unsubscribe(this.widget('TopicField').value, this._notificationHandler);
    });
    this.widget('SampleMenu').on('action', () => {
      messageField.setValue(this._createSampleMessage());
    });

    this.widget('PublishButton').on('click', () => {
      let message = JSON.parse(this.widget('MessageField').value);
      let topic = this.widget('PublishTopicField').value;
      ajax.postJson('api/ui-notifications/put', {
        message: message,
        topic: topic
      }).then(() => {
        this._addLogEntry('Message published');
      }).catch((error: AjaxError) => {
        this._addLogEntry('Publish failed: ' + error.errorDo?.message);
      });
    });
  }

  protected _createSampleMessage(): string {
    return JSON.stringify({
      note: 'hi there!',
      timestamp: new Date().getTime()
    });
  }

  protected _addLogEntry(message: string) {
    let logField = this.widget('LogField');
    let log = logField.value || '';
    if (log) {
      log += '\n';
    }
    log += message;
    logField.setValue(log);
  }

  protected _onNotification(message: DoEntity) {
    this._addLogEntry(`Notification received: ${JSON.stringify(message)}`);
  }
}
