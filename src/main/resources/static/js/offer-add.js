$(document).ready(() => {

    let imageInput = document.getElementById("imageUrl")
    let img = document.querySelector(".image-preview__image")
    let imgPreviewText = document.querySelector(".image-preview__default-text")
    let imagePreview = document.querySelector(".image-preview")
    let btnLeft = document.querySelector("#button-left")
    let btnRight = document.querySelector("#button-right")
    let index = 0;

    imageInput.addEventListener("change", function () {

        let files = this.files
        let file = files[0]
        if(file){
            if (files.length > 1){
                btnRight.style.display = "inline-block"
                btnLeft.style.display = "inline-block"

                btnRight.addEventListener("click", function (){
                    if (index + 1 < files.length){
                        index++;

                    }else {
                        index = 0;
                    }
                    reader.readAsDataURL(files[index])

                })

                btnLeft.addEventListener("click", function (){
                    if (index - 1 >= 0){
                        index--;

                    }else {
                        index = files.length - 1;
                    }
                    reader.readAsDataURL(files[index])
                })
            }
            const reader = new FileReader();
            img.style.display = "block"
            imagePreview.style.minHeight = "0"
            imgPreviewText.style.display = "none"

            reader.addEventListener("load", (progressEvent) =>{

                img.setAttribute("src", progressEvent.currentTarget.result)

            });

            reader.readAsDataURL(file)


        }


    })
})

