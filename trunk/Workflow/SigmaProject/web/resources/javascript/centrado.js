function getWindowHeight() {
    var windowHeight = 0;
    if (typeof(window.innerHeight) == 'number') {
        windowHeight = window.innerHeight;
    }
    else {
        if (document.documentElement && document.documentElement.clientHeight) {
            windowHeight = document.documentElement.clientHeight;
        }
        else {
            if (document.body && document.body.clientHeight) {
                windowHeight = document.body.clientHeight;
            }
        }
    }
    return windowHeight;
}

function getWindowWidth() {
    var windowWidtht = 0;
    if (typeof(window.innerWidth) == 'number') {
        windowWidtht = window.innerWidth;
    }
    else {
        if (document.documentElement && document.documentElement.clientWidth) {
            windowWidtht = document.documentElement.clientWidth;
        }
        else {
            if (document.body && document.body.clientWidth) {
                windowWidtht = document.body.clientWidth;
            }
        }
    }
    return windowWidtht;
}

function setContent() {
    if (document.getElementById) {
        var windowHeight = getWindowHeight();
        if (windowHeight > 0) {
            //var contentElement = document.getElementById('pagina');
            //contentElement.style.width = '90%';
            var contentHeight = contentElement.offsetHeight;
            if (windowHeight - contentHeight > 0) {
                contentElement.style.position = 'relative';
            }
            else {
                contentElement.style.position = 'static';
            }
        }
    }
}

window.onresize = function() {
    setContent();
}

function alComienzo() {
    redondear();
}