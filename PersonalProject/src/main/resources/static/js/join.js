// join.html 회원가입 페이지 JavaScript
// - 비밀번호 확인
// - 가입 유형(USER/HOSPITAL)에 따른 닉네임 입력칸 표시/숨김
(function () {
    function checkPasswordMatch() {
        const passwordInput = document.getElementById('password');
        const confirmInput = document.getElementById('password-confirm');
        const messageSpan = document.getElementById('password-message');

        if (!passwordInput || !confirmInput || !messageSpan) {
            return;
        }

        if (passwordInput.value === confirmInput.value) {
            messageSpan.textContent = '';
            confirmInput.setCustomValidity('');
        } else {
            messageSpan.textContent = '비밀번호가 일치하지 않습니다.';
            confirmInput.setCustomValidity('비밀번호 불일치');
        }
    }

    function toggleInputFields() {
        const nicknameField = document.getElementById('nickname-field');
        const nicknameInput = document.getElementById('nickname');
        const userRadio = document.getElementById('user');

        if (!nicknameField || !nicknameInput || !userRadio) {
            return;
        }

        if (userRadio.checked) {
            nicknameField.style.display = 'block';
            nicknameInput.required = true;
        } else {
            nicknameField.style.display = 'none';
            nicknameInput.required = false;
            nicknameInput.value = '';
        }
    }

    // 기존 HTML에 onchange="toggleInputFields()"가 남아 있어도 작동하도록 전역에 등록
    window.toggleInputFields = toggleInputFields;

    document.addEventListener('DOMContentLoaded', function () {
        const passwordInput = document.getElementById('password');
        const confirmInput = document.getElementById('password-confirm');
        const userRadio = document.getElementById('user');
        const hospitalRadio = document.getElementById('hospital');

        if (passwordInput) {
            passwordInput.addEventListener('input', checkPasswordMatch);
        }

        if (confirmInput) {
            confirmInput.addEventListener('input', checkPasswordMatch);
        }

        if (userRadio) {
            userRadio.addEventListener('change', toggleInputFields);
        }

        if (hospitalRadio) {
            hospitalRadio.addEventListener('change', toggleInputFields);
        }

        toggleInputFields();
        checkPasswordMatch();
    });
})();
