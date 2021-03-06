/*
 * Copyright 2012 Takao Nakaguchi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.trie4j;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.trie4j.patricia.MapPatriciaTrie;

public abstract class AbstractMapTrieTest extends AbstractTrieTest {
	@Override
	protected MapTrie<Integer> createFirstTrie(){
		return new MapPatriciaTrie<Integer>();
	}

	protected MapTrie<Integer> buildSecondTrie(MapTrie<Integer> firstTrie){
		return firstTrie;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected final MapTrie<Integer> buildSecondTrie(Trie firstTrie){
		return buildSecondTrie((MapTrie<Integer>)firstTrie);
	}

	@Test
	public void test_MapTrie_get_1() throws Exception{
		String[] words = {"hello", "hi", "world", "happy", "haru"};
		Integer[] values = {0, 1, 2, 3, 4};
		MapTrie<Integer> trie = newMapTrie(words, values);
		for(int i = 0; i < words.length; i++){
			Assert.assertEquals(values[i], trie.get(words[i]));
		}
	}

	@Test
	public void test_MapTrie_get_2() throws Exception{
		String[] words = {"ab", "ac", "ba", "bc", "ca",};
		Integer[] values = {0, 1, 2, 3, 4};
		MapTrie<Integer> trie = newMapTrie(words, values);
		for(int i = 0; i < words.length; i++){
			Assert.assertEquals(values[i], trie.get(words[i]));
		}
	}

	@Test
	public void test_MapTrie_get_3_get_from_empty_trie() throws Exception{
		String[] words = {};
		Integer[] values = {};
		MapTrie<Integer> trie = newMapTrie(words, values);
		trie.get("hello");
	}

	@Test
	public void test_MapTrie_predictiveSearchEntries_1() throws Throwable{
		String[] keys = {"A", "AB", "ABC"};
		Integer[] vals = {1, 2, 3};
		MapTrie<Integer> trie = newMapTrie(keys, vals);
		Iterator<Map.Entry<String, Integer>> it = trie.predictiveSearchEntries("A").iterator();
		Set<Integer> values = new HashSet<>(Arrays.asList(vals));
		Assert.assertTrue(values.remove(it.next().getValue()));
		Assert.assertTrue(values.remove(it.next().getValue()));
		Assert.assertTrue(values.remove(it.next().getValue()));
		Assert.assertEquals(0, values.size());
	}

	protected MapTrie<Integer> newMapTrie(String[] words, Integer[] values){
		Assert.assertEquals(words.length, values.length);
		int n = words.length;
		MapTrie<Integer> trie = (MapTrie<Integer>)createFirstTrie();
		for(int i = 0; i < n; i++){
			trie.insert(words[i], values[i]);
		}
		return buildSecondTrie(trie);
	}
}
