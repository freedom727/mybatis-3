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
package org.apache.ibatis.cache.decorators.gc;

/**
 * 如果一个对象只具有弱引用，无论内存充足与否，Java GC后对象如果只有弱引用将会被自动回收。
 */

import java.lang.ref.WeakReference;

public class WeakReferenceTest {
	
	public static int M = 1024*1024;
	
	public static void printlnMemory(String tag){
		Runtime runtime = Runtime.getRuntime();
		int M = WeakReferenceTest.M;
		System.out.println("\n"+tag+":");
		System.out.println(runtime.freeMemory()/M+"M(free)/" + runtime.totalMemory()/M+"M(total)");
	}
	
	public static void main(String[] args){  
		WeakReferenceTest.printlnMemory("1.原可用内存和总内存");

		//创建弱引用
		WeakReference<Object> weakRerference = new WeakReference<>(new byte[10 * WeakReferenceTest.M]);
		WeakReferenceTest.printlnMemory("2.实例化10M的数组,并建立弱引用");
		System.out.println("weakRerference.get() : "+weakRerference.get());
		
		System.gc();
		StrongReferenceTest.printlnMemory("3.GC后");
		System.out.println("weakRerference.get() : "+weakRerference.get());
	}   
}
