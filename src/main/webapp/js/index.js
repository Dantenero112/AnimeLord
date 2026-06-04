const menuBtn = document.getElementById("menuBtn");
const closeBtn = document.getElementById("closeBtn");

const drawer = document.getElementById("userDrawer");
const overlay = document.getElementById("overlay");

menuBtn.addEventListener("click", () => {

    drawer.classList.add("show");
    overlay.classList.add("show");

});

closeBtn.addEventListener("click", closeDrawer);
overlay.addEventListener("click", closeDrawer);

function closeDrawer(){

    drawer.classList.remove("show");
    overlay.classList.remove("show");

}