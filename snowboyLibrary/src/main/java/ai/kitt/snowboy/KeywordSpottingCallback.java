package ai.kitt.snowboy;

public interface KeywordSpottingCallback {

    void onListening(boolean result);

    void onKeywordDetected();

}
