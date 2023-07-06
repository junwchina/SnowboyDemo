package ai.kitt.snowboy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import ai.kitt.snowboy.audio.AudioDataSaver;
import ai.kitt.snowboy.audio.RecordingThread;

public class KeywordSpottingManager {

    public static final KeywordSpottingManager sharedInstance = new KeywordSpottingManager();
    private Context context;
    private KeywordSpottingCallback callback;
    private RecordingThread recordingThread;

    private KeywordSpottingManager() {
    }

    public void setup(Context context, KeywordSpottingCallback callback) {
        this.context = context;
        this.callback = callback;
        AppResCopy.copyResFromAssetsToSD(context);
        this.recordingThread = new RecordingThread(this.context, this.handle, new AudioDataSaver(this.context));
    }

    public void shutdown() {
    }

    public void start() {
        recordingThread.startRecording();
    }

    public void stop() {
        recordingThread.stopRecording();
    }

    @SuppressLint("HandlerLeak")
    public Handler handle = new Handler(Looper.getMainLooper(), msg -> {
        MsgEnum message = MsgEnum.getMsgEnum(msg.what);
        switch (message) {
            case MSG_ACTIVE:
                callback.onKeywordDetected();
                break;
            case MSG_INFO:
                break;
            case MSG_VAD_SPEECH:
                break;
            case MSG_VAD_NOSPEECH:
                break;
            case MSG_ERROR:
                break;
            default:
                break;
        }
        return true;
    });
}
