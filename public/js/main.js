window.onload = function() {
	if (!('webkitSpeechRecognition' in window) && !('SpeechRecognition' in window)) {
		alert("This browser does not support speech recognition.");
	} else {
		var SpeechRecognition = SpeechRecognition || webkitSpeechRecognition;
		var recognition = new SpeechRecognition();
		var interim_span = document.querySelector('#interim');
		var final_span = document.querySelector('#final');

		final_transcript = '';
		recognition.lang = 'en-US';// select_dialect.value;
		recognition.continuous = true;
		recognition.interimResults = true;
		recognition.start();

		recognition.onstart = function() {

		}
		recognition.onresult = function(event) {
			var interim_transcript = '';

			for (var i = event.resultIndex; i < event.results.length; ++i) {
				if (event.results[i].isFinal) {
					final_transcript += event.results[i][0].transcript;
				} else {
					interim_transcript += event.results[i][0].transcript;
				}
			}
			final_transcript = capitalize(final_transcript);
			final_span.innerHTML = final_transcript;
			interim_span.innerHTML = interim_transcript;
		}
		recognition.onend = function() {

		}
		recognition.onerror = function(event) {
			console.log('Error occurred in recognition: ' + event.error);
		}
	}

	// https://github.com/cwilso/volume-meter/
	window.AudioContext = window.AudioContext || window.webkitAudioContext;
    audioContext = new AudioContext();
    vol_span = document.querySelector('#volume');
    clip_span = document.querySelector('#clipping');

    try {
        navigator.getUserMedia = 
        	navigator.getUserMedia ||
        	navigator.webkitGetUserMedia ||
        	navigator.mozGetUserMedia;

        navigator.getUserMedia(
        {
            "audio": {
                "mandatory": {
                    "googEchoCancellation": "false",
                    "googAutoGainControl": "false",
                    "googNoiseSuppression": "false",
                    "googHighpassFilter": "false"
                },
                "optional": []
            },
        }, gotStream, didntGetStream);
    } catch (e) {
        alert('getUserMedia threw exception :' + e);
    }
}

function didntGetStream(err) {
    alert('Stream generation failed.\nError: ' + err);
}

var mediaStreamSource = null;

function gotStream(stream) {
    mediaStreamSource = audioContext.createMediaStreamSource(stream);
    meter = createAudioMeter(audioContext);
    mediaStreamSource.connect(meter);
    updateVolume();
}

function updateVolume(time) {
	clip_span.innerHTML = meter.checkClipping();
	vol_span.innerHTML = meter.volume;

	window.requestAnimationFrame(updateVolume);
}

function capitalize(str) {
    return str.replace(/\w\S*/g, function(txt){return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();});
}