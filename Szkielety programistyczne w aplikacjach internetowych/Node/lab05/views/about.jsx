const React = require('react')

const HelloMessage = (props) => {
    return (
        <html>
            <body>
                <div>Nazwisko: {props.nazwisko}</div>
                <div>Email: {props.email}</div>
                <div>Wiek: {props.wiek}</div>
            </body>
        </html>
    );
}

module.exports = HelloMessage