/** Franchisee Inquiry Button */
const inquiryBtn = document.querySelector("button[name=franchiseInquiry]");
if(inquiryBtn) {
    inquiryBtn.addEventListener("click", async () => {
        inquiryBtn.disabled = true;
        try {
            // 유효성 검사
            // if(!fncValidateFranchiseeInquiry()) return;

            console.log("가맹문의 등록 시작");

            const frm = document.querySelector("form");
            const formData = new FormData(frm).entries();
            const reqData = Object.fromEntries(formData);

            const res = await fetch("/api/franchiseInquriy/create", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(reqData)
            });

            if(!res.ok) {
                const errorData = await res.json();
                throw new Error(errorData.message || "요청 처리 실패");
            }

            const data = await res.json();
            alert(data.message);
            location.replace("/franchies/inquiry");
        }catch (e) {
            alert(e.message || "요청 처리 실패");
            location.replace("/franchies/inquiry");
        }finally {
            inquiryBtn.disabled = false;
        }
    });
}

function fncValidateFranchiseeInquiry() {
    const agreeCheck = document.getElementById("AgreePrivacy").checked;
    if(!agreeCheck){
        alert("개인정보 처리방침에 동의해 주세요. ");
        $("#AgreePrivacy").focus();
        return false;
    }
    const reqName = $("#reqName");
    if(reqName.val() == '' || reqName.val() == undefined){
        alert("이름은 필수 입력 사항입니다. ");
        reqName.focus();
        return false;
    }
    const reqPhone1 = $("#reqPhoneNum1");
    const reqPhone2 = $("#reqPhoneNum2");
    const reqPhone3 = $("#reqPhoneNum3");
    if(reqPhone1.val() == '' || reqPhone2.val() == '' || reqPhone3.val() == ''){
        alert("전화번호는 필수 입력 사항입니다. ");
        reqPhone1.focus();
        return false;
    }
    const addr1 = $("#reqAddr1");
    const addr2 = $("#reqAddr2");
    if(addr1.val() == '' || addr2.val() == ''){
        alert("창업희망지역은 필수 입력 사항입니다. ");
        addr1.focus();
        return false;
    }
    const isShop = $("input[name=isShop]:checked");
    if(isShop.val() == undefined) {
        alert("점포유무는 필수 입력 사항입니다. ");
        isShop.focus();
        return false;
    }
    if(isShop.val() == "Y") {
        const shopSize = $("input[name=shopSize]");
        if(shopSize.val() == undefined) {
            alert("점포크기는 필수 입력 사항입니다. ");
            shopSize.focus();
            return false;
        }
    }else{
        $("input[name=shopSize]").val(0);
    }
    const operMethod = $("input[name=operMethod]:checked");
    if(operMethod.val() == undefined) {
        alert("운영방식은 필수 입력 사항입니다. ");
        operMethod.focus();
        return false;
    }
    const operExp = $("input[name=operExp]:checked");
    if(operExp.val() == undefined) {
        alert("운영경험은 필수 입력 사항입니다. ");
        operExp.focus();
        return false;
    }
    return true;
}

// 이메일 선택
const selDomain = document.querySelector("select[name=selectEmailDomain]");
const emailDomain = document.querySelector("input[name=emailDomain]");
if(selDomain) {
    selDomain.addEventListener("change", () => {
        const domain = selDomain.value;
        if(domain == "none") {
            emailDomain.value = "";
        }else{
            emailDomain.value = domain;
        }
    });
}