scout.XyField = function() {
  scout.XyField.parent.call(this);
};
scout.inherits(scout.XyField, scout.ValueField);

scout.XyField.prototype._render = function($parent) {
  this.addContainer($parent, 'xy-field');
  this.addLabel();
  this.addMandatoryIndicator();
  this.addField($parent.appendDiv());
  this.addStatus();
};
