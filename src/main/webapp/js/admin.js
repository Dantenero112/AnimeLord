/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


document.addEventListener("DOMContentLoaded", () => {

const menuBtn = document.getElementById("menuBtn");
const sidebar = document.getElementById("sidebarDrawer");
const overlay = document.getElementById("overlay");
const closeBtn = document.getElementById("closeBtn");

function openDrawer(){
    sidebar.classList.add("show");
    overlay.classList.add("show");
}

function closeDrawer(){
    sidebar.classList.remove("show");
    overlay.classList.remove("show");
}

menuBtn.addEventListener("click", openDrawer);
overlay.addEventListener("click", closeDrawer);
closeBtn.addEventListener("click", closeDrawer);
});
