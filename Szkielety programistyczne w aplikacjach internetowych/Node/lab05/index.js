// zadanie 1.1.
// const http = require('http')
// function process_request(req, res) {
//     const body = 'Witaj na platformie Node!\n'
//     const content_length = body.length
//     res.writeHead(200, {
//         'Content-Length': content_length,
//         'Content-Type': 'text/plain'
//     });
//     res.end(body)
// }
// const server = http.createServer(process_request)
// server.listen(3000, () => console.log('Serwer działa!'))

// zadanie 1.3.
// const http = require('http')
// const fs = require('fs')
// const port = 3000
// function serveStaticFile(res, path, contentType, responseCode = 200){
//     fs.readFile(__dirname + path, (err, data) => {
//         if(err){
//             res.writeHead(500, { 'Content-Type': 'text/plain'})
//             return res.end('500 - Blad wewnetrzny')
//         }
//         res.writeHead(responseCode, { 'Content-Type':contentType})
//         res.end(data)
//     })
// }
// const server = http.createServer((req, res) => {
//     switch(req.url){
//         case '/':
//             serveStaticFile(res, '/public/home.html', 'text/html' )
//             break
//         case '/about':
//             serveStaticFile(res, '/public/about.html', 'text/html' )
//             break
//         case '/img/logo.jpg':
//             serveStaticFile(res, '/public/img/logo.jpg', 'image/jpg' )
//             break
//         default:
//             serveStaticFile(res, '/public/404.html','text/html' )
//             break
//     }
// })
// server.listen(port, () => console.log(`Server działa na porcie ${port}; `+ 'naciśnij Ctrl+C, aby zakończyć'))

// zadanie 1.4.
// przykład
// const http = require('http')
// const url = require('url')
// // http.createServer(function (req, res) {
// //     res.writeHead(200, {'Content-Type': 'text/html'})
// //     let q = url.parse(req.url, true).query
// //     let txt = q.year + " " + q.month + " " + q.day
// //     res.end(txt)
// // }).listen(3000)

// http.createServer(function (req, res) {
//     res.writeHead(200, {'Content-Type': 'text/html'})
//     let q = url.parse(req.url, true).query
//     let a = parseInt(q.bok1)
//     let b = parseInt(q.bok2)
//     let c = parseInt(q.bok3)
    
//     if (a + b > c && a + c > b && b + c > a) {
//         let p = (a + b + c) / 2
//         let pole = Math.sqrt(p * (p - a) * (p - b) * (p - c))
//         let result = "Pole wynosi: " + String(pole)
//         res.end(result)
//     } else {
//         res.end("To nie jest trojkat");
//     }
// }).listen(3000)

// zadanie 1.5.
// const http = require("http")
// const hostname = "localhost"
// const port = 3000
// const server = http.createServer((req, res) => {
//     res.statusCode = 200
//     res.setHeader("Content-Type", "text/html")
//     let text = "<h1>Aplikacja w Node</h1><h3>To jest odpowiedz HTML</h3><ol><b><li></li><li></li><li></li></b></ol>"
//     res.end(text)
// })
// server.listen(port, hostname, () => {
//     console.log(`Server running at http://${hostname}:${port}/`)
// })

// zadanie 1.6.
// const http = require("http")
// const hostname = "localhost"
// const port = 3000
// const server = http.createServer((req, res) => {
//     res.statusCode = 200
//     res.setHeader("Content-Type", "text/html")
//     switch (req.url) {
//         case "/home":
//             res.end(`<h1>Strona domowa</h1><p>Witaj na stronie glownej!</p>`);
//             break
//         case "/about":
//             res.end(`<h1>Strona o mnie</h1><p>Informacje o mnie.</p>`);
//             break
//         default:
//             res.end(`<h1>404 Not Found</h1><p>Nie znaleziono strony.</p>`);
//             break
//     }
// })
// server.listen(port, hostname, () => {
//     console.log(`Server running at http://${hostname}:${port}/`)
// })

// zadanie 1.7.
function show(res) {
    const html = '<html><head><title>Lista zadan</title></head><body>' +
        '<h1>Lista zadan</h1>' +
        '<form method="post" action="/">' +
        '<p><input type="text" name="item" />' +
        '<input type="submit" value="Dodaj" /></p>' +
        '<ul>'
        +items.map(function (item) {
            return '<li>' + item + '</li>'
        }).join('')
        +'</ul>' +
        '</form></body></html>'
    res.setHeader('Content-Type', 'text/html')
    res.setHeader('Content-Length', Buffer.byteLength(html))
    res.end(html)
}

function notFound(res) {
    res.statusCode = 404
    res.setHeader('Content-Type', 'text/plain')
    res.end('Not Found')
}

function badRequest(res) {
    res.statusCode = 400
    res.setHeader('Content-Type', 'text/plain')
    res.end('Bad Request')
}

let qs = require('querystring')
function add(req, res) {
    var body = ''
    req.setEncoding('utf8')
    req.on('data', function (chunk) { body += chunk })
    req.on('end', function () {
        var obj = qs.parse(body)
        items.push(obj.item)
        show(res)
    })
}

const http = require('http')
let items = []

const server = http.createServer((req, res) => {
    if (req.url === '/') {
        switch (req.method) {
            case 'GET':
                show(res)
                break
            case 'POST':
                add(req, res)
                break
            default:
                badRequest(res)
        }
    } else {
        notFound(res)
    }
})
server.listen(3000)