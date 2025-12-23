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
 *  StrongReference是Java的默认引用形式，使用时不需要显示定义。任何通过强引用所使用的对象不管系统资源有多紧张，Java GC都不会主动回收具有强引用的对象。
 */
public class StrongReferenceTest {

	public static int M = 1024*1024;

	public static void printlnMemory(String tag){
		Runtime runtime = Runtime.getRuntime();
		int M = StrongReferenceTest.M;
		System.out.println("\n"+tag+":");
		System.out.println(runtime.freeMemory()/M+"M(free)/" + runtime.totalMemory()/M+"M(total)");
	}
	
	public static void main(String[] args){
		StrongReferenceTest.printlnMemory("1.原可用内存和总内存");
		
		//实例化10M的数组并与strongReference建立强引用
		byte[] strongReference = new byte[10*StrongReferenceTest.M];
		StrongReferenceTest.printlnMemory("2.实例化10M的数组,并建立强引用");
		System.out.println("strongReference : "+strongReference);
		
		System.gc();
		StrongReferenceTest.printlnMemory("3.GC后");
		System.out.println("strongReference : "+strongReference);

		//strongReference = null;后,强引用断开了
		strongReference = null;
		StrongReferenceTest.printlnMemory("4.强引用断开后");
		System.out.println("strongReference : "+strongReference);
		
		System.gc();
		StrongReferenceTest.printlnMemory("5.GC后");
		System.out.println("strongReference : "+strongReference);
		}
}
