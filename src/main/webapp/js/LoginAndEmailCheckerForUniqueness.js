let logins = [];
let emails = [];
let loginInput;
let emailInput;
let messageFromLoginSpan;
let messageFromEmailSpan;

window.onload = function () {
    loginInput = document.getElementById("loginInput");
    emailInput = document.getElementById("emailInput");
    messageFromLoginSpan = document.getElementById("messageFromLogin");
    messageFromEmailSpan = document.getElementById("messageFromEmail");
    loginInput.addEventListener("keyup", checkLogin);
    emailInput.addEventListener("keyup", checkEmail);
};

function checkLogin() {
    if (loginInput.value !== "") {
        for (let i = 0; i < logins.length; i++) {
            if (loginInput.value.toLowerCase() === logins[i].toLowerCase()) {
                loginInput.setCustomValidity("Invalid");
                messageFromLoginSpan.style.color = "red";
                messageFromLoginSpan.innerText = "Занято";
                return;
            } else {
                loginInput.setCustomValidity("");
                messageFromLoginSpan.style.color = "green";
                messageFromLoginSpan.innerText = "";
            }
        }
    } else {
        messageFromLoginSpan.innerText = "";
    }
}

function checkEmail() {
    if (emailInput.value !== "") {
        for (let i = 0; i < emails.length; i++) {
            if (emailInput.value.toLowerCase() === emails[i].toLowerCase()) {
                emailInput.setCustomValidity("Invalid");
                messageFromEmailSpan.style.color = "red";
                messageFromEmailSpan.innerText = "Занято";
                return;
            } else {
                emailInput.setCustomValidity("");
                messageFromEmailSpan.style.color = "green";
                messageFromEmailSpan.innerText = "";
            }
        }
    } else {
        messageFromEmailSpan.innerText = "";
    }
}

function addLogin(login) {
    logins.push(login)
}
function addEmail(email) {
    emails.push(email)
}