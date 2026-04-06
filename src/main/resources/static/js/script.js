// ============================
// Student Management System JS
// ============================

// Auto-hide flash messages after 3 seconds
document.addEventListener("DOMContentLoaded", function () {
    const messages = document.querySelectorAll(".flash-message");
    messages.forEach(function (msg) {
        setTimeout(() => msg.remove(), 3000);
    });
});
