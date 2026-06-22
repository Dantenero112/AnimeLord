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
    RANGE FILTER
==========================================
*/

const rangeButtons =
        document.querySelectorAll(
                ".episode-range-btn"
        );

rangeButtons.forEach(
        button => {

            button.addEventListener(
                    "click",
                    function(){

                        rangeButtons.forEach(
                                btn =>
                                        btn.classList.remove(
                                                "active"
                                        )
                        );

                        this.classList.add(
                                "active"
                        );

                        const start =
                                parseInt(
                                        this.dataset.start
                                );

                        const end =
                                parseInt(
                                        this.dataset.end
                                );

                        filterEpisodes(
                                start,
                                end
                        );
                    }
            );
        }
);

/*
==========================================
    FILTER EPISODES
==========================================
*/

function filterEpisodes(
        start,
        end
){

    const episodeCards =
            document.querySelectorAll(
                    ".episode-card"
            );

    episodeCards.forEach(
            card => {

                const episodeNumber =
                        parseInt(
                                card.dataset.episode
                        );

                if(
                        episodeNumber >= start
                        &&
                        episodeNumber <= end
                ){

                    card.style.display =
                            "";

                }
                else{

                    card.style.display =
                            "none";
                }
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

    if(!episodeCard){

        return;
    }

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

                    filterEpisodes(
                            start,
                            end
                    );
                }
            }
    );

    episodeCard.scrollIntoView({

        behavior:
                "smooth",

        block:
                "center"
    });

    episodeCard.classList.add(
            "highlighted"
    );

    setTimeout(
            () => {

                episodeCard.classList.remove(
                        "highlighted"
                );

            },
            4000
    );
}

/*
==========================================
    DEFAULT ACTIVE RANGE
==========================================
*/

document.addEventListener(
        "DOMContentLoaded",
        function(){

            const activeRange =
                    document.querySelector(
                            ".episode-range-btn.active"
                    );

            if(activeRange){

                filterEpisodes(

                        parseInt(
                                activeRange.dataset.start
                        ),

                        parseInt(
                                activeRange.dataset.end
                        )
                );
            }
        }
);