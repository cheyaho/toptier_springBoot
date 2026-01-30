/* Main Visual Slider Script*/
let video, video_m;
let mainVisual = $("#MainVisual").bxSlider({
    auto: true,
    autoControls: true,
    pager: true,
    mode: 'fade',
    speed: 1000,
    pause: 7000,
    adaptiveHeight: true,
    onSliderLoad : function(){
        if(Mobile()){
            video_m = document.querySelector('#MainVideo_0_m');
            video_m.play();
        }else{
            video = document.querySelector('#MainVideo_0');
            video.play();
        }
        $("#Visual_0 .subCopy").addClass("aniOn");
        $("#Visual_0 .mainCopy").addClass("aniOn");
    },
    onSlideBefore : function($slideElement, oldIndex, newIndex){
        if(Mobile()){
            video_m = document.querySelector('#MainVideo_' + newIndex + "_m");
            video_m.play();
        }else{
            video = document.querySelector('#MainVideo_' + newIndex);
            video.play();
        }
        $("#Visual_"+newIndex+" .subCopy").removeClass("aniOn");
        $("#Visual_"+newIndex+" .mainCopy").removeClass("aniOn");
    },
    onSlideAfter: function($slideElement, oldIndex, newIndex){
        $("#Visual_"+newIndex+" .subCopy").addClass("aniOn");
        $("#Visual_"+newIndex+" .mainCopy").addClass("aniOn");
    }
});

/** Menu Slider Script **/
let menuSlider = $("#MenuList").bxSlider({
    minSlides: 4,
    maxSlides: 4,
    slideWidth: 285,
    slideMargin: 20,
    pager: false,
    infiniteLoop: false,
    nextSelector: '#MenuSlideNext',
    prevSelector: '#MenuSlidePrev'
});

/** Menu Load Handler **/
const signatureBtn = document.querySelector("button[name=signature]");
signatureBtn.addEventListener('click', async () => {
    try {
        signatureBtn.disabled = true;
        const url = "/api/menu/signature";
        const res = await fetch(url);
        if (!res.ok) throw new Error(`HTTP error : ${res.status}`);
        const menu = await res.json();
        fncSetMenu(menu);
    }catch (e) {
        alert("시그니쳐 메뉴 조회에 싪패했습니다.");
    }finally {
        signatureBtn.disabled = false;
    }
});

const coffeeBtn = document.querySelector("button[name=coffee]");
coffeeBtn.addEventListener('click', async () => {
    try {
        coffeeBtn.disabled = true;
        const url = "/api/menu/categories";
        const res = await fetch(url+"?categoryIds=1&categoryIds=2");
        if (!res.ok) throw new Error(`HTTP error : ${res.status}`);
        const menu = await res.json();
        fncSetMenu(menu);
    }catch (e) {
        alert("메뉴 조회에 싪패했습니다.");
    }finally {
        coffeeBtn.disabled = false;
    }
});

const teaBtn = document.querySelector("button[name=tea]");
const menuContainer = document.querySelector("#MenuList");
teaBtn.addEventListener('click', async () => {
    try {
        teaBtn.disabled = true;
        const url = "/api/menu/category";
        const res = await fetch(url+"?categoryId=3");
        if (!res.ok) throw new Error(`HTTP error : ${res.status}`);
        const menu = await res.json();
        fncSetMenu(menu);
    }catch (e) {
        alert("메뉴 조회에 싪패했습니다.");
    }finally {
        teaBtn.disabled = false;
    }
});

function fncSetMenu(menuList) {
    let fragment = document.createDocumentFragment();
    for(let i=0; i<menuList.length; i++) {
        const menu = menuList[i];
        const menuItem = document.createElement("div");
        menuItem.className = "menuItem";

        const menuImg = document.createElement("div");
        menuImg.className = "menuImg";

        const img = document.createElement("img");
        img.src = encodeURI(menu.imagePath);
        img.alt = menu.name_kr ?? "";
        menuImg.appendChild(img);

        const floatItem = document.createElement("div");
        floatItem.className = "floatItem";

        if(menu.signatureYN === "Y") {
            const sigSpan = document.createElement("span");
            sigSpan.className = "ico signature";

            const sigImg = document.createElement("img");
            sigImg.src = "/images/common/SVG/ico_signature.svg";
            sigImg.alt = "시그니쳐";

            sigSpan.appendChild(sigImg);
            floatItem.appendChild(sigSpan);
        }
        if(menu.bestYN === "Y") {
            const bestSpan = document.createElement("span");
            bestSpan.className = "ico best";

            const bestImg = document.createElement("img");
            bestImg.src = "/images/common/SVG/ico_best.svg";
            bestImg.alt = "Best";

            bestSpan.appendChild(bestImg);
            floatItem.appendChild(bestSpan);
        }
        const menuInfo = document.createElement("div");
        menuInfo.className = "menuInfo";

        const menuName = document.createElement("div");
        menuName.className = "menuName";
        const nameKr = document.createElement("p");
        nameKr.className = "kr";
        nameKr.textContent = menu.name_kr ?? "";
        const nameEn = document.createElement("p");
        nameEn.className = "en";
        nameEn.textContent = menu.name_en ?? "";

        menuName.appendChild(nameKr);
        menuName.appendChild(nameEn);
        menuInfo.appendChild(menuName);

        menuItem.appendChild(menuImg);
        menuItem.appendChild(floatItem);
        menuItem.appendChild(menuInfo);

        fragment.appendChild(menuItem);
    }

    // menuSlider.destroySlider();
    menuContainer.innerHTML = "";
    menuContainer.appendChild(fragment);
    menuSlider.reloadSlider();
}