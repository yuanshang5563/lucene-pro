package org.ys.lucene;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class TestLucene {
	private static final String PATH = "D:\\MyEclipse2014Workspace\\lucene-pro\\src\\main\\index_01";
	
	@Test
	public void testCreateDir(){
		try {
			String doc1 = "hello world";
			String doc2 = "hello java world";
			String doc3 = "hello lucene world";
			Directory directory = FSDirectory.open(new File(PATH));
			Analyzer analyzer = new IKAnalyzer();
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_4,analyzer);
			IndexWriter indexWriter = new IndexWriter(directory,indexWriterConfig);
			Document document1 = new Document();
			document1.add(new TextField("id","1",Store.YES));
			document1.add(new TextField("title","title1",Store.YES));
			document1.add(new TextField("content",doc1,Store.YES));
			Document document2 = new Document();
			document2.add(new TextField("id","2",Store.YES));
			document2.add(new TextField("title","title2",Store.YES));
			document2.add(new TextField("content",doc2,Store.YES));
			Document document3 = new Document();
			document3.add(new TextField("id","3",Store.YES));
			document3.add(new TextField("title","title3",Store.YES));
			document3.add(new TextField("content",doc3,Store.YES));
			indexWriter.addDocument(document1);
			indexWriter.addDocument(document2);
			indexWriter.addDocument(document3);
			indexWriter.commit();
			indexWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testReadDir(){
		try {
			Directory directory = FSDirectory.open(new File(PATH));
			IndexReader IndexReader = DirectoryReader.open(directory);
			IndexSearcher indexSearcher = new IndexSearcher(IndexReader);
			Query query = new TermQuery(new Term("content","world"));
			TopDocs topDocs = indexSearcher.search(query, 100);
			System.out.println("---------命中数：-----" + topDocs.totalHits);
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (ScoreDoc scoreDoc : scoreDocs) {
				Document doc = indexSearcher.doc(scoreDoc.doc);
				System.out.println("-----id----->"+doc.get("id"));
				System.out.println("-----title----->"+doc.get("title"));
				System.out.println("-----content----->"+doc.get("content"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
