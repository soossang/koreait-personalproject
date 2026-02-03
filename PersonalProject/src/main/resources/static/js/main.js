// 문서가 로드되면 실행될 함수
document.addEventListener('DOMContentLoaded', function() {
    console.log("병원 공유 게시판 사이트가 시작되었습니다.");

    // 예시: 로그인 버튼 클릭 시 이벤트
    const loginButton = document.querySelector('#login-btn');
    if (loginButton) {
        loginButton.addEventListener('click', function() {
            const username = document.querySelector('#username').value;
            if (!username) {
                alert('아이디를 입력해주세요!');
            }
        });
    }

    // 예시: 회원가입 시 비밀번호 확인
    const password = document.querySelector('#password');
    const passwordConfirm = document.querySelector('#password-confirm');
    if(password && passwordConfirm) {
        passwordConfirm.addEventListener('keyup', function() {
            const message = document.querySelector('#password-message');
            if (password.value === passwordConfirm.value) {
                message.textContent = '비밀번호가 일치합니다.';
                message.style.color = 'green';
            } else {
                message.textContent = '비밀번호가 일치하지 않습니다.';
                message.style.color = 'red';
            }
        });
    }
});