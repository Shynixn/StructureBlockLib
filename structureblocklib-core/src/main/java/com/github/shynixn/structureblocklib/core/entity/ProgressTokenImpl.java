package com.github.shynixn.structureblocklib.core.entity;

import com.github.shynixn.structureblocklib.api.entity.ProgressToken;
import com.github.shynixn.structureblocklib.api.service.ProxyService;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.function.Consumer;

/*
 *Basic Generic implementation of the progress token.
 *
 * @param <T> Type parameter.
 */
public class ProgressTokenImpl<T> implements ProgressToken<T> {
    private final Set<Consumer<Double>> progressConsumers = new HashSet<>();
    private final ProxyService proxyService;
    private CompletionStage<T> item;
    private volatile boolean cancelled = false;

    public ProgressTokenImpl(ProxyService proxyService) {
        this.proxyService = proxyService;
    }

    /**
     * Calls the progress consumers with the progress update.
     *
     * @param progress update.
     */
    public void progress(double progress) {
        progressConsumers.forEach(e -> e.accept(progress));
    }

    /**
     * Sets the completion stage.
     *
     * @param item {@link CompletionStage}
     */
    public void setCompletionStage(CompletionStage<T> item) {
        this.item = item;
    }

    /**
     * Gets the already started {@link CompletionStage} which completes with the result.
     * This instance can be used to execute calls after the progress has finished
     * and listens to exceptions thrown during the progress.
     *
     * @return NotNull instance of {@link CompletionStage}.
     */
    @Override
    public CompletionStage<T> getCompletionStage() {
        return item;
    }

    /**
     * Adds a new consumer to the progressToken which gets called each time the
     * progress percentage from 0.0(0%) to 1.0 (100%) changes. Is at least called
     * once for 0.0 and once for 1.0.
     *
     * @param progress progress.
     * @return This instance.
     */
    @Override
    public ProgressToken<T> onProgress(Consumer<Double> progress) {
        progressConsumers.add(progress);
        return this;
    }

    /**
     * Adds a new consumer to the progressToken which gets called if the
     * the progress fails with an exception.
     *
     * @param exception exception.
     * @return This instance.
     */
    @Override
    public ProgressToken<T> onException(Consumer<Throwable> exception) {
        this.item.exceptionally(e -> {
            exception.accept(e);
            return null;
        });

        return this;
    }

    /**
     * Adds a new consumer to the progressToken which gets called once the
     * the progress completes.
     *
     * @param result result.
     * @return This instance.
     */
    @Override
    public ProgressToken<T> onResult(Consumer<T> result) {
        this.item.thenAcceptAsync(result, proxyService.getSyncExecutor());
        return this;
    }

    /**
     * Is the progress cancelled or not.
     *
     * @return True if cancelled. False if not.
     */
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * Cancels the progress if it has not finished yet.
     */
    @Override
    public void cancel() {
        this.cancelled = true;
    }
}
