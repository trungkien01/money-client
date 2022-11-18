function chooseFile(fileInput) {
    debugger;
    if(fileInput.files && fileInput.files[0]){
        var reader = new FileReader();
        reader.onload = function (e) {
            $('#image').attr('src', e.target.result);
        }
        reader.readAsDataURL(fileInput.files[0]);
    }
}

function chooseFile1(fileInput1) {
    if(fileInput1.files && fileInput1.files[0]){
        var reader1 = new FileReader();
        reader1.onload = function (e) {
            $('#image1').attr('src', e.target.result);
        }
        reader1.readAsDataURL(fileInput1.files[0]);
    }
}

function chooseFile2(fileInput2) {
    if(fileInput2.files && fileInput2.files[0]){
        var reader2 = new FileReader();
        reader2.onload = function (e) {
            $('#image2').attr('src', e.target.result);
        }
        reader2.readAsDataURL(fileInput2.files[0]);
    }
}


//// JS thông báo
//function toast1({
//                    title = '',
//                    message = '',
//                    type = 'success',
//                    duration = 3000
//                }) {
//    const main = document.getElementById('toast1');
//    if(main){
//        const toast =  document.createElement('div');
//
//        const autoRemove = setTimeout(function () {
//            main.removeChild(toast);
//        }, duration + 5000)
//
//        toast.onclick = function (e) {
//            if(e.target.close('.toast_close')){
//                main.removeChild(toast);
//                clearTimeout(autoRemove);
//            }
//        }
//        const icons ={
//            success: 'fas fa-check-circle',
//        };
//        const icon = icons[type];
//        const delay = (duration / 1000).toFixed(2);
//        toast.classList.add('toast1', `toast--${type}`);
//        toast.style.animation = `slideInLeft ease .4s, fadeOut linear 1s ${delay}s forwards`;
//        toast.innerHTML = `
//                <div class="toast_icon">
//                    <i class="${icon}"></i>
//                </div>
//                <div class="toast_body">
//                    <h3 class="toast_title">${title}</h3>
//                    <p class="toast_msg">${message}</p>
//                </div>
//                <div class="toast_close">
//                    <i class="fas fa-times"></i>
//                </div>
//            `;
//        main.append(toast);
//    }
//}
//
//function showSuccess() {
//    toast1({
//        title: 'Thành Công',
//        message: 'Bạn đã gửi form thành công',
//        type: 'success',
//        duration: 1000
//    });
//}
//
//
//// JS disabled input
//var image1 = document.getElementById("img");
//var name1 = document.getElementById("name");
//var smgName1 = document.getElementById("smgName1");
//var age1 = document.getElementById("age");
//var name2 = document.getElementById("name1");
//var age2 = document.getElementById("age1");
//var date1 = document.getElementById("date");
//var  gender1 = document.getElementById("gender");
//var phone1 = document.getElementById("phone");
//var address1 = document.getElementById("address");
//var phone2 = document.getElementById("phone1");
//var address2 = document.getElementById("address1");
//var submit1 = document.getElementById("submit1");
//var pSubmit = document.getElementById("p-submit");
//debugger;
//
//function validatePersonInfor() {
//    if(name1.value == ""){
//        smgName1.innerHTML = "Bạn chưa nhaajo họ tê!"
//    }else {
//        smgName1.innerHTML = ""
//    }
//}
//
//    // else if(name1.value == name2.value && age1.value == age2.value && phone1.value == phone2.value && address1.value == address2.value){
//    //     submit1.style.display = "block";
//    // }
////
//// submit1.onclick = function(e){
////     submit1.style.display = "none";
////     pSubmit.style.display = "block";
//// }



