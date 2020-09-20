package com.github.shynixn.structureblocklib.api.entity;

import java.util.concurrent.CompletionStage;
import java.util.function.Consumer;

/*
 * ProgressToken to manage a long running task.
 *
 * @param <T> Type of result.
 */
public interface ProgressToken<T> {
    /**
     * Gets the already started {@link CompletionStage} which completes with the result.
     * This instance can be used to execute calls after the progress has finished
     * and listens to exceptions thrown during the progress.
     *
     * @return NotNull instance of {@link CompletionStage}.
     */
    CompletionStage<T> getCompletionStage();

    /**
     * Adds a new consumer to the progressToken which gets called each time the
     * progress percentage from 0.0(0%) to 1.0 (100%) changes. Is at least called
     * once for 0.0 and once for 1.0.
     *
     * @param progress {@link Consumer}.
     * @return This instance.
     */
    ProgressToken<T> onProgress(Consumer<Double> progress);

    /**
     * Adds a new consumer to the progressToken which gets called if the
     * the progress fails with an exception.
     *
     * @param exception {@link Consumer}.
     * @return This instance.
     */
    ProgressToken<T> onException(Consumer<Throwable> exception);

    /**
     * Adds a new consumer to the progressToken which gets called once the
     * the progress completes.
     *
     * @param result{@link Consumer}.
     * @return This instance.
     */
    ProgressToken<T> onResult(Consumer<T> result);

    /**
     * Is the progress cancelled or not.
     *
     * @return True if cancelled. False if not.
     */
    boolean isCancelled();

    /**
     * Cancels the progress if it has not finished yet.
     */
    void cancel();
}
