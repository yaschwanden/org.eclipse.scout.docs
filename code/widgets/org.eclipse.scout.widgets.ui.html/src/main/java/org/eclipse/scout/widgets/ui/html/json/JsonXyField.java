package org.eclipse.scout.widgets.ui.html.json;

import org.eclipse.scout.rt.ui.html.IUiSession;
import org.eclipse.scout.rt.ui.html.json.IJsonAdapter;
import org.eclipse.scout.rt.ui.html.json.form.fields.JsonFormField;
import org.eclipse.scout.widgets.client.ui.forms.fields.xy.IXyField;

public class JsonXyField extends JsonFormField<IXyField> {

  public JsonXyField(IXyField model, IUiSession uiSession, String id, IJsonAdapter<?> parent) {
    super(model, uiSession, id, parent);
  }

  @Override
  public String getObjectType() {
    return "widgets.XyField";
  }

}
