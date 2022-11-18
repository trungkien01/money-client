function checkFullName(fieldConfirmPassword) {
    debugger;
    let fullName = document.getElementById("fullName");
    if (fieldConfirmPassword.value == null || fieldConfirmPassword.value === "" || fullName.value === "") {
        fieldConfirmPassword.setCustomValidity("Họ tên không được để trống!");
    } else {
        fieldConfirmPassword.setCustomValidity("");
    }
}
function checkAge(fieldConfirmPassword) {
    debugger;
    let age = document.getElementById("age");
    if (fieldConfirmPassword.value == null || fieldConfirmPassword.value === "" || age.value === "") {
        fieldConfirmPassword.setCustomValidity("Tuổi không được để trống!");
    } else {
        fieldConfirmPassword.setCustomValidity("");
    }
}
function checkDateOfBirth(fieldConfirmPassword) {
    if (fieldConfirmPassword.value == null || fieldConfirmPassword.value === "") {
        fieldConfirmPassword.setCustomValidity("Năm sinh không được để trống!");
    } else {
        fieldConfirmPassword.setCustomValidity("");
    }
}
function checkPhone(fieldConfirmPassword) {
    if (fieldConfirmPassword.value == null || fieldConfirmPassword.value === "") {
        fieldConfirmPassword.setCustomValidity("Số điện thoại không được để trống!");
    } else {
        fieldConfirmPassword.setCustomValidity("");
    }
}
function checkAddress(fieldConfirmPassword) {
    if (fieldConfirmPassword.value == null || fieldConfirmPassword.value === "") {
        fieldConfirmPassword.setCustomValidity("Địa chỉ không được để trống!");
    } else {
        fieldConfirmPassword.setCustomValidity("");
    }
}
function checkCmndCccd(fieldConfirmPassword) {
    if (fieldConfirmPassword.value == null || fieldConfirmPassword.value === "") {
        fieldConfirmPassword.setCustomValidity("Cmnd/Cccd không được để trống!");
    } else {
        fieldConfirmPassword.setCustomValidity("");
    }
}