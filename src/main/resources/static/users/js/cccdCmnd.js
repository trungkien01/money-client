// thay đổi trường nhập
var choose = document.getElementById("choose");
var dv1 = document.getElementById("div1");
var dv2 = document.getElementById("div2");
function choose1(e){
    if(choose.value === "CMND"){
        dv1.style.display = "block";
        dv2.style.display = "none";
    } else if(choose.value === "CCCD" ){
        dv1.style.display = "none";
        dv2.style.display = "block";
    }
}