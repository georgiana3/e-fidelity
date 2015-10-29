function overlay($self, isDisable){
	var existingOverlayId = "#" + $self.attr('id') + "Overlay";
	var $existingOverlay = $(existingOverlayId);
	
	if(isDisable){
		if ($existingOverlay.length > 0) 
			$existingOverlay.remove();
		return;
	}
	
    // get it's parent label element
   if (!($existingOverlay.length > 0)) {
    var $parent = $self.closest(".form-group")
    // create an overlay
    , $overlay = $("<div id='" + $self.attr('id') + "Overlay' />");

    // style the overlay
    $overlay.css({
        // position the overlay in the same real estate as the original parent element 
        position: "absolute"
        , top: $parent.offset().top
        , left: $parent.offset().left
        , width: $parent.outerWidth()
        , height: $parent.outerHeight()
        , zIndex: 10000
        // IE needs a color in order for the layer to respond to mouse events
        , backgroundColor: "#fff"
        // set the opacity to 0, so the element is transparent
        , opacity: 0
    });
    
    $("body").append($overlay);
	}
  }

function setCheckBoxStateByContract($checkbox, state) {
    if (state === "undefined" || state === null || state === "") {
        $checkbox.prop("checked", false);
        overlay($checkbox, true); // clickable
        $checkbox.parent().removeClass("stroked");
        $checkbox.parent().removeClass("italic");
    } else if (state) {
        $checkbox.prop("checked", true);
        overlay($checkbox); // unclickable
        $checkbox.parent().removeClass("stroked");
        $checkbox.parent().addClass("italic");
    } else {
        $checkbox.prop("checked", false);
        overlay($checkbox); // unclickable
        $checkbox.parent().addClass("stroked");
        $checkbox.parent().addClass("italic");
    }
}