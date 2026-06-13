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

/*
=========================================
UPLOAD EPISODE PAGE
=========================================
*/

document.addEventListener(
    "DOMContentLoaded",
    () => {

        /*
        ============================
        ANIME SEARCH FILTER
        ============================
        */

        const animeSearch =
                document.getElementById(
                        "animeSearch"
                );

        const animeSelect =
                document.getElementById(
                        "animeId"
                );

        if(animeSearch && animeSelect){

            animeSearch.addEventListener(
                    "input",
                    () => {

                const keyword =
                        animeSearch.value
                        .toLowerCase()
                        .trim();

                Array.from(
                        animeSelect.options
                ).forEach(
                        option => {

                    /*
                        KEEP DEFAULT OPTION
                    */
                    if(option.value === ""){

                        return;

                    }

                    const animeTitle =
                            option.text
                            .toLowerCase();

                    option.hidden =
                            !animeTitle.includes(
                                    keyword
                            );

                });

            });

        }

        /*
        ============================
        ENCODING MODE
        ============================
        */

        const autoMode =
                document.getElementById(
                        "autoMode"
                );

        const customMode =
                document.getElementById(
                        "customMode"
                );

        const resolutionOptions =
                document.getElementById(
                        "resolutionOptions"
                );

        function updateEncodingMode(){

            if(!resolutionOptions){

                return;

            }

            resolutionOptions.style.display =
                    customMode
                    && customMode.checked
                    ? "block"
                    : "none";

        }

        if(autoMode){

            autoMode.addEventListener(
                    "change",
                    updateEncodingMode
            );

        }

        if(customMode){

            customMode.addEventListener(
                    "change",
                    updateEncodingMode
            );

        }

        updateEncodingMode();

    }
);

/*
=========================================
FLASH MESSAGES
=========================================
*/

document.addEventListener(
    "DOMContentLoaded",
    () => {

        const successAlert =
                document.getElementById(
                        "successAlert"
                );

        const errorAlert =
                document.getElementById(
                        "errorAlert"
                );

        function autoHideAlert(alert){

            if(!alert){

                return;

            }

            setTimeout(
                () => {

                    alert.style.transition =
                            "opacity .5s ease";

                    alert.style.opacity =
                            "0";

                    setTimeout(
                        () => {

                            alert.remove();

                        },
                        500
                    );

                },
                5000
            );

        }

        autoHideAlert(
                successAlert
        );

        autoHideAlert(
                errorAlert
        );

    }
);

/*
=========================================
GENRE PICKER
=========================================
*/

document.addEventListener(
    "DOMContentLoaded",
    () => {

        const genreSearch =
                document.getElementById(
                        "genreSearch"
                );

        const genreDropdown =
                document.getElementById(
                        "genreDropdown"
                );

        const selectedGenres =
                document.getElementById(
                        "selectedGenres"
                );

        const hiddenInputs =
                document.getElementById(
                        "genreHiddenInputs"
                );

        const animeForm =
                document.querySelector(
                        ".admin-form"
                );

        /*
            NOT ON ADD ANIME PAGE
        */
        if(
                !genreSearch
                || !genreDropdown
                || !selectedGenres
                || !hiddenInputs
        ){

            return;

        }

        const selectedGenreIds =
                new Set();
        //preload the existing genres
        document.querySelectorAll(
            "#genreHiddenInputs input"
        )
        .forEach(
            input => {

                selectedGenreIds.add(
                    input.value
                );

            }
        );
        
        /*
        =========================================
        PRELOAD EXISTING CHIPS (EDIT PAGE)
        =========================================
        */

        selectedGenres
        .querySelectorAll(
                ".genre-chip"
        )
        .forEach(
                chip => {

            const genreId =
                    chip.dataset.id;

            const removeButton =
                    chip.querySelector(
                            ".genre-chip-remove"
                    );

            const hiddenInput =
                    hiddenInputs.querySelector(
                            '[data-genre-id="'
                            + genreId +
                            '"]'
                    );

            const option =
                    genreDropdown.querySelector(
                            '.genre-option[data-id="'
                            + genreId +
                            '"]'
                    );

            if(option){

                option.classList.add(
                        "selected"
                );

            }

            removeButton.addEventListener(
                    "click",
                    () => {

                chip.remove();

                if(hiddenInput){

                    hiddenInput.remove();

                }

                selectedGenreIds.delete(
                        genreId
                );

                if(option){

                    option.classList.remove(
                            "selected"
                    );

                }

            });

        });

        let highlightedIndex =
                -1;

        /*
        =================================
        OPEN DROPDOWN
        =================================
        */

        genreSearch.addEventListener(
                "focus",
                () => {

            genreDropdown.classList.add(
                    "show"
            );

            filterGenres();

        });

        /*
        =================================
        CLOSE DROPDOWN
        =================================
        */

        document.addEventListener(
                "click",
                event => {

            if(
                    !event.target.closest(
                            ".genre-selector"
                    )
            ){

                genreDropdown.classList.remove(
                        "show"
                );

            }

        });

        /*
        =================================
        SEARCH
        =================================
        */

        genreSearch.addEventListener(
                "input",
                () => {

            highlightedIndex = -1;

            filterGenres();

        });

        function filterGenres(){

            const keyword =
                    genreSearch.value
                    .toLowerCase()
                    .trim();

            const options =
                    genreDropdown.querySelectorAll(
                            ".genre-option"
                    );

            let visibleCount = 0;

            options.forEach(
                    option => {

                const name =
                        option.dataset.name
                        .toLowerCase();

                const visible =
                        name.includes(
                                keyword
                        );

                option.style.display =
                        visible
                        ? ""
                        : "none";

                if(visible){

                    visibleCount++;

                }

            });

            let noResult =
                    document.getElementById(
                            "genreNoResult"
                    );

            if(
                    visibleCount === 0
            ){

                if(!noResult){

                    noResult =
                            document.createElement(
                                    "div"
                            );

                    noResult.id =
                            "genreNoResult";

                    noResult.className =
                            "genre-no-result";

                    noResult.innerText =
                            "No genres found";

                    genreDropdown.appendChild(
                            noResult
                    );

                }

            }
            else{

                if(noResult){

                    noResult.remove();

                }

            }

        }

        /*
        =================================
        KEYBOARD NAVIGATION
        =================================
        */

        genreSearch.addEventListener(
                "keydown",
                event => {

            const visibleOptions =
                    Array.from(
                            genreDropdown.querySelectorAll(
                                    ".genre-option"
                            )
                    )
                    .filter(
                            option =>
                            option.style.display
                            !== "none"
                    );

            if(
                    !visibleOptions.length
            ){

                return;

            }

            if(
                    event.key ===
                    "ArrowDown"
            ){

                event.preventDefault();

                highlightedIndex++;

                if(
                        highlightedIndex
                        >= visibleOptions.length
                ){

                    highlightedIndex = 0;

                }

                updateHighlight(
                        visibleOptions
                );

            }

            else if(
                    event.key ===
                    "ArrowUp"
            ){

                event.preventDefault();

                highlightedIndex--;

                if(
                        highlightedIndex < 0
                ){

                    highlightedIndex =
                            visibleOptions.length - 1;

                }

                updateHighlight(
                        visibleOptions
                );

            }

            else if(
                    event.key ===
                    "Enter"
            ){

                if(
                        highlightedIndex >= 0
                ){

                    event.preventDefault();

                    visibleOptions[
                            highlightedIndex
                    ].click();

                }

            }

            else if(
                    event.key ===
                    "Escape"
            ){

                genreDropdown.classList.remove(
                        "show"
                );

            }

        });

        function updateHighlight(
                visibleOptions
        ){

            visibleOptions.forEach(
                    option => {

                option.classList.remove(
                        "highlighted"
                );

            });

            if(
                    highlightedIndex >= 0
            ){

                visibleOptions[
                        highlightedIndex
                ]
                .classList.add(
                        "highlighted"
                );

                visibleOptions[
                        highlightedIndex
                ]
                .scrollIntoView({
                    block:"nearest"
                });

            }

        }

        /*
        =================================
        SELECT GENRE
        =================================
        */

        genreDropdown
        .querySelectorAll(
                ".genre-option"
        )
        .forEach(
                option => {

            option.addEventListener(
                    "click",
                    () => {

                const genreId =
                        option.dataset.id;

                const genreName =
                        option.dataset.name;

                if(
                        selectedGenreIds.has(
                                genreId
                        )
                ){

                    return;

                }

                selectedGenreIds.add(
                        genreId
                );

                option.classList.add(
                        "selected"
                );

                /*
                    CHIP
                */

                const chip =
                        document.createElement(
                                "div"
                        );

                chip.className =
                        "genre-chip";

                chip.innerHTML =
                        `
                        <span>
                            ${genreName}
                        </span>

                        <span
                            class="genre-chip-remove">

                            ✕

                        </span>
                        `;

                selectedGenres.appendChild(
                        chip
                );

                /*
                    HIDDEN INPUT
                */

                const hidden =
                        document.createElement(
                                "input"
                        );

                hidden.type =
                        "hidden";

                hidden.name =
                        "genreIds";

                hidden.value =
                        genreId;

                hidden.dataset.genreId =
                        genreId;

                hiddenInputs.appendChild(
                        hidden
                );

                /*
                    REMOVE
                */

                chip
                .querySelector(
                        ".genre-chip-remove"
                )
                .addEventListener(
                        "click",
                        () => {

                    chip.remove();

                    hidden.remove();

                    selectedGenreIds.delete(
                            genreId
                    );

                    option.classList.remove(
                            "selected"
                    );

                });

                genreSearch.value = "";

                highlightedIndex = -1;

                filterGenres();

                genreSearch.focus();

            });

        });

        /*
        =================================
        FORM VALIDATION
        =================================
        */

        if(animeForm){

            animeForm.addEventListener(
                    "submit",
                    event => {

                const selectedCount =
                        hiddenInputs
                        .querySelectorAll(
                                "input"
                        )
                        .length;

                if(
                        selectedCount === 0
                ){

                    event.preventDefault();

                    alert(
                        "Please select at least one genre."
                    );

                }

            });

        }

    }
);