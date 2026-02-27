const startBtn = document.getElementById('startBtn');
const timerEl = document.getElementById('timer');

/* START SESSION */
function startSession(equipmentId) {
    fetch(`http://localhost:8081/sessions/start/${equipmentId}`, {
        method: 'POST'
    })
        .then(response => response.json())
        .then(session => {
            console.log("Session started:", session);

            if (session.status === "WAITING") {
                alert("Equipment in use. You are added to the queue.");
            } else if (session.status === "ACTIVE") {
                startTimer(session);
            }
        })
        .catch(err => {
            console.error(err);
            alert("Cannot start session. Equipment may already be in use.");
        });
}

/* BUTON MANUAL */
startBtn.addEventListener('click', () => {
    const equipmentId = document.getElementById('equipmentId').value;

    if (!equipmentId) {
        alert("Enter equipment ID");
        return;
    }

    startSession(equipmentId);
});

/* TIMER */
let countdownInterval;

function startTimer(session) {
    clearInterval(countdownInterval);

    const maxMinutes = session.equipment.maxSessionMinutes;
    let remainingSeconds = maxMinutes * 60;

    timerEl.textContent = formatTime(remainingSeconds);

    countdownInterval = setInterval(() => {
        remainingSeconds--;
        timerEl.textContent = formatTime(remainingSeconds);

        if (remainingSeconds <= 0) {
            clearInterval(countdownInterval);
            alert('Session expired!');
            timerEl.textContent = "00:00";
        }
    }, 1000);
}

function formatTime(seconds) {
    const m = Math.floor(seconds / 60).toString().padStart(2, '0');
    const s = (seconds % 60).toString().padStart(2, '0');
    return `${m}:${s}`;
}

/* QR SCANNER */
function onScanSuccess(decodedText) {
    console.log("QR detected:", decodedText);

    if (!decodedText.startsWith("EQUIPMENT:")) {
        alert("Invalid QR Code");
        return;
    }

    const equipmentId = decodedText.split(":")[1];

    document.getElementById("scan-result").textContent =
        "Equipment detected: " + equipmentId;

    startSession(equipmentId);
}

/* pornim camera */
const html5QrcodeScanner = new Html5QrcodeScanner(
    "qr-reader",   // corect ID-ul
    { fps: 10, qrbox: 250 }
);

html5QrcodeScanner.render(onScanSuccess);