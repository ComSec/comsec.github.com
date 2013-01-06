(function() {
    var href = window.location.href;
    var temp = document.createElement('a');
    temp.href = href;

    var host = temp.host;
    var prot = temp.protocol;
    var path = temp.pathname;
    if ((host !== 'comsec.uwaterloo.ca') &&
        (prot === 'http:' || prot === 'https:')) {
        window.location = 'http://comsec.uwaterloo.ca' + path;
    }
}());
