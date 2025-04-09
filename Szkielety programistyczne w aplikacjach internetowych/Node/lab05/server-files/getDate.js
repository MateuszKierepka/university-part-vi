function getDate() {
    const now = new Date();
    const date = now.toLocaleDateString('pl-PL');
    const time = now.toLocaleTimeString('pl-PL', { hour12: false });
    const milliseconds = now.getMilliseconds().toString().padStart(3, '0');
    return `${date} ${time}.${milliseconds}`;
}

module.exports = getDate;