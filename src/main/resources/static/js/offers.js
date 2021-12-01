$(document).ready(function () {
    const offers = document.getElementsByClassName('offer');


    $('.list').click(function () {
        const value = $(this).attr('data-filter');
        if (value == 'ALL') {
            $('.offer').show(1000);
        } else {
            $('.offer').not('.' + value).hide(1000)
            $('.offer').filter('.' + value).show(1000)

        }
    })
    $('.list').click(function () {
        $(this).addClass('active').siblings().removeClass('active')

    })


    $('.filter').click(function () {
        for (let currentOffer of offers) {
            currentOffer.classList.remove('d-none')
        }

        const minPrice = $('#price-range').slider("values")[0];
        const maxPrice = $('#price-range').slider("values")[1];
        console.log(minPrice + " " + maxPrice)

        for (let currentOffer of offers) {

            const items = currentOffer.className.split(' ')
            const currentPrice = (items[items.length - 1])
            console.log(currentPrice)
            console.log(currentOffer)

            if (currentPrice < minPrice) {
                currentOffer.classList.add('d-none')


                console.log(currentOffer.className)
            }
            if (currentPrice > maxPrice) {
                currentOffer.classList.add('d-none')
            }

        }


    })
})


$(function () {
    $("#price-range").slider({
        step: 10,
        range: true,
        min: 0,
        max: 1000,
        values: [0, 1000],
        slide: function (event, ui) {
            $("#priceRange").val(ui.values[0] + " - " + ui.values[1]);
        }
    });
    $("#priceRange").val($("#price-range").slider("values", 0) + " - " + $("#price-range").slider("values", 1));

});





