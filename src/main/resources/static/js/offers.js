$(document).ready(function () {
    const offers = document.getElementsByClassName('offer');

    $('.list').click(function () {
        const category = $(this).attr('data-filter');
        $(this).addClass('active').siblings().removeClass('active')

        categoryFilter(category)
    })

    function categoryFilter(category){

        if (category == 'ALL') {
            $('.conditionFiltered').addClass('categoryFiltered').show(1000);
        } else {
            $('.conditionFiltered').not('.' + category).hide().removeClass('categoryFiltered')
            $('.conditionFiltered').filter('.' + category).show(1000).addClass('categoryFiltered')

        }
    }

    $('.clothCondition').change(function () {

        conditionFilter()
        const category = document.querySelector('.active').attributes.getNamedItem('data-filter').value
        categoryFilter(category)

    })

    function conditionFilter(){
        const value = formid.clothCondition[formid.clothCondition.selectedIndex].value
        console.log(value)
        if (value == 'ALL') {
            $('.categoryFiltered').addClass('conditionFiltered').show(1000);
        } else {
            $('.categoryFiltered').not('.' + value).removeClass('conditionFiltered').hide()
            $('.categoryFiltered').filter('.' + value).addClass('conditionFiltered').show(1000)
        }
    }



    $('.filter').click(function () {
        for (let currentOffer of offers) {
            currentOffer.classList.remove('d-none')
        }

        const minPrice = $('#price-range').slider("values")[0];
        const maxPrice = $('#price-range').slider("values")[1];


        for (let currentOffer of offers) {

            const items = currentOffer.className.split(' ')
            const currentPrice = (items[items.length - 1])


            if (currentPrice < minPrice) {
                currentOffer.classList.add('d-none')


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





