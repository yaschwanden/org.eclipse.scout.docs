widgets.XyField = function() {
  widgets.XyField.parent.call(this);
};
scout.inherits(widgets.XyField, scout.FormField);

widgets.XyField.prototype._render = function() {
  this.addContainer(this.$parent, 'xy-field');
  this.$container.on('click', this._onClick.bind(this));
  this.addLabel();
  this.addMandatoryIndicator();
  this.addField(this.$parent.appendDiv());
  this.addStatus();
};

widgets.XyField.prototype._onClick = function(event) {
  var target = '';
  if (event.target == this.$label[0]) {
    target = 'label';
  }
  else if (event.target == this.$field[0]) {
    target = 'field';
  }
  else if (event.target == this.$mandatory[0]) {
    target = 'mandatoryIndicator';
  }
  else if (event.target == this.$status[0]) {
    target = 'status';
  }
  if (target) {
    scout.MessageBoxes.openOk(this, 'This is the ' + target + '.');
  }
};
