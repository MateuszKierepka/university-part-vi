// zadanie 2.1. Parametr resztowy i szablon ciągu tekstowego
function wyswietlLiczby(...argumenty){
    console.log(argumenty)
}
wyswietlLiczby(4,6,7,9,0,2) // [4, 6, 7, 9, 0, 2]

function sumator(...liczby) {
    const suma = liczby.reduce((acc, val) => acc + val, 0);
    return `Suma liczb: ${liczby.join(', ')} wynosi ${suma}.`;
}
console.log(sumator(1, 2, 3));

// zadanie 2.2. Obsługa tablic za pomocą metod forEach, map i filter
const listaZadan = [
    {
        id: 1,
        tekst: "Zrobienie zakupów",
        zrealizowano: true
    },
    {
        id: 2,
        tekst: "Przegląd techniczny samochodu",
        zrealizowano: false
    },
    {
        id: 3,
        tekst: "Wizyta u dentysty",
        zrealizowano: false
    },
]
// a) 
listaZadan.forEach(zadanie => {
    console.log(zadanie.tekst);
});
// b)
const teksty = listaZadan.map(zadanie => zadanie.tekst);
console.log(teksty);
// c)
const zrealizowane = listaZadan.filter(zadanie => zadanie.zrealizowano).map(zadanie => zadanie.tekst);
console.log(zrealizowane);

// zadanie 2.3. Aplikacja wykorzystująca elementy programowania funkcyjnego
const poniedzialek = [
    {
        'nazwa': 'Przygotowania do zajęć z AI',
        'czas': 180
    },
    {
        'nazwa': 'Realizacja projektu z AI',
        'czas': 120
    }
]
const wtorek = [
    {
        'nazwa': 'Rozbudowa swojego bloga',
        'czas': 240
    },
    {
        'nazwa': 'Administrowanie serwisem szkoly',
        'czas': 180
    },
    {
        'nazwa': 'Sluchanie koncertu online',
        'czas': 240
    }
]
// 1. 
const dwadni = [poniedzialek, wtorek].reduce((acc, curr) => [...acc, ...curr], []);
console.log(dwadni);
// 2.
const godziny = dwadni.map(zadanie => zadanie.czas / 60);
console.log(godziny);
// 3.
const filtrowane = godziny.filter(czas => czas > 2);
console.log(filtrowane);
// 4.
const kwoty = filtrowane.map(czas => czas * 35);
console.log(kwoty);
// 5.
const suma = kwoty.reduce(function(acc, curr) {
    return [(+acc) + (+curr)];
})
console.log(suma);
// 6.
const sformatowane = suma.map(kwota => `${kwota.toFixed(2)} PLN`);
console.log(sformatowane);
// 7.
const wartosc = sformatowane.reduce((acc, curr) => curr);
console.log(wartosc);

// zadanie 2.4. Zastosowanie metod filter() i map()
const firmy = [
    {nazwa:"Abasco", kategoria:"IT", poczatek:1999, koniec:2010},
    {nazwa:"Redis", kategoria:"IT", poczatek:1993, koniec:1998},
    {nazwa:"Komp", kategoria:"IT", poczatek:2003, koniec:2018},
    {nazwa:"Bosco", kategoria:"Technologie", poczatek:2011, koniec:2014},
    {nazwa:"CCA", kategoria:"IT", poczatek:1991, koniec:1995},
    {nazwa:"Autosan", kategoria:"Auto", poczatek:2009, koniec:2018},
    {nazwa:"Broke", kategoria:"Finanse", poczatek:1990, koniec:1992},
    {nazwa:"Funds", kategoria:"Finanse", poczatek:2000, koniec:2021}
]
// a)
const firmyIT = firmy.filter(firma => firma.kategoria === "IT");
console.log(firmyIT);
// b)
const firmy90 = firmy.filter(firma => 
    firma.poczatek >= 1990 && firma.poczatek <= 1999 && 
    firma.koniec >= 1990 && firma.koniec <= 1999
);
console.log(firmy90);
// c)
const firmy10Lat = firmy.filter(firma => {
    const czasDzialania = firma.koniec - firma.poczatek;
    return czasDzialania > 10;
});
console.log(firmy10Lat);

// zadanie 2.5. Używanie modułów
const calc = (a, b, p) => {
    if (!(a || b || p)) {
        return "Podaj wszystkie argumenty!"
    }
    switch (p) {
        case '+': {
            return a + b
        }
        case '-': {
            return a - b
        }
        case '*': {
            return a * b
        }
        case '/': {
            return a / b
        }
        default: { return }
    }
}
// a)
// exports.calc = calc

// b)
// export { calc };

// c)
export default calc;