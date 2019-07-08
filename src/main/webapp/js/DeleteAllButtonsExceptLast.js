window.onload = function () {
    let deleteButtons = document.getElementsByClassName("delete_button");
    let editButtons = document.getElementsByClassName("edit_button");
    let allMessages = document.getElementsByClassName("message");
    while (deleteButtons[1]) {
        deleteButtons[0].parentNode.removeChild(deleteButtons[0]);
        editButtons[0].parentNode.removeChild(editButtons[0]);
    }
    if (deleteButtons.length > 0 && !(allMessages[allMessages.length - 1].isEqualNode(deleteButtons[0].parentNode.parentNode))) {
        deleteButtons[0].parentNode.removeChild(deleteButtons[0]);
        editButtons[0].parentNode.removeChild(editButtons[0]);
    }
};