/*
==========================================
    EPISODE SEARCH
==========================================
*/

const episodeSearch =
        document.getElementById(
                "episodeSearch"
        );

if(episodeSearch){

    episodeSearch.addEventListener(
            "keyup",
            function(event){

                if(event.key !== "Enter"){

                    return;
                }

                const episodeNumber =
                        parseInt(
                                this.value.trim()
                        );

                if(
                        isNaN(
                                episodeNumber
                        )
                ){

                    return;
                }

                jumpToEpisode(
                        episodeNumber
                );
            }
    );
}

/*
==========================================
    JUMP TO EPISODE
==========================================
*/

function jumpToEpisode(
        episodeNumber
){

    const episodeCard =
            document.querySelector(
                    `[data-episode="${episodeNumber}"]`
            );

    /*
        NOT FOUND
    */
    if(!episodeCard){

        return;
    }

    /*
        REMOVE OLD HIGHLIGHTS
    */
    document
            .querySelectorAll(
                    ".episode-card.highlighted"
            )
            .forEach(
                    card =>
                            card.classList.remove(
                                    "highlighted"
                            )
            );

    /*
        FIND RANGE BUTTON
    */
    const rangeButtons =
            document.querySelectorAll(
                    ".episode-range-btn"
            );

    rangeButtons.forEach(
            button => {

                button.classList.remove(
                        "active"
                );

                const start =
                        parseInt(
                                button.dataset.start
                        );

                const end =
                        parseInt(
                                button.dataset.end
                        );

                if(
                        episodeNumber >= start
                        &&
                        episodeNumber <= end
                ){

                    button.classList.add(
                            "active"
                    );
                }
            }
    );

    /*
        SCROLL
    */
    episodeCard.scrollIntoView({

        behavior:
                "smooth",

        block:
                "center"
    });

    /*
        HIGHLIGHT
    */
    episodeCard.classList.add(
            "highlighted"
    );

    /*
        REMOVE HIGHLIGHT LATER
    */
    setTimeout(
            () => {

                episodeCard.classList.remove(
                        "highlighted"
                );

            },
            4000
    );
}