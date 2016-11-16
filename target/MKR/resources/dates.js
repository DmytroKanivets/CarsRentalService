/**
 * Created by Dima on 15-Nov-16.
 */

function changeTypes () {
    var dates = document.getElementsByClassName("dateInput");

    for (var i = 0;i < dates.length; i++) {
        dates[i].setAttribute("type", "date");
    }
}

window.onload = changeTypes;
