/* 메뉴 수정 버튼 */
const btnUpdateMenu = document.querySelector("button[name=updateMenu]");
if (btnUpdateMenu) {
    btnUpdateMenu.addEventListener("click", async (e) => {
        // 중복 클릭 방지
        e.target.disabled = true;

        // 폼 데이터 가져오기
        const form = document.getElementById("frm");
        const formData = new FormData(form);

        // 체크박스 값 처리 (체크 안 된 경우 'N'으로 설정)
        formData.set("signatureYN", form.querySelector("#signature").checked ? "Y" : "N");
        formData.set("bestYN", form.querySelector("#best").checked ? "Y" : "N");
        formData.set("newYN", form.querySelector("#new").checked ? "Y" : "N");

        // CKEditor 데이터 가져오기
        if (window.editor) {
            formData.set("description", window.editor.getData());
        }

        try {
            const res = await fetch("/api/menu/update", {
                method: "POST",
                body: formData
            });
            await handleResponse(res);
        }catch (error) {
            console.error(error);
            alert("메뉴 수정 중 오류가 발생했습니다.");
        }finally {
            e.target.disabled = false;
        }
    });
}

/* 메뉴 등록 버튼 */
const btnCreateMenu = document.querySelector("button[name=btnCreateMenu]");
if (btnCreateMenu) {
    btnCreateMenu.addEventListener("click", async (e) => {
        // 중복 클릭 방지
        e.target.disabled = true;

        // 폼 데이터 가져오기
        const form = document.getElementById("frm");
        const formData = new FormData(form);

        // 체크박스 값 처리 (체크 안 된 경우 'N'으로 설정)
        formData.set("signatureYN", form.querySelector("#signature").checked ? "Y" : "N");
        formData.set("bestYN", form.querySelector("#best").checked ? "Y" : "N");
        formData.set("newYN", form.querySelector("#new").checked ? "Y" : "N");

        // CKEditor 데이터 가져오기
        if (window.editor) {
            formData.set("description", window.editor.getData());
        }

        try {
            const res = await fetch("/api/menu/create", {
                method: "POST",
                body: formData
            });
            await handleResponse(res, "/manage/menu");
        }catch (error) {
            console.error(error);
            alert("메뉴 수정 중 오류가 발생했습니다.");
        }finally {
            e.target.disabled = false;
        }
    });
}

/* 매장 수정 버튼 */
const btnUpdateShop = document.querySelector("button[name=updateShop]");
if (btnUpdateShop) {
    btnUpdateShop.addEventListener("click", async (e) => {
        // 중복 클릭 방지
        e.target.disabled = true;
        try {
            // 폼 데이터 가져오기
            const form = document.querySelector("#frm");
            const formData = new FormData(form);
            formData.set("takeoutYN", form.querySelector("#takeout").checked ? "Y" : "N");
            formData.set("seatYN", form.querySelector("#seat").checked ? "Y" : "N");
            formData.set("wifiYN", form.querySelector("#wifi").checked ? "Y" : "N");
            formData.set("deliveryYN", form.querySelector("#delivery").checked ? "Y" : "N");
            formData.set("parkingYN", form.querySelector("#parking").checked ? "Y" : "N");
            formData.set("desertYN", form.querySelector("#desert").checked ? "Y" : "N");
            formData.set("easyPayYN", form.querySelector("#easyPay").checked ? "Y" : "N");

            const data = Object.fromEntries(formData.entries())
            const id = form.id.value;

            // 폼 데이터 전송
            const res = await fetch("/api/shop/update/"+id, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(data)
            })
            await handleResponse(res);
        } catch (error) {
            console.log(error.message);
            alert("매장 수정 중 오류가 발생했습니다.");
        } finally {
            e.target.disabled = false;
        }
    });
}

/* 매장 상태 변경(게시, 숨김) */
const btnChgShopStatus = document.getElementsByName("btnChgShopStatus");
if (btnChgShopStatus.length > 0){
    btnChgShopStatus.forEach(button => {
        button.addEventListener("click", async (e) => {
            // 중복 클릭 방지
            e.target.disabled = true;
            const id = e.target.dataset.id;
            try {
                const res = await fetch("/api/shop/update/"+id, {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        hidden : e.target.dataset.status
                    })
                });
                await handleResponse(res);
            } catch (error) {
                console.log(error.message);
                alert("매장 상태 변경 중 오류가 발생했습니다")
            } finally {
                e.target.disabled = false;
            }
        });
    });
}

/* 가맹 문의 처리 버튼 */
const btnFrStatus = document.getElementsByName("btnFrStatus");
if (btnFrStatus.length > 0){
    btnFrStatus.forEach(button => {
        button.addEventListener("click", async (e) => {
            // 중복 클릭 방지
            e.target.disabled = true;
            try {
                const res = await fetch("/api/franchise/update/"+e.target.dataset.id, {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        confirmYN : e.target.dataset.status
                    })
                });
                await handleResponse(res);
            } finally {
                e.target.disabled = false;
            }
        })
    })
}


/* 가맹 삭제 처리 버튼 */
const btnFrDelete = document.getElementsByName("btnFrDelete");
if (btnFrDelete.length > 0){
    btnFrDelete.forEach(button => {
        button.addEventListener("click", async (e) => {
            if(!confirm("가맹문의를 삭제하시겠습니까? \n삭제시 내용을 확인할 수 없습니다.")) return;
            // 중복 클릭 방지
            e.target.disabled = true;
            try {
                const res = await fetch("/api/franchise/delete/"+e.target.dataset.id, {
                    method: "DELETE",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        hidden : e.target.dataset.hidden
                    })
                });
                await handleResponse(res);
            } finally {
                e.target.disabled = false;
            }
        })
    })
}

/* 게시판 등록 처리 버튼 */
const btnCreateBoard = document.getElementsByName("btnCreateBoard");
if (btnCreateBoard.length > 0){
    btnCreateBoard.forEach(button => {
        button.addEventListener("click", async (e) => {
            // 중복 클릭 방지
            e.target.disabled = true;
            try {
                const form = document.getElementById("frm");
                const formData = new FormData(form);
                // CKEditor 데이터 가져오기
                if (window.editor) {
                    formData.set("content", window.editor.getData());
                }
                const id = button.dataset.id;

                const res = await fetch("/api/board/create", {
                    method: "POST",
                    body: formData
                });
                await handleResponse(res, "/manage/board");
            } catch (e) {
                console.log(e.message);
                alert("게시판 등록 중 오류가 발생했습니다.");
            } finally {
                e.target.disabled = false;
            }
        });
    });
}

/* 게시판 수정 처리 버튼 */
const btnUpdateBoard = document.getElementsByName("btnUpdateBoard");
if (btnUpdateBoard.length > 0){
    btnUpdateBoard.forEach(button => {
        button.addEventListener("click", async (e) => {
            // 중복 클릭 방지
            e.target.disabled = true;
            try {
                const form = document.getElementById("frm");
                const formData = new FormData(form);
                // CKEditor 데이터 가져오기
                if (window.editor) {
                    formData.set("content", window.editor.getData());
                }
                const id = button.dataset.id;

                const res = await fetch("/api/board/update/" + id, {
                    method: "POST",
                    body: formData
                });
                await handleResponse(res);
            } catch (e) {
                console.log(e.message);
                alert("게시판 수정 중 오류가 발생했습니다.");
            } finally {
                e.target.disabled = false;
            }
        });
    });
}

/** Response 처리 */
async function handleResponse(res, path = null) {
    const data = await res.json();
    if (data.success) {
        alert(data.message);
        if(path != null) {
            window.location.href = path;
        } else {
            window.location.reload();
        }
    } else {
        alert(data.message);
    }
}