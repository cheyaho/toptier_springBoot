/* 공통 스크립트 */
$(function(){
    $("header nav ul li").hover(function(){
        $(this).addClass("on");
    }, function(){
        $(this).removeClass("on");
    })
});

// Mobile 체크
function Mobile(){
    return /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent);}