/**
 *    Copyright 2009-2025 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.cache.decorators;

import org.apache.ibatis.cache.Cache;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * 基于 java.lang.ref.WeakReference 的 Cache 实现类
 * 用 WeakReference 做缓存值，允许 JVM 自动回收缓存对象，同时尽量避免“缓存雪崩”。
 * 和SoftCache对比，二级缓存的“内存友好版本”，牺牲命中率，换取更低的 OOM 风险
 *
 * Weak Reference cache decorator.
 * Thanks to Dr. Heinz Kabutz for his guidance here.
 *
 * @author Clinton Begin
 */
public class WeakCache implements Cache {
  /**
   * 强引用的键的队列， 最近访问的 value 强引用队列
   */
  private final Deque<Object> hardLinksToAvoidGarbageCollection;
  /**
   * 被 GC 回收的 WeakEntry 集合。 被 GC 的 WeakReference 会进这个队列
   * 当 WeakReference 指向的对象被 GC 回收，JVM 会自动把这个 WeakReference 放进这个队列
   */
  private final ReferenceQueue<Object> queueOfGarbageCollectedEntries;
  /**
   * 装饰的 Cache 对象
   */
  private final Cache delegate;
  /**
   * {@link #hardLinksToAvoidGarbageCollection} 的大小，强引用数量上限（默认 256）
   */
  private int numberOfHardLinks;

  public WeakCache(Cache delegate) {
    this.delegate = delegate;
    this.numberOfHardLinks = 256;
    this.hardLinksToAvoidGarbageCollection = new LinkedList<>();
    this.queueOfGarbageCollectedEntries = new ReferenceQueue<>();
  }

  @Override
  public String getId() {
    return delegate.getId();
  }

  @Override
  public int getSize() {
    // 移除已经被 GC 回收的 WeakEntry
    removeGarbageCollectedItems();
    // size 前先清理垃圾
    // 保证 size 相对准确
    return delegate.getSize();
  }

  public void setSize(int size) {
    this.numberOfHardLinks = size;
  }

  @Override
  public void putObject(Object key, Object value) {
    removeGarbageCollectedItems();
    // 本类并不真正存数据，把缓存 value 变成 WeakReference，利用 JVM GC 自动回收不再被强引用的缓存对象，用一小段“强引用窗口”避免刚访问过的数据立刻被回收
    // 在这里指定的queueOfGarbageCollectedEntries，当WeakEntry 被 GC 回收时，会放进这个队列
    delegate.putObject(key, new WeakEntry(key, value, queueOfGarbageCollectedEntries));
  }

  @Override
  public Object getObject(Object key) {
    Object result = null;
    @SuppressWarnings("unchecked") // assumed delegate cache is totally managed by this cache
    WeakReference<Object> weakReference = (WeakReference<Object>) delegate.getObject(key);
    if (weakReference != null) {
      result = weakReference.get();
      if (result == null) {
        // 为空，从 delegate 中移除 。为空的原因是，意味着已经被 GC 回收
        delegate.removeObject(key);
      } else {
        // 非空，添加到 hardLinksToAvoidGarbageCollection 中，避免被 GC
        hardLinksToAvoidGarbageCollection.addFirst(result);
        if (hardLinksToAvoidGarbageCollection.size() > numberOfHardLinks) {
          // 超过上限，移除 hardLinksToAvoidGarbageCollection 的队尾
          hardLinksToAvoidGarbageCollection.removeLast();
        }
      }
    }
    return result;
  }

  @Override
  public Object removeObject(Object key) {
    removeGarbageCollectedItems();
    return delegate.removeObject(key);
  }

  @Override
  public void clear() {
    hardLinksToAvoidGarbageCollection.clear();
    removeGarbageCollectedItems();
    delegate.clear();
  }

  @Override
  public ReadWriteLock getReadWriteLock() {
    return null;
  }

  /**
   * 移除已经被 GC 回收的缓存
   */
  private void removeGarbageCollectedItems() {
    WeakEntry sv;
    while ((sv = (WeakEntry) queueOfGarbageCollectedEntries.poll()) != null) {
      delegate.removeObject(sv.key);
    }
  }

  private static class WeakEntry extends WeakReference<Object> {
    private final Object key;

    private WeakEntry(Object key, Object value, ReferenceQueue<Object> garbageCollectionQueue) {
      super(value, garbageCollectionQueue);
      this.key = key;
    }
  }

}
