var t;
t = 1;
function changerTaille(modif) {
	t = t + modif;
	document.getElementsByTagName("body")[0].style.fontSize = t + "em";
}

function survol(zone, t) {
var pic = document.getElementsByID('hovered');
pic.src = 'areas/' + t + '.png';
zone.onmouseout = function() { pic.drc = 'areas/0.png' };
}

$(function() {
    $.event.special.swipe.durationThreshold = 200;

    // swipe left for next page
    $( "#bloc_page" ).on( "swipeleft", function( event ) {
            window.location.href = $("#gauche a:nth-child(2)").attr("href");
    });

    // swipe right for previous page
    $( "#bloc_page" ).on( "swiperight", function( event ) {
            window.location.href = $("#gauche a:nth-child(1)").attr("href");
    });
});
