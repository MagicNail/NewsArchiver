package articleMatcher;

import java.util.*;

import dataProcessing.Article;

public class ArticleComparator implements Comparator<Article>{
	
	private Article article;
	private Set<String> keyWordsInPhrases;
	private Set<String> keyWordsInContent;
	
	public ArticleComparator(Article a){
		article = a;
		keyWordsInPhrases = new HashSet<String>();
		keyWordsInContent = new HashSet<String>();
		Scanner sc = new Scanner(a.getDescription());
		String next;
		while(sc.hasNext()) if(TrieSearch.phrases.contains(next = sc.next())) keyWordsInPhrases.add(next);
		sc = new Scanner(a.getContent());
		while(sc.hasNext()) if(TrieSearch.phrases.contains(next = sc.next())) keyWordsInContent.add(next);
	}

	@Override
	public int compare(Article a1, Article a2) {
		int count1 = 0;
		if(a1.getCategory().equals(article.getCategory())) count1 += 4;
		Scanner sc1 = new Scanner(a1.getDescription());
		while(sc1.hasNext()) if(keyWordsInPhrases.contains(sc1.next())) count1 += 2;
		sc1 = new Scanner(a1.getContent());
		while(sc1.hasNext()) if(keyWordsInContent.contains(sc1.next())) count1 += 1;
		int count2 = 0;
		if(a2.getCategory().equals(article.getCategory())) count2 += 4;
		Scanner sc2 = new Scanner(a2.getDescription());
		while(sc2.hasNext()) if(keyWordsInPhrases.contains(sc2.next())) count2 += 2;
		sc2 = new Scanner(a2.getContent());
		while(sc2.hasNext()) if(keyWordsInContent.contains(sc2.next())) count2 += 1;
		
		return count2-count1;
	}
}
