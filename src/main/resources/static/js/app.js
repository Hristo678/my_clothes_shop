
const sellersCntr = document.getElementById('sellersCntr')
const allSellers = []

const displaySellers = (selelrs) => {
    sellersCntr.innerHTML = selelrs.map(
        (s)=> {
            return asSeller(s)
        }
    ).join('')
}

function asSeller(s) {
    let sellerHtml = `<div id="seller">`

    sellerHtml += `<h4><a href="/seller/details/${s.offerId}"> ${s.username} </a> : ${s.numberOfOffers} оферти</h4><br/>`
    sellerHtml += `</div>`

    return sellerHtml
}

fetch(`http://localhost:8080/api/sellers`).
then(response => response.json()).
then(data => {
    for (let seller of data) {
        allSellers.push(seller)
    }
    displaySellers(allSellers)
})

