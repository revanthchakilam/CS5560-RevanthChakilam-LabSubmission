/**
 * Created by Mayanka on 20-Jul-15.
 * Reference : https://github.com/shekhargulati/day20-stanford-sentiment-analysis-demo
 */

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

import java.util.Properties;

public class SentimentAnalyzer {

    public TweetWithSentiment findSentiment(String line) {

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        int mainSentiment = 0;
        if (line != null && line.length() > 0) {
            int longest = 0;
            Annotation annotation = pipeline.process(line);
            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence.get(SentimentCoreAnnotations.AnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                String partText = sentence.toString();
                if (partText.length() > longest) {
                    mainSentiment = sentiment;
                    longest = partText.length();
                }

            }
        }
        if (mainSentiment == 2 || mainSentiment > 4 || mainSentiment < 0) {
            return null;
        }

        TweetWithSentiment tweetWithSentiment = new TweetWithSentiment(line, toCss(mainSentiment));
        return tweetWithSentiment;

    }
    public int findSentiment(String line,int i) {
    Properties props = new Properties();
            props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
            StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
            int mainSentiment = 0;
            if (line != null && line.length() > 0) {
                int longest = 0;
                Annotation annotation = pipeline.process(line);
                for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                    Tree tree = sentence.get(SentimentCoreAnnotations.AnnotatedTree.class);
                    int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                    String partText = sentence.toString();
                    if (partText.length() > longest) {
                        mainSentiment = sentiment;
                        longest = partText.length();
                    }

                }
            }
            if (mainSentiment == 2 || mainSentiment > 4 || mainSentiment < 0) {
                return -1;
            }

            return mainSentiment;


    }

    private String toCss(int sentiment) {
        switch (sentiment) {
            case 0:
                return "sentiment : very negative";
            case 1:
                return "sentiment : negative";
            case 2:
                return "sentiment : neutral";
            case 3:
                return "sentiment : positive";
            case 4:
                return "sentiment : very positive";
            default:
                return "";
        }
    }

    public static void main(String[] args) {
        SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();
        TweetWithSentiment tweetWithSentiment = sentimentAnalyzer
                .findSentiment("Kumble breaks Kapil's record\n" +
                        "\n" +
                        "First Test, Dhaka, day one (stumps): Bangladesh 184 all out v India\n" +
                        "\n" +
                        "Kumble overtook the mark set by Kapil Dev when he had Mohammad Rafique lbw. And he followed up with a wicket next ball before Bangladesh were bowled out for 184 in 58 overs in Dhaka. After the first session was lost to rain, Irfan Pathan took five wickets to reduce the hosts to 106-7 before Mohammad Ashraful dug in. Ashraful ended unbeaten on 60, having hit six fours and faced 135 balls. Kumble had a chance of a hat-trick after removing Tapash Baisya via a catch at first slip but Mashrafe Mortaza safely defended the fifth ball of his 12th over. But a run out ended the innings not long afterwards.\n" +
                        "\n" +
                        "India did not get chance to begin their reply as openers Virender Sehwag and Gautam Gambhir were immediately offered the light on stepping to the wicket. India won the toss and Pathan soon got stuck into the top order, dismissing Javed Omar lbw in his second over with one that nipped back. Nafis Iqbal and Rajin Saleh were also ajudged lbw by umpire Jeremy Lloyds off consecutive balls in Pathan's fifth over. Captain Habibul Bashar then pulled Zaheer Khan straight to square leg and when the same bowler had Khaled Mashud caught behind, Bangladesh were 50-5 after just 16 overs. Ashraful, largely in partnership with Rafique (47), did his best to build a recovery but India will expect to amass a huge lead on Saturday. Kumble is now fifth in the all-time list. Aged 34, he may still be able to reach the 500-mark, passed by only three men. Fellow leg-spinner Shane Warne tops the list with 552 wickets.\n" +
                        "\n" +
                        "Habibul Bashar (capt), Nafis Iqbal, Javed Omar, Mohammad Ashraful, Rajin Saleh, Khaled Mashud (wkt), Mushfiqur Rahman, Mohammad Rafique, Tapash Baisya, Mashrafe bin Mortaza, Manjurul Islam Rana.\n" +
                        "\n" +
                        "S Ganguly (capt), V Sehwag, G Gambhir, S Tendulkar, R Dravid, M Kaif, D Karthik (wkt), I Pathan, A Kumble, Harbhajan Singh, Z Khan.\n");
        System.out.println("Sentiment Analysis output:");
        System.out.println(tweetWithSentiment);

    }
}
