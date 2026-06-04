const menuBtn = document.getElementById("drawerBtn");
const closeBtn = document.getElementById("closeDrawerBtn");

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