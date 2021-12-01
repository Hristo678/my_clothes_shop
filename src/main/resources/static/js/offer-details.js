let slidePosition = 0;
const slides = document.getElementsByClassName('carousel__item');
const totalSlides = slides.length;

for (let slide of slides) {
    slide.classList.remove('carousel__item--visible');
    slide.classList.add('carousel__item--hidden');

}

slides[slidePosition].classList.remove('carousel__item--hidden');
slides[slidePosition].classList.add('carousel__item--visible');


$('.carousel__button--prev').click(function () {
    moveToPrevSlide()
})

$('.carousel__button--next').click(function () {
    moveToNextSlide()
})


function updateSlidePosition() {
    for (let slide of slides) {
        slide.classList.remove('carousel__item--visible');
        slide.classList.add('carousel__item--hidden');

    }
    slides[slidePosition].classList.remove('carousel__item--hidden');
    slides[slidePosition].classList.add('carousel__item--visible');
}

function moveToNextSlide() {
    if (slidePosition === totalSlides - 1) {
        slidePosition = 0;
    } else {
        slidePosition++;
    }

    updateSlidePosition();
}

function moveToPrevSlide() {
    if (slidePosition === 0) {
        slidePosition = totalSlides - 1;
    } else {
        slidePosition--;
    }

    updateSlidePosition();
}

