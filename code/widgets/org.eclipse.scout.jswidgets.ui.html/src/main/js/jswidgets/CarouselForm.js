jswidgets.CarouselForm = function() {
  jswidgets.CarouselForm.parent.call(this);
};
scout.inherits(jswidgets.CarouselForm, scout.Form);

jswidgets.CarouselForm.prototype._init = function(model) {
  jswidgets.CarouselForm.parent.prototype._init.call(this, model);

  var bodyGrid = new scout.HorizontalGroupBoxBodyGrid();
  bodyGrid.validate(this.rootGroupBox);
  bodyGrid = new scout.HorizontalGroupBoxBodyGrid();
  bodyGrid.validate(this.widget('DetailBox'));
  bodyGrid = new scout.HorizontalGroupBoxBodyGrid();
  bodyGrid.validate(this.widget('PropertiesBox'));

  var carousel = this.widget('Carousel');
  scout.arrays.ensure(carousel.widgets).forEach(function(widget) {
    if (widget.rootGroupBox) {
      var grid = new scout.HorizontalGroupBoxBodyGrid();
      grid.validate(widget.rootGroupBox);
    }
  });

  var statusEnabledField = this.widget('StatusEnabledField');
  statusEnabledField.setValue(carousel.statusEnabled);
  statusEnabledField.on('propertyChange', this._onStatusEnabledPropertyChange.bind(this));
};

jswidgets.CarouselForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.CarouselForm');
};

jswidgets.CarouselForm.prototype._onStatusEnabledPropertyChange = function(event) {
  if (scout.arrays.containsAny(event.changedProperties, ['value'])) {
    var enabled = event.source.value;
    this.widget('Carousel').setStatusEnabled(enabled);
  }
};
