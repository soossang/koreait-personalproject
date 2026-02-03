// map.js
// 이 코드는 HTML에 <div id="map" style="width:100%;height:400px;"></div> 와 같은 요소가 있어야 합니다.

document.addEventListener('DOMContentLoaded', function() {
    const mapContainer = document.getElementById('map'); // 지도를 표시할 div
    if(mapContainer) {
        const mapOption = {
            center: new kakao.maps.LatLng(36.3504119, 127.3845475), // 대전 시청을 중심으로 설정
            level: 5 // 지도의 확대 레벨
        };

        // 지도를 생성합니다
        const map = new kakao.maps.Map(mapContainer, mapOption);

        // 예시: 지도에 마커 표시하기
        const markerPosition  = new kakao.maps.LatLng(36.3504119, 127.3845475);

        // 마커를 생성합니다
        const marker = new kakao.maps.Marker({
            position: markerPosition
        });

        // 마커가 지도 위에 표시되도록 설정합니다
        marker.setMap(map);

        // 여기에 서버에서 병원 목록 데이터를 받아와서 지도에 여러 개 마커를 표시하는 로직을 추가할 수 있습니다.
        // 예: fetch('/api/hospitals?subject=내과')
        //      .then(response => response.json())
        //      .then(data => {
        //          data.forEach(hospital => {
        //              // 병원 위치에 마커 생성 및 표시
        //          });
        //      });
    }
});