document.addEventListener(
    "DOMContentLoaded",
    () => {

        const searchInput =
                document.getElementById(
                        "animeSearchInput"
                );

        const suggestionsBox =
                document.getElementById(
                        "searchSuggestions"
                );

        if(!searchInput
                || !suggestionsBox){

            return;

        }

        let debounceTimer;

        searchInput.addEventListener(
                "input",
                function(){

                    clearTimeout(
                            debounceTimer
                    );

                    const keyword =
                            this.value.trim();

                    if(keyword.length < 2){

                        suggestionsBox.innerHTML = "";
                        suggestionsBox.style.display = "none";

                        return;
                    }

                    debounceTimer =
                            setTimeout(
                                    () => {

                            fetch(
                                    `${window.location.origin}`
                                    + `${window.location.pathname.substring(
                                            0,
                                            window.location.pathname.indexOf(
                                                    '/',
                                                    1
                                            )
                                    )}`
                                    + `/searchAnimeAjax?keyword=`
                                    + encodeURIComponent(
                                            keyword
                                    )
                            )
                            .then(
                                    response =>
                                            response.json()
                            )
                            .then(
                                    animeList => {

                                renderSuggestions(
                                        animeList
                                );

                            }
                            )
                            .catch(
                                    error => {

                                console.error(
                                        error
                                );

                            }
                            );

                        },
                        250
                    );

                }
        );

        document.addEventListener(
                "click",
                function(event){

                    if(!suggestionsBox.contains(
                            event.target
                    )
                    &&
                    event.target !== searchInput){

                        suggestionsBox.style.display =
                                "none";

                    }

                }
        );

        function renderSuggestions(
                animeList){

            suggestionsBox.innerHTML = "";

            if(!animeList
                    || animeList.length === 0){

                suggestionsBox.style.display =
                        "none";

                return;

            }

            animeList.forEach(
                    anime => {

                const item =
                        document.createElement(
                                "a"
                        );

                item.className =
                        "search-suggestion-item";

                item.href =
                        `${window.location.origin}`
                        + `${window.location.pathname.substring(
                                0,
                                window.location.pathname.indexOf(
                                        '/',
                                        1
                                )
                        )}`
                        + `/anime?id=`
                        + anime.animeId;

                item.innerHTML =
                        `
                        <img
                            src="${window.location.origin}${window.location.pathname.substring(
                                0,
                                window.location.pathname.indexOf(
                                        '/',
                                        1
                                )
                            )}${anime.coverImage}"
                            class="search-suggestion-cover"
                            alt="${anime.title}">

                        <span
                            class="search-suggestion-title">

                            ${anime.title}

                        </span>
                        `;

                suggestionsBox.appendChild(
                        item
                );

            }
            );

            suggestionsBox.style.display =
                    "block";
        }
    }
);