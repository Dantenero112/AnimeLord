/*
==========================================
    ANIMELORD PLAYER
==========================================
*/

let hls = null;

let controlsTimer = null;
/*
==========================================
    VOLUME UI SYNC
==========================================
*/

function syncVolumeUI(){

    const video =
            document.getElementById(
                    "animePlayer"
            );

    const volumeSlider =
            document.getElementById(
                    "volumeSlider"
            );

    const muteBtn =
            document.getElementById(
                    "muteBtn"
            );

    if(
            !video
            ||
            !volumeSlider
            ||
            !muteBtn
    ){

        return;
    }

    const volumePercent =
            video.muted
            ? 0
            : video.volume * 100;

    volumeSlider.value =
            video.muted
            ? 0
            : video.volume;

    volumeSlider.style.setProperty(
            "--volume",
            volumePercent + "%"
    );

    muteBtn.textContent =
            (
                    video.muted
                    ||
                    video.volume === 0
            )
            ? "🔇"
            : "🔊";
}
/*
==========================================
    DOM READY
==========================================
*/

document.addEventListener(
        "DOMContentLoaded",
        function(){

            initializePlayer();

            initializeControls();

            initializeSubtitleUpload();

            initializeHotkeys();

            initializeAutoNextEpisode();
        }
);

/*
==========================================
    HLS PLAYER
==========================================
*/

function initializePlayer(){

    const video =
            document.getElementById(
                    "animePlayer"
            );

    const playlistInput =
            document.getElementById(
                    "masterPlaylist"
            );

    if(
            !video
            ||
            !playlistInput
    ){

        return;
    }

    const playlistUrl =
            playlistInput.value;

    if(
            !playlistUrl
            ||
            playlistUrl.trim() === ""
    ){

        console.error(
                "No playlist found."
        );

        return;
    }

    if(
            typeof Hls !== "undefined"
            &&
            Hls.isSupported()
    ){

        hls = new Hls({
            enableWorker:true,

            lowLatencyMode:false,

            backBufferLength:15,

            maxBufferLength:10,

            maxMaxBufferLength:15
        });

        hls.loadSource(
                playlistUrl
        );

        hls.attachMedia(
                video
        );
  
        hls.on(
            Hls.Events.ERROR,
            function(
                    event,
                    data
            ){

               console.error(
                        "[HLS]",
                        data.type,
                        data.details,
                        "fatal:",
                        data.fatal
                );
        
                if( data.details === Hls.ErrorDetails.BUFFER_STALLED_ERROR
                ){
                    console.warn("Buffer stalled...");
                }
                
                if(
                        !data.fatal
                ){

                    return;
                }

                switch(data.type){

                    case Hls.ErrorTypes.NETWORK_ERROR:

                        hls.startLoad();
                        break;

                    case Hls.ErrorTypes.MEDIA_ERROR:

                        hls.recoverMediaError();
                        break;

                    default:

                        hls.destroy();
                        break;
                }
            }
    );

        hls.on(
        Hls.Events.MANIFEST_PARSED,
        function(){

            populateQualitySelector();
            }
        );
    }

    updateTime();

    video.addEventListener(
            "timeupdate",
            updateTime
    );

    video.addEventListener(
            "loadedmetadata",
            updateTime
    );
}

/*
==========================================
    QUALITY SELECTOR
==========================================
*/
function populateQualitySelector(){

    const selector =
            document.getElementById(
                    "qualitySelector"
            );

    const video =
            document.getElementById(
                    "animePlayer"
            );

    const loader =
            document.getElementById(
                    "playerLoader"
            );

    if(
            !selector
            ||
            !video
            ||
            !hls
    ){

        return;
    }

    /*
    ==========================================
        RESET OPTIONS
    ==========================================
    */

    selector.innerHTML = "";

    const autoOption =
            document.createElement(
                    "option"
            );

    autoOption.value =
            "-1";

    autoOption.textContent =
            "Auto";

    selector.appendChild(
            autoOption
    );

    /*
    ==========================================
        QUALITY LEVELS
    ==========================================
    */

    hls.levels.forEach(
            function(
                    level,
                    index
            ){

                const option =
                        document.createElement(
                                "option"
                        );

                option.value =
                        index;

                option.textContent =
                        level.height + "p";

                selector.appendChild(
                        option
                );
            }
    );

    /*
    ==========================================
        BUFFER CHECKER
    ==========================================
    */

    function waitForBuffer(
            minimumSeconds
    ){

        return new Promise(
                function(resolve){

                    const interval =
                            setInterval(
                                    function(){

                                        try{

                                            if(
                                                    video.buffered.length
                                                    === 0
                                            ){

                                                return;
                                            }

                                            const bufferEnd =
                                                    video.buffered.end(
                                                            video.buffered.length - 1
                                                    );

                                            const bufferedAhead =
                                                    bufferEnd
                                                    -
                                                    video.currentTime;

                                            if(
                                                    bufferedAhead
                                                    >=
                                                    minimumSeconds
                                            ){

                                                clearInterval(
                                                        interval
                                                );

                                                resolve();
                                            }

                                        }
                                        catch(error){

                                        }

                                    },
                                    100
                            );
                }
        );
    }

    /*
    ==========================================
        CHANGE QUALITY
    ==========================================
    */

    selector.addEventListener(
            "change",
            async function(){

                const selectedLevel =
                        parseInt(
                                this.value
                        );

                /*
                ==========================
                    AUTO
                ==========================
                */

                if(
                        selectedLevel === -1
                ){

                    hls.autoLevelEnabled =
                            true;

                    hls.nextLevel =
                            -1;

                    this.blur();

                    video.focus();

                    return;
                }

                /*
                ==========================
                    MANUAL QUALITY
                ==========================
                */

                const currentTime =
                        video.currentTime;

                const wasPlaying =
                        !video.paused;

                /*
                    SHOW SPINNER
                */

                loader?.classList.remove(
                        "hidden"
                );

                /*
                    PAUSE VIDEO
                */

                video.pause();

                /*
                    DISABLE AUTO
                */

                hls.autoLevelEnabled =
                        false;

                /*
                    FORCE QUALITY
                */

                hls.currentLevel =
                        selectedLevel;

                /*
                    REMOVE FOCUS
                */

                this.blur();

                video.focus();

                /*
                ==========================
                    WAIT FOR NEW QUALITY
                ==========================
                */

                await new Promise(
                        resolve =>
                                setTimeout(
                                        resolve,
                                        1200
                                )
                );

                /*
                    SEEK BACK
                */

                video.currentTime =
                        currentTime;

                /*
                ==========================
                    WAIT BUFFER
                ==========================
                */

                try{

                    await waitForBuffer(
                            3
                    );

                }
                catch(error){

                    console.warn(
                            error
                    );
                }

                /*
                ==========================
                    EXTRA SMOOTHING
                ==========================
                */

                await new Promise(
                        resolve =>
                                setTimeout(
                                        resolve,
                                        500
                                )
                );

                /*
                    HIDE SPINNER
                */

                loader?.classList.add(
                        "hidden"
                );

                /*
                    RESUME
                */

                if(
                        wasPlaying
                ){

                    video.play()
                         .catch(
                                 () => {}
                         );
                }
            }
    );

    /*
    ==========================================
        AUTO MODE SYNC
    ==========================================
    */

    hls.on(
            Hls.Events.LEVEL_SWITCHED,
            function(){

                if(
                        hls.autoLevelEnabled
                ){

                    selector.value =
                            "-1";
                }
            }
    );
}

/*
==========================================
    CONTROLS
==========================================
*/
/*
==========================================
    AUTO HIDE CONTROLS
==========================================
*/

function showControls(){

    const video =
            document.getElementById(
                    "animePlayer"
            );

    const controls =
            document.getElementById(
                    "playerControls"
            );

    if(
            !video
            ||
            !controls
    ){

        return;
    }

    controls.classList.remove(
            "controls-hidden"
    );

    clearTimeout(
            controlsTimer
    );

    controlsTimer =
            setTimeout(
                    function(){

                        if(
                                !video.paused
                        ){

                            controls.classList.add(
                                    "controls-hidden"
                            );
                        }

                    },
                    2000
            );
}

function initializeControls(){

    const video =
            document.getElementById(
                    "animePlayer"
            );

    const controls =
            document.getElementById(
                    "playerControls"
            );

    const wrapper =
            document.getElementById(
                    "playerWrapper"
            );

    const playPauseBtn =
            document.getElementById(
                    "playPauseBtn"
            );

    const bigPlayButton =
            document.getElementById(
                    "bigPlayButton"
            );

    const fullscreenBtn =
            document.getElementById(
                    "fullscreenBtn"
            );
    
    const volumeSlider =
        document.getElementById(
                "volumeSlider"
        );

    const muteBtn =
            document.getElementById(
                    "muteBtn"
            );

    const progressBar =
            document.getElementById(
                    "progressBar"
            );

    const speedSelector =
            document.getElementById(
                    "speedSelector"
            );

    if(
            !video
            ||
            !controls
    ){

        return;
    }
    

    function togglePlay(){

        if(video.paused){

            video.play();
        }
        else{

            video.pause();
        }

        updatePlayButtons();
    }

    function updatePlayButtons(){

        playPauseBtn.textContent =
                video.paused
                ? "▶"
                : "❚❚";

        if(video.paused){

            bigPlayButton.innerHTML =
                    "▶";

            bigPlayButton.classList.remove(
                    "hidden"
            );
        }
        else{

            bigPlayButton.classList.add(
                    "hidden"
            );
        }
    }


    playPauseBtn.addEventListener(
            "click",
            togglePlay
    );

    bigPlayButton.addEventListener(
            "click",
            togglePlay
    );

    video.addEventListener(
            "play",
            updatePlayButtons
    );

    video.addEventListener(
            "pause",
            updatePlayButtons
    );

    video.addEventListener(
            "timeupdate",
            function(){

                if(video.duration){

                    progressBar.value =
                            (
                                    video.currentTime
                                    /
                                    video.duration
                            ) * 100;

                    progressBar.style.setProperty(
                            "--progress",
                            progressBar.value + "%"
                    );
                }
            }
    );
    
    video.addEventListener(
            "ended",
            function(){

                bigPlayButton.style.display =
                        "flex";
            }
    );
    
    video.addEventListener(
            "click",
            function(){

                if(video.paused){

                    video.play();
                }
                else{

                    video.pause();
                }

                showControls();
            }
    );
    
    progressBar.addEventListener(
            "input",
            function(){

                if(video.duration){

                    video.currentTime =
                            (
                                    this.value
                                    / 100
                            )
                            *
                            video.duration;
                }
            }
    );
    if(volumeSlider){
        volumeSlider.addEventListener(
                "input",
                function(){

                    video.volume =
                            parseFloat(
                                    this.value
                            );

                    video.muted =
                            video.volume === 0;

                    syncVolumeUI();
                }
        );
    }
    
    muteBtn.addEventListener(
            "click",
            function(){

                video.muted =
                        !video.muted;

                syncVolumeUI();
            }
    );

    speedSelector.addEventListener(
            "change",
            function(){

                video.playbackRate =
                        parseFloat(
                                this.value
                        );
            }
    );

    fullscreenBtn.addEventListener(
            "click",
            function(){

                if(
                        !document.fullscreenElement
                ){

                    wrapper.requestFullscreen?.();
                }
                else{

                    document.exitFullscreen?.();
                }
            }
    );

    /*
    ==========================================
        AUTO HIDE
    ==========================================
    */

    syncVolumeUI();
    updatePlayButtons();

    wrapper.addEventListener(
            "mousemove",
            showControls
    );

    wrapper.addEventListener(
            "click",
            showControls
    );

    wrapper.addEventListener(
            "keydown",
            showControls
    );

    showControls();
}

/*
==========================================
    TIME DISPLAY
==========================================
*/

function updateTime(){

    const video =
            document.getElementById(
                    "animePlayer"
            );

    const display =
            document.getElementById(
                    "timeDisplay"
            );

    if(
            !video
            ||
            !display
    ){

        return;
    }

    display.textContent =
            formatTime(
                    video.currentTime
            )
            +
            " / "
            +
            formatTime(
                    video.duration
            );
}

function formatTime(seconds){

    if(
            isNaN(
                    seconds
            )
    ){

        return "00:00";
    }

    const mins =
            Math.floor(
                    seconds / 60
            );

    const secs =
            Math.floor(
                    seconds % 60
            );

    return String(mins)
            .padStart(
                    2,
                    "0"
            )
            +
            ":"
            +
            String(secs)
                    .padStart(
                            2,
                            "0"
                    );
}

/*
==========================================
    SUBTITLE UPLOAD
==========================================
*/

function initializeSubtitleUpload(){

    const upload =
            document.getElementById(
                    "subtitleUpload"
            );

    const selector =
            document.getElementById(
                    "subtitleSelector"
            );

    const video =
            document.getElementById(
                    "animePlayer"
            );

    if(
            !upload
            ||
            !selector
            ||
            !video
    ){

        return;
    }

    upload.addEventListener(
            "change",
            function(){

                const file =
                        this.files[0];

                if(!file){

                    return;
                }

                const name =
                        file.name
                            .toLowerCase();

                if(
                        name.endsWith(
                                ".ass"
                        )
                        ||
                        name.endsWith(
                                ".ssa"
                        )
                ){

                    alert(
                            "ASS subtitles will be supported in a future update."
                    );

                    return;
                }

                const url =
                        URL.createObjectURL(
                                file
                        );

                const track =
                        document.createElement(
                                "track"
                        );

                track.kind =
                        "subtitles";

                track.label =
                        file.name;

                track.src =
                        url;

                track.default =
                        false;

                video.appendChild(
                        track
                );

                const option =
                        document.createElement(
                                "option"
                        );

                option.value =
                        video.textTracks.length - 1;

                option.textContent =
                        file.name;

                selector.appendChild(
                        option
                );
            }
    );

    selector.addEventListener("change",
            function(){

                if(
                        this.value === "upload"
                ){

                    upload.click();

                    this.selectedIndex = 0;

                    return;
                }

                const tracks =
                        video.textTracks;

                for(
                        let i = 0;
                        i < tracks.length;
                        i++
                ){

                    tracks[i].mode =
                            "disabled";
                }

                if(
                        this.value === "off"
                ){

                    return;
                }

                tracks[
                        parseInt(
                                this.value
                        )
                ].mode =
                        "showing";
            }
    );
}

/*
==========================================
    HOTKEYS
==========================================
*/

function initializeHotkeys(){

    document.addEventListener(
            "keydown",
            function(event){

                const video =
                        document.getElementById(
                                "animePlayer"
                        );
                const activeElement =
                        document.activeElement;

                if(
                        activeElement
                        &&
                        activeElement.tagName === "SELECT"
                ){

                    activeElement.blur();
                }
              
                if(!video){

                    return;
                }
                
                showControls();
             
                switch(event.code){

                    case "Space":

                        event.preventDefault();

                        video.paused
                                ? video.play()
                                : video.pause();

                        break;

                    case "ArrowLeft":

                        video.currentTime =
                                Math.max(
                                        0,
                                        video.currentTime - 5
                                );

                        break;

                    case "ArrowRight":

                        video.currentTime =
                                Math.min(
                                        video.duration || Infinity,
                                        video.currentTime + 5
                                );

                        break;

                    case "ArrowUp":

                        video.volume =
                                Math.min(
                                        1,
                                        video.volume + 0.05
                                );

                        video.muted = false;

                        syncVolumeUI();

                        break;

                    case "ArrowDown":

                        video.volume =
                                Math.max(
                                        0,
                                        video.volume - 0.05
                                );

                        if(video.volume === 0){

                            video.muted = true;
                        }

                        syncVolumeUI();

                        break;

                    case "KeyM":

                        video.muted =
                                !video.muted;

                        syncVolumeUI();

                        break;

                    case "KeyF":

                        if(document.fullscreenElement){

                            document.exitFullscreen();
                        }
                        else{

                            document.getElementById(
                                    "playerWrapper"
                            ).requestFullscreen();
                        }

                        showControls();

                        break;
                }
            }
    );
}

/*
==========================================
    AUTO NEXT EPISODE
==========================================
*/

function initializeAutoNextEpisode(){

    const video =
            document.getElementById(
                    "animePlayer"
            );

    const nextEpisodeUrl =
            document.getElementById(
                    "nextEpisodeUrl"
            );

    if(
            !video
            ||
            !nextEpisodeUrl
    ){

        return;
    }

    video.addEventListener(
            "ended",
            function(){

                const url =
                        nextEpisodeUrl.value;

                if(
                        url
                        &&
                        url.trim() !== ""
                ){

                    window.location =
                            url;
                }
            }
    );
}