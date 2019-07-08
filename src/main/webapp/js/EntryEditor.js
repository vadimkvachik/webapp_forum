function showEditForm(id) {
    let entry = document.getElementById(id);
    let form = entry.getElementsByClassName("edit_form")[0];
    let input = form.getElementsByClassName("new_text")[0];
    let oldText = entry.getElementsByClassName("name")[0];
    if (form.style.display === "block") {
        oldText.style.display = "block";
        form.style.display = "none";
    } else {
        form.style.display = "block";
        oldText.style.display = "none";
        input.value = oldText.innerText;
    }
}
