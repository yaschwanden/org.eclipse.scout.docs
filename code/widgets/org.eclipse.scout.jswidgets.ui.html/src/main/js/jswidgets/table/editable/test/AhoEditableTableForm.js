jswidgets.AhoEditableTableForm = function() {
  jswidgets.AhoEditableTableForm.parent.call(this);

  this.rowNo = 0;
  this.groupNo = 0;
};
scout.inherits(jswidgets.AhoEditableTableForm, scout.Form);

jswidgets.AhoEditableTableForm.GROUP_SIZE = 2;

jswidgets.AhoEditableTableForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.AhoEditableTableForm');
};

jswidgets.AhoEditableTableForm.prototype._init = function(model) {
  jswidgets.AhoEditableTableForm.parent.prototype._init.call(this, model);

  this.table = this.widget('Table');


  this.table.insertRows([this._createRow(), this._createRow(), this._createRow()]);
};

jswidgets.AhoEditableTableForm.prototype._createRow = function() {
  var locales = jswidgets.LocaleLookupCall.DATA.map(function(lookupRow) {
    return lookupRow[0];
  });
  var stringValue = 'Row #' + this.rowNo;
  var smartValue = locales[this.rowNo % locales.length];
  return {
    cells: [stringValue, smartValue]
  };
};
