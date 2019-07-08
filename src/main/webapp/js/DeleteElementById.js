function del(id) {
    let element = document.getElementById(id);
    if (element != null) {
        element.parentNode.removeChild(element);
    }
}